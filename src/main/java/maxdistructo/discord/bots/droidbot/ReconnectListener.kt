package maxdistructo.discord.bots.droidbot

import sx.blah.discord.api.events.EventSubscriber
import sx.blah.discord.handle.impl.events.shard.ReconnectFailureEvent
import sx.blah.discord.handle.impl.events.shard.ShardReadyEvent
import sx.blah.discord.handle.obj.ActivityType
import sx.blah.discord.handle.obj.StatusType
import java.lang.Thread.sleep

class ReconnectListener {

    @EventSubscriber
    fun reconnector(event : ReconnectFailureEvent){
        sleep(15000) //Wait for default systems to finish attempting to reconnect before kicking in.
        if(event.isShardAbandoned) { //Check if abandoned or if the default reconnect worked.
            while (true) {
                BaseBot.LOGGER.error("A shard has disconnected. Attempting to log the shard back in.")
                if (event.shard.isLoggedIn) { //Loop until shard is logged back in.
                    break
                }
                else { //If is not logged in, attempt to log the shard back in and wait 1 minute before checking if logged in again.
                    event.shard.login()
                    sleep(60000)
                }
            }
        }
    }

    @EventSubscriber
    fun shardConnect(event : ShardReadyEvent){
        val shard = event.shard
        shard.changePresence(StatusType.ONLINE, ActivityType.PLAYING, "Use !help")
    }


}