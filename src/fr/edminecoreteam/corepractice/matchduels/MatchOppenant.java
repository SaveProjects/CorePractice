package fr.edminecoreteam.corepractice.matchduels;

import org.bukkit.entity.Player;

import java.util.HashMap;

public class MatchOppenant
{
    private HashMap<Player, Player> getMatchOppenant;

    public MatchOppenant() {
        getMatchOppenant = new HashMap<>();
    }

    public void putMatchOppenant(Player player, Player oppenant) { getMatchOppenant.put(player, oppenant); }

    public Player getMatchOppenant(Player player) {
        return getMatchOppenant.get(player);
    }

    public void removeMatchOppenant(Player player) { getMatchOppenant.remove(player); }

    public HashMap<Player, Player> getMatchOppenant() { return getMatchOppenant; }
}
