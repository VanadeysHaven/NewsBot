package me.Cooltimmetje.NewsBot.Database;

import com.zaxxer.hikari.HikariDataSource;

/**
 * <Needs to be documented>
 *
 * @author Tim (Cooltimmetje)
 * @version v0.1-ALPHA-DEV
 * @since v0.1-ALPHA-DEV
 */
public class MySqlManager {

    private static HikariDataSource hikari = null;

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

    public static void disconnect(){
        hikari.close();
    }

    public static void loadAdmins(){
        
    }

}
