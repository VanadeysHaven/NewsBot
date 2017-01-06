package me.Cooltimmetje.NewsBot.Commands;

import me.Cooltimmetje.NewsBot.Main;
import me.Cooltimmetje.NewsBot.Objects.Faction;
import me.Cooltimmetje.NewsBot.Utilities.Constants;
import me.Cooltimmetje.NewsBot.Utilities.FactionManager;
import me.Cooltimmetje.NewsBot.Utilities.MessagesUtils;
import me.Cooltimmetje.NewsBot.Utilities.MiscUtils;
import sx.blah.discord.handle.obj.IGuild;
import sx.blah.discord.handle.obj.IMessage;
import sx.blah.discord.util.DiscordException;
import sx.blah.discord.util.MissingPermissionsException;
import sx.blah.discord.util.RateLimitException;

import java.text.MessageFormat;

/**
 * This class will allow other people to choose a faction that they would like access to.
 *
 * @author Tim (Cooltimmetje)
 * @version v0.1-ALPHA-DEV
 * @since v0.1-ALPHA-DEV
 */
public class SetFactionCommand {

    /**
     * This will add a user to a faction.
     *
     * @param message Message that triggered the command.
     */
    public static void join(IMessage message){
        String[] args = message.getContent().split(" ");
        if(args.length > 1){
            if(MiscUtils.isInt(args[1])){
                Faction faction = FactionManager.getFaction(Integer.parseInt(args[1]));
                if(faction != null){
                    IGuild guild = Main.getInstance().getNewsBot().getGuildByID(Constants.SERVER_ID);
                    if(!message.getAuthor().getRolesForGuild(guild).contains(guild.getRoleByID(faction.getRoleId()))){
                        try {
                            message.getAuthor().addRole(guild.getRoleByID(faction.getRoleId()));
                        } catch (MissingPermissionsException e) {
                            e.printStackTrace();
                            MessagesUtils.sendError("Something happened with permissions, try again. If this happens again, then contact a server admin!", message.getChannel());
                        } catch (RateLimitException e) {
                            e.printStackTrace();
                            MessagesUtils.sendError("The bot has been rate limited, try again in a bit.", message.getChannel());
                        } catch (DiscordException e) {
                            e.printStackTrace();
                            MessagesUtils.sendError("Discord decided to be a ass, try again or contact a server admin.", message.getChannel());
                        }
                        MessagesUtils.sendSuccess("You joined the faction **" + faction.getName() + "**! You can now access their channel!", message.getChannel());
                        MessagesUtils.sendPlain(MessageFormat.format(":fast_forward: **{0}#{1}** `ID: {2}` joined faction **{3}**!",
                                message.getAuthor().getName(), message.getAuthor().getDiscriminator(), message.getAuthor().getID(), faction.getName()), guild.getChannelByID(Constants.LOG_CHANNEL));
                    } else {
                        MessagesUtils.sendError("You are already in that faction, to leave you can do !leavefaction " + faction.getId(), message.getChannel());
                    }
                } else {
                    MessagesUtils.sendError("That faction doesn't exist! Please double check the ID. You can find the ID's in the faction-list channel.", message.getChannel());
                }
            } else {
                MessagesUtils.sendError("Please enter a faction ID. You can find the ID's in the faction-list channel.", message.getChannel());
            }
        } else {
            MessagesUtils.sendError("Please enter a faction ID. You can find the ID's in the faction-list channel.", message.getChannel());
        }
    }

    /**
     * This will remove a user from a faction.
     *
     * @param message Message that triggered the command.
     */
    public static void leave(IMessage message){
        String[] args = message.getContent().split(" ");
        if(args.length > 1){
            if(MiscUtils.isInt(args[1])){
                Faction faction = FactionManager.getFaction(Integer.parseInt(args[1]));
                if(faction != null){
                    IGuild guild = Main.getInstance().getNewsBot().getGuildByID(Constants.SERVER_ID);
                    if(message.getAuthor().getRolesForGuild(guild).contains(guild.getRoleByID(faction.getRoleId()))){
                        try {
                            message.getAuthor().removeRole(guild.getRoleByID(faction.getRoleId()));
                        } catch (MissingPermissionsException e) {
                            e.printStackTrace();
                            MessagesUtils.sendError("Something happened with permissions, try again. If this happens again, then contact a server admin!", message.getChannel());
                        } catch (RateLimitException e) {
                            e.printStackTrace();
                            MessagesUtils.sendError("The bot has been rate limited, try again in a bit.", message.getChannel());
                        } catch (DiscordException e) {
                            e.printStackTrace();
                            MessagesUtils.sendError("Discord decided to be a ass, try again or contact a server admin.", message.getChannel());
                        }
                        MessagesUtils.sendSuccess("You left the faction **" + faction.getName() + "**! You can no longer access their channel!", message.getChannel());
                        MessagesUtils.sendPlain(MessageFormat.format(":rewind: **{0}#{1}** `ID: {2}` left faction **{3}**!",
                                message.getAuthor().getName(), message.getAuthor().getDiscriminator(), message.getAuthor().getID(), faction.getName()), guild.getChannelByID(Constants.LOG_CHANNEL));
                    } else {
                        MessagesUtils.sendError("You are not a member of that faction, to join you can do !joinfaction " + faction.getId(), message.getChannel());
                    }
                } else {
                    MessagesUtils.sendError("That faction doesn't exist! Please double check the ID. You can find the ID's in the faction-list channel.", message.getChannel());
                }
            } else {
                MessagesUtils.sendError("Please enter a faction ID. You can find the ID's in the faction-list channel.", message.getChannel());
            }
        } else {
            MessagesUtils.sendError("Please enter a faction ID. You can find the ID's in the faction-list channel.", message.getChannel());
        }
    }

}
