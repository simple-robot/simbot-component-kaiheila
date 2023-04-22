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
package love.forte.simbot.kook.api.message

import io.ktor.http.*
import kotlinx.serialization.DeserializationStrategy
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlinx.serialization.builtins.ListSerializer
import love.forte.simbot.CharSequenceID
import love.forte.simbot.ID
import love.forte.simbot.Timestamp
import love.forte.simbot.kook.api.ApiResultType
import love.forte.simbot.kook.api.BaseKookApiRequestKey
import love.forte.simbot.kook.api.KookGetRequest

/**
 * [获取频道消息某回应的用户列表](https://developer.kaiheila.cn/doc/http/message#%E8%8E%B7%E5%8F%96%E9%A2%91%E9%81%93%E6%B6%88%E6%81%AF%E6%9F%90%E5%9B%9E%E5%BA%94%E7%9A%84%E7%94%A8%E6%88%B7%E5%88%97%E8%A1%A8)
 *
 * @author ForteScarlet
 */
public class MessageReactionListRequest(
    /** 频道消息的id */
    private val msgId: ID,
    /** emoji的id, 可以为Guild Emoji或者Emoji, 注意：在get中，应该进行url-encode */
    private val emoji: ID,
) : KookGetRequest<List<MessageReactor>>() {
    public constructor(msgId: ID, emoji: love.forte.simbot.message.Emoji) : this(msgId, emoji.id)
    public constructor(msgId: ID, emoji: Emoji) : this(msgId, emoji.id)
    
    public companion object Key : BaseKookApiRequestKey("message", "reaction-list") {
        private val serializer = ListSerializer(MessageReactor.serializer())
        
        /**
         * 构建 [MessageReactionListRequest]
         * @param msgId 频道消息的id
         * @param emoji emoji的id
         */
        @JvmStatic
        public fun create(msgId: ID, emoji: ID): MessageReactionListRequest = MessageReactionListRequest(msgId, emoji)
        
        /**
         * 构建 [MessageReactionListRequest]
         * @param msgId 频道消息的id
         * @param emoji emoji
         */
        @JvmStatic
        public fun create(msgId: ID, emoji: love.forte.simbot.message.Emoji): MessageReactionListRequest =
            create(msgId, emoji.id)
        
        /**
         * 构建 [MessageReactionListRequest]
         * @param msgId 频道消息的id
         * @param emoji emoji
         */
        @JvmStatic
        public fun create(msgId: ID, emoji: Emoji): MessageReactionListRequest = create(msgId, emoji.id)
    }
    
    override val resultDeserializer: DeserializationStrategy<List<MessageReactor>> get() = serializer
    override val apiPaths: List<String> get() = apiPathList
    
    override fun ParametersBuilder.buildParameters() {
        append("msg_id", msgId.toString())
        append("emoji", emoji.toString())
    }
    
}


/**
 * api [MessageReactionListRequest] 的响应列表元素。
 */
@Serializable
public class MessageReactor @ApiResultType constructor(
    
    /**
     * 用户的id
     */
    public val id: CharSequenceID,
    
    /**
     * 用户的名称
     */
    public val username: String,
    
    /**
     * 用户在服务器内的呢称
     */
    public val nickname: String,
    
    /**
     * 用户名的认证数字，用户名正常为：user_name#identify_num
     */
    @SerialName("identify_num")
    public val identifyNum: String,
    /**
     * 当前是否在线
     */
    public val online: Boolean,
    /**
     * 用户的状态, 0代表正常，10代表被封禁
     */
    public val status: Int,
    
    /**
     * 用户的头像的url地址
     */
    public val avatar: String,
    
    /**
     * 	用户是否为机器人
     */
    @SerialName("bot")
    public val isBot: Boolean,
    /**
     * 用户点击reaction的毫秒时间戳
     */
    @SerialName("reaction_time")
    public val reactionTime: Timestamp,
) {
    public companion object {
        public const val STATUS_NORMAL: Int = 0
        public const val STATUS_BAN: Int = 10
    }
    
}


public fun MessageReactor.isNormal(): Boolean = status == MessageReactor.STATUS_NORMAL
public fun MessageReactor.isBan(): Boolean = status == MessageReactor.STATUS_BAN
