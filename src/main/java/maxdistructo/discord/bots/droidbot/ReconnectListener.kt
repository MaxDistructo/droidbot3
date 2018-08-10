package maxdistructo.discord.bots.droidbot

import sx.blah.discord.api.events.EventSubscriber
import sx.blah.discord.handle.impl.events.shard.ShardReadyEvent
import sx.blah.discord.handle.obj.ActivityType
import sx.blah.discord.handle.obj.StatusType

class ReconnectListener {
    @EventSubscriber
    fun shardConnect(event : ShardReadyEvent){
        val shard = event.shard
        shard.changePresence(StatusType.ONLINE, ActivityType.PLAYING, "Use !help")
    }
}