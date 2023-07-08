package fr.edminecoreteam.corepractice.data.hashmap.ranked;

import fr.edminecoreteam.corepractice.data.hashmap.ranked.RankedPlayedData;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class RankedPlayedDataManager
{
    private final Map<UUID, RankedPlayedData> players = new HashMap<>();

    public RankedPlayedData getPlayerData(UUID playerId) {
        return players.computeIfAbsent(playerId, id -> new RankedPlayedData(id, 0));
    }

    public void addData(UUID playerId, int amount) {
        getPlayerData(playerId).addTime(amount);
    }

    public void removeData(UUID playerId, int amount) {
        getPlayerData(playerId).removeTime(amount);
    }

    public void resetData(UUID playerId) { getPlayerData(playerId).resetTime(); }

    public int getData(UUID playerId) { return getPlayerData(playerId).getTime(); }

    public Map<UUID, RankedPlayedData> returnPlayers()
    {
        return this.players;
    }
}
