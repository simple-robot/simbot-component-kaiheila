/*
 * Copyright (c) 2024. ForteScarlet.
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

@file:JvmName("EventProcessors")
@file:JvmMultifileClass
package love.forte.simbot.kook.stdlib

import love.forte.simbot.kook.event.Event
import kotlin.jvm.JvmMultifileClass
import kotlin.jvm.JvmName
import kotlin.jvm.JvmSynthetic


/**
 * 事件处理器函数。
 *
 * Java 中可以使用 `EventProcessors`
 * 中提供的静态工厂来构建。
 *
 * @author ForteScarlet
 */
public fun interface EventProcessor {
    @JvmSynthetic
    public suspend operator fun Event<*>.invoke(raw: String)
}

internal suspend fun EventProcessor.doInvoke(e: Event<*>, r: String) {
    e.apply { invoke(r) }
}
