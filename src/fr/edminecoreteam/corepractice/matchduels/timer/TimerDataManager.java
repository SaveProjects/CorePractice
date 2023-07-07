package fr.edminecoreteam.corepractice.matchduels.timer;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class TimerDataManager
{
    private final Map<UUID, TimerData> players = new HashMap<>();

    public TimerData getPlayerData(UUID playerId) {
        return players.computeIfAbsent(playerId, id -> new TimerData(id, 0));
    }

    public void addTime(UUID playerId, int amount) {
        getPlayerData(playerId).addTime(amount);
    }

    public void removeTime(UUID playerId, int amount) {
        getPlayerData(playerId).removeTime(amount);
    }

    public void resetTime(UUID playerId) { getPlayerData(playerId).resetTime(); }

    public Map<UUID, TimerData> returnPlayers()
    {
        return this.players;
    }
}
