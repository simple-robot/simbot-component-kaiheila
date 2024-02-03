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

package love.forte.simbot.component.kook

import kotlinx.coroutines.CoroutineScope
import love.forte.simbot.annotations.ExperimentalSimbotAPI
import love.forte.simbot.common.collectable.Collectable
import love.forte.simbot.common.id.ID
import love.forte.simbot.common.id.StringID.Companion.ID
import love.forte.simbot.component.kook.role.KookGuildRole
import love.forte.simbot.component.kook.role.KookGuildRoleCreator
import love.forte.simbot.component.kook.role.KookRole
import love.forte.simbot.component.kook.role.KookRoleOperator
import love.forte.simbot.definition.Guild
import love.forte.simbot.suspendrunner.ST
import love.forte.simbot.suspendrunner.STP
import kotlin.coroutines.CoroutineContext
import love.forte.simbot.kook.objects.Guild as KGuild


/**
 * 一个 KOOK 中的频道服务器。
 *
 * @author ForteScarlet
 */
public interface KookGuild : Guild, CoroutineScope, KookRoleOperator {
    /**
     * 源于 bot 的上下文，但是没有 Job。
     */
    override val coroutineContext: CoroutineContext

    /**
     * 得到此 Guild 内对应的 api 模块下的原始 guild 信息。
     *
     * @see KGuild
     */
    public val source: KGuild

    /**
     * 频道服务器ID
     */
    override val id: ID
        get() = source.id.ID

    /**
     * 频道服务器名称
     */
    override val name: String
        get() = source.name

    /**
     * 服务器主题
     */
    public val topic: String
        get() = source.topic

    /**
     * 服务器icon的地址
     */
    public val icon: String
        get() = source.icon

    @STP
    override suspend fun botAsMember(): KookMember

    /**
     * 获取当前频道服务器下的子频道序列。
     *
     * 子频道列表**不包含**分组类型的频道，这类"分类频道"请参考 [categories]。
     */
    override val channels: Collectable<KookChatChannel>

    /**
     * 尝试根据指定ID获取匹配的 [KookChatChannel]。未找到时得到null。
     */
    @ST(
        blockingBaseName = "getChannel",
        blockingSuffix = "",
        asyncBaseName = "getChannel",
        reserveBaseName = "getChannel"
    )
    override suspend fun channel(id: ID): KookChannel?

    /**
     * 尝试根据指定ID获取匹配的 [KookChatChannel]。未找到时得到null。
     *
     * @see channel
     */
    @ST(
        blockingBaseName = "getChatChannel",
        blockingSuffix = "",
        asyncBaseName = "getChatChannel",
        reserveBaseName = "getChatChannel"
    )
    override suspend fun chatChannel(id: ID): KookChatChannel?

    // categories

    /**
     * 得到当前频道下所有的分组型频道。
     */
    @ExperimentalSimbotAPI
    public val categories: Collectable<KookCategoryChannel>

    /**
     * 尝试根据ID获取匹配的分类对象。
     */
    @ExperimentalSimbotAPI
    public fun getCategory(id: ID): KookCategoryChannel?

    // members

    /**
     * 此频道下的成员序列。
     */
    override val members: Collectable<KookMember>

    /**
     * 根据ID寻找一个此频道下的成员。
     */
    @ST(blockingBaseName = "getMember", blockingSuffix = "", asyncBaseName = "getMember", reserveBaseName = "getMember")
    override suspend fun member(id: ID): KookMember?

    /**
     * 当前频道的创建人ID
     */
    override val ownerId: ID?

    /**
     * 获取当前频道的创建人。
     *
     * @throws KookMemberNotExistsException 如果无法寻找到此成员时
     */
    @STP
    public suspend fun owner(): KookMember


    //region roles API

    /**
     * 获取当前频道服务器中配置的所有角色信息。
     *
     * _Note: [roles] 尚在实验阶段，可能会在未来做出变更。_
     *
     * @see KookRole
     * @see KookGuildRole
     */
    @ExperimentalSimbotAPI
    override val roles: Collectable<KookGuildRole>
        get() = getRoles(null, null)

    /**
     * 获取当前频道服务器中配置的所有角色信息。
     *
     * _Note: [roles] 尚在实验阶段，可能会在未来做出变更。_
     *
     * @see KookRole
     * @see KookGuildRole
     */
    @ExperimentalSimbotAPI
    public fun getRoles(startPage: Int?, pageSize: Int?): Collectable<KookGuildRole>


    @ExperimentalSimbotAPI
    override fun roleCreator(): KookGuildRoleCreator
    //endregion
}
