package maxdistructo.droidbot2.commands;

import maxdistructo.droidbot2.background.message.Message;
import sx.blah.discord.handle.obj.IMessage;

public class Ping{

public static void onPingCommand(IMessage message){
  Message.sendMessage(message.getChannel(), "Pong! Time taken: " + message.getClient().getOurUser().getShard().getResponseTime() + " milliseconds");
}

}
