package me.Cooltimmetje.NewsBot;

import me.Cooltimmetje.NewsBot.Commands.CommandManager;
import me.Cooltimmetje.NewsBot.Database.MySqlManager;
import me.Cooltimmetje.NewsBot.Listener.CreateServerListener;
import me.Cooltimmetje.NewsBot.Utilities.Constants;
import me.Cooltimmetje.NewsBot.Utilities.Logger;
import me.Cooltimmetje.NewsBot.Utilities.MessagesUtils;
import sx.blah.discord.api.ClientBuilder;
import sx.blah.discord.api.IDiscordClient;
import sx.blah.discord.api.events.EventSubscriber;
import sx.blah.discord.handle.impl.events.DisconnectedEvent;
import sx.blah.discord.handle.impl.events.MentionEvent;
import sx.blah.discord.handle.impl.events.ReadyEvent;
import sx.blah.discord.handle.obj.Status;
import sx.blah.discord.util.DiscordException;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * This is the instance of the bot.
 *
 * @author Tim (Cooltimmetje)
 * @version v0.1-ALPHA-DEV
 * @since v0.1-ALPHA-DEV
 */
public class NewsBot {

    private volatile IDiscordClient newsBot;
    private String token;
    private final AtomicBoolean reconnect = new AtomicBoolean(true);
    private boolean preListenersReady = false;
    private boolean listenersReady = false;

    /**
     * Create a instance of the bot.
     *
     * @param token The Discord token that should be used to login.
     */
    public NewsBot(String token){
        this.token = token;
    }

    /**
     * Get the bot logged in to Discord, and register the core listeners.
     *
     * @throws DiscordException I dunno
     */
    public void login() throws DiscordException {
        newsBot = new ClientBuilder().withToken(token).login();
        if(!preListenersReady){
            newsBot.getDispatcher().registerListener(this);
            newsBot.getDispatcher().registerListener(new CreateServerListener());

            preListenersReady = true;
        }
    }

    /**
     * This registers all other listeners.
     *
     * @param event The event that triggers this bit of code.
     */
    @EventSubscriber
    public void onReady(ReadyEvent event){
        if(!listenersReady){
            event.getClient().changeStatus(Status.game("Collecting News"));
            newsBot.getDispatcher().registerListener(new CommandManager());
            MySqlManager.loadAdmins();
            MySqlManager.loadFactions();

            listenersReady = true;
        }
    }

    /**
     * When the bot dies, attempt to reconnect it.
     *
     * @param event The event that triggers this bit of code.
     */
    public void onDisconnect(DisconnectedEvent event){
        CompletableFuture.runAsync(() -> {
            if(reconnect.get()){
                Logger.info("Attempting to reconnect bot.");
                try {
                    login();
                } catch (DiscordException e){
                    Logger.warn("Well rip.", e);
                }
            }
        });
    }

    /**
     * Used to logout the bot from Discord.
     *
     * @param event The event that triggers this bit of code.
     */
    @EventSubscriber
    public void onMention(MentionEvent event){
        if(event.getMessage().getContent().split(" ").length > 1) {
            if (event.getMessage().getContent().split(" ")[1].equalsIgnoreCase("logout")) {
                if (event.getMessage().getAuthor().getID().equals(Constants.TIMMY_ID)) {
                    MessagesUtils.sendSuccess("Well, okay then...\n`Shutting down...`", event.getMessage().getChannel());

                    terminate();
                } else {
                    MessagesUtils.sendError("Ur not timmy >=(", event.getMessage().getChannel());
                }
            }
        }
    }

    /**
     * Terminate the bot. This will prevent it from reconnecting.
     */
    public void terminate() {
        reconnect.set(false);
        try {
            newsBot.logout();
            MySqlManager.disconnect();
        } catch (DiscordException e) {
            Logger.warn("Couldn't log out.", e);
        }

    }

    /**
     * Get the bot.
     *
     * @return Current instance of NewsBot.
     */
    public IDiscordClient getNewsBot(){
        return newsBot;
    }


}