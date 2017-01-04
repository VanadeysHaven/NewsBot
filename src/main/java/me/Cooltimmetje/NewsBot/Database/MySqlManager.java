package me.Cooltimmetje.NewsBot.Database;

import com.zaxxer.hikari.HikariDataSource;
import me.Cooltimmetje.NewsBot.Main;
import me.Cooltimmetje.NewsBot.Objects.Faction;
import me.Cooltimmetje.NewsBot.Utilities.Constants;
import me.Cooltimmetje.NewsBot.Utilities.FactionManager;
import me.Cooltimmetje.NewsBot.Utilities.Logger;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * This Class serves as a interface with the Database.
 *
 * @author Tim (Cooltimmetje)
 * @version v0.1-ALPHA-DEV
 * @since v0.1-ALPHA-DEV
 */
public class MySqlManager {

    private static HikariDataSource hikari = null;

    /**
     * Set's up the database connection.
     *
     * @param user Username of the account that should be used to login.
     * @param pass Password of the account that should be used to login.
     */
    public static void setupHikari(String user, String pass){
        hikari = new HikariDataSource();
        hikari.setMaximumPoolSize(10);

        hikari.setDataSourceClassName("com.mysql.jdbc.jdbc2.optional.MysqlDataSource");
        hikari.addDataSourceProperty("serverName", "localhost");
        hikari.addDataSourceProperty("port", 3306);
        hikari.addDataSourceProperty("databaseName", "newsbot");
        hikari.addDataSourceProperty("user", user);
        hikari.addDataSourceProperty("password", pass);
    }

    /**
     * Closes the database connection.
     */
    public static void disconnect(){
        hikari.close();
    }

    /**
     * Load all bot admins from the Database.
     */
    public static void loadAdmins(){
        Connection c = null;
        PreparedStatement ps = null;
        ResultSet rs = null;

        String query = "SELECT * FROM admins;";

        try {
            c = hikari.getConnection();
            ps = c.prepareStatement(query);
            rs = ps.executeQuery();

            while (rs.next()){
                Constants.admins.add(rs.getString(1));
                Logger.info("[Admins] Loaded admin: " + Main.getInstance().getNewsBot().getUserByID(rs.getString(1)).getName() + " (ID: " + rs.getString(1) + ")");
            }
        } catch (SQLException e){
            e.printStackTrace();
        } finally {
            if(c != null){
                try {
                    c.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
            if(ps != null){
                try {
                    ps.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
            if(rs != null){
                try {
                    rs.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    /**
     * Load all factions from the database.
     */
    public static void loadFactions(){
        Connection c = null;
        PreparedStatement ps = null;
        ResultSet rs = null;

        String query = "SELECT * FROM factions;";

        try {
            c = hikari.getConnection();
            ps = c.prepareStatement(query);
            rs = ps.executeQuery();

            while (rs.next()){
                FactionManager.loadFaction(rs.getInt(1), rs.getString(2), rs.getString(3), rs.getString(4), rs.getString(5));
            }
        } catch (SQLException e){
            e.printStackTrace();
        } finally {
            if(c != null){
                try {
                    c.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
            if(ps != null){
                try {
                    ps.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
            if(rs != null){
                try {
                    rs.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    /**
     * Add a new faction to the database.
     *
     * @param faction The faction object that should be added.
     */
    public static void addFaction(Faction faction){
        Connection c = null;
        PreparedStatement ps = null;

        String query = "INSERT INTO factions VALUES(null,?,?,?,?);";

        try {
            c = hikari.getConnection();
            ps = c.prepareStatement(query);

            ps.setString(1, faction.getName());
            ps.setString(2, faction.getChannelId());
            ps.setString(3, faction.getRoleId());
            ps.setString(4, faction.getMessageId());

            ps.execute();
        } catch (SQLException e){
            e.printStackTrace();
        } finally {
            if(c != null){
                try {
                    c.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
            if(ps != null){
                try {
                    ps.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    /**
     * Used to retrieve the ID of the faction.
     *
     * @param channelId The channel ID of the faction's channel. We use this because it is less error sensitive.
     */
    public static int fetchFactionId(String channelId){
        Connection c = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        int id = 0;

        String query = "SELECT * FROM factions WHERE channel_id=?";

        try {
            c = hikari.getConnection();
            ps = c.prepareStatement(query);

            ps.setString(1, channelId);

            rs = ps.executeQuery();
            while (rs.next()){
                id = rs.getInt(1);
            }
        } catch (SQLException e){
            e.printStackTrace();
        } finally {
            if(c != null){
                try {
                    c.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
            if(ps != null){
                try {
                    ps.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }

        return id;
    }

    /**
     * This saves a existing faction to the database.
     *
     * @param faction Faction that should be saved.
     */
    public static void saveFaction(Faction faction){
        Connection c = null;
        PreparedStatement ps = null;

        String query = "UPDATE factions SET faction_name=?,channel_id=?,role_id=?,message_id=? WHERE faction_id=?";

        try {
            c = hikari.getConnection();
            ps = c.prepareStatement(query);

            ps.setString(1, faction.getName());
            ps.setString(2, faction.getChannelId());
            ps.setString(3, faction.getRoleId());
            ps.setString(4, faction.getMessageId());
            ps.setInt(5, faction.getId());

            ps.execute();
        } catch (SQLException e){
            e.printStackTrace();
        } finally {
            if(c != null){
                try {
                    c.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
            if(ps != null){
                try {
                    ps.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    /**
     * Removes a faction from the database.
     *
     * @param faction The faction ID.
     */
    public static void removeFaction(Faction faction){

    }

}
