package me.Cooltimmetje.NewsBot.Objects;

import lombok.Getter;
import lombok.Setter;
import me.Cooltimmetje.NewsBot.Database.MySqlManager;
import me.Cooltimmetje.NewsBot.Main;
import me.Cooltimmetje.NewsBot.Utilities.Constants;
import me.Cooltimmetje.NewsBot.Utilities.MessagesUtils;
import sx.blah.discord.handle.obj.IChannel;
import sx.blah.discord.handle.obj.IEmbed;
import sx.blah.discord.handle.obj.IMessage;
import sx.blah.discord.util.DiscordException;
import sx.blah.discord.util.EmbedBuilder;
import sx.blah.discord.util.MissingPermissionsException;
import sx.blah.discord.util.RateLimitException;

import java.text.MessageFormat;

/**
 * This is the Faction object, it holds all data for one faction.
 *
 * @author Tim (Cooltimmetje)
 * @version v0.1-ALPHA
 * @since v0.1-ALPHA-DEV
 */
@Getter
@Setter
public class Faction {

    private int id;
    private String name;
    private String channelId;
    private String roleId;
    private String messageId;

    /**
     * Creates a new faction that isn't in the database yet.
     *
     * @param name The name of the faction.
     * @param channelId The ID of the channel of the faction.
     * @param roleId The ID of the role of the faction
     * @param messageId The message ID of the factions message.
     */
    public Faction(String name, String channelId, String roleId, String messageId) {
        this.name = name;
        this.channelId = channelId;
        this.roleId = roleId;
        this.messageId = messageId;
    }

    /**
     * Creates a new faction that was in the database.
     *
     * @param id ID of the faction.
     * @param name The name of the faction.
     * @param channelId The channel ID of the factions channel.
     * @param roleId The role ID of the factions role.
     * @param messageId The message ID of the factions message.
     */
    public Faction(int id, String name, String channelId, String roleId, String messageId){
        this.id = id;
        this.name = name;
        this.channelId = channelId;
        this.roleId = roleId;
        this.messageId = messageId;
    }

    /**
     * Save the current Faction to the database.
     */
    public void save(){
        MySqlManager.saveFaction(this);
    }

    /**
     * Edit the message in the faction list.
     *
     * @param message The message that triggered the edit.
     */
    public void editMessage(IMessage message) {
        IChannel channel = message.getChannel();
        IMessage factionMessage = Main.getInstance().getNewsBot().getMessageByID(getMessageId());
        IEmbed embed = null;
        if (!factionMessage.getEmbedded().isEmpty()) {
            embed = factionMessage.getEmbedded().get(0);
        }
        EmbedBuilder embedBuilder = new EmbedBuilder();

        embedBuilder.withAuthorName(getName());
        embedBuilder.appendField("__Faction ID:__ " + String.valueOf(getId()), "** **",false);
        embedBuilder.withFooterIcon("http://i.imgur.com/whBocR5.png");
        embedBuilder.withFooterText("Galactic Broadcasting Corporation | EliteGBC.com");

        if (embed != null) {
            if (embed.getColor() != null) {
                embedBuilder.withColor(embed.getColor());
            }
            if (embed.getThumbnail() != null) {
                String input = message.getContent().split(" ")[2];
                if(!input.equalsIgnoreCase("null")) {
                    embedBuilder.withThumbnail(embed.getThumbnail().getUrl());
                }
            }
        }

        if (message.getContent().toLowerCase().startsWith("!editlogo")) {
            String input = message.getContent().split(" ")[2];
            if(!input.equalsIgnoreCase("null")){
                embedBuilder.withThumbnail(input);
            } else {
                embedBuilder.withThumbnail("http://i.imgur.com/wPviXKL.png");
            }
        } else if (message.getContent().toLowerCase().startsWith("!editcolor")){
            String[] args = message.getContent().split(" ");
            embedBuilder.withColor(Integer.parseInt(args[1]), Integer.parseInt(args[2]), Integer.parseInt(args[3]));
        }

        try {
            factionMessage.edit("", embedBuilder.build());
        } catch (MissingPermissionsException e) {
            e.printStackTrace();
            MessagesUtils.sendError("Hmm... I seem to have no permissions to do that.", channel);
        } catch (RateLimitException e) {
            e.printStackTrace();
            MessagesUtils.sendError("Ratelimits!", channel);
        } catch (DiscordException e) {
            e.printStackTrace();
            MessagesUtils.sendError("Discord fucked up! Try again!", channel);
        }

        if (message.getContent().toLowerCase().startsWith("!editlogo")) {
            String input = message.getContent().split(" ")[2];
            if(!input.equalsIgnoreCase("null")) {
                MessagesUtils.sendPlain(MessageFormat.format(":camera: Admin **{0}#{1}** changed the logo of faction **{2}** to <{3}>.",
                        message.getAuthor().getName(), message.getAuthor().getDiscriminator(), getName(), input),
                        Main.getInstance().getNewsBot().getChannelByID(Constants.LOG_CHANNEL));
            } else {
                MessagesUtils.sendPlain(MessageFormat.format(":camera: Admin **{0}#{1}** removed the logo of faction **{2}**.",
                        message.getAuthor().getName(), message.getAuthor().getDiscriminator(), getName()),
                        Main.getInstance().getNewsBot().getChannelByID(Constants.LOG_CHANNEL));
            }
        } else if (message.getContent().toLowerCase().startsWith("!editcolor")){
            String[] args = message.getContent().split(" ");
            MessagesUtils.sendPlain(
                    MessageFormat.format(":paintbrush:  Admin **{0}#{1}** changed the color of faction **{2}** to `{3}`.",
                    message.getAuthor().getName(), message.getAuthor().getDiscriminator(), getName(), args[2] + "," + args[3] + "," + args[4]),
                    Main.getInstance().getNewsBot().getChannelByID(Constants.LOG_CHANNEL));
        }

        MessagesUtils.succesReaction(message);
    }
}
