package maxdistructo.droidbot2.commands;

import sx.blah.discord.handle.obj.*;

public class Fortune{

   // @Command(aliases = {"/casino" }, description = "Casino Commands.", usage = "/casino [payday|balance]")
    public static String onFortuneCommand(Object[] args, IMessage message){
        IUser author = message.getAuthor();
        String[] fortunes = {"He who laughs at himself never runs out of things to laugh at.","Man who fart in church sit in his own pew","Man who go to bed with itchy butt wake up with stinky finger","There is no I in team but U in cunt","Gay man always order same, Sum Yung Guy","Man piss in wind, wind piss back","you'll be lucky today",
                "good news is coming your way", "you will be happy!",
                "someone will do something nice for you", "you'll find something",
                "you are not illiterate", "someone will appreciate you today",
                "you will receive a compliment", "someone is missing you",
                "your smile will bring good news"};
        int random = (int)(Math.random() * fortunes.length);
        return author.mention() + "\n Your fortune is: \n " + fortunes[random];
    }
}
