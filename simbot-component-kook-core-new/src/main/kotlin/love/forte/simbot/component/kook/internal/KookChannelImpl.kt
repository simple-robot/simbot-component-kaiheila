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

package love.forte.simbot.component.kook.internal

import love.forte.simbot.ID
import love.forte.simbot.SimbotIllegalArgumentException
import love.forte.simbot.component.kook.KookChannel
import love.forte.simbot.component.kook.KookChannelCategory
import love.forte.simbot.component.kook.bot.KookGuildBot
import love.forte.simbot.component.kook.bot.internal.KookBotImpl
import love.forte.simbot.component.kook.kookGuildNotExistsException
import love.forte.simbot.component.kook.message.KookChannelMessageDetailsContent
import love.forte.simbot.component.kook.message.KookMessageCreatedReceipt.Companion.asReceipt
import love.forte.simbot.component.kook.message.KookMessageReceipt
import love.forte.simbot.component.kook.message.KookReceiveMessageContent
import love.forte.simbot.component.kook.message.sendToChannel
import love.forte.simbot.component.kook.util.requestDataBy
import love.forte.simbot.definition.Guild
import love.forte.simbot.definition.GuildMember
import love.forte.simbot.definition.Organization
import love.forte.simbot.definition.Role
import love.forte.simbot.delegate.getValue
import love.forte.simbot.delegate.stringID
import love.forte.simbot.kook.api.message.SendChannelMessageApi
import love.forte.simbot.kook.messages.MessageType
import love.forte.simbot.kook.objects.Channel
import love.forte.simbot.literal
import love.forte.simbot.message.Message
import love.forte.simbot.message.MessageContent
import love.forte.simbot.utils.item.Items
import kotlin.time.Duration


/**
 *
 * @author ForteScarlet
 */
internal class KookChannelImpl(
    private val baseBot: KookBotImpl,
    override val source: Channel,
) : KookChannel {
    override val bot: KookGuildBot
        get() = baseBot.internalGuild(source.guildId)?.bot
            ?: throw kookGuildNotExistsException(source.guildId)

    override val guildId: ID by stringID { source.guildId }

    override val currentMember: Int
        get() = TODO("Not yet implemented")

    override suspend fun guild(): Guild {
        TODO("Not yet implemented")
    }

    override val maximumMember: Int
        get() = TODO("Not yet implemented")

    override val members: Items<GuildMember>
        get() = TODO("Not yet implemented")

    override suspend fun owner(): GuildMember {
        TODO("Not yet implemented")
    }

    override suspend fun member(id: ID): GuildMember? {
        TODO("Not yet implemented")
    }

    override suspend fun previous(): Organization? {
        TODO("Not yet implemented")
    }

    override val roles: Items<Role>
        get() = TODO("Not yet implemented")

    override suspend fun send(message: Message, quote: ID?, tempTargetId: ID?): KookMessageReceipt {
        return send(message, quote?.literal, tempTargetId?.literal)
    }

    override suspend fun send(message: MessageContent, quote: ID?, tempTargetId: ID?): KookMessageReceipt {
        return send(message, quote?.literal, tempTargetId?.literal)
    }

    internal suspend fun send(message: Message, quote: String? = null, tempTargetId: String? = null): KookMessageReceipt {
        return message.sendToChannel(
            bot,
            targetId = source.id,
            quote = quote,
            tempTargetId = tempTargetId,
            defaultTempTargetId = null
        ) ?: throw SimbotIllegalArgumentException("Valid messages must not be empty.")
    }

    internal suspend fun send(message: MessageContent, quote: String? = null, tempTargetId: String? = null): KookMessageReceipt {
        return when (message) {
            is KookReceiveMessageContent -> {
                val source = message.source
                SendChannelMessageApi.create(
                    type = source.type?.value,
                    targetId = this.source.id,
                    content = source.content,
                    quote = quote,
                    nonce = null,
                    tempTargetId = tempTargetId,
                ).requestDataBy(baseBot).asReceipt(false, baseBot)
            }

            is KookChannelMessageDetailsContent -> {
                val details = message.details
                SendChannelMessageApi.create(
                    type = details.type,
                    targetId = this.source.id,
                    content = details.content,
                    quote = quote ?: details.quote?.id,
                    nonce = null,
                    tempTargetId = tempTargetId,
                ).requestDataBy(baseBot).asReceipt(false, baseBot)
            }

            else -> {
                send(message.messages)
            }
        }
    }

    internal suspend fun send(text: String, quote: String? = null, tempTargetId: String? = null): KookMessageReceipt {
        return send(
            MessageType.TEXT.type,
            text,
            quote,
            null,
            tempTargetId
        )
    }

    /**
     * 根据 [SendChannelMessageApi] api 构建并发送消息。
     */
    private suspend fun send(
        type: Int,
        content: String,
        quote: String? = null,
        nonce: String? = null,
        tempTargetId: String? = null,
    ): KookMessageReceipt {
        val request = SendChannelMessageApi.create(type, source.id, content, quote, nonce, tempTargetId)
        return send(request)
    }

    override suspend fun mute(duration: Duration): Boolean {
        TODO("Not yet implemented")
    }

    override suspend fun unmute(): Boolean {
        TODO("Not yet implemented")
    }

    override val category: KookChannelCategory?
        get() = TODO("Not yet implemented")

    override fun toString(): String {
        return "KookChannel(id=${source.id}, name=${source.name}, guildId=${source.guildId})"
    }
}
