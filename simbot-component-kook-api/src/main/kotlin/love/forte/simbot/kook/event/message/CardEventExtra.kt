/*
 * Copyright (c) 2021-2023. ForteScarlet.
 *
 * This file is part of simbot-component-kook.
 *
 * simbot-component-kook is free software: you can redistribute it and/or modify it under the terms of the GNU Lesser General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.
 *
 * simbot-component-kook is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public License along with simbot-component-kook, If not, see <https://www.gnu.org/licenses/>.
 */

package love.forte.simbot.kook.event.message

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import love.forte.simbot.CharSequenceID
import love.forte.simbot.ID
import love.forte.simbot.LongID
import love.forte.simbot.kook.event.Event
import love.forte.simbot.kook.objects.User
import love.forte.simbot.kook.objects.UserImpl

/**
 * 卡片消息事件 [CardEvent] 对应的 extra 类型。
 */
public interface CardEventExtra : MessageEventExtra {
    override val guildId: ID
    override val channelName: String
    override val mention: List<ID>
    override val isMentionAll: Boolean
    override val mentionRoles: List<ID>
    override val isMentionHere: Boolean
    override val author: User
}


@Serializable
internal data class CardEventExtraImpl(
    override val type: Event.Type,
    @SerialName("guild_id")
    override val guildId: CharSequenceID = CharSequenceID.EMPTY,
    @SerialName("channel_name")
    override val channelName: String = "",
    override val mention: List<CharSequenceID> = emptyList(),
    @SerialName("mention_all")
    override val isMentionAll: Boolean = false,
    @SerialName("mention_roles")
    override val mentionRoles: List<LongID> = emptyList(),
    @SerialName("mention_here")
    override val isMentionHere: Boolean = false,
    override val author: UserImpl
) : CardEventExtra

