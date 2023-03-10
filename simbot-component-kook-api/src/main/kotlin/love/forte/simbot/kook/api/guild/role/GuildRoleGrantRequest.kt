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

package love.forte.simbot.kook.api.guild.role

import kotlinx.serialization.DeserializationStrategy
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import love.forte.simbot.ID
import love.forte.simbot.kook.api.BaseKookApiRequestKey
import love.forte.simbot.kook.api.KookPostRequest


/**
 *
 * [赋予用户角色](https://developer.kaiheila.cn/doc/http/guild-role#赋予用户角色)
 *
 * method `POST`
 *
 * `/api/v3/guild-role/grant`
 */
public class GuildRoleGrantRequest internal constructor(
    private val guildId: ID,
    private val userId: ID,
    private val roleId: ID,
) : KookPostRequest<UserRoleOperated>() {
    public companion object Key : BaseKookApiRequestKey("guild-role", "grant") {
        
        /**
         * 构建 [GuildRoleGrantRequest]
         * @param guildId 频道ID
         * @param userId 用户ID
         * @param roleId 角色ID
         */
        @JvmStatic
        public fun create(guildId: ID, userId: ID, roleId: ID): GuildRoleGrantRequest =
            GuildRoleGrantRequest(guildId, userId, roleId)
        
    }
    
    override val resultDeserializer: DeserializationStrategy<out UserRoleOperated>
        get() = UserRoleOperated.serializer()
    
    override val apiPaths: List<String>
        get() = apiPathList
    
    protected override fun createBody(): Any = Body(guildId, userId, roleId)
    
    @Serializable
    private data class Body(
        @SerialName("guild_id")
        @Serializable(ID.AsCharSequenceIDSerializer::class)
        val guildId: ID,
        @SerialName("user_id")
        @Serializable(ID.AsCharSequenceIDSerializer::class)
        val userId: ID,
        @SerialName("role_id")
        @Serializable(ID.AsCharSequenceIDSerializer::class)
        val roleId: ID,
    )
}

