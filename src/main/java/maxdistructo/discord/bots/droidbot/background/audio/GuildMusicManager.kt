package maxdistructo.discord.bots.droidbot.background.audio

import com.sedmelluq.discord.lavaplayer.player.AudioPlayer
import com.sedmelluq.discord.lavaplayer.player.AudioPlayerManager

class GuildMusicManager
/**
 * Creates a player and a track scheduler.
 * @param manager Audio player manager to use for creating the player.
 */
(manager: AudioPlayerManager) {
    /**
     * Audio player for the guild.
     */
    val player: AudioPlayer
    /**
     * Track scheduler for the player.
     */
    val scheduler: TrackScheduler

    /**
     * @return Wrapper around AudioPlayer to use it as an AudioSendHandler.
     */
    val audioProvider: AudioProvider
        get() = AudioProvider(player)

    init {
        player = manager.createPlayer()
        scheduler = TrackScheduler(player)
        player.addListener(scheduler)
    }
}
