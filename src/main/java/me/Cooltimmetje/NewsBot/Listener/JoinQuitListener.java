package me.Cooltimmetje.NewsBot.Listener;

import me.Cooltimmetje.NewsBot.Main;
import me.Cooltimmetje.NewsBot.Utilities.Constants;
import me.Cooltimmetje.NewsBot.Utilities.MessagesUtils;
import sx.blah.discord.api.events.EventSubscriber;
import sx.blah.discord.handle.impl.events.UserJoinEvent;
import sx.blah.discord.handle.impl.events.UserLeaveEvent;

import java.text.MessageFormat;

/**
 * <Needs to be documented>
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
    }

    @EventSubscriber
    public void onLeave(UserLeaveEvent event){
        MessagesUtils.sendPlain(MessageFormat.format(":arrow_left: **{0}#{1}** `ID: {2}` left the server.",
                event.getUser().getName(), event.getUser().getDiscriminator(), event.getUser().getID()),
                Main.getInstance().getNewsBot().getChannelByID(Constants.LOG_CHANNEL));
    }

}
