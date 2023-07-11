package fr.edminecoreteam.corepractice.matchduels;

import org.bukkit.entity.Player;

import java.util.HashMap;

public class IfSoloOrDuo
{
    private final HashMap<Player, String> ifSoloOrDuo;

    public IfSoloOrDuo() {
        ifSoloOrDuo = new HashMap<>();
    }

    public void putIfSoloOrDuo(Player player, String id) { ifSoloOrDuo.put(player, id); }

    public String getIfSoloOrDuo(Player player) {
        return ifSoloOrDuo.get(player);
    }

    public void removeIfSoloOrDuo(Player player) { ifSoloOrDuo.remove(player); }

    public HashMap<Player, String> getIfSoloOrDuo() { return ifSoloOrDuo; }
}
