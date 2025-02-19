package fr.edminecoreteam.corepractice.matchduels.timer;

import java.util.UUID;

public class TimerData
{
    private final UUID playerId;
    private int timer;

    public TimerData(UUID playerId, int timer) {
        this.playerId = playerId;
        this.timer = timer;
    }

    public UUID getPlayerId() {
        return playerId;
    }

    public int getTime() {
        return timer;
    }

    public void addTime(int amount) {
        timer += amount;
    }

    public void removeTime(int amount) {
        timer -= amount;
    }

    public void resetTime() {
        timer = 0;
    }
}
