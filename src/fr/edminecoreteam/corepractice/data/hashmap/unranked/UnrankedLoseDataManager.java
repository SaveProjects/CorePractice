package fr.edminecoreteam.corepractice.data.hashmap.unranked;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class UnrankedLoseDataManager
{
    private final Map<UUID, UnrankedLoseData> players = new HashMap<>();

    public UnrankedLoseData getPlayerData(UUID playerId) {
        return players.computeIfAbsent(playerId, id -> new UnrankedLoseData(id, 0));
    }

    public void addData(UUID playerId, int amount) {
        getPlayerData(playerId).addTime(amount);
    }

    public void removeData(UUID playerId, int amount) {
        getPlayerData(playerId).removeTime(amount);
    }

    public void resetData(UUID playerId) { getPlayerData(playerId).resetTime(); }

    public int getData(UUID playerId) { return getPlayerData(playerId).getTime(); }

    public Map<UUID, UnrankedLoseData> returnPlayers()
    {
        return this.players;
    }
}
