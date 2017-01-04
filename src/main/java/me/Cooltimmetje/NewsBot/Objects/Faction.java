package me.Cooltimmetje.NewsBot.Objects;

import lombok.Getter;
import lombok.Setter;
import me.Cooltimmetje.NewsBot.Database.MySqlManager;

/**
 * This is the Faction object, it holds all data for one faction.
 *
 * @author Tim (Cooltimmetje)
 * @version v0.1-ALPHA-DEV
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
     * @param roleId The ID of the role of the faction.
     */
    public Faction(String name, String channelId, String roleId, String messageId) {
        this.name = name;
        this.channelId = channelId;
        this.roleId = roleId;
        this.messageId = messageId;
    }

    public Faction(int id, String name, String channelId, String roleId, String messageId){
        this.id = id;
        this.name = name;
        this.channelId = channelId;
        this.roleId = roleId;
        this.messageId = messageId;
    }

    public void save(){
        MySqlManager.saveFaction(this);
    }


}
