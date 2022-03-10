package love.forte.simbot.component.kaihieila

import kotlinx.coroutines.flow.*
import love.forte.simbot.*
import love.forte.simbot.definition.*
import love.forte.simbot.message.*
import java.util.concurrent.*
import java.util.stream.*
import kotlin.time.*
import love.forte.simbot.kaiheila.objects.Channel as KhlChannel


/**
 *
 * 开黑啦组件的子频道类型定义。
 *
 * @author ForteScarlet
 */
public interface KaiheilaChannel : Channel, KaiheilaComponentDefinition<KhlChannel> {

    /**
     * 得到当前频道所对应的api模块下的频道对象。
     */
    override val source: KhlChannel


    override val bot: KaiheilaComponentBot
    override val id: ID get() = source.id
    override val icon: String get() = source.icon
    override val name: String get() = source.name
    override val createTime: Timestamp get() = Timestamp.notSupport()
    override val description: String get() = source.description

    override val guildId: ID
    override val currentMember: Int
    override val maximumMember: Int

    @OptIn(Api4J::class)
    override val owner: KaiheilaGuildMember
    override suspend fun owner(): Member = owner
    override val ownerId: ID

    //region members api
    override fun getMember(id: ID): KaiheilaGuildMember?

    override suspend fun member(id: ID): KaiheilaGuildMember?

    @OptIn(Api4J::class)
    override fun getMembers(): Stream<out KaiheilaGuildMember>

    @OptIn(Api4J::class)
    override fun getMembers(groupingId: ID?): Stream<out KaiheilaGuildMember>

    @OptIn(Api4J::class)
    override fun getMembers(groupingId: ID?, limiter: Limiter): Stream<out KaiheilaGuildMember>

    @OptIn(Api4J::class)
    override fun getMembers(limiter: Limiter): Stream<out KaiheilaGuildMember>


    override suspend fun members(groupingId: ID?, limiter: Limiter): Flow<KaiheilaGuildMember>
    //endregion


    //region guild api
    @OptIn(Api4J::class)
    override val guild: KaiheilaGuild

    @OptIn(Api4J::class)
    override val previous: KaiheilaGuild?
        get() = guild

    override suspend fun guild(): KaiheilaGuild = guild
    override suspend fun previous(): KaiheilaGuild = guild
    //endregion


    //region roles api
    @Api4J
    override fun getRoles(groupingId: ID?, limiter: Limiter): Stream<out Role>
    override suspend fun roles(groupingId: ID?, limiter: Limiter): Flow<Role>
    //endregion


    //region send api
    override suspend fun send(text: String): MessageReceipt
    override suspend fun send(message: Message): MessageReceipt
    override suspend fun send(message: MessageContent): MessageReceipt

    @Api4J
    override fun sendBlocking(text: String): MessageReceipt

    @Api4J
    override fun sendBlocking(message: Message): MessageReceipt

    @Api4J
    override fun sendBlocking(message: MessageContent): MessageReceipt
    //endregion

    //region Invalid api
    @Deprecated("Channel mute is not supported", ReplaceWith("false"))
    override suspend fun mute(duration: Duration): Boolean = false

    @Deprecated("Channel mute is not supported", ReplaceWith("false"))
    override suspend fun unmute(): Boolean = false

    @OptIn(Api4J::class)
    @Deprecated("Channel mute is not supported", ReplaceWith("false"))
    override fun muteBlocking(time: Long, unit: TimeUnit): Boolean = false

    @OptIn(Api4J::class)
    @Deprecated("Channel mute is not supported", ReplaceWith("false"))
    override fun unmuteBlocking(): Boolean = false

    @Deprecated("Kaiheila channel has no children", ReplaceWith("emptyFlow()", "kotlinx.coroutines.flow.emptyFlow"))
    override suspend fun children(groupingId: ID?): Flow<Organization> = emptyFlow()

    @Deprecated("Kaiheila channel has no children", ReplaceWith("emptyFlow()", "kotlinx.coroutines.flow.emptyFlow"))
    override suspend fun children(groupingId: ID?, limiter: Limiter): Flow<Organization> = emptyFlow()

    @OptIn(Api4J::class)
    @Deprecated("Kaiheila channel has no children", ReplaceWith("Stream.empty()", "java.util.stream.Stream"))
    override fun getChildren(groupingId: ID?, limiter: Limiter): Stream<Organization> = Stream.empty()

    @OptIn(Api4J::class)
    @Deprecated("Kaiheila channel has no children", ReplaceWith("Stream.empty()", "java.util.stream.Stream"))
    override fun getChildren(): Stream<out Organization> = Stream.empty()

    @OptIn(Api4J::class)
    @Deprecated("Kaiheila channel has no children", ReplaceWith("Stream.empty()", "java.util.stream.Stream"))
    override fun getChildren(groupingId: ID?): Stream<out Organization> = Stream.empty()
    //endregion
}