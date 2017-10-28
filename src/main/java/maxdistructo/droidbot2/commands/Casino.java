package maxdistructo.droidbot2.commands;

import maxdistructo.droidbot2.background.*;
import maxdistructo.droidbot2.background.message.Message;
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
            Config.newCasino(message);
            message.delete();
            return "You have been registered to join "+ message.getGuild().getName() + " Casino";
        }

        else if(args.length == 2){
            if(args[1].equals("payday") && !Roles.checkForPayday(message)){
                checkMembership(message);
                if(Config.MEMBERSHIP.equals("null")){
                    Config.CHIPS = Config.CHIPS + 1000;
                    Config.writeCasino(message);
                    Message.sendMessage(message.getChannel(),  Message.simpleEmbed(message.getAuthor(), "Casino", "You have collected your 1,000 chip payday", message));
                    payday(message,message.getAuthor());
                    return author.mention(true) + "Your payday is ready!";
                }
                else if (Config.MEMBERSHIP.equals("A")){
                    Config.CHIPS = Config.CHIPS + 1500;
                    Config.writeCasino(message);
                    Message.sendMessage(message.getChannel(), Message.simpleEmbed(message.getAuthor(), "Casino", "You have collected your 1,500 chip payday", message));
                    payday(message,message.getAuthor());
                    return author.mention(true) + "Your payday is ready!";
                }
                else if (Config.MEMBERSHIP.equals("B")){
                    Config.CHIPS = Config.CHIPS + 1500;
                    Config.writeCasino(message);
                    Message.sendMessage(message.getChannel(), Message.simpleEmbed(message.getAuthor(), "Casino", "You have collected your 1,500 chip payday", message));
                    payday(message,message.getAuthor());
                    return author.mention(true) + "Your payday is ready!";
                }
                else if (Config.MEMBERSHIP.equals("C")){
                    Config.CHIPS = Config.CHIPS + 1800;
                    Config.writeCasino(message);
                    Message.sendMessage(message.getChannel(), Message.simpleEmbed(message.getAuthor(), "Casino", "You have collected your 1,800 chip payday", message));
                    payday(message,message.getAuthor());
                    return author.mention(true) + "Your payday is ready!";
                }
                else if (Config.MEMBERSHIP.equals("D")){
                    Config.CHIPS = Config.CHIPS + 2000;
                    Config.writeCasino(message);
                    Message.sendMessage(message.getChannel(), Message.simpleEmbed(message.getAuthor(), "Casino", "You have collected your 2,000 chip payday", message));
                    payday(message,message.getAuthor());
                    return author.mention(true) + "Your payday is ready!";
                }
                else if (Config.MEMBERSHIP.equals("E")){
                    Config.CHIPS = Config.CHIPS + 3000;
                    Config.writeCasino(message);
                    Message.sendMessage(message.getChannel(), Message.simpleEmbed(message.getAuthor(), "Casino", "You have collected your 3,000 chip payday", message));
                    payday(message,message.getAuthor());
                    return author.mention(true) + "Your payday is ready!";
                }
                else if (Config.MEMBERSHIP.equals("F")){
                    Config.CHIPS = Config.CHIPS + 4500;
                    Config.writeCasino(message);
                    Message.sendMessage(message.getChannel(), Message.simpleEmbed(message.getAuthor(), "Casino", "You have collected your 4,500 chip payday", message));
                    payday(message,message.getAuthor());
                    return author.mention(true) + "Your payday is ready!";
                }
                else if (Config.MEMBERSHIP.equals("G")){
                    Config.CHIPS = Config.CHIPS + 6000;
                    Config.writeCasino(message);
                    Message.sendMessage(message.getChannel(), Message.simpleEmbed(message.getAuthor(), "Casino", "You have collected your 6,000 chip payday", message));
                    payday(message,message.getAuthor());
                    return author.mention(true) + "Your payday is ready!";
                }
                else if (Config.MEMBERSHIP.equals("H")){
                    Config.CHIPS = Config.CHIPS + 6000;
                    Config.writeCasino(message);
                    Message.sendMessage(message.getChannel(), Message.simpleEmbed(message.getAuthor(), "Casino", "You have collected your 6,000 chip payday", message));
                    payday(message,message.getAuthor());
                    return author.mention(true) + "Your payday is ready!";
                }
                else if(Config.MEMBERSHIP.equals("I")){
                    Config.CHIPS =  Config.CHIPS + 7500;
                    Config.writeCasino(message);
                    Message.sendMessage(message.getChannel(), Message.simpleEmbed(message.getAuthor(), "Casino", "You have collected your 7,500 chip payday", message));
                    payday(message,message.getAuthor());
                    return author.mention(true) + "Your payday is ready!";
                }
                else if(Config.MEMBERSHIP.equals(":star:")){
                    Config.CHIPS += 7500;
                    Config.writeCasino(message);
                    Message.sendMessage(message.getChannel(), casinoPayday(message, 7500));
                    payday(message,message.getAuthor());
                    return author.mention(true) + "Your payday is ready!";
                }
                else if(Config.MEMBERSHIP.equals(":star::star:")){
                    Config.CHIPS += 9000;
                    Config.writeCasino(message);
                    Message.sendMessage(message.getChannel(), casinoPayday(message, 9000));
                    payday(message,message.getAuthor());
                    return author.mention(true) + "Your payday is ready!";
                }
                else if(Config.MEMBERSHIP.equals(":star::star::star:")){
                    Config.CHIPS += 9000;
                    Config.writeCasino(message);
                    Message.sendMessage(message.getChannel(), casinoPayday(message, 9000));
                    payday(message, message.getAuthor());
                    return author.mention(true) + "Your payday is ready!";
                }
                else if(Config.MEMBERSHIP.equals(":star::star::star::star:")){
                    Config.CHIPS += 12000;
                    Config.writeCasino(message);
                    Message.sendMessage(message.getChannel(), casinoPayday(message, 12000));
                    payday(message, message.getAuthor());
                    return author.mention(true)+ "Your payday is ready!";
                }
                else if(Config.MEMBERSHIP.equals(":star::star::star::star::star:")){

                }
                else{
                    return "Command "+ Listener.prefix +"casino payday has errored. Your balance has not been affected.";
                }
            }
            else if(args[1].equals("balance")){
                Config.readCasino(message);
                return "You have " + nf.format(Config.CHIPS) + " Casino chips";
            }
            else if(args[1].equals("payday") && Roles.checkForPayday(message)){
                return "Please wait until your payday role is removed to receive your next payday.";
            }
        }

        return "Error in command: Casino.";
    }
    public static void checkMembership(IMessage user){
        Config.readCasino(user);
        if(Config.CHIPS < 10000){
            Config.MEMBERSHIP = "null";
        }
        else if(Config.CHIPS > 10000 && Config.CHIPS < 25000){
            Config.MEMBERSHIP = "A";
        }
        else if(Config.CHIPS > 25000 && Config.CHIPS < 60000){
            Config.MEMBERSHIP = "B";
        }
        else if(Config.CHIPS > 60000 && Config.CHIPS < 100000){
            Config.MEMBERSHIP = "C";
        }
        else if(Config.CHIPS > 100000 && Config.CHIPS < 200000){
            Config.MEMBERSHIP = "D";
        }
        else if(Config.CHIPS > 200000 && Config.CHIPS < 350000){
            Config.MEMBERSHIP = "E";
        }
        else if(Config.CHIPS > 350000 && Config.CHIPS < 500000){
            Config.MEMBERSHIP = "F";
        }
        else if(Config.CHIPS > 500000 && Config.CHIPS < 800000){
            Config.MEMBERSHIP = "G";
        }
        else if(Config.CHIPS > 800000 && Config.CHIPS < 1000000){
            Config.MEMBERSHIP = "H";
        }
        else if(Config.CHIPS > 1000000 && Config.CHIPS < 1200000){
            Config.MEMBERSHIP = "I";
        }
        else if(Config.CHIPS > 1200000 && Config.CHIPS < 1500000){
            Config.MEMBERSHIP = ":star:";
        }
        else if(Config.CHIPS > 1500000 && Config.CHIPS < 1800000){
            Config.MEMBERSHIP = ":star::star:";
        }
        else if(Config.CHIPS > 1800000 && Config.CHIPS < 2100000){
            Config.MEMBERSHIP = ":star::star::star:";
        }
        else if(Config.CHIPS > 2100000 && Config.CHIPS < 2500000){
            Config.MEMBERSHIP = ":star::star::star::star:";
        }
        else if(Config.CHIPS > 2500000 && Config.CHIPS < 2900000){
            Config.MEMBERSHIP = ":star::star::star::star::star:";
        }
        else{
            Config.MEMBERSHIP = ":crown:";
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
        Config.readCasino(message);
        EmbedBuilder builder = new EmbedBuilder();
        builder.withTitle("Casino");
        builder.withDesc(".");
        builder.withColor(message.getAuthor().getColorForGuild(message.getGuild()));
        builder.withAuthorName(message.getAuthor().getName() + "#" + message.getAuthor().getDiscriminator());
        builder.withAuthorIcon(message.getAuthor().getAvatarURL());
        builder.withTimestamp(LocalDateTime.now());
        builder.withFooterIcon(message.getGuild().getIconURL());
        builder.withFooterText(message.getGuild().getName());
        builder.appendField("Casino Balance", "" + nf.format(Config.CHIPS), false);
        builder.appendField("Casino Membership", Config.MEMBERSHIP, false);

        return builder.build();
    }
    public static EmbedObject onCasinoInfo(IMessage message, IUser mentioned){
        Config.readCasino(mentioned, message.getGuild());
        EmbedBuilder builder = new EmbedBuilder();
        builder.withColor(message.getAuthor().getColorForGuild(message.getGuild()));
        builder.withAuthorName(message.getAuthor().getName() + "#" + message.getAuthor().getDiscriminator());
        builder.withAuthorIcon(message.getAuthor().getAvatarURL());
        builder.withTimestamp(LocalDateTime.now(ZoneId.systemDefault()));
        builder.withFooterIcon(message.getGuild().getIconURL());
        builder.withFooterText(message.getGuild().getName());
        builder.appendField("Casino Member", "" + mentioned.getName(), false);
        builder.appendField("Casino Balance", "" + nf.format(Config.CHIPS), false);
        builder.appendField("Casino Membership", Config.MEMBERSHIP, false);

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
