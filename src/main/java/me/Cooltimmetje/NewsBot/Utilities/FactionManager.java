package me.Cooltimmetje.NewsBot.Utilities;

import me.Cooltimmetje.NewsBot.Database.MySqlManager;
import me.Cooltimmetje.NewsBot.Main;
import me.Cooltimmetje.NewsBot.Objects.Faction;
import sx.blah.discord.handle.obj.*;
import sx.blah.discord.util.*;

import java.text.MessageFormat;
import java.util.EnumSet;
import java.util.HashMap;

/**
 * Manages factions.
 *
 * @author Tim (Cooltimmetje)
 * @version v0.1-ALPHA
 * @since v0.1-ALPHA-DEV
 */
public class FactionManager {

    private static HashMap<Integer,Faction> factions = new HashMap<>();

    /**
     * This creates a new faction and saves it into the database. Also makes the channels and roles and sets the permissions properly.
     *
     * @param name The name of the Faction.
     * @param messageSent This is the message that triggered this bit of code.
     */
    public static void createNewFaction(String name, IMessage messageSent) {
        try {
        IGuild guild = Main.getInstance().getNewsBot().getGuildByID(Constants.SERVER_ID);
        IChannel channel = guild.createChannel(name.replace("'", "").replace(" ", "-"));
        RoleBuilder rb = new RoleBuilder(guild).withName(name).withPermissions(EnumSet.of(Permissions.VOICE_CONNECT));
        IRole role = rb.build();

        channel.overrideRolePermissions(role, EnumSet.of(Permissions.READ_MESSAGES), null);
        channel.overrideRolePermissions(guild.getEveryoneRole(), null, EnumSet.of(Permissions.READ_MESSAGES));
        channel.overrideRolePermissions(guild.getRolesByName("Moderator").get(0), EnumSet.of(Permissions.READ_MESSAGES), null);
        channel.overrideRolePermissions(guild.getRolesByName("Administrator").get(0), EnumSet.of(Permissions.READ_MESSAGES), null);

        Faction faction = new Faction(name, channel.getID(), role.getID(), "1");

        MySqlManager.addFaction(faction);
        int id = MySqlManager.fetchFactionId(channel.getID());
        faction.setId(id);

        factions.put(id, faction);

        EmbedBuilder embedBuilder = new EmbedBuilder();
        embedBuilder.withAuthorName(faction.getName());
        embedBuilder.appendField("__Faction ID:__ " + String.valueOf(id), "** **",false);
        embedBuilder.withThumbnail("http://i.imgur.com/wPviXKL.png");
        embedBuilder.withFooterIcon("http://i.imgur.com/whBocR5.png");
        embedBuilder.withFooterText("Galactic Broadcasting Corporation | EliteGBC.com");

        IMessage message = channel.getGuild().getChannelByID("266287084719964161").sendMessage("", embedBuilder.build(), false);

        faction.setMessageId(message.getID());
        faction.save();

        Logger.info(MessageFormat.format("[Factions][Created] New faction has been created: {0} (ID: {1}) - Channel ID: {2} | Role ID: {3} | Message ID: {4}", name, id, channel.getID(), role.getID(), message.getID()));
        MessagesUtils.sendPlain(MessageFormat.format(":new: Admin **{0}** added faction **{1}**!",
                messageSent.getAuthor().getName() + "#" + messageSent.getAuthor().getDiscriminator(), faction.getName()),
                Main.getInstance().getNewsBot().getChannelByID(Constants.LOG_CHANNEL));

        } catch (DiscordException | RateLimitException | MissingPermissionsException e) {
            e.printStackTrace();
        }

        MessagesUtils.succesReaction(messageSent);
    }

    /**
     * Load a faction with the given data.
     *
     * @param id ID of the faction.
     * @param name The name of the faction.
     * @param channelId The channel ID of the factions channel.
     * @param roleId The role ID of the factions role.
     * @param messageId The message ID of the factions message.
     */
    public static void loadFaction(int id, String name, String channelId, String roleId, String messageId){
        Faction faction = new Faction(id, name, channelId, roleId, messageId);
        factions.put(id, faction);

        Logger.info(MessageFormat.format("[Factions][Loaded] Faction has been loaded: {0} (ID: {1}) - Channel ID: {2} | Role ID: {3} | Message ID: {4}", name, id, channelId, roleId, messageId));
    }

    /**
     * Gets the faction by ID.
     *
     * @param id The ID of the faction that we want.
     * @return The faction instance. - Can return null! Handle properly!
     */
    public static Faction getFaction(int id){
        if(factions.containsKey(id)) {
            return factions.get(id);
        } else {
            return null;
        }
    }

    /**
     * This returns the faction based on channel ID.
     *
     * @param channelId The channel ID of the faction we want.
     * @return The faction instance. - Can return null! Handle properly!
     */
    public static Faction getFaction(String channelId){
        for(int id : factions.keySet()){
            if(getFaction(id) != null) {
                if (getFaction(id).getChannelId().equals(channelId)) {
                    return getFaction(id);
                }
            }
        }
        return null;
    }
}
