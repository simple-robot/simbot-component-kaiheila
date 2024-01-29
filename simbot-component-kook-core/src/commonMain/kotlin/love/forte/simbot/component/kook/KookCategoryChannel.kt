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

import love.forte.simbot.common.id.ID
import love.forte.simbot.common.id.StringID.Companion.ID
import love.forte.simbot.definition.Category
import kotlin.jvm.JvmSynthetic
import love.forte.simbot.kook.objects.Channel as KChannel

/**
 * 表示为一个 KOOK 中的分类类型
 */
public interface KookCategory : Category {
    public val source: KChannel

    /**
     * 分类ID
     */
    override val id: ID
        get() = source.id.ID

    /**
     * 分类名称
     */
    @JvmSynthetic
    override suspend fun name(): String = source.name

    /**
     * 将此分类类型转化为 [KookCategoryChannel]。
     * [KookCategoryChannel] 的分类等于自己所代表的分类。
     */
    public fun toChannel(): KookCategoryChannel
}

/**
 * KOOK 组件中对于 [频道类型(分组)][Category] 的定义。
 *
 * @author ForteScarlet
 */
public interface KookCategoryChannel : KookChannel {
    /**
     * 转化为 [KookCategory] 类型。
     */
    override val category: KookCategory
}
