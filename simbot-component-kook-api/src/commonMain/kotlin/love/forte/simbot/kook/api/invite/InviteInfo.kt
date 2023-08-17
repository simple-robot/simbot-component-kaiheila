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

package love.forte.simbot.kook.api.invite

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import love.forte.simbot.kook.api.ApiResultType
import love.forte.simbot.kook.objects.SimpleUser

/**
 * 邀请相关涉及到的 API 数据响应体。
 *
 */
@Serializable
public data class InviteInfo @ApiResultType constructor(
    /**
     * 服务器id
     *
     */
    @SerialName("guild_id")
    public val guildId: String,
    /**
     * 频道id
     *
     */
    @SerialName("channel_id")
    public val channelId: String,
    /**
     * url code
     *
     */
    @SerialName("url_code")
    public val urlCode: String,
    /**
     * 地址
     *
     */
    public val url: String,
    /**
     * 用户
     *
     */
    public val user: SimpleUser,
)
