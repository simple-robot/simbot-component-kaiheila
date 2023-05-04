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

import io.ktor.client.*
import io.ktor.client.request.*
import io.ktor.client.statement.*
import io.ktor.http.*
import kotlinx.coroutines.withContext
import kotlinx.serialization.DeserializationStrategy
import kotlinx.serialization.builtins.serializer
import kotlinx.serialization.json.Json
import love.forte.simbot.logger.LoggerFactory
import love.forte.simbot.util.api.requestor.API
import kotlin.coroutines.AbstractCoroutineContextElement
import kotlin.coroutines.CoroutineContext
import kotlin.coroutines.coroutineContext
import kotlin.jvm.JvmField
import kotlin.jvm.JvmSynthetic
import kotlin.jvm.Volatile

/**
 * 面向平台实现的 [KookApi] 的抽象类。
 *
 * 用于面向平台提供额外支持的能力。仅用于平台实现，不应被直接使用，请直接使用 [KookApi]。
 */
public expect abstract class PlatformKookApi<T>() : API<KookApiRequestor, T> {

    /**
     * 通过一个 [HttpClient] 和校验信息 [authorization] 对当前API发起请求，并得到一个 [ApiResult]。
     *
     * 得到原始的 [HttpResponse] 而不对结果有任何处理。
     */
    @JvmSynthetic
    public abstract suspend fun request(client: HttpClient, authorization: String): HttpResponse

    /**
     * 通过一个 [HttpClient] 和校验信息 [authorization] 对当前API发起请求，并得到一个结果字符串。
     *
     * @throws ApiResponseException 请求结果的状态码不是 200..300 之间
     */
    @JvmSynthetic
    public abstract suspend fun requestRaw(client: HttpClient, authorization: String): String

    /**
     * 通过一个 [HttpClient] 和校验信息 [authorization] 对当前API发起请求，并得到一个 [ApiResult] 结果。
     *
     * @throws ApiResponseException 请求结果的状态码不是 200..300 之间
     * @throws ApiResultException 请求结果的 [ApiResult.code] 校验失败
     */
    @JvmSynthetic
    public abstract suspend fun requestResult(client: HttpClient, authorization: String): ApiResult

    /**
     * 通过一个 [HttpClient] 和校验信息 [authorization] 对当前API发起请求，并得到一个具体结果。
     *
     * @throws ApiResponseException 请求结果的状态码不是 200..300 之间
     * @throws ApiResultException 请求结果的 [ApiResult.code] 校验失败
     */
    @JvmSynthetic
    public abstract suspend fun requestData(client: HttpClient, authorization: String): T

    /**
     * 通过一个 [requestor] 对当前API发起请求，并得到一个具体结果。
     *
     * @throws ApiResponseException 请求结果的状态码不是 200..300 之间
     * @throws ApiResultException 请求结果的 [ApiResult.code] 校验失败
     */
    @JvmSynthetic
    abstract override suspend fun requestBy(requestor: KookApiRequestor): T
}

/**
 * 一个用于表示 KOOK API 封装的抽象类。
 *
 * [KookApi] 面向 `Ktor` 并基于 `kotlinx.serialization` 进行反序列化。
 *
 * @param T 此API预期的响应结果数据类型
 *
 * @author ForteScarlet
 */
public abstract class KookApi<T> : API<KookApiRequestor, T>, PlatformKookApi<T>() {

    /**
     * 此请求最终的url。
     */
    public abstract val url: Url

    /**
     * 此请求使用的 [HttpMethod]。
     */
    public abstract val method: HttpMethod

    /**
     * 预期结果类型的反序列化策略。
     *
     * 策略应为以下类型中的一种：
     * - [ListData]
     * - []
     * -
     *
     */
    public abstract val resultDeserializer: DeserializationStrategy<T>

    /**
     * 可以为 [request] 提供更多行为的函数，例如提供body、重置contentType等。
     */
    protected open fun HttpRequestBuilder.requestPostAction() {
        headers[HttpHeaders.ContentType] = ContentType.Application.Json.toString()
    }


    /**
     * 通过一个 [HttpClient] 和校验信息 [authorization] 对当前API发起请求，并得到一个 [ApiResult]。
     *
     * 得到原始的 [HttpResponse] 而不对结果有任何处理。
     */
    @JvmSynthetic
    override suspend fun request(client: HttpClient, authorization: String): HttpResponse {
        val apiId: String? = if (apiLogger.isDebugEnabled()) {
            "${method.value} ${url.encodedPath}"
        } else null

        coroutineContext[APIContext]?.apiId = apiId

        if (this is KookPostApi) {
            apiLogger.debug("API[{}] ======> query: {}, body: {}", apiId, url.encodedQuery.ifEmpty { "<EMPTY>" }, body)
        } else {
            apiLogger.debug("API[{}] ======> query: {}", apiId, url.encodedQuery.ifEmpty { "<EMPTY>" })
        }

        val response = reqForResp(client, authorization) {
            requestPostAction()
        }

        apiLogger.debug("API[{}] <====== status: {}, response: {}", apiId, response.status, response)

        return response
    }

    /**
     * 通过一个 [HttpClient] 和校验信息 [authorization] 对当前API发起请求，并得到一个结果字符串。
     *
     * @throws ApiResponseException 请求结果的状态码不是 200..300 之间
     */
    @JvmSynthetic
    override suspend fun requestRaw(client: HttpClient, authorization: String): String {
        val response = request(client, authorization)
        return response.requireSuccess().bodyAsText()
    }

    private fun HttpResponse.requireSuccess(): HttpResponse {
        if (!status.isSuccess()) {
            throw ApiResponseException(this, "API [${this@KookApi}] response status non-successful: $status")
        }

        return this
    }

    /**
     * 通过一个 [HttpClient] 和校验信息 [authorization] 对当前API发起请求，并得到一个具体结果。
     *
     * @throws ApiResponseException 请求结果的状态码不是 200..300 之间
     * @throws ApiResultException 请求结果的 [ApiResult.code] 校验失败
     */
    @JvmSynthetic
    override suspend fun requestResult(client: HttpClient, authorization: String): ApiResult {
        var apiContext: APIContext? = null
        val response = if (apiLogger.isDebugEnabled()) {
            apiContext = coroutineContext[APIContext] ?: APIContext(null)
            withContext(apiContext) {
                request(client, authorization)
            }
        } else {
            request(client, authorization)
        }.requireSuccess()

        val raw = response.bodyAsText()

        val result = DEFAULT_JSON.decodeFromString(ApiResult.serializer(), raw)
        result.httpStatus = response.status
        result.raw = raw

        val rateLimit = response.headers.createRateLimit().also {
            apiLogger.debug("API[{}] <====== rate limit: {}", apiContext?.apiId, it)
        }
        result.rateLimit = rateLimit

        return result
    }


    /**
     * 通过一个 [HttpClient] 和校验信息 [authorization] 对当前API发起请求，并得到一个具体结果。
     *
     * @throws ApiResponseException 请求结果的状态码不是 200..300 之间
     * @throws ApiResultException 请求结果的 [ApiResult.code] 校验失败
     */
    @JvmSynthetic
    override suspend fun requestData(client: HttpClient, authorization: String): T {
        val result = requestResult(client, authorization)

        if (!result.isSuccess) {
            throw ApiResultException(result, "result.code is not success(${result.code})")
        }

        if (resultDeserializer == Unit.serializer()) {
            @Suppress("UNCHECKED_CAST")
            return Unit as T
        }

        return result.parseDataOrThrow(DEFAULT_JSON, resultDeserializer)
    }

    /**
     * 通过一个 [requestor] 对当前API发起请求，并得到一个具体结果。
     *
     * @throws ApiResponseException 请求结果的状态码不是 200..300 之间
     * @throws ApiResultException 请求结果的 [ApiResult.code] 校验失败
     */
    @JvmSynthetic
    override suspend fun requestBy(requestor: KookApiRequestor): T =
        requestData(requestor.client, requestor.authorization)


    public companion object {
        @JvmField
        internal val apiLogger = LoggerFactory.getLogger("love.forte.simbot.kook.api")

        @JvmField
        internal val DEFAULT_JSON = Json {
            isLenient = true
            ignoreUnknownKeys = true
            encodeDefaults = true
        }
    }
}

private class APIContext(
    var apiId: String? = null
) : AbstractCoroutineContextElement(APIContext) {
    /**
     * Key for [APIContext] instance in the coroutine context.
     */
    companion object Key : CoroutineContext.Key<APIContext>

    /**
     * Returns a string representation of the object.
     */
    override fun toString(): String = "APIContext(apiId=$apiId)"
}

private suspend inline fun KookApi<*>.reqForResp(
    client: HttpClient,
    authorization: String,
    preAction: HttpRequestBuilder.() -> Unit = {},
    postAction: HttpRequestBuilder.() -> Unit = {}
): HttpResponse = client.request {
    preAction()
    this.method = this@reqForResp.method
    url(this@reqForResp.url)
    headers[HttpHeaders.Authorization] = authorization
    postAction()
}


/**
 * 使用 [`POST`][HttpMethod.Post] 进行请求的 [KookApi] 基础抽象。
 *
 * [KookPostApi] 对外暴露其用于请求的 [body] 实体。
 *
 */
public abstract class KookPostApi<T> : KookApi<T>() {
    override val method: HttpMethod
        get() = HttpMethod.Post

    @Volatile
    private lateinit var _body: Any

    /**
     * 用于请求的body实体。
     *
     * [body] 内部懒加载，
     * 但不保证任何时间得到的结果始终如一 （无锁）
     *
     * 可通过重写 [body] 来改变此行为
     *
     */
    public open val body: Any?
        get() = if (::_body.isInitialized) _body.takeIf { it !is NULL } else {
            createBody().also {
                _body = it ?: NULL
            }
        }

    /**
     * 用于为 [body] 提供实例构造的函数. 默认得到 null。
     * 当得到null时 [body] 的结果即为null。
     */
    protected open fun createBody(): Any? = null

    /**
     * NULL Marker
     */
    private object NULL
}
