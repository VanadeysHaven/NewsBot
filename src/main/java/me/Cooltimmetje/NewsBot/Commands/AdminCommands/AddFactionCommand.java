package me.Cooltimmetje.NewsBot.Commands.AdminCommands;

import me.Cooltimmetje.NewsBot.Utilities.Constants;
import me.Cooltimmetje.NewsBot.Utilities.FactionManager;
import me.Cooltimmetje.NewsBot.Utilities.MessagesUtils;
import sx.blah.discord.handle.obj.IMessage;
import sx.blah.discord.util.DiscordException;
import sx.blah.discord.util.MissingPermissionsException;
import sx.blah.discord.util.RateLimitException;

/**
 * This class handles the creation of a faction.
 *
 * @author Tim (Cooltimmetje)
 * @version v0.1-ALPHA-DEV
 * @since v0.1-ALPHA-DEV
 */
public class AddFactionCommand {

    public static void run(IMessage message){
        if(Constants.admins.contains(message.getAuthor().getID())){
            String[] args = message.getContent().split(" ");

            if(args.length > 1){
                try {
                    FactionManager.createNewFaction(message.getContent().substring(12), message.getChannel());
                } catch (RateLimitException | DiscordException | MissingPermissionsException e) {
                    e.printStackTrace();
                }
            } else {
                MessagesUtils.sendError("Please specify a name!", message.getChannel());
            }
        }
    }

}
