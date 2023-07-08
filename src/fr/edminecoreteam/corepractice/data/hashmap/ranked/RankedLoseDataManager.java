package fr.edminecoreteam.corepractice.data.hashmap.ranked;

import fr.edminecoreteam.corepractice.data.hashmap.ranked.RankedLoseData;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class RankedLoseDataManager
{
    private final Map<UUID, RankedLoseData> players = new HashMap<>();

    public RankedLoseData getPlayerData(UUID playerId) {
        return players.computeIfAbsent(playerId, id -> new RankedLoseData(id, 0));
    }

    public void addData(UUID playerId, int amount) {
        getPlayerData(playerId).addTime(amount);
    }

    public void removeData(UUID playerId, int amount) {
        getPlayerData(playerId).removeTime(amount);
    }

    public void resetData(UUID playerId) { getPlayerData(playerId).resetTime(); }

    public Map<UUID, RankedLoseData> returnPlayers()
    {
        return this.players;
    }
}
