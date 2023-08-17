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

package love.forte.simbot.kook.api

/**
 * 标记为一个作为 KOOK API 请求而返回的类型，
 * 应由 KOOK API 请求结果进行反序列化实现而不可直接构造。
 */
@RequiresOptIn(
    "作为KOOK API请求结果的类型，请避免直接构造",
    level = RequiresOptIn.Level.WARNING
)
@Retention(AnnotationRetention.BINARY)
@MustBeDocumented
public annotation class ApiResultType
