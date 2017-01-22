package me.Cooltimmetje.NewsBot.Listener;

import me.Cooltimmetje.NewsBot.Main;
import me.Cooltimmetje.NewsBot.Utilities.Constants;
import me.Cooltimmetje.NewsBot.Utilities.MessagesUtils;
import sx.blah.discord.api.events.EventSubscriber;
import sx.blah.discord.handle.impl.events.UserJoinEvent;
import sx.blah.discord.handle.impl.events.UserLeaveEvent;

import java.text.MessageFormat;

/**
 * Listens for joins and quits.
 *
 * @author Tim (Cooltimmetje)
 * @version v0.1-ALPHA-DEV
 * @since v0.1-ALPHA-DEV
 */
public class JoinQuitListener {

    @EventSubscriber
    public void onJoin(UserJoinEvent event){
        MessagesUtils.sendPlain(MessageFormat.format(":arrow_right: **{0}#{1}** `ID: {2}` joined the server.",
                event.getUser().getName(), event.getUser().getDiscriminator(), event.getUser().getID()),
                Main.getInstance().getNewsBot().getChannelByID(Constants.LOG_CHANNEL));

        MessagesUtils.sendPM(event.getUser(),
                "Welcome to the Galactic Broadcast Corporation! You just received a private message where you'll be able to join the group you represent. If you are an independent commander then please join the \"Freelancer\" Faction. \n" +
                "\n" +
                "If you have an urgent, breaking story please tag @editors and one of our reporters will get to you ASAP.\n" +
                "\n" +
                "To join your group use the command: `!joinfaction <id>` in this PM conversation! - The ID's can be found in the <#266287084719964161> channel.");
    }

    @EventSubscriber
    public void onLeave(UserLeaveEvent event){
        MessagesUtils.sendPlain(MessageFormat.format(":arrow_left: **{0}#{1}** `ID: {2}` left the server.",
                event.getUser().getName(), event.getUser().getDiscriminator(), event.getUser().getID()),
                Main.getInstance().getNewsBot().getChannelByID(Constants.LOG_CHANNEL));
    }

}
