package fr.edminecoreteam.corepractice.data.hashmap.elo;

import java.util.UUID;

public class PlayerEloData
{
    private final UUID playerId;
    private int data;

    public PlayerEloData(UUID playerId, int data) {
        this.playerId = playerId;
        this.data = data;
    }

    public UUID getPlayerId() {
        return playerId;
    }

    public int getTime() {
        return data;
    }

    public void addTime(int amount) {
        data += amount;
    }

    public void removeTime(int amount) {
        data -= amount;
    }

    public void resetTime() {
        data = 0;
    }
}
