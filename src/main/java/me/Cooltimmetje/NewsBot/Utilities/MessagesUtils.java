package me.Cooltimmetje.NewsBot.Utilities;

import me.Cooltimmetje.NewsBot.Enums.ErrorMessages;
import me.Cooltimmetje.NewsBot.Main;
import sx.blah.discord.handle.obj.IChannel;
import sx.blah.discord.handle.obj.IMessage;
import sx.blah.discord.handle.obj.IUser;
import sx.blah.discord.util.DiscordException;
import sx.blah.discord.util.MissingPermissionsException;
import sx.blah.discord.util.RateLimitException;

import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * This class handles sending messages.
 *
 * @author Tim (Cooltimmetje)
 * @version v0.1-ALPHA-DEV
 * @since v0.1-ALPHA-DEV
 */
public class MessagesUtils {

    public static void sendSuccess(String message, IChannel channel) {
        try {
            channel.sendMessage(":white_check_mark: " + message);
        } catch (MissingPermissionsException | RateLimitException | DiscordException e) {
            e.printStackTrace();
        }
    }

    public static void sendError(String error, IChannel channel) {
        ScheduledThreadPoolExecutor exec = new ScheduledThreadPoolExecutor(1);
        final IMessage message;

        try {
            message = channel.sendMessage(":x: " + ErrorMessages.random().getError() + "\n \n`" + error + "`");
            exec.schedule(() -> {
                assert message != null;
                try {
                    message.delete();
                } catch (MissingPermissionsException | RateLimitException | DiscordException e) {
                    e.printStackTrace();
                }
            }, 10, TimeUnit.SECONDS);
        } catch (MissingPermissionsException | RateLimitException | DiscordException e) {
            e.printStackTrace();
        }
    }

    public static void sendSuccessTime(String messageString, IChannel channel) {
        ScheduledThreadPoolExecutor exec = new ScheduledThreadPoolExecutor(1);
        final IMessage message;

        try {
            message = channel.sendMessage(":white_check_mark: " + messageString);
            exec.schedule(() -> {
                assert message != null;
                try {
                    message.delete();
                } catch (MissingPermissionsException | RateLimitException | DiscordException e) {
                    e.printStackTrace();
                }
            }, 10, TimeUnit.SECONDS);
        } catch (MissingPermissionsException | RateLimitException | DiscordException e) {
            e.printStackTrace();
        }
    }

    @SuppressWarnings("all") //Just because IntelliJ decided to be a dick.
    public static IMessage sendPlain(String msg, IChannel channel) {
        try {
            IMessage message = channel.sendMessage(msg);
            return message;
        } catch (MissingPermissionsException | RateLimitException | DiscordException e) {
            e.printStackTrace();
        }
        return null;
    }

    @SuppressWarnings("all") //Just because IntelliJ decided to be a dick.
    public static IMessage sendForce(String msg, IChannel channel) {
        try {
            IMessage message = channel.sendMessage(msg);
            return message;
        } catch (MissingPermissionsException | RateLimitException | DiscordException e) {
            e.printStackTrace();
        }
        return null;
    }


    public static IMessage sendPM(IUser user, String message) {
        try {
            return Main.getInstance().getNewsBot().getOrCreatePMChannel(user).sendMessage(message);

        } catch (DiscordException | RateLimitException | MissingPermissionsException e) {
            e.printStackTrace();
        }
        return null;
    }

}
