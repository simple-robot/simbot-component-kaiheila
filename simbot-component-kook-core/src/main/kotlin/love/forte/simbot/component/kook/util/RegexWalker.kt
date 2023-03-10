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

package love.forte.simbot.component.kook.util

/**
 * @return true if find([input]) != null
 */
public inline fun Regex.walk(input: String, onOther: (input: String, start: Int, end: Int) -> Unit, onMatch: (MatchResult) -> Unit): Boolean {
    var match: MatchResult? = find(input) ?: return false
    
    var lastStart = 0
    val length = input.length
    do {
        val foundMatch = match!!
        onOther(input, lastStart, foundMatch.range.first)
        onMatch(foundMatch)
        lastStart = foundMatch.range.last + 1
        match = foundMatch.next()
    } while (lastStart < length && match != null)
    
    if (lastStart < length) {
        onOther(input, lastStart, length)
    }
    
    return true
}
