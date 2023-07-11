package fr.edminecoreteam.corepractice.matchduels;

import org.bukkit.entity.Player;

import java.util.HashMap;

public class DuoGestion
{
    private HashMap<Player, Player> duoGestion;

    public DuoGestion() {
        duoGestion = new HashMap<>();
    }

    public void putDuoGestion(Player player, Player id) { duoGestion.put(player, id); }

    public Player getDuo(Player player) {
        return duoGestion.get(player);
    }

    public void removeDuoGestion(Player player) { duoGestion.remove(player); }

    public HashMap<Player, Player> getDuoGestion() { return duoGestion; }
}
