package maxdistructo.discord.bots.droidbot.background.filter

import maxdistructo.discord.core.Config
import sx.blah.discord.handle.obj.IMessage

object SwearFilter {

    fun filter(message: IMessage) {
        val config = Config.readServerConfig(message.guild)
        val swearWords = config.getJSONArray("SwearWords").toMutableList()
        if(config.getBoolean("SwearFilter")){
            for(value in swearWords){
                for(value2 in message.content.split(" ")) {
                    if (value == value2){
                        message.delete()
                    }
                }
            }
        }

    }


}
