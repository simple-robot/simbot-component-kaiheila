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

package love.forte.simbot.kook

import io.ktor.http.*
import kotlin.jvm.JvmField
import kotlin.jvm.JvmStatic

/**
 * 部分当前 KOOK 信息常量，例如api版本信息。
 *
 * 相关内容参考 [KOOK开发者文档](https://developer.kookapp.cn/doc/reference)
 */
@Suppress("MemberVisibilityCanBePrivate")
public object KOOK {

    /**
     * 当前的 KOOK api 的版本数值。
     *
     * 参考 [文档](https://developer.kookapp.cn/doc/reference)。
     */
    public const val API_VERSION_NUMBER: Int = 3

    /**
     * 当前的 KOOK api 的版本，等同于 `"v$VERSION_NUMBER"`
     *
     * 参考 [文档](https://developer.kookapp.cn/doc/reference)。
     */
    public const val API_VERSION: String = "v$API_VERSION_NUMBER"

    /**
     * KOOK api 的 url host.
     *
     * 参考 [文档](https://developer.kookapp.cn/doc/reference)。
     */
    public const val HOST: String = "www.kookapp.cn"

    /**
     * KOOK api 的 base url.
     *
     * 参考 [文档](https://developer.kookapp.cn/doc/reference)。
     */
    public const val BASE_URL_VALUE: String = "https://$HOST/api"

    /**
     * 不携带 [API_VERSION] 信息的 [Url] 类型 [BASE_URL_VALUE]
     */
    @JvmField
    public val SERVER_URL_WITHOUT_VERSION: Url = Url(BASE_URL_VALUE)

    /**
     * 携带 [API_VERSION] 信息的 [Url] 类型 [BASE_URL_VALUE]，会额外追加一段 [API_VERSION] 路径。
     */
    @JvmField
    public val SERVER_URL_WITH_VERSION: Url = Url("$BASE_URL_VALUE/v$API_VERSION")
}

/**
 * 鉴权Token类型。
 *
 * 参考 [文档](https://developer.kookapp.cn/doc/reference)。
 */
public enum class TokenType(public val prefix: String) {
    /** `TOKEN_TYPE = Bot` */
    BOT("Bot"),
    /** `TOKEN_TYPE = Bearer` */
    BEARER("Bearer");

    public companion object {

        /**
         * 通过 [prefix] 寻找匹配的 [TokenType]。
         *
         * 需要注意匹配是大小写相关的。
         *
         * @throws NoSuchElementException 没有匹配的结果时
         */
        @JvmStatic
        public fun byPrefix(prefix: String): TokenType = when(prefix) {
            BOT.prefix -> BOT
            BEARER.prefix -> BEARER
            else -> throw NoSuchElementException("prefix $prefix")
        }
    }
}

