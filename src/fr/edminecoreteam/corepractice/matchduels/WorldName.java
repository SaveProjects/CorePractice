package fr.edminecoreteam.corepractice.matchduels;

import org.bukkit.entity.Player;

import java.util.HashMap;

public class WorldName
{
    private HashMap<Player, String> getWorldName;

    public WorldName() {
        getWorldName = new HashMap<>();
    }

    public void putWorldName(Player player, String gameType) { getWorldName.put(player, gameType); }

    public String getWorldName(Player player) {
        return getWorldName.get(player);
    }

    public void removeWorldName(Player player) { getWorldName.remove(player); }

    public HashMap<Player, String> getWorld() { return getWorldName; }
}
