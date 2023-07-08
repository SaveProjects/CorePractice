package fr.edminecoreteam.corepractice.data;

import fr.edminecoreteam.corepractice.edorm.MySQL;
import org.bukkit.entity.Player;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class PracticeData
{
    private final Player p;
    private final String table;

    public PracticeData(Player p)
    {
        this.p = p;
        this.table = "ed_practice";
    }

    public void createData()
    {
        if (!hasData())
        {
            try
            {
                PreparedStatement preparedStatement = MySQL.getConnection().prepareStatement("INSERT INTO " + table + " (" +
                        "player_uuid, " +
                        "player_name, " +
                        "unranked_played, " +
                        "unranked_win, " +
                        "unranked_lose, " +
                        "ranked_played, " +
                        "ranked_win, " +
                        "ranked_lose, " +
                        "player_elo, " +
                        "player_rank" +
                        ") VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)");
                preparedStatement.setString(1, p.getUniqueId().toString()); /*player_uuid*/
                preparedStatement.setString(2, p.getName()); /*player_name*/
                preparedStatement.setInt(3, 0); /*unranked_played*/
                preparedStatement.setInt(4, 0); /*unranked_win*/
                preparedStatement.setInt(5, 0); /*unranked_lose*/
                preparedStatement.setInt(6, 0); /*ranked_played*/
                preparedStatement.setInt(7, 0); /*ranked_win*/
                preparedStatement.setInt(8, 0); /*ranked_lose*/
                preparedStatement.setInt(9, 800); /*player_elo*/
                preparedStatement.setInt(10, 0); /*player_rank*/
                preparedStatement.execute();
                preparedStatement.close();
            }
            catch (SQLException e)
            {
                e.toString();
            }
        }
    }

    public boolean hasData()
    {
        try
        {
            PreparedStatement preparedStatement = MySQL.getConnection().prepareStatement("SELECT player_elo FROM " + table + " WHERE player_uuid = ?");
            preparedStatement.setString(1, p.getUniqueId().toString());
            ResultSet rs = preparedStatement.executeQuery();
            return rs.next();
        }
        catch (SQLException e)
        {
            e.toString();
            return false;
        }
    }

    public String getPlayerName()
    {
        try
        {
            PreparedStatement preparedStatement = MySQL.getConnection().prepareStatement("SELECT player_name FROM " + table + " WHERE player_uuid = ?");
            preparedStatement.setString(1, p.getUniqueId().toString());
            String id = "";
            ResultSet rs = preparedStatement.executeQuery();
            while (rs.next())
            {
                id = rs.getString("player_name");
            }
            preparedStatement.close();
            return id;
        }
        catch (SQLException e)
        {
            e.printStackTrace();
            return "";
        }
    }

    public String getPlayerUUID()
    {
        try
        {
            PreparedStatement preparedStatement = MySQL.getConnection().prepareStatement("SELECT player_uuid FROM " + table + " WHERE player_uuid = ?");
            preparedStatement.setString(1, p.getUniqueId().toString());
            String id = "";
            ResultSet rs = preparedStatement.executeQuery();
            while (rs.next())
            {
                id = rs.getString("player_uuid");
            }
            preparedStatement.close();
            return id;
        }
        catch (SQLException e)
        {
            e.printStackTrace();
            return "";
        }
    }

    public int getUnrankedPlayed()
    {
        try
        {
            PreparedStatement preparedStatement = MySQL.getConnection().prepareStatement("SELECT unranked_played FROM " + table + " WHERE player_uuid = ?");
            preparedStatement.setString(1, p.getUniqueId().toString());
            int id = 0;
            ResultSet rs = preparedStatement.executeQuery();
            while (rs.next())
            {
                id = rs.getInt("unranked_played");
            }
            preparedStatement.close();
            return id;
        }
        catch (SQLException e)
        {
            e.printStackTrace();
            return 0;
        }
    }

    public int getRankedPlayed()
    {
        try
        {
            PreparedStatement preparedStatement = MySQL.getConnection().prepareStatement("SELECT ranked_played FROM " + table + " WHERE player_uuid = ?");
            preparedStatement.setString(1, p.getUniqueId().toString());
            int id = 0;
            ResultSet rs = preparedStatement.executeQuery();
            while (rs.next())
            {
                id = rs.getInt("ranked_played");
            }
            preparedStatement.close();
            return id;
        }
        catch (SQLException e)
        {
            e.printStackTrace();
            return 0;
        }
    }

    public int getUnrankedWin()
    {
        try
        {
            PreparedStatement preparedStatement = MySQL.getConnection().prepareStatement("SELECT unranked_win FROM " + table + " WHERE player_uuid = ?");
            preparedStatement.setString(1, p.getUniqueId().toString());
            int id = 0;
            ResultSet rs = preparedStatement.executeQuery();
            while (rs.next())
            {
                id = rs.getInt("unranked_win");
            }
            preparedStatement.close();
            return id;
        }
        catch (SQLException e)
        {
            e.printStackTrace();
            return 0;
        }
    }

    public int getRankedWin()
    {
        try
        {
            PreparedStatement preparedStatement = MySQL.getConnection().prepareStatement("SELECT ranked_win FROM " + table + " WHERE player_uuid = ?");
            preparedStatement.setString(1, p.getUniqueId().toString());
            int id = 0;
            ResultSet rs = preparedStatement.executeQuery();
            while (rs.next())
            {
                id = rs.getInt("ranked_win");
            }
            preparedStatement.close();
            return id;
        }
        catch (SQLException e)
        {
            e.printStackTrace();
            return 0;
        }
    }

    public int getUnrankedLose()
    {
        try
        {
            PreparedStatement preparedStatement = MySQL.getConnection().prepareStatement("SELECT unranked_lose FROM " + table + " WHERE player_uuid = ?");
            preparedStatement.setString(1, p.getUniqueId().toString());
            int id = 0;
            ResultSet rs = preparedStatement.executeQuery();
            while (rs.next())
            {
                id = rs.getInt("unranked_lose");
            }
            preparedStatement.close();
            return id;
        }
        catch (SQLException e)
        {
            e.printStackTrace();
            return 0;
        }
    }

    public int getRankedLose()
    {
        try
        {
            PreparedStatement preparedStatement = MySQL.getConnection().prepareStatement("SELECT ranked_lose FROM " + table + " WHERE player_uuid = ?");
            preparedStatement.setString(1, p.getUniqueId().toString());
            int id = 0;
            ResultSet rs = preparedStatement.executeQuery();
            while (rs.next())
            {
                id = rs.getInt("ranked_lose");
            }
            preparedStatement.close();
            return id;
        }
        catch (SQLException e)
        {
            e.printStackTrace();
            return 0;
        }
    }

    public int getPlayerElo()
    {
        try
        {
            PreparedStatement preparedStatement = MySQL.getConnection().prepareStatement("SELECT player_elo FROM " + table + " WHERE player_uuid = ?");
            preparedStatement.setString(1, p.getUniqueId().toString());
            int id = 0;
            ResultSet rs = preparedStatement.executeQuery();
            while (rs.next())
            {
                id = rs.getInt("player_elo");
            }
            preparedStatement.close();
            return id;
        }
        catch (SQLException e)
        {
            e.printStackTrace();
            return 0;
        }
    }

    public int getPlayerRank()
    {
        try
        {
            PreparedStatement preparedStatement = MySQL.getConnection().prepareStatement("SELECT player_rank FROM " + table + " WHERE player_uuid = ?");
            preparedStatement.setString(1, p.getUniqueId().toString());
            int id = 0;
            ResultSet rs = preparedStatement.executeQuery();
            while (rs.next())
            {
                id = rs.getInt("player_rank");
            }
            preparedStatement.close();
            return id;
        }
        catch (SQLException e)
        {
            e.printStackTrace();
            return 0;
        }
    }

    public void setUnrankedPlayed(int amount)
    {
        try
        {
            PreparedStatement preparedStatement = MySQL.getConnection().prepareStatement("UPDATE " + table + " SET unranked_played = ? WHERE player_uuid = ?");
            preparedStatement.setInt(1, amount);
            preparedStatement.setString(2, p.getUniqueId().toString());
            preparedStatement.executeUpdate();
            preparedStatement.close();
        }
        catch (SQLException e)
        {
            e.printStackTrace();
        }
    }

    public void setRankedPlayed(int amount)
    {
        try
        {
            PreparedStatement preparedStatement = MySQL.getConnection().prepareStatement("UPDATE " + table + " SET ranked_played = ? WHERE player_uuid = ?");
            preparedStatement.setInt(1, amount);
            preparedStatement.setString(2, p.getUniqueId().toString());
            preparedStatement.executeUpdate();
            preparedStatement.close();
        }
        catch (SQLException e)
        {
            e.printStackTrace();
        }
    }

    public void setUnrankedWin(int amount)
    {
        try
        {
            PreparedStatement preparedStatement = MySQL.getConnection().prepareStatement("UPDATE " + table + " SET unranked_win = ? WHERE player_uuid = ?");
            preparedStatement.setInt(1, amount);
            preparedStatement.setString(2, p.getUniqueId().toString());
            preparedStatement.executeUpdate();
            preparedStatement.close();
        }
        catch (SQLException e)
        {
            e.printStackTrace();
        }
    }

    public void setRankedWin(int amount)
    {
        try
        {
            PreparedStatement preparedStatement = MySQL.getConnection().prepareStatement("UPDATE " + table + " SET ranked_win = ? WHERE player_uuid = ?");
            preparedStatement.setInt(1, amount);
            preparedStatement.setString(2, p.getUniqueId().toString());
            preparedStatement.executeUpdate();
            preparedStatement.close();
        }
        catch (SQLException e)
        {
            e.printStackTrace();
        }
    }

    public void setUnrankedLose(int amount)
    {
        try
        {
            PreparedStatement preparedStatement = MySQL.getConnection().prepareStatement("UPDATE " + table + " SET unranked_lose = ? WHERE player_uuid = ?");
            preparedStatement.setInt(1, amount);
            preparedStatement.setString(2, p.getUniqueId().toString());
            preparedStatement.executeUpdate();
            preparedStatement.close();
        }
        catch (SQLException e)
        {
            e.printStackTrace();
        }
    }

    public void setRankedLose(int amount)
    {
        try
        {
            PreparedStatement preparedStatement = MySQL.getConnection().prepareStatement("UPDATE " + table + " SET ranked_lose = ? WHERE player_uuid = ?");
            preparedStatement.setInt(1, amount);
            preparedStatement.setString(2, p.getUniqueId().toString());
            preparedStatement.executeUpdate();
            preparedStatement.close();
        }
        catch (SQLException e)
        {
            e.printStackTrace();
        }
    }

    public void setPlayerElo(int amount)
    {
        try
        {
            PreparedStatement preparedStatement = MySQL.getConnection().prepareStatement("UPDATE " + table + " SET player_elo = ? WHERE player_uuid = ?");
            preparedStatement.setInt(1, amount);
            preparedStatement.setString(2, p.getUniqueId().toString());
            preparedStatement.executeUpdate();
            preparedStatement.close();
        }
        catch (SQLException e)
        {
            e.printStackTrace();
        }
    }

    public void setPlayerRank(int amount)
    {
        try
        {
            PreparedStatement preparedStatement = MySQL.getConnection().prepareStatement("UPDATE " + table + " SET player_rank = ? WHERE player_uuid = ?");
            preparedStatement.setInt(1, amount);
            preparedStatement.setString(2, p.getUniqueId().toString());
            preparedStatement.executeUpdate();
            preparedStatement.close();
        }
        catch (SQLException e)
        {
            e.printStackTrace();
        }
    }

    public void updatePlayerName()
    {
        try
        {
            PreparedStatement preparedStatement = MySQL.getConnection().prepareStatement("UPDATE " + table + " SET player_name = ? WHERE player_uuid = ?");
            preparedStatement.setString(1, p.getName());
            preparedStatement.setString(2, p.getUniqueId().toString());
            preparedStatement.executeUpdate();
            preparedStatement.close();
        }
        catch (SQLException e)
        {
            e.printStackTrace();
        }
    }
}
