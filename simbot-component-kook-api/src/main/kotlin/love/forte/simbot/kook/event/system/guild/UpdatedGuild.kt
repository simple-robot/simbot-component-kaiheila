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

package love.forte.simbot.kook.event.system.guild

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import love.forte.simbot.CharSequenceID
import love.forte.simbot.ID
import love.forte.simbot.kook.util.BooleanToIntSerializer


/**
 * [服务器信息更新](https://developer.kaiheila.cn/doc/event/guild#%E6%9C%8D%E5%8A%A1%E5%99%A8%E4%BF%A1%E6%81%AF%E6%9B%B4%E6%96%B0)
 *
 * `updated_guild`
 *
 * @author ForteScarlet
 */
public interface UpdatedGuildExtraBody : GuildEventExtraBody {

    /**
     * 服务器id
     */
    public val id: ID
    /**
     * 服务器名称
     */
    public val name: String
    /**
     * 服务器主id
     */
    public val userId: ID
    /**
     * 服务器icon的地址
     */
    public val icon: String
    /**
     * 通知类型, 0代表默认使用服务器通知设置，1代表接收所有通知, 2代表仅@被提及，3代表不接收通知
     */
    public val notifyType: Int
    /**
     * 服务器默认使用语音区域
     */
    public val region: String
    /**
     * 是否为公开服务器
     */
    public val enableOpen: Boolean
    /**
     * 公开服务器id
     */
    public val openId: ID
    /**
     * 默认频道id
     */
    public val defaultChannelId: ID
    /**
     * 欢迎频道id
     */
    public val welcomeChannelId: ID
}



/**
 * 服务器信息更新
 *
 * `updated_guild`
 *
 * @author ForteScarlet
 */
@Serializable
internal data class UpdatedGuildExtraBodyImpl(
    /**
     * 服务器id
     */
    override val id: CharSequenceID,
    override val name: String,
    @SerialName("user_id")
    override val userId: CharSequenceID,
    /**
     * 服务器icon的地址
     */
    override val icon: String,
    /**
     * 通知类型, 0代表默认使用服务器通知设置，1代表接收所有通知, 2代表仅@被提及，3代表不接收通知
     */
    @SerialName("notify_type")
    override val notifyType: Int,
    /**
     * 服务器默认使用语音区域
     */
    override val region: String,
    /**
     * 是否为公开服务器
     */
    @SerialName("enable_open")
    @Serializable(BooleanToIntSerializer::class)
    override val enableOpen: Boolean,
    /**
     * 公开服务器id
     */
    @SerialName("open_id")
    override val openId: CharSequenceID,
    /**
     * 默认频道id
     */
    @SerialName("default_channel_id")
    override val defaultChannelId: CharSequenceID,
    /**
     * 欢迎频道id
     */
    @SerialName("welcome_channel_id")
    override val welcomeChannelId: CharSequenceID,
) : UpdatedGuildExtraBody {
    init {
        check(notifyType in 0..3) { "Parameter notify_type must be 0|1|2|3, but $notifyType" }
    }
}

