package me.Cooltimmetje.NewsBot.Commands.AdminCommands;

import me.Cooltimmetje.NewsBot.Objects.Faction;
import me.Cooltimmetje.NewsBot.Utilities.Constants;
import me.Cooltimmetje.NewsBot.Utilities.FactionManager;
import me.Cooltimmetje.NewsBot.Utilities.MessagesUtils;
import me.Cooltimmetje.NewsBot.Utilities.MiscUtils;
import sx.blah.discord.handle.obj.IMessage;

/**
 * This command edits the logo of a faction.
 *
 * @author Tim (Cooltimmetje)
 * @version v0.1-ALPHA
 * @since v0.1-ALPHA
 */
public class EditLogoCommand {

    public static void run(IMessage message){
        if(Constants.admins.contains(message.getAuthor().getID())){
            String[] args = message.getContent().split(" ");

            if(args.length > 2){
                if(!args[2].endsWith(".png") && !args[2].equalsIgnoreCase("null")){
                    MessagesUtils.sendError("The URL must end with .png", message.getChannel());
                    return;
                }

                if(MiscUtils.isInt(args[1])){
                    Faction faction = FactionManager.getFaction(Integer.parseInt(args[1]));
                    if(faction != null){
                        faction.editMessage(message);
                    } else {
                        MessagesUtils.sendError("This faction does not exist.", message.getChannel());
                    }
                } else {
                    MessagesUtils.sendError("Please enter a valid ID.", message.getChannel());
                }
            } else {
                MessagesUtils.sendError("Invalid arguments: !editlogo <id> <url>", message.getChannel());
            }
        }
    }

}
