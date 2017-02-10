package me.Cooltimmetje.NewsBot.Commands.AdminCommands;

import me.Cooltimmetje.NewsBot.Main;
import me.Cooltimmetje.NewsBot.Objects.Faction;
import me.Cooltimmetje.NewsBot.Utilities.Constants;
import me.Cooltimmetje.NewsBot.Utilities.FactionManager;
import me.Cooltimmetje.NewsBot.Utilities.MessagesUtils;
import sx.blah.discord.handle.obj.IChannel;
import sx.blah.discord.handle.obj.IMessage;
import sx.blah.discord.handle.obj.IUser;
import sx.blah.discord.handle.obj.Permissions;
import sx.blah.discord.util.DiscordException;
import sx.blah.discord.util.MissingPermissionsException;
import sx.blah.discord.util.RateLimitException;

import java.text.MessageFormat;
import java.util.EnumSet;

/**
 * This class will allow bot admins to add editors to factions.
 *
 * @author Tim (Cooltimmetje)
 * @version v0.1-ALPHA
 * @since v0.1-ALPHA-DEV
 */
public class AddLeaderCommand {

    public static void run(IMessage message) {
        if(Constants.admins.contains(message.getAuthor().getID())){
            if(message.getMentions().size() >= 1){
                IChannel channel = message.getChannel();
                IUser target = message.getMentions().get(0);
                Faction faction = FactionManager.getFaction(channel.getID());
                if(faction != null){
                    if(!channel.getUserOverrides().containsKey(target.getID())){
                        try {
                            channel.overrideUserPermissions(target, EnumSet.of(Permissions.MANAGE_CHANNEL, Permissions.MANAGE_MESSAGES), null);
                            target.addRole(message.getGuild().getRolesByName("Editor").get(0));
                            MessagesUtils.sendSuccess(MessageFormat.format("**{0}** has been added as a editor for faction **{1}**!",
                                    (target.getNicknameForGuild(channel.getGuild()).isPresent() ? target.getNicknameForGuild(channel.getGuild()).get() : target.getName()), faction.getName()), message.getChannel());

                            MessagesUtils.sendPlain(MessageFormat.format(":tophat: Admin **{0}** has added **{1}** `ID: {2}` as a editor for faction **{3}**!",
                                    message.getAuthor().getName() + "#" + message.getAuthor().getDiscriminator(), target.getName(), target.getID(), faction.getName()), Main.getInstance().getNewsBot().getChannelByID(Constants.LOG_CHANNEL));
                        } catch (MissingPermissionsException e) {
                            e.printStackTrace();
                            MessagesUtils.sendError("Hmm... I seem to have no permissions to do that.", channel);
                        } catch (RateLimitException e) {
                            e.printStackTrace();
                            MessagesUtils.sendError("Ratelimits!", channel);
                        } catch (DiscordException e) {
                            e.printStackTrace();
                            MessagesUtils.sendError("Discord fucked up! Try again!", channel);
                        }
                    } else {
                        MessagesUtils.sendError("This person is already a editor of this faction. If you wish to remove them do this through Discord itself.", message.getChannel());
                    }
                } else {
                    MessagesUtils.sendError("This is not a faction channel!", message.getChannel());
                }
            } else {
                MessagesUtils.sendError("Please mention a person. - Usage: !addeditor <mention>", message.getChannel());
            }
        }
    }

}
