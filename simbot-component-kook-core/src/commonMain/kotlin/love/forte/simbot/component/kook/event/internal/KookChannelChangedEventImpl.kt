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

@file:Suppress("UnnecessaryOptInAnnotation")

package love.forte.simbot.component.kook.event.internal

import love.forte.simbot.annotations.Api4J
import love.forte.simbot.component.kook.KookCategoryChannel
import love.forte.simbot.component.kook.KookChatChannel
import love.forte.simbot.component.kook.KookGuild
import love.forte.simbot.component.kook.bot.internal.KookBotImpl
import love.forte.simbot.component.kook.event.*
import love.forte.simbot.component.kook.internal.KookCategoryChannelImpl
import love.forte.simbot.component.kook.internal.KookChatChannelImpl
import love.forte.simbot.component.kook.internal.KookGuildImpl
import love.forte.simbot.component.kook.internal.KookMemberImpl
import love.forte.simbot.component.kook.message.KookChannelMessageDetailsContent.Companion.toContent
import love.forte.simbot.component.kook.util.requestDataBy
import love.forte.simbot.kook.api.message.GetChannelMessageViewApi
import love.forte.simbot.kook.event.*
import love.forte.simbot.message.MessageContent
import love.forte.simbot.kook.event.Event as KEvent

internal data class KookAddedChannelEventImpl(
    override val bot: KookBotImpl,
    override val sourceEvent: KEvent<AddedChannelEventExtra>,
    private val _source: KookGuildImpl,
    private val _after: KookChatChannelImpl,
    override val sourceEventContent: String
) : KookAddedChannelEvent() {
    override val operator: KookMemberImpl? = bot.internalMember(sourceBody.guildId, sourceBody.userId)
    override suspend fun source(): KookGuild = _source
    override suspend fun content(): KookChatChannel = _after
}

@Suppress("UnnecessaryOptInAnnotation")
internal data class KookUpdatedChannelEventImpl(
    override val bot: KookBotImpl,
    override val sourceEvent: KEvent<UpdatedChannelEventExtra>,
    private val _source: KookGuildImpl,
    private val _channel: KookChatChannelImpl,
    override val sourceEventContent: String
) : KookUpdatedChannelEvent() {
    override suspend fun source(): KookGuild = _source
    override suspend fun content(): KookChatChannel = _channel
}

@Suppress("UnnecessaryOptInAnnotation")
@OptIn(Api4J::class)
internal data class KookDeletedChannelEventImpl(
    override val bot: KookBotImpl,
    override val sourceEvent: KEvent<DeletedChannelEventExtra>,
    private val _source: KookGuildImpl,
    private val _before: KookChatChannelImpl,
    override val sourceEventContent: String
) : KookDeletedChannelEvent() {
    override suspend fun source(): KookGuild = _source
    override suspend fun content(): KookChatChannel = _before
}

internal data class KookAddedCategoryEventImpl(
    override val bot: KookBotImpl,
    override val sourceEvent: KEvent<AddedChannelEventExtra>,
    private val _source: KookGuildImpl,
    private val _category: KookCategoryChannelImpl,
    override val sourceEventContent: String
) : KookAddedCategoryEvent() {
    override val operator: KookMemberImpl? = bot.internalMember(sourceBody.guildId, sourceBody.userId)
    override suspend fun content(): KookCategoryChannel = _category
    override suspend fun source(): KookGuild = _source
}

@Suppress("UnnecessaryOptInAnnotation")
internal data class KookUpdatedCategoryEventImpl(
    override val bot: KookBotImpl,
    override val sourceEvent: KEvent<UpdatedChannelEventExtra>,
    private val _source: KookGuildImpl,
    private val _category: KookCategoryChannelImpl,
    override val sourceEventContent: String
) : KookUpdatedCategoryEvent() {
    override suspend fun content(): KookCategoryChannel = _category
    override suspend fun source(): KookGuild = _source
}

@Suppress("UnnecessaryOptInAnnotation")
@OptIn(Api4J::class)
internal data class KookDeletedCategoryEventImpl(
    override val bot: KookBotImpl,
    override val sourceEvent: KEvent<DeletedChannelEventExtra>,
    private val _source: KookGuildImpl,
    private val _before: KookCategoryChannelImpl,
    override val sourceEventContent: String
) : KookDeletedCategoryEvent() {
    override suspend fun source(): KookGuild = _source
    override suspend fun content(): KookCategoryChannel = _before
}

@Suppress("UnnecessaryOptInAnnotation")
@OptIn(Api4J::class)
internal data class KookPinnedMessageEventImpl(
    override val bot: KookBotImpl,
    override val sourceEvent: KEvent<PinnedMessageEventExtra>,
    private val _source: KookGuildImpl,
    private val _channel: KookChatChannelImpl,
    override val sourceEventContent: String
) : KookPinnedMessageEvent() {
    override val operator: KookMemberImpl? = bot.internalMember(sourceEvent.targetId, sourceBody.operatorId)

    override suspend fun source(): KookGuild = _source
    override suspend fun content(): KookChatChannel = _channel

    override suspend fun queryMsg(): MessageContent {
        return GetChannelMessageViewApi.create(sourceBody.msgId).requestDataBy(bot).toContent(bot)
    }
}

@Suppress("UnnecessaryOptInAnnotation")
@OptIn(Api4J::class)
internal data class KookUnpinnedMessageEventImpl(
    override val bot: KookBotImpl,
    override val sourceEvent: KEvent<UnpinnedMessageEventExtra>,
    private val _source: KookGuildImpl,
    private val _channel: KookChatChannelImpl,
    override val sourceEventContent: String
) : KookUnpinnedMessageEvent() {
    override val operator: KookMemberImpl? = bot.internalMember(sourceEvent.targetId, sourceBody.operatorId)

    override suspend fun source(): KookGuild = _source
    override suspend fun content(): KookChatChannel = _channel

    override suspend fun queryMsg(): MessageContent {
        return GetChannelMessageViewApi.create(sourceBody.msgId).requestDataBy(bot).toContent(bot)
    }
}
