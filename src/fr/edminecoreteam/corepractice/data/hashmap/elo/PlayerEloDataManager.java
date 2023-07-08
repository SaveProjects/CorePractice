package fr.edminecoreteam.corepractice.data.hashmap.elo;

import fr.edminecoreteam.corepractice.data.hashmap.elo.PlayerEloData;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class PlayerEloDataManager
{
    private final Map<UUID, PlayerEloData> players = new HashMap<>();

    public PlayerEloData getPlayerData(UUID playerId) {
        return players.computeIfAbsent(playerId, id -> new PlayerEloData(id, 0));
    }

    public void addData(UUID playerId, int amount) {
        getPlayerData(playerId).addTime(amount);
    }

    public void removeData(UUID playerId, int amount) {
        getPlayerData(playerId).removeTime(amount);
    }

    public void resetData(UUID playerId) { getPlayerData(playerId).resetTime(); }

    public Map<UUID, PlayerEloData> returnPlayers()
    {
        return this.players;
    }
}
