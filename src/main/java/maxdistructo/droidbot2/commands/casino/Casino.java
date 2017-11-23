package maxdistructo.droidbot2.commands.casino;

import maxdistructo.droidbot2.background.*;
import maxdistructo.droidbot2.core.message.Message;
import maxdistructo.droidbot2.core.Roles;
import sx.blah.discord.api.internal.json.objects.EmbedObject;
import sx.blah.discord.handle.obj.*;
import sx.blah.discord.util.EmbedBuilder;

import java.text.NumberFormat;
import java.time.LocalDateTime;
import java.time.ZoneId;

public class Casino{
    //@Command(aliases = {"/casino" }, description = "Casino Commands.", usage = "/casino [payday|balance]")
    private static NumberFormat nf = NumberFormat.getInstance();
    public static String onCasinoCommand(Object[] args, IMessage message, IUser mentioned) {
        IUser author = message.getAuthor();

        if(args[1].equals("join")){
            CasinoConfig.newCasino(message);
            message.delete();
            return "You have been registered to join "+ message.getGuild().getName() + " Casino";
        }

        else if(args.length == 2){
            if(args[1].equals("payday") && !Roles.checkForPayday(message)){
                checkMembership(message);
                switch (CasinoConfig.MEMBERSHIP) {
                    case "null":
                        CasinoConfig.CHIPS = CasinoConfig.CHIPS + 1000;
                        CasinoConfig.writeCasino(message);
                        Message.sendMessage(message.getChannel(), Message.simpleEmbed(message.getAuthor(), "Casino", "You have collected your 1,000 chip payday", message));
                        payday(message, message.getAuthor());
                        return author.mention(true) + "Your payday is ready!";
                    case "A":
                        CasinoConfig.CHIPS = CasinoConfig.CHIPS + 1500;
                        CasinoConfig.writeCasino(message);
                        Message.sendMessage(message.getChannel(), Message.simpleEmbed(message.getAuthor(), "Casino", "You have collected your 1,500 chip payday", message));
                        payday(message, message.getAuthor());
                        return author.mention(true) + "Your payday is ready!";
                    case "B":
                        CasinoConfig.CHIPS = CasinoConfig.CHIPS + 1500;
                        CasinoConfig.writeCasino(message);
                        Message.sendMessage(message.getChannel(), Message.simpleEmbed(message.getAuthor(), "Casino", "You have collected your 1,500 chip payday", message));
                        payday(message, message.getAuthor());
                        return author.mention(true) + "Your payday is ready!";
                    case "C":
                        CasinoConfig.CHIPS = CasinoConfig.CHIPS + 1800;
                        CasinoConfig.writeCasino(message);
                        Message.sendMessage(message.getChannel(), Message.simpleEmbed(message.getAuthor(), "Casino", "You have collected your 1,800 chip payday", message));
                        payday(message, message.getAuthor());
                        return author.mention(true) + "Your payday is ready!";
                    case "D":
                        CasinoConfig.CHIPS = CasinoConfig.CHIPS + 2000;
                        CasinoConfig.writeCasino(message);
                        Message.sendMessage(message.getChannel(), Message.simpleEmbed(message.getAuthor(), "Casino", "You have collected your 2,000 chip payday", message));
                        payday(message, message.getAuthor());
                        return author.mention(true) + "Your payday is ready!";
                    case "E":
                        CasinoConfig.CHIPS = CasinoConfig.CHIPS + 3000;
                        CasinoConfig.writeCasino(message);
                        Message.sendMessage(message.getChannel(), Message.simpleEmbed(message.getAuthor(), "Casino", "You have collected your 3,000 chip payday", message));
                        payday(message, message.getAuthor());
                        return author.mention(true) + "Your payday is ready!";
                    case "F":
                        CasinoConfig.CHIPS = CasinoConfig.CHIPS + 4500;
                        CasinoConfig.writeCasino(message);
                        Message.sendMessage(message.getChannel(), Message.simpleEmbed(message.getAuthor(), "Casino", "You have collected your 4,500 chip payday", message));
                        payday(message, message.getAuthor());
                        return author.mention(true) + "Your payday is ready!";
                    case "G":
                        CasinoConfig.CHIPS = CasinoConfig.CHIPS + 6000;
                        CasinoConfig.writeCasino(message);
                        Message.sendMessage(message.getChannel(), Message.simpleEmbed(message.getAuthor(), "Casino", "You have collected your 6,000 chip payday", message));
                        payday(message, message.getAuthor());
                        return author.mention(true) + "Your payday is ready!";
                    case "H":
                        CasinoConfig.CHIPS = CasinoConfig.CHIPS + 6000;
                        CasinoConfig.writeCasino(message);
                        Message.sendMessage(message.getChannel(), Message.simpleEmbed(message.getAuthor(), "Casino", "You have collected your 6,000 chip payday", message));
                        payday(message, message.getAuthor());
                        return author.mention(true) + "Your payday is ready!";
                    case "I":
                        CasinoConfig.CHIPS = CasinoConfig.CHIPS + 7500;
                        CasinoConfig.writeCasino(message);
                        Message.sendMessage(message.getChannel(), Message.simpleEmbed(message.getAuthor(), "Casino", "You have collected your 7,500 chip payday", message));
                        payday(message, message.getAuthor());
                        return author.mention(true) + "Your payday is ready!";
                    case ":star:":
                        CasinoConfig.CHIPS += 7500;
                        CasinoConfig.writeCasino(message);
                        Message.sendMessage(message.getChannel(), casinoPayday(message, 7500));
                        payday(message, message.getAuthor());
                        return author.mention(true) + "Your payday is ready!";
                    case ":star::star:":
                        CasinoConfig.CHIPS += 9000;
                        CasinoConfig.writeCasino(message);
                        Message.sendMessage(message.getChannel(), casinoPayday(message, 9000));
                        payday(message, message.getAuthor());
                        return author.mention(true) + "Your payday is ready!";
                    case ":star::star::star:":
                        CasinoConfig.CHIPS += 9000;
                        CasinoConfig.writeCasino(message);
                        Message.sendMessage(message.getChannel(), casinoPayday(message, 9000));
                        payday(message, message.getAuthor());
                        return author.mention(true) + "Your payday is ready!";
                    case ":star::star::star::star:":
                        CasinoConfig.CHIPS += 12000;
                        CasinoConfig.writeCasino(message);
                        Message.sendMessage(message.getChannel(), casinoPayday(message, 12000));
                        payday(message, message.getAuthor());
                        return author.mention(true) + "Your payday is ready!";
                    case ":star::star::star::star::star:":
                        CasinoConfig.CHIPS += 12000;
                        CasinoConfig.writeCasino(message);
                        Message.sendMessage(message.getChannel(), casinoPayday(message, 12000));
                        payday(message, message.getAuthor());
                        return author.mention(true) + "Your payday is ready!";
                    case ":crown:":
                        CasinoConfig.CHIPS += 15000;
                        CasinoConfig.writeCasino(message);
                        Message.sendMessage(message.getChannel(), casinoPayday(message, 15000));
                        payday(message, message.getAuthor());
                        return author.mention(true) + "Your payday is ready!";
                    default:
                        return "Command " + Listener.prefix + "casino payday has errored. Your balance has not been affected.";
                }
            }
            else if(args[1].equals("balance")){
                CasinoConfig.readCasino(message);
                return "You have " + nf.format(CasinoConfig.CHIPS) + " Casino chips";
            }
            else if(args[1].equals("payday") && Roles.checkForPayday(message)){
                return "Please wait until your payday role is removed to receive your next payday.";
            }
        }

        return "Error in command: Casino.";
    }
    public static void checkMembership(IMessage user){
        CasinoConfig.readCasino(user);
        if(CasinoConfig.CHIPS < 10000){
            CasinoConfig.MEMBERSHIP = "null";
        }
        else if(CasinoConfig.CHIPS > 10000 && CasinoConfig.CHIPS < 25000){
            CasinoConfig.MEMBERSHIP = "A";
        }
        else if(CasinoConfig.CHIPS > 25000 && CasinoConfig.CHIPS < 60000){
            CasinoConfig.MEMBERSHIP = "B";
        }
        else if(CasinoConfig.CHIPS > 60000 && CasinoConfig.CHIPS < 100000){
            CasinoConfig.MEMBERSHIP = "C";
        }
        else if(CasinoConfig.CHIPS > 100000 && CasinoConfig.CHIPS < 200000){
            CasinoConfig.MEMBERSHIP = "D";
        }
        else if(CasinoConfig.CHIPS > 200000 && CasinoConfig.CHIPS < 350000){
            CasinoConfig.MEMBERSHIP = "E";
        }
        else if(CasinoConfig.CHIPS > 350000 && CasinoConfig.CHIPS < 500000){
            CasinoConfig.MEMBERSHIP = "F";
        }
        else if(CasinoConfig.CHIPS > 500000 && CasinoConfig.CHIPS < 800000){
            CasinoConfig.MEMBERSHIP = "G";
        }
        else if(CasinoConfig.CHIPS > 800000 && CasinoConfig.CHIPS < 1000000){
            CasinoConfig.MEMBERSHIP = "H";
        }
        else if(CasinoConfig.CHIPS > 1000000 && CasinoConfig.CHIPS < 1200000){
            CasinoConfig.MEMBERSHIP = "I";
        }
        else if(CasinoConfig.CHIPS > 1200000 && CasinoConfig.CHIPS < 1500000){
            CasinoConfig.MEMBERSHIP = ":star:";
        }
        else if(CasinoConfig.CHIPS > 1500000 && CasinoConfig.CHIPS < 1800000){
            CasinoConfig.MEMBERSHIP = ":star::star:";
        }
        else if(CasinoConfig.CHIPS > 1800000 && CasinoConfig.CHIPS < 2100000){
            CasinoConfig.MEMBERSHIP = ":star::star::star:";
        }
        else if(CasinoConfig.CHIPS > 2100000 && CasinoConfig.CHIPS < 2500000){
            CasinoConfig.MEMBERSHIP = ":star::star::star::star:";
        }
        else if(CasinoConfig.CHIPS > 2500000 && CasinoConfig.CHIPS < 2900000){
            CasinoConfig.MEMBERSHIP = ":star::star::star::star::star:";
        }
        else{
            CasinoConfig.MEMBERSHIP = ":crown:";
        }
    }
    private static void payday(IMessage message, IUser mentioned){
        Roles.applyPayday(message,mentioned);
        try {
            Thread.sleep(21600000);
        } catch (InterruptedException e) {
            Roles.removePayday(message, mentioned);
            e.printStackTrace();
        }
        Roles.removePayday(message, mentioned);
    }

    public static EmbedObject onCasinoInfo(IMessage message){
        CasinoConfig.readCasino(message);
        EmbedBuilder builder = new EmbedBuilder();
        builder.withTitle("Casino");
        builder.withDesc(".");
        builder.withColor(message.getAuthor().getColorForGuild(message.getGuild()));
        builder.withAuthorName(message.getAuthor().getName() + "#" + message.getAuthor().getDiscriminator());
        builder.withAuthorIcon(message.getAuthor().getAvatarURL());
        builder.withTimestamp(LocalDateTime.now());
        builder.withFooterIcon(message.getGuild().getIconURL());
        builder.withFooterText(message.getGuild().getName());
        builder.appendField("Casino Balance", "" + nf.format(CasinoConfig.CHIPS), false);
        builder.appendField("Casino Membership", CasinoConfig.MEMBERSHIP, false);

        return builder.build();
    }
    public static EmbedObject onCasinoInfo(IMessage message, IUser mentioned){
        CasinoConfig.readCasino(mentioned, message.getGuild());
        EmbedBuilder builder = new EmbedBuilder();
        builder.withColor(message.getAuthor().getColorForGuild(message.getGuild()));
        builder.withAuthorName(message.getAuthor().getName() + "#" + message.getAuthor().getDiscriminator());
        builder.withAuthorIcon(message.getAuthor().getAvatarURL());
        builder.withTimestamp(LocalDateTime.now(ZoneId.systemDefault()));
        builder.withFooterIcon(message.getGuild().getIconURL());
        builder.withFooterText(message.getGuild().getName());
        builder.appendField("Casino Member", "" + mentioned.getName(), false);
        builder.appendField("Casino Balance", "" + nf.format(CasinoConfig.CHIPS), false);
        builder.appendField("Casino Membership", CasinoConfig.MEMBERSHIP, false);

        return builder.build();
    }
    public static EmbedObject casinoPayday(IMessage message, Object payday){
        EmbedBuilder builder = new EmbedBuilder();
        builder.withTitle("Casino");
        builder.withDesc("You have collected your " + nf.format(payday) + " payday");
        builder.withColor(message.getAuthor().getColorForGuild(message.getGuild()));
        builder.withAuthorName(message.getAuthor().getName() + "#" + message.getAuthor().getDiscriminator());
        builder.withAuthorIcon(message.getAuthor().getAvatarURL());
        builder.withTimestamp(LocalDateTime.now());
        builder.withFooterIcon(message.getGuild().getIconURL());
        builder.withFooterText(message.getGuild().getName());

        return builder.build();

    }


}
