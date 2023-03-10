/*
 * Copyright (c) 2022-2023. ForteScarlet.
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

import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.KSerializer
import kotlinx.serialization.descriptors.SerialDescriptor
import kotlinx.serialization.modules.PolymorphicModuleBuilder
import kotlinx.serialization.modules.SerializersModule
import kotlinx.serialization.modules.polymorphic
import love.forte.simbot.*
import love.forte.simbot.component.kook.message.*
import love.forte.simbot.kook.objects.KMarkdown
import love.forte.simbot.kook.objects.RawValueKMarkdown
import love.forte.simbot.message.Message

/**
 *
 * Kook 组件的 [Component] 实现。
 *
 * @author ForteScarlet
 */
public class KookComponent @InternalSimbotApi constructor() : Component {
    /**
     * 组件的唯一标识ID。
     */
    override val id: String
        get() = ID_VALUE

    /**
     * Kook 组件中所涉及到的序列化模块。
     *
     */
    override val componentSerializersModule: SerializersModule
        get() = messageSerializersModule

    override fun toString(): String = TO_STRING_VALUE

    override fun equals(other: Any?): Boolean {
        if (other === this) return true
        if (other !is KookComponent) return false

        return id == other.id
    }

    override fun hashCode(): Int {
        return id.hashCode()
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

        private const val TO_STRING_VALUE = "KookComponent(id=$ID_VALUE)"

        /**
         * 组件的ID实例。
         */
        @Deprecated("Unused")
        public val componentID: CharSequenceID = ID_VALUE.ID

        /**
         * 注册器的唯一标识。
         */
        override val key: Attribute<KookComponent> = attribute(ID_VALUE)

        /**
         * [KookComponent] 组件所使用的消息序列化信息。
         *
         * _早期版本的 `khl.xx.xx` 格式的序列化模块请参考 [khlCompatibleMessageSerializersModule] _
         */
        @OptIn(ExperimentalSimbotApi::class)
        @get:JvmStatic
        public val messageSerializersModule: SerializersModule = SerializersModule {
            fun PolymorphicModuleBuilder<KookMessageElement<*>>.include() {
                subclass(KookSimpleAssetMessage::class, KookSimpleAssetMessage.serializer())
                subclass(KookAssetImage::class, KookAssetImage.serializer())
                subclass(KookAtAllHere::class, KookAtAllHere.serializer())
                // KookAttachmentMessage
                subclass(SimpleKookAttachmentMessage::class, SimpleKookAttachmentMessage.serializer())
                subclass(KookAttachmentImage::class, KookAttachmentImage.serializer())
                subclass(KookAttachmentFile::class, KookAttachmentFile.serializer())
                subclass(KookAttachmentVideo::class, KookAttachmentVideo.serializer())

                subclass(KookCardMessage::class, KookCardMessage.serializer())
                subclass(KookKMarkdownMessage::class, KookKMarkdownMessage.serializer())
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
        }

        /**
         * 用于兼容 `khl.xx.xx` 更名为 `kook.xx.xx` 之前的消息序列化模组。
         *
         * 不会默认添加到任何地方，且理论上与 [messageSerializersModule] 相互冲突。如果有需要，请自行使用。
         *
         * **会在beta版本中择机删除，请不要过分依赖。**
         *
         */
        @Deprecated("Only for compatible. Will remove in future.")
        @FragileSimbotApi
        @OptIn(ExperimentalSimbotApi::class)
        public val khlCompatibleMessageSerializersModule: SerializersModule = SerializersModule {
            fun <T> KSerializer<T>.r(): KSerializer<T> {
                return rename { it.replaceFirst("kook.", "khl.") }
            }

            fun PolymorphicModuleBuilder<KookMessageElement<*>>.include() {
                subclass(KookSimpleAssetMessage::class, KookSimpleAssetMessage.serializer().r())
                subclass(KookAssetImage::class, KookAssetImage.serializer().r())
                subclass(KookAtAllHere::class, KookAtAllHere.serializer().r())
                subclass(SimpleKookAttachmentMessage::class, SimpleKookAttachmentMessage.serializer().r())
                subclass(KookAttachmentImage::class, KookAttachmentImage.serializer().r())
                subclass(KookAttachmentFile::class, KookAttachmentFile.serializer().r())
                subclass(KookAttachmentVideo::class, KookAttachmentVideo.serializer().r())
                subclass(KookCardMessage::class, KookCardMessage.serializer().r())
                subclass(KookKMarkdownMessage::class, KookKMarkdownMessage.serializer().r())
            }
            polymorphic(KMarkdown::class) {
                subclass(RawValueKMarkdown::class, RawValueKMarkdown.serializer().rename { "RAW_V_K_MD" }) // 曾经的序列化name为此。
            }

            polymorphic(KookMessageElement::class) {
                include()
            }

            polymorphic(Message.Element::class) {
                include()
            }
        }

        /**
         * 构建一个 [KookComponent] 实例。
         */
        @OptIn(InternalSimbotApi::class)
        override suspend fun create(configurator: KookComponentConfiguration.() -> Unit): KookComponent {
            KookComponentConfiguration.configurator()
            return KookComponent()
        }
    }

}

/*
    下面这些用于 rename 的序列化内容用于使用兼容 `khl.xx.xx` 的消息序列化命名中。
    当 [khlCompatibleMessageSerializersModule] 被移除后，它们也将被移除。
 */

private fun <T> KSerializer<T>.rename(newSerialName: String): KSerializer<T> = RenameKSerializer(newSerialName, this)

@OptIn(ExperimentalSerializationApi::class)
private inline fun <T> KSerializer<T>.rename(newSerialName: (String) -> String): KSerializer<T> {
    return rename(newSerialName(descriptor.serialName))
}

private class RenameKSerializer<T>(serialName: String, serializer: KSerializer<T>) : KSerializer<T> by serializer {
    @OptIn(ExperimentalSerializationApi::class)
    override val descriptor: SerialDescriptor = RenameSerialDescriptor(serialName, serializer.descriptor)
}

@ExperimentalSerializationApi
private class RenameSerialDescriptor(override val serialName: String, serialDescriptor: SerialDescriptor) :
    SerialDescriptor by serialDescriptor


/**
 *
 * [KookComponent] 注册时所使用的配置类。
 *
 * 目前对于 Kook 组件来讲没有需要配置的内容，因此 [KookComponentConfiguration] 中暂无可配置属性。
 *
 */
public object KookComponentConfiguration


/**
 * [KookComponent] 的注册器工厂，用于支持组件的自动加载。
 *
 */
public class KookComponentAutoRegistrarFactory :
    ComponentAutoRegistrarFactory<KookComponent, KookComponentConfiguration> {
    override val registrar: KookComponent.Factory
        get() = KookComponent
}
