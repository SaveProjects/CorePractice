package fr.edminecoreteam.corepractice.data.ranks;

import fr.edminecoreteam.corepractice.edorm.MySQL;
import org.bukkit.entity.Player;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class RankData
{
    private Player p;
    private String pS;

    public RankData(Player p)
    {
        this.p = p;
    }

    public RankData(String pS)
    {
        this.pS = pS;
    }

    public void updateRankName(String name)
    {
        if (hasRank())
        {
            try
            {
                PreparedStatement preparedStatement = MySQL.getConnection().prepareStatement("UPDATE ed_ranks SET player_rank_name = ? WHERE player_name = ?");
                preparedStatement.setString(1, name);
                preparedStatement.setString(2, p.getName());
                preparedStatement.executeUpdate();
                preparedStatement.close();
            }
            catch (SQLException e)
            {
                e.toString();
            }
        }
    }

    public boolean hasRank()
    {
        try
        {
            PreparedStatement preparedStatement = MySQL.getConnection().prepareStatement("SELECT player_rank_id FROM ed_ranks WHERE player_name = ?");
            preparedStatement.setString(1, p.getName());
            ResultSet rs = preparedStatement.executeQuery();
            return rs.next();
        }
        catch (SQLException e)
        {
            e.toString();
            return false;
        }
    }

    public boolean hasRankString()
    {
        try
        {
            PreparedStatement preparedStatement = MySQL.getConnection().prepareStatement("SELECT player_rank_id FROM ed_ranks WHERE player_name = ?");
            preparedStatement.setString(1, pS);
            ResultSet rs = preparedStatement.executeQuery();
            return rs.next();
        }
        catch (SQLException e)
        {
            e.toString();
            return false;
        }
    }

    public int getRankID()
    {
        try
        {
            PreparedStatement preparedStatement = MySQL.getConnection().prepareStatement("SELECT player_rank_id FROM ed_ranks WHERE player_uuid = ?");
            preparedStatement.setString(1, p.getUniqueId().toString().replaceAll("-", ""));
            int power = 0;
            ResultSet rs = preparedStatement.executeQuery();
            while (rs.next())
            {
                power = rs.getInt("player_rank_id");
            }
            preparedStatement.close();
            return power;
        }
        catch (SQLException e)
        {
            e.toString();
            return 0;
        }
    }

    public int getRankIDString()
    {
        try
        {
            PreparedStatement preparedStatement = MySQL.getConnection().prepareStatement("SELECT player_rank_id FROM ed_ranks WHERE player_name = ?");
            preparedStatement.setString(1, pS);
            int power = 0;
            ResultSet rs = preparedStatement.executeQuery();
            while (rs.next())
            {
                power = rs.getInt("player_rank_id");
            }
            preparedStatement.close();
            return power;
        }
        catch (SQLException e)
        {
            e.toString();
            return 0;
        }
    }

    public String getRankType()
    {
        if (hasRank())
        {
            try
            {
                PreparedStatement preparedStatement = MySQL.getConnection().prepareStatement("SELECT player_rank_type FROM ed_ranks WHERE player_uuid = ?");
                preparedStatement.setString(1, p.getUniqueId().toString().replaceAll("-", ""));
                String response = "";
                ResultSet rs = preparedStatement.executeQuery();
                while (rs.next())
                {
                    response = rs.getString("player_rank_type");
                }
                preparedStatement.close();
                return response;
            }
            catch (SQLException e)
            {
                e.toString();
            }
        }
        return "";
    }

    public String getRankTypeString()
    {
        if (hasRankString())
        {
            try
            {
                PreparedStatement preparedStatement = MySQL.getConnection().prepareStatement("SELECT player_rank_type FROM ed_ranks WHERE player_name = ?");
                preparedStatement.setString(1, pS);
                String response = "";
                ResultSet rs = preparedStatement.executeQuery();
                while (rs.next())
                {
                    response = rs.getString("player_rank_type");
                }
                preparedStatement.close();
                return response;
            }
            catch (SQLException e)
            {
                e.toString();
            }
        }
        return "";
    }

    public String getPurchaseDate()
    {
        if (hasRank())
        {
            try
            {
                PreparedStatement preparedStatement = MySQL.getConnection().prepareStatement("SELECT player_rank_purchase_date FROM ed_ranks WHERE player_uuid = ?");
                preparedStatement.setString(1, p.getUniqueId().toString().replaceAll("-", ""));
                String response = "";
                ResultSet rs = preparedStatement.executeQuery();
                while (rs.next())
                {
                    response = rs.getString("player_rank_purchase_date");
                }
                preparedStatement.close();
                return response;
            }
            catch (SQLException e)
            {
                e.toString();
            }
        }
        return "";
    }

    public String getDeadLineDate()
    {
        if (hasRank())
        {
            try
            {
                PreparedStatement preparedStatement = MySQL.getConnection().prepareStatement("SELECT player_rank_deadline_date FROM ed_ranks WHERE player_uuid = ?");
                preparedStatement.setString(1, p.getUniqueId().toString().replaceAll("-", ""));
                String response = "";
                ResultSet rs = preparedStatement.executeQuery();
                while (rs.next())
                {
                    response = rs.getString("player_rank_deadline_date");
                }
                preparedStatement.close();
                return response;
            }
            catch (SQLException e)
            {
                e.toString();
            }
        }
        return "";
    }

    public int getRankModule()
    {
        if (hasRank())
        {
            try
            {
                PreparedStatement preparedStatement = MySQL.getConnection().prepareStatement("SELECT player_modulable_rank FROM ed_ranks WHERE player_uuid = ?");
                preparedStatement.setString(1, p.getUniqueId().toString().replaceAll("-", ""));
                int response = 0;
                ResultSet rs = preparedStatement.executeQuery();
                while (rs.next())
                {
                    response = rs.getInt("player_modulable_rank");
                }
                preparedStatement.close();
                return response;
            }
            catch (SQLException e)
            {
                e.toString();
            }
        }
        return 0;
    }

    public int getRankModuleString()
    {
        if (hasRankString())
        {
            try
            {
                PreparedStatement preparedStatement = MySQL.getConnection().prepareStatement("SELECT player_modulable_rank FROM ed_ranks WHERE player_name = ?");
                preparedStatement.setString(1, pS);
                int response = 0;
                ResultSet rs = preparedStatement.executeQuery();
                while (rs.next())
                {
                    response = rs.getInt("player_modulable_rank");
                }
                preparedStatement.close();
                return response;
            }
            catch (SQLException e)
            {
                e.toString();
            }
        }
        return 0;
    }
}
