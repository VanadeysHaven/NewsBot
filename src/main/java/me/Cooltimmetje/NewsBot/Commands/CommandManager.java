package me.Cooltimmetje.NewsBot.Commands;

import me.Cooltimmetje.NewsBot.Commands.AdminCommands.*;
import me.Cooltimmetje.NewsBot.Utilities.MessagesUtils;
import sx.blah.discord.api.events.EventSubscriber;
import sx.blah.discord.handle.impl.events.MessageReceivedEvent;

/**
 * This class handles the triggering of commands.
 *
 * @author Tim (Cooltimmetje)
 * @version v0.1-ALPHA
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
                    break;
                case "!addfaction":
                    AddFactionCommand.run(event.getMessage());
                    break;
                case "!addleader":
                    AddLeaderCommand.run(event.getMessage());
                    break;
                case "!ts":
                    MessagesUtils.sendPlain("92.222.104.72:9989", event.getMessage().getChannel());
                    break;
                case "!editlogo":
                    EditLogoCommand.run(event.getMessage());
                    break;
                case "!editcolor":

                    break;
            }
        } else {
            switch (event.getMessage().getContent().split(" ")[0]){
                case "!joinfaction":
                    SetFactionCommand.join(event.getMessage());
                    break;
                case "!leavefaction":
                    SetFactionCommand.leave(event.getMessage());
                    break;
            }
        }
    }

}
