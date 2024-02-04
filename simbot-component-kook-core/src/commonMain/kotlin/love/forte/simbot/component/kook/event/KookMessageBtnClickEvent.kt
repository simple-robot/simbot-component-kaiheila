/*
 *     Copyright (c) 2023-2024. ForteScarlet.
 *
 *     This file is part of simbot-component-kook.
 *
 *     simbot-component-kook is free software: you can redistribute it and/or modify
 *     it under the terms of the GNU Lesser General Public License as published by
 *     the Free Software Foundation, either version 3 of the License, or
 *     (at your option) any later version.
 *
 *     simbot-component-kook is distributed in the hope that it will be useful,
 *     but WITHOUT ANY WARRANTY; without even the implied warranty of
 *     MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 *     GNU Lesser General Public License for more details.
 *
 *     You should have received a copy of the GNU Lesser General Public License
 *     along with simbot-component-kook,
 *     If not, see <https://www.gnu.org/licenses/>.
 */

package love.forte.simbot.component.kook.event

import love.forte.simbot.common.id.ID
import love.forte.simbot.common.id.StringID.Companion.ID
import love.forte.simbot.common.time.Timestamp
import love.forte.simbot.kook.event.MessageBtnClickEventBody
import love.forte.simbot.kook.event.MessageBtnClickEventExtra


/**
 * 一个 `Card` 中的按钮被按下的事件。
 *
 * @see MessageBtnClickEventExtra
 *
 * @author ForteScarlet
 */
public abstract class KookMessageBtnClickEvent : KookSystemEvent() {
    override val time: Timestamp get() = Timestamp.ofMilliseconds(sourceEvent.msgTimestamp)

    abstract override val sourceEvent: love.forte.simbot.kook.event.Event<MessageBtnClickEventExtra>

    override val sourceBody: MessageBtnClickEventBody
        get() = sourceEvent.extra.body

    /**
     * 按钮 `return-val` 时的返回值
     *
     * @see MessageBtnClickEventBody.value
     */
    public val value: String get() = sourceBody.value

    /**
     * 点击的用户ID
     *
     * @see MessageBtnClickEventBody.userId
     */
    public val userId: ID get() = sourceBody.userId.ID
}



