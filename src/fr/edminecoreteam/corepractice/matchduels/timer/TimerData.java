package fr.edminecoreteam.corepractice.matchduels.timer;

import java.util.UUID;

public class TimerData
{
    private final UUID playerId;
    private int coins;

    public TimerData(UUID playerId, int coins) {
        this.playerId = playerId;
        this.coins = coins;
    }

    public UUID getPlayerId() {
        return playerId;
    }

    public int getTime() {
        return coins;
    }

    public void addTime(int amount) {
        coins += amount;
    }

    public void removeTime(int amount) {
        coins -= amount;
    }

    public void resetTime() {
        coins = 0;
    }
}
