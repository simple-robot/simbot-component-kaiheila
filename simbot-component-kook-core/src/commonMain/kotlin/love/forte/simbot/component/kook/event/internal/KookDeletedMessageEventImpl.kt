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

package love.forte.simbot.component.kook.event.internal

import love.forte.simbot.component.kook.bot.internal.KookBotImpl
import love.forte.simbot.component.kook.event.KookDeletedChannelMessageEvent
import love.forte.simbot.component.kook.event.KookDeletedPrivateMessageEvent
import love.forte.simbot.kook.event.DeletedMessageEventExtra
import love.forte.simbot.kook.event.DeletedPrivateMessageEventExtra
import love.forte.simbot.kook.event.Event


internal class KookDeletedChannelMessageEventImpl(
    override val bot: KookBotImpl,
    override val sourceEvent: Event<DeletedMessageEventExtra>,
    override val sourceEventRaw: String
) : KookDeletedChannelMessageEvent()

internal class KookDeletedPrivateMessageEventImpl(
    override val bot: KookBotImpl,
    override val sourceEvent: Event<DeletedPrivateMessageEventExtra>,
    override val sourceEventRaw: String
) : KookDeletedPrivateMessageEvent()
