package fr.edminecoreteam.corepractice.data;

import fr.edminecoreteam.corepractice.edorm.MySQL;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class LeaderBoardData
{
    private String p;

    public LeaderBoardData() {
        //rien
    }

    public LeaderBoardData(String p) {
        this.p = p;
    }

    public List<String> getTopPlayers(String getValue) {
        List<String> topPlayers = new ArrayList<String>();
        try {
            PreparedStatement preparedStatement = MySQL.getConnection().prepareStatement("SELECT player_name FROM ed_practice ORDER BY " + getValue + " DESC LIMIT 10");
            ResultSet rs = preparedStatement.executeQuery();
            while (rs.next())
            {
                topPlayers.add(rs.getString(getValue));
            }
            preparedStatement.close();
        } catch (SQLException e) {
            e.printStackTrace();
            return topPlayers;
        }
        return topPlayers;
    }

    public int getGameData(String valueToGet)
    {
        try
        {
            PreparedStatement preparedStatement = MySQL.getConnection().prepareStatement("SELECT " + valueToGet + " FROM ed_practice WHERE player_name = ?");
            preparedStatement.setString(1, p);
            int id = 0;
            ResultSet rs = preparedStatement.executeQuery();
            while (rs.next())
            {
                id = rs.getInt(valueToGet);
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
}
