/*
 * Copyright (c) 2022-2023. ForteScarlet.
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

package love.forte.simbot.component.kook.event

import love.forte.simbot.DiscreetSimbotApi
import love.forte.simbot.ID
import love.forte.simbot.Timestamp
import love.forte.simbot.component.kook.bot.KookBot
import love.forte.simbot.delegate.getValue
import love.forte.simbot.delegate.stringID
import love.forte.simbot.event.BaseEventKey
import love.forte.simbot.kook.event.Event
import love.forte.simbot.kook.event.EventExtra
import love.forte.simbot.kook.event.UnknownExtra
import love.forte.simbot.message.doSafeCast


/**
 * 所有未提供针对性实现的其他 KOOK 事件。
 *
 * [UnsupportedKookEvent] 不实现任何其他事件类型，
 * 仅实现 KOOK 组件中的事件父类型 [KookEvent]，是一个完全独立的事件类型。
 *
 * [UnsupportedKookEvent] 会将所有 **尚未支持** 的事件通过此类型进行推送。
 * 如果要监听 [UnsupportedKookEvent], 你需要谨慎处理其中的一切，
 * 因为 [UnsupportedKookEvent] 能够提供的事件会随着当前组件支持的特定事件的增多而减少，
 * 这种减少可能会伴随着版本更新而产生，且可能不会有任何说明或错误提示。
 *
 * 因此你应当首先查看 [KookEvent] 下是否有所需的已经实现的事件类型，并且不应当过分依赖 [UnsupportedKookEvent].
 *
 * ### [UnknownExtra]
 *
 * [`sourceEvent.extra`][Event.extra] 中（理所应当地）有可能会出现 [UnknownExtra]。
 * [UnknownExtra] 的含义与其他 [EventExtra] 的含义略有区别。详细说明可参考 [UnknownExtra] 的文档描述。
 *
 * @author ForteScarlet
 */
@DiscreetSimbotApi
public class UnsupportedKookEvent(
    override val bot: KookBot,
    override val sourceEvent: Event<EventExtra>,
    override val sourceEventContent: String
) : KookEvent<EventExtra, Event<EventExtra>>() {

    /**
     * 事件ID。
     */
    override val id: ID by stringID { sourceEvent.msgId }

    /**
     * 事件时间。
     */
    override val timestamp: Timestamp get() = Timestamp.byMillisecond(sourceEvent.msgTimestamp)


    override fun toString(): String {
        return "UnsupportedKookEvent(sourceEvent=$sourceEvent)"
    }

    override fun equals(other: Any?): Boolean {
        if (other === this) return true
        if (other !is UnsupportedKookEvent) return false

        return sourceEvent == other.sourceEvent
    }

    override fun hashCode(): Int {
        return sourceEvent.hashCode()
    }


    override val key: love.forte.simbot.event.Event.Key<UnsupportedKookEvent>
        get() = Key

    public companion object Key : BaseEventKey<UnsupportedKookEvent>("kook.unsupported", KookEvent) {
        override fun safeCast(value: Any): UnsupportedKookEvent? = doSafeCast(value)
    }

}
