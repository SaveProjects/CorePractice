package fr.edminecoreteam.corepractice.edorm;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import org.bukkit.plugin.Plugin;

import fr.edminecoreteam.corepractice.Core;
public class MySQL
{
    private MySQL instance;
    private Core api;
    private String urlBase;
    private String host;
    private String database;
    private String userName;
    private String password;
    private static Connection connection;

    public MySQL(Core api, String urlBase, String host, String database, String userName, String password) {
        this.api = api;
        this.urlBase = urlBase;
        this.host = host;
        this.database = database;
        this.userName = userName;
        this.password = password;
    }

    public static Connection getConnection() { return MySQL.connection; }

    public void connexion() {
        if (!this.isOnline()) {
            try {
                instance = this;

                MySQL.connection = DriverManager.getConnection(String.valueOf(this.urlBase) + this.host + "/" + this.database, this.userName, this.password);

                SQLTasks start = new SQLTasks(api, instance);
                start.runTaskTimer((Plugin)this.api, 0L, 20L);

                message("connectMSG");
            }
            catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    public void deconnexion() {
        if (this.isOnline()) {
            try {
                MySQL.connection.close();

                message("disconnectMSG");
            }
            catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    public boolean isOnline() {
        try
        {
            if (MySQL.connection == null || MySQL.connection.isClosed())
            {
                return false;
            }
            else if (MySQL.connection != null || !MySQL.connection.isClosed())
            {
                return true;
            }
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public void creatingTablePractice() {
        try
        {
            PreparedStatement stm = MySQL.connection.prepareStatement("CREATE TABLE IF NOT EXISTS ed_practice (" +
                    "`player_uuid` varchar(255) NOT NULL, " +
                    "`player_name` varchar(255), " +

                    "`unranked_played` int(11), " +
                    "`unranked_win` int(11), " +
                    "`unranked_lose` int(11), " +

                    "`ranked_played` int(11), " +
                    "`ranked_win` int(11), " +
                    "`ranked_lose` int(11), " +

                    "`player_elo` int(11), " +
                    "`player_rank` int(11), " +

                    "PRIMARY KEY (`player_uuid`), UNIQUE(`player_uuid`), INDEX(`player_uuid`)) CHARACTER SET utf8");
            stm.execute();
            stm.close();
            System.out.println("ED-NETWORK API");
            System.out.println("DATABASE: ed_practice loaded.");
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void updateColumnsPractice(String gameType)
    {
        try
        {
            PreparedStatement stm = MySQL.connection.prepareStatement("ALTER TABLE ed_practice ADD COLUMN IF NOT EXISTS " + gameType + "_unranked_win INT(11)");
            stm.execute();
            stm.close();
            System.out.println("ED-NETWORK API");
            System.out.println("DATABASE: column " + gameType + "_unranked_win added to ed_practice.");
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
        try
        {
            PreparedStatement stm = MySQL.connection.prepareStatement("ALTER TABLE ed_practice ADD COLUMN IF NOT EXISTS " + gameType + "_ranked_win INT(11)");
            stm.execute();
            stm.close();
            System.out.println("DATABASE: column " + gameType + "_ranked_win added to ed_practice.");
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
        try
        {
            PreparedStatement stm = MySQL.connection.prepareStatement("ALTER TABLE ed_practice ADD COLUMN IF NOT EXISTS " + gameType + "_ranked_lose INT(11)");
            stm.execute();
            stm.close();
            System.out.println("DATABASE: column " + gameType + "_ranked_lose added to ed_practice.");
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
        try
        {
            PreparedStatement stm = MySQL.connection.prepareStatement("ALTER TABLE ed_practice ADD COLUMN IF NOT EXISTS " + gameType + "_elo INT(11)");
            stm.execute();
            stm.close();
            System.out.println("DATABASE: column " + gameType + "_elo added to ed_practice.");
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
        try
        {
            PreparedStatement stm = MySQL.connection.prepareStatement("ALTER TABLE ed_practice ADD COLUMN IF NOT EXISTS " + gameType + "_bestwinstreak INT(11)");
            stm.execute();
            stm.close();
            System.out.println("DATABASE: column " + gameType + "_bestwinstreak added to ed_practice.");
            System.out.println("");
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
    }


    private void message(String condition) {
        if (condition == "connectMSG")
        {
            System.out.println("+--------------------+");
            System.out.println("ED-NETWORK API");
            System.out.println("ORM: Enable");
            System.out.println("ORM-DATABASE: Connected");
            System.out.println("+--------------------+");
        }
        if (condition == "disconnectMSG")
        {
            System.out.println("+--------------------+");
            System.out.println("ED-NETWORK API");
            System.out.println("ORM: Disable");
            System.out.println("ORM-DATABASE: Disconnected");
            System.out.println("+--------------------+");
        }
    }


}
