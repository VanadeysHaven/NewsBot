package me.Cooltimmetje.NewsBot.Utilities;

/**
 * This class contains all sorts of utilities.
 *
 * @author Tim (Cooltimmetje)
 * @version v0.1-ALPHA-DEV
 * @since v0.1-ALPHA-DEV
 */
public class MiscUtils {

    /**
     * Attempts to parse a string to a integer.
     *
     * @param str The integer that should be parsed.
     * @return If parsing succeeds: true, otherwise false.
     */
    public static boolean isInt(String str){
        try {
            int num = Integer.parseInt(str);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

}
