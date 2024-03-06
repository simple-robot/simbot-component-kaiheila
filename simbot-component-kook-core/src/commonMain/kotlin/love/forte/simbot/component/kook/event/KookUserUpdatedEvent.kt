/*
 *     Copyright (c) 2022-2024. ForteScarlet.
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

import love.forte.simbot.common.time.Timestamp
import love.forte.simbot.event.ChangeEvent
import love.forte.simbot.kook.event.UserUpdatedEventBody
import love.forte.simbot.kook.event.UserUpdatedEventExtra
import love.forte.simbot.suspendrunner.STP
import love.forte.simbot.kook.event.Event as KEvent

/**
 * Kook 用户信息更新事件。
 * 此事件属于一个 [ChangeEvent],
 * [ChangeEvent.content] 为用户变更事件的内容本体，
 * 即 [sourceBody]。
 *
 * 此事件不一定是某个具体频道服务器中的用户，
 * 只要有好友关系即会推送。
 *
 * @see UserUpdatedEventExtra
 */
public abstract class KookUserUpdatedEvent : KookSystemEvent(), ChangeEvent {
    override val time: Timestamp
        get() = Timestamp.ofMilliseconds(sourceEvent.msgTimestamp)

    abstract override val sourceEvent: KEvent<UserUpdatedEventExtra>

    override val sourceBody: UserUpdatedEventBody
        get() = sourceEvent.extra.body

    /**
     * 等同于 [sourceBody]。
     */
    @STP
    override suspend fun content(): UserUpdatedEventBody = sourceBody
}
