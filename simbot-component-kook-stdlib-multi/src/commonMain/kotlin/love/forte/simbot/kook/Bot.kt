/*
 * Copyright (c) 2023. ForteScarlet.
 *
 * This file is part of simbot-component-kook.
 *
 * simbot-component-kook is free software: you can redistribute it and/or modify it under the terms of
 * the GNU Lesser General Public License as published by the Free Software Foundation,
 * either version 3 of the License, or (at your option) any later version.
 *
 * simbot-component-kook is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY;
 * without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.
 * See the GNU Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public License along with simbot-component-kook,
 * If not, see <https://www.gnu.org/licenses/>.
 */

package love.forte.simbot.kook

import io.ktor.client.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.sync.Mutex
import love.forte.simbot.JvmSuspendTrans
import love.forte.simbot.JvmSuspendTransProperty
import love.forte.simbot.kook.api.KookApiRequestor
import love.forte.simbot.kook.api.user.GetMeApi
import love.forte.simbot.kook.api.user.Me
import love.forte.simbot.kook.api.user.OfflineApi
import love.forte.simbot.kook.event.Event
import love.forte.simbot.kook.event.EventExtra
import love.forte.simbot.kook.event.Signal
import kotlin.coroutines.CoroutineContext
import kotlin.jvm.JvmStatic
import kotlin.jvm.JvmSynthetic

/**
 * 一个平台 KOOK Bot。
 *
 * 针对某个平台的 KOOK BOT 类型，应当由 [Bot] 实现并由
 * Kotlin 多平台决定具体细节。
 *
 * [PlatformBot] 不应直接被使用，请使用 [Bot]。
 *
 * @see Bot
 *
 * @author ForteScarlet
 */
public expect interface PlatformBot : CoroutineScope, KookApiRequestor {

    /**
     * 添加一个事件处理器。所有事件处理器会在每次触发的时候按照添加顺序依次进行处理。
     *
     * @param processorType 事件处理器类型。默认为 [ProcessorType.NORMAL]。
     * @param processor 事件处理器。receiver 为本次接收到的事件信令 [Signal.Event] 中的 [data][Signal.Event.d]，参数 raw 则为接收到的原始事件信令的JSON字符串。
     */
    @JvmSynthetic
    public fun processor(
        processorType: ProcessorType = ProcessorType.NORMAL,
        processor: suspend Event<*>.(raw: String) -> Unit
    )
}

/**
 * 一个 KOOK Bot。
 *
 * [Bot] 承载了通过 `WebSocket` 的方式与 KOOK 服务器连接并订阅事件的能力，
 * 以及通过各种 [KOOK HTTP API][love.forte.simbot.kook.api.KookApi] 进行功能交互的能力。
 *
 * ### 日志
 *
 * [Bot] 中主要提供了两个日志命名：
 * - `love.forte.simbot.kook.bot.${ticket.clickId}`
 * - `love.forte.simbot.kook.event.${ticket.clickId}`
 *
 * (其中 `ticket.clickId` 对应了当前 bot 的 [Bot.ticket] 中的实际信息)
 *
 * 其中，前缀为 `love.forte.simbot.kook.event` 的日志命名会主要输出与事件有较大关系的信息，例如每次收到的事件原始JSON等。
 * 而其他的日志则主要由前缀为 `love.forte.simbot.kook.bot` 的日志命名输出。
 *
 * 大部分日志都是 `DEBUG` 或 `TRACE` 级别的。
 *
 * @author ForteScarlet
 */
public interface Bot : PlatformBot, CoroutineScope, KookApiRequestor {
    override val coroutineContext: CoroutineContext

    /**
     * 此 bot 的票据信息。
     */
    public val ticket: Ticket

    /**
     * 当前 bot 所使用的配置信息。
     */
    public val configuration: BotConfiguration

    /**
     * 当前bot所使用的 [HttpClient] 实例。
     *
     * 此实例代表的用于进行API请求（xxxRequest）的http client。
     */
    public val apiClient: HttpClient

    /**
     * @see apiClient
     */
    override val client: HttpClient
        get() = apiClient

    /**
     * Bot 是否处于活跃状态。
     *
     * @see kotlinx.coroutines.Job.isActive
     */
    public val isActive: Boolean

    /**
     * Bot 是否至少启动过一次。
     */
    public val isStarted: Boolean

    /**
     * 当前bot作为用户时的信息。
     *
     * bot必须至少启动一次或执行过一次 [me] 才能获取到此信息。
     *
     * @throws IllegalStateException 如果bot未启动或未执行过 [me] 则会抛出此异常。
     */
    public val botUserInfo: Me


    /**
     * 查询 bot 作为用户的信息。
     *
     * @see GetMeApi
     */
    @JvmSuspendTransProperty
    public suspend fun me(): Me


    /**
     * 让 bot 下线。
     *
     * @see OfflineApi
     */
    @JvmSuspendTrans
    public suspend fun offline()

    /**
     * 启动此bot，即连接到 ws 服务器并订阅事件。
     *
     * 如果 bot 已经被启动，则关闭旧连接并重新连接。
     *
     * [start] 会由 [Mutex] 进行同步，同一时间只会有一个启动流程在执行。
     *
     * 如果启动过程中出现任何异常则会关闭当前 Bot。
     *
     * @throws kotlinx.coroutines.CancellationException Bot 已经被关闭时 ([isActive] == false)
     * @throws IllegalStateException bot 启动失败
     *
     */
    @JvmSuspendTrans
    public suspend fun start() {
        start(true)
    }

    /**
     * 启动此bot，即连接到 ws 服务器并订阅事件。
     *
     * 如果 bot 已经被启动，则关闭旧连接并重新连接。
     *
     * [start] 会由 [Mutex] 进行同步，同一时间只会有一个启动流程在执行。
     *
     * 当 [closeBotOnFailure] 为 true 时，如果启动过程中出现任何异常则会关闭当前 Bot。
     *
     * @see start
     * @param closeBotOnFailure 为 true 时，如果启动过程中出现任何异常则会关闭当前 Bot。
     * @throws kotlinx.coroutines.CancellationException Bot 已经被关闭时 ([isActive] == false)
     * @throws IllegalStateException bot 启动失败
     *
     */
    @JvmSuspendTrans
    public suspend fun start(closeBotOnFailure: Boolean)

    /**
     * 挂起 bot 直到其被 [关闭][close]。
     */
    @JvmSuspendTrans(asyncBaseName = "asFuture", asyncSuffix = "")
    public suspend fun join()

    /**
     * 关闭此 bot 。
     * bot 被关闭后 [isActive] 将会开始得到 `false`。
     *
     * @see kotlinx.coroutines.Job.cancel
     */
    public fun close()
}

/**
 * Start and return bot self.
 *
 * @see Bot.start
 */
public suspend fun Bot.alsoStart(): Bot = also { start() }

/**
 * Close bot and join.
 *
 * @see Bot.close
 * @see Bot.join
 */
public suspend fun Bot.closeAndJoin() {
    close()
    join()
}

/**
 * Start bot and join.
 *
 * @see Bot.start
 * @see Bot.join
 */
public suspend fun Bot.startAndJoin() {
    start()
    join()
}

/**
 * Bot 所使用的部分权限"票据"。
 *
 * 这些信息可以在 [KOOK Bot 应用](https://developer.kookapp.cn/app/index)
 * 中选择某个具体的 Bot 进行查看、配置。
 *
 * @author ForteScarlet
 */
public interface Ticket {
    /**
     * Client Id.
     */
    public val clickId: String

    /**
     * Token.
     */
    public val token: String

    /**
     * Type of token.
     */
    public val type: TokenType

    public companion object {

        /**
         * 得到 [TokenType.BOT] 类型的、用于 WebSocket 连接的票据信息。
         */
        @JvmStatic
        public fun botWsTicket(clickId: String, token: String): Ticket = BotWsTicket(clickId, token)
    }
}


private data class BotWsTicket(override val clickId: String, override val token: String) : Ticket {
    override val type: TokenType
        get() = TokenType.BOT
}


/**
 * 针对一个具体的 [EventExtra] 类型进行监听。
 *
 * @param EX 事件内容 [Event.extra] 的具体类型。
 *
 */
public inline fun <reified EX : EventExtra> Bot.processor(crossinline processor: suspend Event<EX>.(raw: String) -> Unit) {
    processor { raw ->
        if (extra is EX) {
            @Suppress("UNCHECKED_CAST")
            processor.invoke(this as Event<EX>, raw)
        }
    }
}

/**
 * 针对一个 [Event.type] 目标进行监听。
 *
 * @param type 事件类型。
 */
public inline fun Bot.processor(type: Event.Type, crossinline processor: suspend Event<*>.(raw: String) -> Unit) {
    processor { raw ->
        if (this.type == type) {
            processor(raw)
        }
    }
}

/**
 * [Bot] 中的事件处理器类型。
 */
public enum class ProcessorType {
    /**
     * 前置类型。所有前置事件处理器会在每次触发的时候优先于 [普通类型][NORMAL] 的事件处理器进行处理，
     * 并且所有前置处理器**不会**异步执行。
     *
     * 前置类型的事件处理器应当尽可能的快速执行完毕。
     */
    PREPARE,

    /**
     * 普通类型。所有事件处理器会在每次触发的时候按照添加顺序依次进行处理，
     * 但是以事件为单位，整个流程可能是异步的。
     *
     * [NORMAL] 最终是否会异步处理会受配置项 [BotConfiguration.isNormalEventProcessAsync]
     * 的影响，默认为异步。如果此配置为 `false` 则 [NORMAL] 的实际表现效果将会与 [PREPARE] 类似，
     * 只是优先级低于 [PREPARE]。
     */
    NORMAL;
}
