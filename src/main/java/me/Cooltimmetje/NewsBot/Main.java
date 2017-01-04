package me.Cooltimmetje.NewsBot;

import me.Cooltimmetje.NewsBot.Database.MySqlManager;
import me.Cooltimmetje.NewsBot.Utilities.Constants;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import sx.blah.discord.util.DiscordException;

/**
 * This is the Main class, this handles starting up and alot of other things.
 *
 * @author Tim (Cooltimmetje)
 * @version v0.1-ALPHA-DEV
 * @since v0.1-ALPHA-DEV
 */
public class Main {

    private static NewsBot newsBot;
    private static final Logger log = LoggerFactory.getLogger(Main.class);

    /**
     * Main method, this gets called when the bot starts up.
     *
     * @param args The program arguments, contains passwords and tokens.
     */
    public static void main(String[] args) {

        if (args.length < 3) {
            throw new IllegalArgumentException("Oi m8, I need a discord token, mysql username and pass m9");
        } else {
            log.info("Setting up database connection...");
            MySqlManager.setupHikari(args[1], args[2]);

            log.info("Setting up Discord connection...");
            newsBot = new NewsBot(args[0]);
        }

        log.info("All systems operational, Ready to connect to Discord.");

        try {
            newsBot.login();

            log.info("Connected! ALL THE MEMES!");
        } catch (DiscordException e) {
            e.printStackTrace();
        }

        Constants.STARTUP_TIME = System.currentTimeMillis();
        Constants.setAdmins();
    }

    public static NewsBot getInstance(){
        return newsBot;
    }

}
