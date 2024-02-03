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

package love.forte.simbot.component.kook.message

import love.forte.simbot.kook.api.asset.CreateAssetApi
import love.forte.simbot.kook.api.asset.createCreateAssetApi
import love.forte.simbot.resource.FileResource
import love.forte.simbot.resource.PathResource
import love.forte.simbot.resource.Resource
import love.forte.simbot.resource.URIResource

internal actual fun platformCreateAssetApi(resource: Resource): CreateAssetApi? = when (resource) {
    is URIResource -> createCreateAssetApi(resource.uri)
    is FileResource -> createCreateAssetApi(resource.file)
    is PathResource -> createCreateAssetApi(resource.path)
    else -> null
}
