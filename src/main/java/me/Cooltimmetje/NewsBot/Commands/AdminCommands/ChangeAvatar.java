package me.Cooltimmetje.NewsBot.Commands.AdminCommands;

import me.Cooltimmetje.NewsBot.Main;
import me.Cooltimmetje.NewsBot.Utilities.Constants;
import me.Cooltimmetje.NewsBot.Utilities.MessagesUtils;
import sx.blah.discord.handle.obj.IMessage;
import sx.blah.discord.util.DiscordException;
import sx.blah.discord.util.Image;
import sx.blah.discord.util.RateLimitException;

/**
 * Changes the avatar of the bot.
 *
 * @author Tim (Cooltimmetje)
 * @version v0.1-ALPHA-DEV
 * @since v0.1-ALPHA-DEV
 */
public class ChangeAvatar {

    public static void run(IMessage message){
        if(Constants.admins.contains(message.getAuthor().getID())){
            String[] args = message.getContent().split(" ");

            if(args.length > 1){
                if(!args[1].endsWith(".png")){
                    MessagesUtils.sendError("The URL must end with .png", message.getChannel());
                    return;
                }
                try {
                    Main.getInstance().getNewsBot().changeAvatar(Image.forUrl("png", args[1]));

                    MessagesUtils.sendSuccess("Changed image!", message.getChannel());
                } catch (DiscordException e) {
                    e.printStackTrace();
                    MessagesUtils.sendError("That didn't work. Try again.", message.getChannel());
                } catch (RateLimitException e) {
                    e.printStackTrace();
                    MessagesUtils.sendError("You got rate limited, try again later.", message.getChannel());
                }
            } else {
                MessagesUtils.sendError("Please enter a URL to change the avatar to. (URL must end in '.png')", message.getChannel());
            }
        }
    }

}
