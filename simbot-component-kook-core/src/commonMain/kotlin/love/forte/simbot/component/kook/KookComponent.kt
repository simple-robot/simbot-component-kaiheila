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


import kotlinx.serialization.modules.PolymorphicModuleBuilder
import kotlinx.serialization.modules.SerializersModule
import kotlinx.serialization.modules.polymorphic
import kotlinx.serialization.modules.subclass
import love.forte.simbot.*
import love.forte.simbot.annotations.ExperimentalSimbotAPI
import love.forte.simbot.bot.serializableBotConfigurationPolymorphic
import love.forte.simbot.common.function.ConfigurerFunction
import love.forte.simbot.common.function.invokeBy
import love.forte.simbot.component.Component
import love.forte.simbot.component.ComponentConfigureContext
import love.forte.simbot.component.ComponentFactory
import love.forte.simbot.component.ComponentFactoryProvider
import love.forte.simbot.component.kook.bot.KookBotVerifyInfoConfiguration
import love.forte.simbot.component.kook.message.*
import love.forte.simbot.kook.ExperimentalKookApi
import love.forte.simbot.kook.objects.kmd.KMarkdown
import love.forte.simbot.kook.objects.kmd.RawValueKMarkdown
import love.forte.simbot.message.Message
import kotlin.jvm.JvmStatic

/**
 * KOOK 组件的 [Component] 实现。
 *
 * @author ForteScarlet
 */
public class KookComponent : Component {
    /**
     * 组件的唯一标识ID。
     */
    override val id: String
        get() = ID_VALUE

    /**
     * Kook 组件中所涉及到的序列化模块。
     *
     */
    override val serializersModule: SerializersModule
        get() = messageSerializersModule

    override fun toString(): String = TO_STRING_VALUE

    override fun equals(other: Any?): Boolean {
        if (other === this) return true

        return other is KookComponent
    }

    override fun hashCode(): Int {
        return ID_VALUE_HASH_CODE
    }

    /**
     * 组件 [KookComponent] 的注册器。
     *
     */
    public companion object Factory : ComponentFactory<KookComponent, KookComponentConfiguration> {

        /**
         * 组件 [KookComponent] 的ID
         */
        @Suppress("MemberVisibilityCanBePrivate")
        public const val ID_VALUE: String = "simbot.kook"

        private val ID_VALUE_HASH_CODE = ID_VALUE.hashCode()
        private const val TO_STRING_VALUE = "KookComponent(id=$ID_VALUE)"


        /**
         * 注册器的唯一标识。
         */
        override val key: ComponentFactory.Key = object : ComponentFactory.Key {}

        override fun create(
            context: ComponentConfigureContext,
            configurer: ConfigurerFunction<KookComponentConfiguration>
        ): KookComponent {
            KookComponentConfigurationInstance.invokeBy(configurer)
            return KookComponent()
        }

        /**
         * [KookComponent] 组件所使用的消息序列化信息。
         */
        @OptIn(ExperimentalSimbotAPI::class, ExperimentalKookApi::class)
        @get:JvmStatic
        public val messageSerializersModule: SerializersModule = SerializersModule {
            fun PolymorphicModuleBuilder<KookMessageElement<*>>.include() {
                subclass(KookAsset::class, KookAsset.serializer())
                subclass(KookAssetImage::class, KookAssetImage.serializer())
                subclass(KookAtAllHere::class, KookAtAllHere.serializer())
                // KookAttachmentMessage
                subclass(KookAttachment::class, KookAttachment.serializer())
                subclass(KookAttachmentImage::class, KookAttachmentImage.serializer())
                subclass(KookAttachmentFile::class, KookAttachmentFile.serializer())
                subclass(KookAttachmentVideo::class, KookAttachmentVideo.serializer())

                subclass(KookCardMessage::class, KookCardMessage.serializer())
                subclass(KookKMarkdownMessage::class, KookKMarkdownMessage.serializer())

                subclass(KookTempTarget.Current::class, KookTempTarget.Current.serializer())
                subclass(KookTempTarget.Target::class, KookTempTarget.Target.serializer())
            }

            polymorphic(KMarkdown::class) {
                subclass(RawValueKMarkdown::class, RawValueKMarkdown.serializer())
            }

            polymorphic(KookMessageElement::class) {
                include()
            }

            polymorphic(Message.Element::class) {
                include()
            }

            serializableBotConfigurationPolymorphic {
                subclass(KookBotVerifyInfoConfiguration.serializer())
            }
        }

    }
}

/**
 *
 * [KookComponent] 注册时所使用的配置类。
 *
 * 目前对于 KOOK 组件来讲没有需要配置的内容，因此 [KookComponentConfiguration] 中暂无可配置属性。
 *
 */
public class KookComponentConfiguration internal constructor()

private val KookComponentConfigurationInstance = KookComponentConfiguration()

/**
 * [KookComponent] 的注册器工厂，用于支持组件的自动加载。
 *
 */
public class KookComponentAutoRegistrarFactory :
    ComponentFactoryProvider<KookComponentConfiguration> {
    override fun provide(): ComponentFactory<*, KookComponentConfiguration> =
        KookComponent
}
