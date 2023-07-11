package fr.edminecoreteam.corepractice.matchduels;

import org.bukkit.entity.Player;

import java.util.HashMap;
import java.util.Map;

public class MatchDuoOppenant
{
    private Map<Integer, HashMap<Player, String>> teamMap;

    public MatchDuoOppenant() {
        teamMap = new HashMap<>();
    }

    public void createDuoGame(Integer gameID, HashMap<Player, String> team) {
        teamMap.put(gameID, team);
    }

    public String getTeam(Integer idRecherche, String valeurRecherche) {
        for (Map.Entry<Integer, HashMap<Player, String>> entry : teamMap.entrySet()) {
            Integer joueur = entry.getKey();
            HashMap<Player, String> valeursJoueur = entry.getValue();
            if (joueur.equals(idRecherche) && valeursJoueur.containsValue(valeurRecherche)) {
                return valeursJoueur.get(valeurRecherche);
            }
        }
        return null;
    }

    public boolean isInTeam(Integer idRecherche, Player p, String valeurRecherche) {
        for (Map.Entry<Integer, HashMap<Player, String>> entry : teamMap.entrySet()) {
            Integer joueur = entry.getKey();
            HashMap<Player, String> valeursJoueur = entry.getValue();
            if (joueur.equals(idRecherche) && valeursJoueur.containsKey(p) && valeursJoueur.containsValue(valeurRecherche)) {
                return true;
            }
        }
        return false;
    }

    public void updateTeam(Integer idRecherche, Player cle, String nouvelleValeur) {
        HashMap<Player, String> hashMapJoueur = teamMap.get(idRecherche);
        if (hashMapJoueur != null) {
            hashMapJoueur.put(cle, nouvelleValeur);
        }
    }

    public HashMap<Player, String> getHashMapForTeam(Integer id) {
        return teamMap.get(id);
    }

    public void removeGameTeams(Integer id) {
        teamMap.remove(id);
    }

    public void removeGameTeamsPlayer(Integer id, Player player) {
        HashMap<Player, String> hashMapJoueur = teamMap.get(id);
        if (hashMapJoueur != null && hashMapJoueur.containsKey(player)) {
            hashMapJoueur.remove(player);
        }
    }
}
