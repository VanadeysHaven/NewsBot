package me.Cooltimmetje.NewsBot.Commands;

import me.Cooltimmetje.NewsBot.Commands.AdminCommands.ChangeAvatar;
import me.Cooltimmetje.NewsBot.Commands.AdminCommands.ChangeName;
import sx.blah.discord.api.events.EventSubscriber;
import sx.blah.discord.handle.impl.events.MessageReceivedEvent;

/**
 * This class handles the triggering of commands.
 *
 * @author Tim (Cooltimmetje)
 * @version v0.1-ALPHA-DEV
 * @since v0.1-ALPHA-DEV
 */
public class CommandManager {

    @EventSubscriber
    public void onMessage(MessageReceivedEvent event){
        if(!event.getMessage().getChannel().isPrivate()){
            switch (event.getMessage().getContent().split(" ")[0]){
                case "!changename":
                    ChangeName.run(event.getMessage());
                    break;
                case "!changeavatar":
                    ChangeAvatar.run(event.getMessage());
            }
        }
    }

}
