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

package love.forte.simbot.kook.api.message

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import love.forte.simbot.kook.api.ApiResultType

/**
 * 发送聊天消息 API 的响应实例。
 *
 *
 * @property msgId 服务端生成的消息 id
 * @property msgTimestamp 消息发送时间(服务器时间戳)
 * @property nonce 随机(原样返回的)字符串，见参数列表
 *
 * @see SendChannelMessageApi
 * @see SendDirectMessageApi
 */
@Serializable
public data class SendMessageResult @ApiResultType constructor(
    @SerialName("msg_id") val msgId: String,
    @SerialName("msg_timestamp") val msgTimestamp: Long,
    val nonce: String,
)
