package maxdistructo.droidbot2.commands

public class Ping{

public static void onPingCommand(IMessage message){
  Message.sendMessage(message.getChannel(), "Pong! Time taken: " + message.getClient().getOurUser().getShard().getResponseTime() + " milliseconds");
}

}
