/*
 * Copyright (c) 2022-2023. ForteScarlet.
 *
 * This file is part of simbot-component-kook.
 *
 * simbot-component-kook is free software: you can redistribute it and/or modify it under the terms of the GNU Lesser General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.
 *
 * simbot-component-kook is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public License along with simbot-component-kook, If not, see <https://www.gnu.org/licenses/>.
 */

package love.forte.simbot.component.kook.event

import love.forte.plugin.suspendtrans.annotation.JvmAsync
import love.forte.plugin.suspendtrans.annotation.JvmBlocking
import love.forte.simbot.ExperimentalSimbotApi
import love.forte.simbot.ID
import love.forte.simbot.Timestamp
import love.forte.simbot.component.kook.KookChannel
import love.forte.simbot.component.kook.KookChannelCategory
import love.forte.simbot.component.kook.KookGuild
import love.forte.simbot.component.kook.KookGuildMember
import love.forte.simbot.component.kook.message.KookChannelMessageDetailsContent.Companion.toContent
import love.forte.simbot.component.kook.util.requestDataBy
import love.forte.simbot.definition.ChannelInfoContainer
import love.forte.simbot.event.*
import love.forte.simbot.kook.api.message.MessageViewRequest
import love.forte.simbot.kook.event.system.channel.*
import love.forte.simbot.message.MessageContent
import love.forte.simbot.message.doSafeCast

/**
 *
 * Kook 系统事件中与频道变更事件相关的事件针对simbot标准事件的实现基准类。
 *
 * 涉及的相关 Kook **原始**事件有：
 * - [AddedChannelEvent]
 * - [UpdatedChannelEvent]
 * - [DeletedChannelEvent]
 * - [UnpinnedMessageEvent]
 * - [PinnedMessageEvent]
 *
 *
 * @see ChangedEvent
 *
 * @author ForteScarlet
 */
@BaseEvent
public abstract class KookChannelChangedEvent<out Body : ChannelEventExtraBody> :
    KookSystemEvent<Body>(), ChangedEvent {
    
    /**
     * 此事件涉及的频道所属的频道服务器。
     */
    @JvmBlocking(asProperty = true, suffix = "")
    @JvmAsync(asProperty = true)
    abstract override suspend fun source(): KookGuild
    
    //// Impl
    
    
    override val changedTime: Timestamp
        get() = sourceEvent.msgTimestamp
    
    abstract override val key: Event.Key<out KookChannelChangedEvent<*>>
    
    public companion object Key : BaseEventKey<KookChannelChangedEvent<*>>(
        "kook.channel_changed", KookSystemEvent, ChangedEvent
    ) {
        override fun safeCast(value: Any): KookChannelChangedEvent<*>? = doSafeCast(value)
    }
}

/**
 * 某频道服务器中新增了一个频道后的事件。
 *
 * @see IncreaseEvent
 * @see AddedChannelEvent
 */
public abstract class KookAddedChannelChangedEvent :
    KookChannelChangedEvent<AddedChannelExtraBody>(),
    IncreaseEvent {
    
    /**
     * 操作者，即此频道的创建者。
     *
     * 创建者获取自 [AddedChannelExtraBody.masterId],
     * 如果在此事件实例化的过程中此人离开频道服务器导致内置缓存被清理，则可能得到null。
     *
     */
    public abstract val operator: KookGuildMember?
    
    /**
     * 增加的频道。
     */
    @JvmBlocking(asProperty = true, suffix = "")
    @JvmAsync(asProperty = true)
    abstract override suspend fun after(): KookChannel
    
    
    override val key: Event.Key<out KookAddedChannelChangedEvent>
        get() = Key
    
    public companion object Key : BaseEventKey<KookAddedChannelChangedEvent>(
        "kook.added_channel_changed", KookChannelChangedEvent, IncreaseEvent
    ) {
        override fun safeCast(value: Any): KookAddedChannelChangedEvent? = doSafeCast(value)
    }
}

/**
 * 某频道发生了信息变更。
 *
 * _Note: 无法获取变更前的信息，[before] 恒为null_
 *
 * @see UpdatedChannelEvent
 */
public abstract class KookUpdatedChannelChangedEvent :
    KookChannelChangedEvent<UpdatedChannelExtraBody>(),
    ChangedEvent,
    ChannelInfoContainer {
    
    /**
     * 频道信息。
     */
    @JvmBlocking(asProperty = true, suffix = "")
    @JvmAsync(asProperty = true)
    abstract override suspend fun channel(): KookChannel
    
    /**
     * 无法获取变更前的信息，[before] 恒为null。
     */
    @JvmBlocking(asProperty = true, suffix = "")
    @JvmAsync(asProperty = true)
    override suspend fun before(): UpdatedChannelExtraBody? = null
    
    /**
     * 信息变更内容。
     */
    @JvmBlocking(asProperty = true, suffix = "")
    @JvmAsync(asProperty = true)
    override suspend fun after(): UpdatedChannelExtraBody = sourceBody
    
    override val key: Event.Key<out KookUpdatedChannelChangedEvent>
        get() = Key
    
    public companion object Key : BaseEventKey<KookUpdatedChannelChangedEvent>(
        "kook.updated_channel_changed", KookChannelChangedEvent, ChangedEvent
    ) {
        override fun safeCast(value: Any): KookUpdatedChannelChangedEvent? = doSafeCast(value)
    }
}

/**
 * 某频道被删除的事件。
 *
 * ## _ExperimentalSimbotApi_
 * 此事件未来会细化为区分"类型频道（[KookChannelCategory]）" 和 "普通频道（[KookChannel]）"的相关事件，
 * 因此目前此事件的定义仅供参考，未来会发生变更，且不保证变更兼容性。
 *
 * @see DeletedChannelEvent
 */
@ExperimentalSimbotApi
public abstract class KookDeletedChannelChangedEvent :
    KookChannelChangedEvent<DeletedChannelExtraBody>(),
    DecreaseEvent {
    
    /**
     * 被删除的频道。
     */
    @JvmBlocking(asProperty = true, suffix = "")
    @JvmAsync(asProperty = true)
    abstract override suspend fun before(): KookChannel
    
    
    /**
     * 始终为null。
     */
    @JvmBlocking(asProperty = true, suffix = "")
    @JvmAsync(asProperty = true)
    override suspend fun after(): KookChannel? = null
    
    
    override val key: Event.Key<out KookDeletedChannelChangedEvent>
        get() = Key
    
    public companion object Key : BaseEventKey<KookDeletedChannelChangedEvent>(
        "kook.deleted_channel_changed", KookChannelChangedEvent, DecreaseEvent
    ) {
        
        override fun safeCast(value: Any): KookDeletedChannelChangedEvent? = doSafeCast(value)
    }
}


/**
 * 与频道消息置顶相关的事件。
 * 涉及的原始事件有：
 * - [PinnedMessageEvent]
 * - [UnpinnedMessageEvent]
 *
 * 此事件为 [ChangedEvent], 事件源为发生事件的频道服务器，变动主体为被设置为置顶消息 **的ID**。
 * 由于事件无法确定变更前的消息，因此此事件的实现子事件中，[before] 和 [after] 只可能有一个不为null。
 *
 * 如果你只关心相关消息的ID，可以直接使用 [msgId] 属性获取。
 *
 */
@BaseEvent
public abstract class KookMessagePinEvent<Body : ChannelEventExtraBody> :
    KookChannelChangedEvent<Body>(),
    ChangedEvent, ChannelInfoContainer {
    
    /**
     * 此事件涉及的频道信息。
     */
    @JvmBlocking(asProperty = true, suffix = "")
    @JvmAsync(asProperty = true)
    abstract override suspend fun channel(): KookChannel
    
    
    /**
     * 此事件涉及的操作者。会通过 [operatorId] 获取。
     *
     * 假若在此事件触发前的瞬间此人离开频道，则可能造成无法获取的情况。
     */
    public abstract val operator: KookGuildMember?
    
    /**
     * 涉及消息的ID
     */
    public abstract val msgId: ID
    
    /**
     * 操作人ID
     */
    public abstract val operatorId: ID
    
    /**
     * 涉及频道ID
     */
    public abstract val channelId: ID
    
    /**
     * 变更前ID。如果此事件是 [KookUnpinnedMessageEvent], 则有值，否则为null。
     * 有值时同 [msgId].
     */
    @JvmBlocking(asProperty = true, suffix = "")
    @JvmAsync(asProperty = true)
    abstract override suspend fun before(): ID?
    
    /**
     * 变更后ID。如果此事件是 [KookUnpinnedMessageEvent], 则有值，否则为null。
     * 有值时同 [msgId].
     */
    @JvmBlocking(asProperty = true, suffix = "")
    @JvmAsync(asProperty = true)
    abstract override suspend fun after(): ID?
    
    
    //// Api
    
    /**
     * 通过 [msgId] 查询这条被置顶的消息。
     */
    @JvmBlocking
    @JvmAsync
    public suspend fun queryMsg(): MessageContent {
        val messageView = MessageViewRequest.create(msgId).requestDataBy(bot)
        return messageView.toContent(bot)
    }
    
    override val key: Event.Key<out KookMessagePinEvent<*>>
        get() = Key
    
    public companion object Key : BaseEventKey<KookMessagePinEvent<*>>(
        "kook.message_pin", KookChannelChangedEvent, ChangedEvent
    ) {
        override fun safeCast(value: Any): KookMessagePinEvent<*>? = doSafeCast(value)
    }
}


/**
 *
 * 新消息置顶事件。此事件的body也属于 [ChannelEventExtraBody] 类型的自类型，
 * 代表一个新的消息被设置为了目标频道的置顶消息。
 *
 * 此事件为 [ChangedEvent], 事件源为发生事件的频道服务器，变动主体为被设置为置顶消息 **的ID**。
 * 由于事件无法确定变更前的消息，因此只能获取到 **变更后的**，而 [before] 恒为null。
 *
 * @see ChannelEventExtraBody
 * @see PinnedMessageEvent
 */
public abstract class KookPinnedMessageEvent :
    KookMessagePinEvent<PinnedMessageExtraBody>() {
    
    /**
     * 涉及消息的ID。
     *
     */
    override val msgId: ID
        get() = sourceBody.msgId
    
    
    /**
     * 操作者ID。
     */
    override val operatorId: ID
        get() = sourceBody.operatorId
    
    /**
     * 频道ID。
     */
    override val channelId: ID
        get() = sourceBody.channelId
    
    /**
     * 始终为null。
     */
    @JvmSynthetic
    override suspend fun before(): ID? = null
    
    /**
     * 同 [msgId].
     */
    @JvmSynthetic
    override suspend fun after(): ID = msgId
    
    override val key: Event.Key<out KookPinnedMessageEvent>
        get() = Key
    
    public companion object Key : BaseEventKey<KookPinnedMessageEvent>(
        "kook.pinned_message", KookMessagePinEvent
    ) {
        override fun safeCast(value: Any): KookPinnedMessageEvent? = doSafeCast(value)
    }
    
}

/**
 *
 * 消息取消置顶事件。此事件的body也属于 [ChannelEventExtraBody] 类型的自类型，
 * 代表一个新的消息被设置为了目标频道的置顶消息。
 *
 * 此事件为 [ChangedEvent], 事件源为发生事件的频道服务器，变动主体为被设置为置顶消息 **的ID**。
 * 由于事件无法确定变更前的消息，因此只能获取到 **删除前的** 消息ID，而 [after] 恒为null。
 *
 * @see ChannelEventExtraBody
 * @see UnpinnedMessageEvent
 */
public abstract class KookUnpinnedMessageEvent :
    KookMessagePinEvent<UnpinnedMessageExtraBody>() {
    
    //// Impl
    override val msgId: ID
        get() = sourceBody.msgId
    
    override val operatorId: ID
        get() = sourceBody.operatorId
    
    override val channelId: ID
        get() = sourceBody.channelId
    
    /**
     * 同 [msgId].
     */
    @JvmSynthetic
    override suspend fun before(): ID = msgId
    
    /**
     * 始终为null。
     */
    @JvmSynthetic
    override suspend fun after(): ID? = null
    
    
    override val key: Event.Key<out KookUnpinnedMessageEvent>
        get() = Key
    
    public companion object Key : BaseEventKey<KookUnpinnedMessageEvent>(
        "kook.unpinned_message", KookMessagePinEvent
    ) {
        override fun safeCast(value: Any): KookUnpinnedMessageEvent? = doSafeCast(value)
    }
    
}

