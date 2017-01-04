package me.Cooltimmetje.NewsBot.Utilities;

import org.slf4j.LoggerFactory;

/**
 * <Needs to be documented>
 *
 * @author Tim (Cooltimmetje)
 * @version v0.1-ALPHA-DEV
 * @since v0.1-ALPHA-DEV
 */
public class Logger {

    private static final org.slf4j.Logger log = LoggerFactory.getLogger(Logger.class);

    public static void info(String string){
        log.info(string);
    }

    public static void warn(String string, Exception e){
        log.warn(string,e);
    }


}
