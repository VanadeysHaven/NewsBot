package me.Cooltimmetje.NewsBot.Commands.AdminCommands;

import me.Cooltimmetje.NewsBot.Main;
import me.Cooltimmetje.NewsBot.Utilities.Constants;
import me.Cooltimmetje.NewsBot.Utilities.MessagesUtils;
import sx.blah.discord.handle.obj.IMessage;
import sx.blah.discord.util.DiscordException;
import sx.blah.discord.util.RateLimitException;

/**
 * This can change the name of the bot to something.
 *
 * @author Tim (Cooltimmetje)
 * @version v0.1-ALPHA-DEV
 * @since v0.1-ALPHA-DEV
 */
public class ChangeName {

    public static void run(IMessage message){
        if(Constants.admins.contains(message.getAuthor().getID())){
            String[] args = message.getContent().split(" ");

            if(args.length > 1){
                try {
                    Main.getInstance().getNewsBot().changeUsername(message.getContent().substring(12).trim());

                    MessagesUtils.sendSuccess("Bot name changed to: `" + message.getContent().substring(12).trim() + "`", message.getChannel());
                } catch (DiscordException e) {
                    e.printStackTrace();
                    MessagesUtils.sendError("That didn't work. Try again.", message.getChannel());
                } catch (RateLimitException e) {
                    e.printStackTrace();
                    MessagesUtils.sendError("You got rate limited, try again later.", message.getChannel());
                }
            } else {
                MessagesUtils.sendError("Please specify a name!", message.getChannel());
            }
        }
    }

}
