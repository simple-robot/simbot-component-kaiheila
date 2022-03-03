package love.forte.simbot.kaiheila.util

import java.util.*


/**
 * 将目标转化为不可变列表。
 */
public inline fun <reified T> List<T>.unmodifiable(): List<T> = Collections.unmodifiableList(this.toTypedArray().asList())

/**
 * 构建不可变列表。
 */
public inline fun <reified T> unmodifiableListOf(vararg values: T): List<T> = when {
    values.isEmpty() -> emptyList()
    values.size == 1 -> listOf(values[0])
    else -> listOf(*values).unmodifiable()
}
