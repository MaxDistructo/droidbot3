package maxdistructo.discord.bots.droidbot.background

import maxdistructo.discord.core.Config
import maxdistructo.discord.core.Perms
import maxdistructo.discord.core.Utils
import maxdistructo.discord.core.message.Message
import sx.blah.discord.api.events.EventSubscriber
import sx.blah.discord.handle.impl.events.guild.channel.message.MessageReceivedEvent


class AdminListener{
  
  @EventSubscriber
  fun onMessageReceivedEvent(event: MessageReceivedEvent){
      val message = event.message
      val guild = message.guild
      val prefix = Config.readPrefix() // To allow for easy compatability with old code. All new code will reference
      val channelMention = Utils.getMentionedChannel(message)
      val mentioned = Utils.getMentionedUser(message)
      val casinoEnable = false
      val content = message.content
      val messageContent = content.split(" ".toRegex()).dropLastWhile { it.isEmpty() }.toTypedArray()
      val messageContentAny = content.split(" ".toRegex()).dropLastWhile { it.isEmpty() }.toTypedArray() as Array<Any>

      if(messageContent.isNotEmpty() && Perms.checkMod(message) && messageContent[0].contains("!")){
        try{
          when(messageContent[0].toString().replace(prefix + "@", ""))
          {
            
          }
          
        }
        catch(e : Exception){
          Message.throwError(e)
        }
      }
  }

}
