package maxdistructo.droidbot2.background.message;

import maxdistructo.droidbot2.BaseBot;
import net.dv8tion.jda.core.entities.Guild;
import net.dv8tion.jda.core.entities.Member;
import sx.blah.discord.api.internal.json.objects.EmbedObject;
import sx.blah.discord.handle.obj.*;
import sx.blah.discord.util.*;

import java.awt.*;
import java.time.LocalDateTime;
import java.time.ZoneId;

import static maxdistructo.droidbot2.BaseBot.jda;

public class Message {
    public IPrivateChannel channel;

    public static EmbedObject simpleEmbed(IUser user, String title, String description, IMessage message){
        EmbedBuilder builder = new EmbedBuilder();
        String authorAvatar = user.getAvatarURL();
        Guild guild = jda.getGuildById(message.getGuild().getLongID());
        Member member = guild.getMemberById(user.getLongID());
        Color color = member.getColor();
        String guildImage = guild.getIconUrl();
        String guildName = guild.getName();


        //builder.appendField(title1, content1, true);
      //  builder.appendField(title2, content2, true);
       // builder.appendField("fieldTitleNotInline", "fieldContentNotInline", false);
      //  builder.appendField(":tada: fieldWithCoolThings :tada:", "[hiddenLink](http://i.imgur.com/Y9utuDe.png)", false);

        builder.withAuthorName(message.getAuthor().getName() + "#" + message.getAuthor().getDiscriminator());
        builder.withAuthorIcon(authorAvatar);

        //builder.withDesc("withDesc");
        builder.withDescription(description);
        builder.withTitle(title);
        builder.withTimestamp(LocalDateTime.now());
       // builder.withUrl("http://i.imgur.com/IrEVKQq.png");
       // builder.withImage("http://i.imgur.com/agsp5Re.png");

        builder.withFooterIcon(guildImage);
        builder.withFooterText(guildName);
        builder.withColor(color);
       // builder.withFooterIcon("http://i.imgur.com/TELh8OT.png");
        //builder.withThumbnail("http://i.imgur.com/7heQOCt.png");

        //builder.appendDesc(" + appendDesc");
      //  builder.appendDescription(" + appendDescription");

        return builder.build();
    }
    public static void sendDM(IUser user, String message){
        IPrivateChannel pm = null;
        try {
            pm = user.getOrCreatePMChannel();
        } catch (RateLimitException | DiscordException e) {
            e.printStackTrace();
        }
        try {
            pm.sendMessage(message);
        } catch (MissingPermissionsException | DiscordException | RateLimitException e) {
            e.printStackTrace();
        }
    }
    public static void sendMessage(IChannel channel, String content){
        try {
            new MessageBuilder(BaseBot.client).withChannel(channel).withContent(content).build();
        } catch (RateLimitException | DiscordException | MissingPermissionsException e) {
            e.printStackTrace();
        }
    }
    public static void sendMessage(IChannel channel, EmbedObject embedded){
            new MessageBuilder(BaseBot.client).withChannel(channel).withEmbed(embedded).build();
    }
}