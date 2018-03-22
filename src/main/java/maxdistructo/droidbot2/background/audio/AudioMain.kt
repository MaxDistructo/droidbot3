package maxdistructo.droidbot2.background.audio

import com.sedmelluq.discord.lavaplayer.player.AudioLoadResultHandler
import com.sedmelluq.discord.lavaplayer.player.AudioPlayerManager
import com.sedmelluq.discord.lavaplayer.player.DefaultAudioPlayerManager
import com.sedmelluq.discord.lavaplayer.source.AudioSourceManagers
import com.sedmelluq.discord.lavaplayer.tools.FriendlyException
import com.sedmelluq.discord.lavaplayer.track.AudioPlaylist
import com.sedmelluq.discord.lavaplayer.track.AudioTrack
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import sx.blah.discord.api.events.EventSubscriber
import sx.blah.discord.handle.audio.IAudioManager
import sx.blah.discord.handle.impl.events.guild.channel.message.MessageReceivedEvent
import sx.blah.discord.handle.obj.IChannel
import sx.blah.discord.handle.obj.IGuild
import sx.blah.discord.handle.obj.IMessage
import sx.blah.discord.handle.obj.IVoiceChannel
import sx.blah.discord.util.MissingPermissionsException

import java.util.HashMap

class AudioMain {
    private val playerManager: AudioPlayerManager
    private val musicManagers: MutableMap<Long, GuildMusicManager>

    init {
        this.musicManagers = HashMap()

        this.playerManager = DefaultAudioPlayerManager()
        AudioSourceManagers.registerRemoteSources(playerManager)
        AudioSourceManagers.registerLocalSource(playerManager)
    }

    @Synchronized
    private fun getGuildAudioPlayer(guild: IGuild): GuildMusicManager {
        val guildId = guild.longID
        var musicManager: GuildMusicManager? = musicManagers[guildId]

        if (musicManager == null) {
            musicManager = GuildMusicManager(playerManager)
            musicManagers[guildId] = musicManager
        }

        guild.audioManager.audioProvider = musicManager.audioProvider

        return musicManager
    }

    @EventSubscriber
    fun onMessageReceived(event: MessageReceivedEvent) {
        val message = event.message

        val command = message.content.split(" ".toRegex(), 2).toTypedArray()
        val guild = message.guild

        if (guild != null) {
            if ("/play" == command[0] && command.size == 2) {
                loadAndPlay(message.channel, command[1])
            } else if ("/skip" == command[0]) {
                skipTrack(message.channel)
            }
        }
    }

    private fun loadAndPlay(channel: IChannel, trackUrl: String) {
        val musicManager = getGuildAudioPlayer(channel.guild)

        playerManager.loadItemOrdered(musicManager, trackUrl, object : AudioLoadResultHandler {
            override fun trackLoaded(track: AudioTrack) {
                sendMessageToChannel(channel, "Adding to queue " + track.info.title)

                play(channel.guild, musicManager, track)
            }

            override fun playlistLoaded(playlist: AudioPlaylist) {
                var firstTrack: AudioTrack? = playlist.selectedTrack

                if (firstTrack == null) {
                    firstTrack = playlist.tracks[0]
                }

                sendMessageToChannel(channel, "Adding to queue " + firstTrack!!.info.title + " (first track of playlist " + playlist.name + ")")

                play(channel.guild, musicManager, firstTrack)
            }

            override fun noMatches() {
                sendMessageToChannel(channel, "Nothing found by $trackUrl")
            }

            override fun loadFailed(exception: FriendlyException) {
                sendMessageToChannel(channel, "Could not play: " + exception.message)
            }
        })
    }

    private fun play(guild: IGuild, musicManager: GuildMusicManager, track: AudioTrack?) {
        connectToFirstVoiceChannel(guild.audioManager)

        musicManager.scheduler.queue(track!!)
    }

    private fun skipTrack(channel: IChannel) {
        val musicManager = getGuildAudioPlayer(channel.guild)
        musicManager.scheduler.nextTrack()

        sendMessageToChannel(channel, "Skipped to next track.")
    }

    private fun sendMessageToChannel(channel: IChannel, message: String) {
        try {
            channel.sendMessage(message)
        } catch (e: Exception) {
            log.warn("Failed to send message {} to {}", message, channel.name, e)
        }

    }

    companion object {

        private val log = LoggerFactory.getLogger(AudioMain::class.java)

        private fun connectToFirstVoiceChannel(audioManager: IAudioManager) {
            for (voiceChannel in audioManager.guild.voiceChannels) {
                if (voiceChannel.isConnected) {
                    return
                }
            }

            for (voiceChannel in audioManager.guild.voiceChannels) {
                try {
                    voiceChannel.join()
                } catch (e: MissingPermissionsException) {
                    log.warn("Cannot enter voice channel {}", voiceChannel.name, e)
                }

            }
        }
    }
}
