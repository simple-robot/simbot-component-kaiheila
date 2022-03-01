/*
 *
 *  * Copyright (c) 2021. ForteScarlet All rights reserved.
 *  * Project  simple-robot
 *  * File     MiraiAvatar.kt
 *  *
 *  * You can contact the author through the following channels:
 *  * github https://github.com/ForteScarlet
 *  * gitee  https://gitee.com/ForteScarlet
 *  * email  ForteScarlet@163.com
 *  * QQ     1149159218
 *
 */

@file:JvmName("KMarkdowns")
@file:Suppress("unused")

package love.forte.simbot.kaiheila.objects

import kotlinx.serialization.*
import love.forte.simbot.kaiheila.objects.AtTarget.*
import love.forte.simbot.kaiheila.objects.AtTarget.User

@Target(AnnotationTarget.FUNCTION, AnnotationTarget.PROPERTY)
@DslMarker
public annotation class KhlMarkdownBuilderDsl

@Target(AnnotationTarget.FUNCTION, AnnotationTarget.PROPERTY)
@DslMarker
public annotation class KhlMarkdownBuilderTopDsl


/**
 * 开黑啦的 [KMarkdown](https://developer.kaiheila.cn/doc/kmarkdown).
 * 此接口中定义的三个属性主要用于 **接收消息** 用。
 *
 * @see RawValueKMarkdown
 * @see KMarkdownBuilder
 * @see KhlMarkdownGrammar
 */
public interface KMarkdown {

    /**
     * 此 markdown 的最终字符串。
     */
    @SerialName("raw_content")
    public val rawContent: String

    /**
     * 提及部分，参考自 [KMarkdown消息](https://developer.kaiheila.cn/doc/event/message#KMarkdown%E6%B6%88%E6%81%AF) 字段
     */
    @SerialName("mention_part")
    public val mentionPart: List<MentionPart>

    /**
     *
     */
    @SerialName("mention_role_part")
    public val mentionRolePart: List<Role>

    // companion object : SerializerModuleRegistrar {
    //     override public fun SerializersModuleBuilder.serializerModule() {
    //         polymorphic(KMarkdown::class) {
    //             subclass(RawValueKMarkdown::class)
    //             default { RawValueKMarkdown.serializer() }
    //         }
    //     }
    // }
}

/**
 * 原始信息 [KMarkdown].
 */
@Serializable
@SerialName(RawValueKMarkdown.SERIAL_NAME)
public data class RawValueKMarkdown(
    @SerialName("raw_content")
    override val rawContent: String,
    @SerialName("mention_part")
    override val mentionPart: List<MentionPart> = emptyList(),
    @SerialName("mention_role_part")
    override val mentionRolePart: List<Role> = emptyList(),
) : KMarkdown {
    internal companion object {
        const val SERIAL_NAME = "RAW_V_K_MD"
    }


}


/**
 * [KMarkdown] 的构建器。
 * 可以通过自定义 [appender] 来提供自定义的字符串拼接器，默认使用 [StringBuilder].
 */
@Suppress("MemberVisibilityCanBePrivate")
public class KMarkdownBuilder(public val appender: Appendable = StringBuilder()) {
    public constructor(capacity: Int) : this(StringBuilder(capacity))

    private fun ap(c: Char): Appendable = appender.also { it.append(c) }
    private fun ap(c: CharSequence): Appendable = appender.also { it.append(c) }

    // var mentionPart: MutableList<MentionPart> = LinkedList()
    // var mentionRolePart: MutableList<Role> = LinkedList()


    //********************************//

    /**
     * 拼接一个文本
     */
    @KhlMarkdownBuilderDsl
    public fun text(text: CharSequence): KMarkdownBuilder = also { KhlMarkdownGrammar.RawText.appendTo(text, appender) }

    /**
     * 拼接一个加粗文本
     */
    @KhlMarkdownBuilderDsl
    public fun bold(value: CharSequence): KMarkdownBuilder = also { KhlMarkdownGrammar.Bold.appendTo(value, appender) }

    /**
     * 拼接一个倾斜文本
     */
    @KhlMarkdownBuilderDsl
    public fun italic(value: CharSequence): KMarkdownBuilder = also { KhlMarkdownGrammar.Italic.appendTo(value, appender) }

    /**
     * 拼接一个加粗倾斜文本
     */
    @KhlMarkdownBuilderDsl
    public fun boldItalic(value: CharSequence): KMarkdownBuilder =
        also { KhlMarkdownGrammar.BoldItalic.appendTo(value, appender) }

    /**
     * 拼接一个删除线
     */
    @KhlMarkdownBuilderDsl
    public fun strikethrough(value: CharSequence): KMarkdownBuilder =
        also { KhlMarkdownGrammar.Strikethrough.appendTo(value, appender) }

    /**
     * 拼接一个链接。
     */
    @KhlMarkdownBuilderDsl
    public fun link(link: MdLink): KMarkdownBuilder = also { KhlMarkdownGrammar.Link.appendTo(link, appender) }

    /**
     * 拼接一个链接。
     * @param name 可以为null.
     */
    @JvmOverloads
    @KhlMarkdownBuilderDsl
    public fun link(name: String? = null, url: String): KMarkdownBuilder =
        also { KhlMarkdownGrammar.Link.appendTo(name, url, appender) }


    /**
     * 追加一个分割线。不会自动在开头换行，但是会在结尾换行，也就是：`---\n`
     */
    @KhlMarkdownBuilderDsl
    public fun divider(): KMarkdownBuilder = also { KhlMarkdownGrammar.Divider.appendTo(appender) }

    /**
     * 引用。如果想要结束引用内容，需要连续换行两次。
     */
    @KhlMarkdownBuilderDsl
    public fun quote(value: CharSequence): KMarkdownBuilder = also { KhlMarkdownGrammar.Quote.appendTo(value, appender) }

    /**
     * 引用，并在结束后自动换行2次。
     */
    @KhlMarkdownBuilderDsl
    public fun quoteAndEnd(value: CharSequence): KMarkdownBuilder =
        also { KhlMarkdownGrammar.Quote.appendToEnd(value, appender) }

    /**
     * 追加下划线内容。
     */
    @KhlMarkdownBuilderDsl
    public fun underscore(value: CharSequence): KMarkdownBuilder =
        also { KhlMarkdownGrammar.Underscore.appendTo(value, appender) }

    /**
     * 追加隐藏内容。
     */
    @KhlMarkdownBuilderDsl
    public fun hide(value: CharSequence): KMarkdownBuilder = also { KhlMarkdownGrammar.Hide.appendTo(value, appender) }

    /**
     * 根据id追加一个emoji
     */
    @KhlMarkdownBuilderDsl
    public fun emoji(id: String): KMarkdownBuilder = also { KhlMarkdownGrammar.Emoji.appendTo(id, appender) }

    /**
     * 服务器表情。
     */
    @KhlMarkdownBuilderDsl
    public fun serverEmoticons(value: MdServerEmoticons): KMarkdownBuilder =
        also { KhlMarkdownGrammar.ServerEmoticons.appendTo(value, appender) }

    /**
     * 服务器表情。
     */
    @KhlMarkdownBuilderDsl
    public fun serverEmoticons(name: CharSequence, id: CharSequence): KMarkdownBuilder =
        also { KhlMarkdownGrammar.ServerEmoticons.appendTo(name, id, appender) }

    /**
     * 提及频道
     */
    @KhlMarkdownBuilderDsl
    public fun channel(id: CharSequence): KMarkdownBuilder = also { KhlMarkdownGrammar.Channel.appendTo(id, appender) }

    /**
     * at
     */
    @KhlMarkdownBuilderDsl
    public fun at(target: AtTarget): KMarkdownBuilder = also { KhlMarkdownGrammar.At.appendTo(target, appender) }

    /**
     * at
     */
    @KhlMarkdownBuilderDsl
    public fun at(target: CharSequence): KMarkdownBuilder = also { KhlMarkdownGrammar.At.appendTo(target, appender) }

    /**
     * role
     */
    @KhlMarkdownBuilderDsl
    public fun role(roleId: CharSequence): KMarkdownBuilder = also { KhlMarkdownGrammar.Role.appendTo(roleId, appender) }

    /**
     * 行内代码
     */
    @KhlMarkdownBuilderDsl
    public fun inlineCode(code: CharSequence): KMarkdownBuilder =
        also { KhlMarkdownGrammar.InlineCode.appendTo(code, appender) }

    /**
     * 代码块
     *
     * 结尾处会自动换行。
     */
    @KhlMarkdownBuilderDsl
    public fun codeBlock(code: MdCodeBlock): KMarkdownBuilder =
        also { KhlMarkdownGrammar.CodeBlock.appendTo(code, appender) }

    /**
     * 代码块
     *
     * 结尾处会自动换行。
     */
    @JvmOverloads
    @KhlMarkdownBuilderDsl
    public fun codeBlock(language: CharSequence? = null, code: CharSequence): KMarkdownBuilder =
        also { KhlMarkdownGrammar.CodeBlock.appendTo(language, code, appender) }


    //********************************//

    /**
     * 一个空格。
     */
    @KhlMarkdownBuilderDsl
    public fun space(): KMarkdownBuilder = also { appender.append(' ') }

    /**
     * 新的一行。
     */
    @KhlMarkdownBuilderDsl
    public fun newLine(): KMarkdownBuilder = also { appender.appendLine() }

    /**
     * 追加一个原始信息到md缓冲器中。
     * 会直接进行拼接，不做处理。
     */
    @KhlMarkdownBuilderDsl
    public fun appendRawMd(raw: String): KMarkdownBuilder = also { ap(raw) }


    /**
     * 通过一个 [KhlMarkdownGrammar] 来实现自定义拼接。
     */
    @KhlMarkdownBuilderDsl
    public fun <P> append(grammar: KhlMarkdownGrammar<P>, params: P): KMarkdownBuilder = also {
        grammar.appendTo(params, appender)
    }

    /**
     * 直接获取原始的markdown字符串。
     */
    public fun buildRaw(): String = appender.toString()


    /**
     * 构建一个 [KMarkdown] 实例。
     */
    public fun build(): KMarkdown = RawValueKMarkdown(buildRaw())


}

@KhlMarkdownBuilderTopDsl
public inline fun KMarkdownBuilder.aroundLine(
    times: Int = 1,
    block: KMarkdownBuilder.() -> Unit,
): KMarkdownBuilder {
    return apply {
        for (i in 1..times) {
            newLine()
        }
        apply(block)
        for (i in 1..times) {
            newLine()
        }
    }
}

@KhlMarkdownBuilderTopDsl
public inline fun KMarkdownBuilder.preLine(times: Int = 1, block: KMarkdownBuilder.() -> Unit): KMarkdownBuilder {
    return apply {
        for (i in 1..times) {
            newLine()
        }
        apply(block)
    }
}

@KhlMarkdownBuilderTopDsl
public inline fun KMarkdownBuilder.postLine(
    times: Int = 1,
    block: KMarkdownBuilder.() -> Unit,
): KMarkdownBuilder {
    return apply {
        apply(block)
        for (i in 1..times) {
            newLine()
        }
    }
}


/**
 * Build kmarkdown for raw string.
 */
@KhlMarkdownBuilderDsl
public inline fun buildRawKMarkdown(block: KMarkdownBuilder.() -> Unit): String {
    return KMarkdownBuilder().apply(block).buildRaw()
}

/**
 * Build [KMarkdown] instance.
 */
@KhlMarkdownBuilderDsl
public inline fun buildKMarkdown(block: KMarkdownBuilder.() -> Unit): KMarkdown {
    return KMarkdownBuilder().apply(block).build()
}


/**
 * 开黑啦 `KMarkdown` 语法封装.
 *
 * @param P 参数类型。一般情况下参数为字符串，但是有些时候参数可能是多个，则或许需要提供一个封装参数。
 */
public interface KhlMarkdownGrammar<P> {

    /**
     * 语法来源。
     *
     * for kt like:
     * ```kotlin
     * Source.Markdown
     * ```
     *
     * for java like:
     * ```java
     * Source.Markdown.INSTANCE
     * ```
     *
     *
     * @see Source
     */
    public val grammarSource: Source

    /**
     * 通过一个 [参数][P] ，将转化结果追加到 [appendable] 中。
     */
    public fun appendTo(param: P, appendable: Appendable)


    /**
     * KMarkdown 的语法来源。
     *
     */
    public sealed class Source(open public val name: String) {

        /** 来源 - markdown官方 */
        public object Markdown : Source("official")

        /** 来源 - 开黑啦官方 */
        public sealed class Kaiheila(name: String) : Source(name) {
            /** 开黑啦官方 - 自定义 */
            public object Custom : Kaiheila("kaiheila-custom")

            /** 开黑啦官方 - emoji */
            public object Emoji : Kaiheila("kaiheila-emoji")
        }

        /** 其他自定义 */
        public data class Custom(override val name: String) : Source(name)
    }


    //**************** 已定义语法 ****************//

    /**
     * 追加一段原始字符串。即没有任何操作。
     */
    public object RawText : BaseMarkdownKhlMarkdownGrammar<CharSequence>() {
        override public fun appendTo(param: CharSequence, appendable: Appendable) {
            appendable.append(param)
        }
    }

    /** 加粗 */
    public object Bold : SymmetricalMarkdownKhlMarkdownGrammar("**")

    /** 斜体 */
    public object Italic : SymmetricalMarkdownKhlMarkdownGrammar("*")

    /** 加粗&斜体 */
    public object BoldItalic : SymmetricalMarkdownKhlMarkdownGrammar("***")

    /** 删除线 */
    public object Strikethrough : SymmetricalMarkdownKhlMarkdownGrammar("~~")

    /**
     * 	链接.
     *
     * 	仅允许 http, https 的链接
     */
    public object Link : BaseMarkdownKhlMarkdownGrammar<MdLink>() {
        // [name](url)

        override fun appendTo(param: MdLink, appendable: Appendable) {
            val (name, url) = param
            appendTo(name, url, appendable)
        }

        @JvmOverloads
        public fun appendTo(name: CharSequence? = null, url: CharSequence, appendable: Appendable) {
            appendable
                .append('[').append(name ?: url)
                .append(']').append('(')
                .append(url).append(')')
        }
    }

    /**
     * 分隔线
     * 会自动在尾部换行。
     */
    public object Divider : ValueAppenderMarkdownKhlMarkdownGrammar<String>("---\n")

    /**
     * 引用.
     *
     * 换行会一直作用，直到遇见两个换行(\n\n),这两个换行实际不会显示换行
     */
    public object Quote : BaseMarkdownKhlMarkdownGrammar<CharSequence>() {
        override public fun appendTo(param: CharSequence, appendable: Appendable) {
            appendable.append("> ").append(param)
        }

        public fun appendToEnd(param: CharSequence, appendable: Appendable) {
            appendTo(param, appendable)
            appendable.appendLine().appendLine()
        }
    }

    /**
     * 下划线
     */
    public object Underscore : SymmetricalKaiheilaCustomKhlMarkdownGrammar("(ins)")


    /**
     * 剧透
     * 内容默认是遮住的，只有用户点击才会显示
     */
    public object Hide : SymmetricalKaiheilaCustomKhlMarkdownGrammar("(spl)")

    /**
     * emoji.
     *
     * 基本与emoji的 [shortcode](https://www.webfx.com/tools/emoji-cheat-sheet/) 写法保持一致
     *
     */
    public object Emoji : SymmetricalKaiheilaEmojiKhlMarkdownGrammar(":")


    /**
     * 服务器表情，需要有服务器发送服务器表情的权限
     *
     * ```
     *  (emj)服务器表情(emj)[服务器表情ID]
     * ```
     *
     */
    public object ServerEmoticons : BaseKaiheilaCustomKhlMarkdownGrammar<MdServerEmoticons>() {
        // (emj)服务器表情(emj)[服务器表情ID]

        override fun appendTo(param: MdServerEmoticons, appendable: Appendable) {
            val (name, id) = param
            appendTo(name, id, appendable)
        }

        public fun appendTo(name: CharSequence, id: CharSequence, appendable: Appendable) {
            appendable.append("(emj)").append(name).append("(emj)[").append(id).append(']')
        }
    }


    /**
     * 频道。提及频道
     * ```
     * (chn)Channel ID(chn)
     * ```
     */
    public object Channel : SymmetricalKaiheilaCustomKhlMarkdownGrammar("(chn)")

    /**
     * \@用户。
     * all 代表 @所有用户，here 代表 @所有在线用户
     *
     * @see AtTarget
     */
    public object At : SymmetricalKaiheilaCustomKhlMarkdownGrammar("(met)")


    /**
     * 	\@某角色所有用户
     */
    public object Role : SymmetricalKaiheilaCustomKhlMarkdownGrammar("(rol)")

    /**
     * 行内代码.
     */
    public object InlineCode : SymmetricalMarkdownKhlMarkdownGrammar("`")


    /**
     * 代码块.
     *
     * 最后会自动换行。
     */
    public object CodeBlock : BaseMarkdownKhlMarkdownGrammar<MdCodeBlock>() {
        override fun appendTo(param: MdCodeBlock, appendable: Appendable) {
            val (language, value) = param
            appendTo(language, value, appendable)
        }

        @JvmOverloads
        public fun appendTo(language: CharSequence? = null, value: CharSequence, appendable: Appendable) {
            appendable.append("```")
            if (language != null) {
                appendable.append(language)
            }
            appendable.appendLine().append(value).appendLine().append("```").appendLine()
        }

    }


}

/** 用于 [超链接语法][KhlMarkdownGrammar.Link] 的参数 */
public data class MdLink
@JvmOverloads
constructor(public val name: CharSequence? = null, public val url: CharSequence)


/** 用于 [服务器表情][KhlMarkdownGrammar.ServerEmoticons] 的参数 */
public data class MdServerEmoticons(public val name: CharSequence, public val id: CharSequence)

/**
 * 可用于 [At][KhlMarkdownGrammar.At] 的目标类型，分为[指定用户][User]、[全体][All]和[在线][Here]。
 */
public sealed class AtTarget : CharSequence {
    @Suppress("MemberVisibilityCanBePrivate")
    public class User(public val id: String) : AtTarget(), CharSequence by id
    public object All : AtTarget(), CharSequence by "all"
    public object Here : AtTarget(), CharSequence by "here"

}

/**
 * 代码块参数
 */
public data class MdCodeBlock
@JvmOverloads
constructor(public val language: CharSequence? = null, public val value: CharSequence)


/**
 * 通过一个单独的 [语法][KhlMarkdownGrammar] 构建一个单独的 [String] 结果。
 */
public fun <P> KhlMarkdownGrammar<P>.build(param: P): String =
    StringBuilder().also { b -> this.appendTo(param, b) }.toString()


/**
 * 提供一个简单的 [KhlMarkdownGrammar] 抽象类, 并将 [grammarSource] 提取至构造中。
 *
 * @see BaseMarkdownKhlMarkdownGrammar
 * @see BaseKaiheilaCustomKhlMarkdownGrammar
 * @see BaseKaiheilaEmojiKhlMarkdownGrammar
 * @see BaseCustomKhlMarkdownGrammar
 */
public abstract class BaseKhlMarkdownGrammar<P>(override val grammarSource: KhlMarkdownGrammar.Source) :
    KhlMarkdownGrammar<P>

//**************** 基于 source 的部分整合 ****************//

public abstract class BaseMarkdownKhlMarkdownGrammar<P> : BaseKhlMarkdownGrammar<P>(KhlMarkdownGrammar.Source.Markdown)
public abstract class BaseKaiheilaCustomKhlMarkdownGrammar<P> :
    BaseKhlMarkdownGrammar<P>(KhlMarkdownGrammar.Source.Kaiheila.Custom)

public abstract class BaseKaiheilaEmojiKhlMarkdownGrammar<P> :
    BaseKhlMarkdownGrammar<P>(KhlMarkdownGrammar.Source.Kaiheila.Emoji)

public abstract class BaseCustomKhlMarkdownGrammar<P>(source: KhlMarkdownGrammar.Source.Custom) :
    BaseKhlMarkdownGrammar<P>(source) {
    public constructor(name: String) : this(KhlMarkdownGrammar.Source.Custom(name))
}


/**
 * 前后追加元素对称的 Grammar. 例如：`Hello World` -> `**Hello World**` 这种，向前后追加相同元素的内容的，即认为为对称Grammar。
 *
 * 提供前后一致的 [wing] 值.
 *
 * @see SymmetricalMarkdownKhlMarkdownGrammar
 * @see SymmetricalKaiheilaCustomKhlMarkdownGrammar
 * @see SymmetricalKaiheilaEmojiKhlMarkdownGrammar
 * @see SymmetricalCustomKhlMarkdownGrammar
 */
public abstract class SymmetricalKhlMarkdownGrammar(
    private val wing: CharSequence,
    grammarSource: KhlMarkdownGrammar.Source,
) : BaseKhlMarkdownGrammar<CharSequence>(grammarSource) {
    override fun appendTo(param: CharSequence, appendable: Appendable) {
        appendable.append(wing).append(param).append(wing)
    }
}

public abstract class SymmetricalMarkdownKhlMarkdownGrammar(wing: CharSequence) :
    SymmetricalKhlMarkdownGrammar(wing, KhlMarkdownGrammar.Source.Markdown)

public abstract class SymmetricalKaiheilaCustomKhlMarkdownGrammar(wing: CharSequence) :
    SymmetricalKhlMarkdownGrammar(wing, KhlMarkdownGrammar.Source.Kaiheila.Custom)

public abstract class SymmetricalKaiheilaEmojiKhlMarkdownGrammar(wing: CharSequence) :
    SymmetricalKhlMarkdownGrammar(wing, KhlMarkdownGrammar.Source.Kaiheila.Emoji)

public abstract class SymmetricalCustomKhlMarkdownGrammar(
    wing: CharSequence,
    source: KhlMarkdownGrammar.Source.Custom,
) :
    SymmetricalKhlMarkdownGrammar(wing, source) {
    public constructor(wing: CharSequence, name: String) : this(wing, KhlMarkdownGrammar.Source.Custom(name))
}


/**
 * 不需要参数的语法，例如一个删除线，它不需要任何内容，也不应该有任何内容。
 *
 * @see NoParamMarkdownKhlMarkdownGrammar
 * @see NoParamKaiheilaCustomKhlMarkdownGrammar
 * @see NoParamKaiheilaEmojiKhlMarkdownGrammar
 * @see NoParamCustomKhlMarkdownGrammar
 */
public abstract class NoParamKhlMarkdownGrammar(grammarSource: KhlMarkdownGrammar.Source) :
    BaseKhlMarkdownGrammar<Nothing?>(grammarSource) {
    override fun appendTo(param: Nothing?, appendable: Appendable) {
        appendTo(appendable)
    }

    /**
     * 追加元素.
     */
    public abstract fun appendTo(appendable: Appendable)
}

public abstract class NoParamMarkdownKhlMarkdownGrammar : NoParamKhlMarkdownGrammar(KhlMarkdownGrammar.Source.Markdown)
public abstract class NoParamKaiheilaCustomKhlMarkdownGrammar :
    NoParamKhlMarkdownGrammar(KhlMarkdownGrammar.Source.Kaiheila.Custom)

public abstract class NoParamKaiheilaEmojiKhlMarkdownGrammar :
    NoParamKhlMarkdownGrammar(KhlMarkdownGrammar.Source.Kaiheila.Emoji)

public abstract class NoParamCustomKhlMarkdownGrammar(source: KhlMarkdownGrammar.Source.Custom) :
    NoParamKhlMarkdownGrammar(source) {
    public constructor(name: String) : this(KhlMarkdownGrammar.Source.Custom(name))
}


/**
 * [NoParamKhlMarkdownGrammar] 的子集，直接拼接一个固定的元素.
 */
public abstract class ValueAppenderKhlMarkdownGrammar<V : CharSequence>(
    private val value: V,
    grammarSource: KhlMarkdownGrammar.Source,
) : NoParamKhlMarkdownGrammar(grammarSource) {
    override fun appendTo(appendable: Appendable) {
        appendable.append(value)
    }
}


public abstract class ValueAppenderMarkdownKhlMarkdownGrammar<V : CharSequence>(value: V) :
    ValueAppenderKhlMarkdownGrammar<V>(value, KhlMarkdownGrammar.Source.Markdown)

public abstract class ValueAppenderKaiheilaCustomKhlMarkdownGrammar<V : CharSequence>(value: V) :
    ValueAppenderKhlMarkdownGrammar<V>(value, KhlMarkdownGrammar.Source.Kaiheila.Custom)

public abstract class ValueAppenderKaiheilaEmojiKhlMarkdownGrammar<V : CharSequence>(value: V) :
    ValueAppenderKhlMarkdownGrammar<V>(value, KhlMarkdownGrammar.Source.Kaiheila.Emoji)

public abstract class ValueAppenderCustomKhlMarkdownGrammar<V : CharSequence>(
    value: V,
    source: KhlMarkdownGrammar.Source.Custom,
) : ValueAppenderKhlMarkdownGrammar<V>(value, source) {
    public constructor(value: V, name: String) : this(value, KhlMarkdownGrammar.Source.Custom(name))
}