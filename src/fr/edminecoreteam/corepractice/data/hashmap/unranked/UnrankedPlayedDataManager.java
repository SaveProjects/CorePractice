package fr.edminecoreteam.corepractice.data.hashmap.unranked;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class UnrankedPlayedDataManager
{
    private final Map<UUID, UnrankedPlayedData> players = new HashMap<>();

    public UnrankedPlayedData getPlayerData(UUID playerId) {
        return players.computeIfAbsent(playerId, id -> new UnrankedPlayedData(id, 0));
    }

    public void addData(UUID playerId, int amount) {
        getPlayerData(playerId).addTime(amount);
    }

    public void removeData(UUID playerId, int amount) {
        getPlayerData(playerId).removeTime(amount);
    }

    public void resetData(UUID playerId) { getPlayerData(playerId).resetTime(); }

    public Map<UUID, UnrankedPlayedData> returnPlayers()
    {
        return this.players;
    }
}
