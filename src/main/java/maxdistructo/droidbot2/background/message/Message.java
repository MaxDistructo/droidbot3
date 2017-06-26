package maxdistructo.droidbot2.background.message;

import maxdistructo.droidbot2.BaseBot;
import maxdistructo.droidbot2.commands.Insult;
import sx.blah.discord.api.IDiscordClient;
import sx.blah.discord.api.internal.json.objects.EmbedObject;
import sx.blah.discord.handle.obj.IChannel;
import sx.blah.discord.handle.obj.IPrivateChannel;
import sx.blah.discord.handle.obj.IUser;
import sx.blah.discord.util.EmbedBuilder;
import sx.blah.discord.util.MessageBuilder;

public class Message {
    public IPrivateChannel channel;

    public EmbedObject createEmbed(IUser user,String title, String title1, String content1, String title2, String content2, String title3){
        EmbedBuilder builder = new EmbedBuilder();
        String authorName = user.getName();
        String authorAvatar = user.getAvatarURL();

        builder.appendField(title1, content1, true);
        builder.appendField(title2, content2, true);
       // builder.appendField("fieldTitleNotInline", "fieldContentNotInline", false);
      //  builder.appendField(":tada: fieldWithCoolThings :tada:", "[hiddenLink](http://i.imgur.com/Y9utuDe.png)", false);

        builder.withAuthorName(authorName);
        builder.withAuthorIcon(authorAvatar);

        builder.withDesc("withDesc");
        builder.withDescription("withDescription");
        builder.withTitle(title);
        builder.withTimestamp(100);
        builder.withUrl("http://i.imgur.com/IrEVKQq.png");
        builder.withImage("http://i.imgur.com/agsp5Re.png");

        builder.withFooterIcon("http://i.imgur.com/Ch0wy1e.png");
        builder.withFooterText("footerText");
        builder.withFooterIcon("http://i.imgur.com/TELh8OT.png");
        builder.withThumbnail("http://i.imgur.com/7heQOCt.png");

        builder.appendDesc(" + appendDesc");
        builder.appendDescription(" + appendDescription");

        return builder.build();
    }
    public static void sendDM(IUser user, String message){
        IPrivateChannel pm = user.getOrCreatePMChannel();
        pm.sendMessage(message);
    }
    public static void sendMessage(IChannel channel, String content){
        new MessageBuilder(BaseBot.client).withChannel(channel).withContent(content).build();
    }
}
