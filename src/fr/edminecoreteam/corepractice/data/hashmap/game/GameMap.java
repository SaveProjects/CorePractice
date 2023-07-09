package fr.edminecoreteam.corepractice.data.hashmap.game;

import org.bukkit.entity.Player;

import java.util.HashMap;
import java.util.Map;

public class GameMap
{
    private Map<Player, HashMap<String, Integer>> playerMap;

    public GameMap() {
        playerMap = new HashMap<>();
    }

    public void ajouterJoueur(Player joueur, HashMap<String, Integer> valeursJoueur) {
        playerMap.put(joueur, valeursJoueur);
    }

    public Integer rechercherValeurPourJoueur(Player joueurRecherche, String valeurRecherche) {
        for (Map.Entry<Player, HashMap<String, Integer>> entry : playerMap.entrySet()) {
            Player joueur = entry.getKey();
            HashMap<String, Integer> valeursJoueur = entry.getValue();
            if (joueur.equals(joueurRecherche) && valeursJoueur.containsKey(valeurRecherche)) {
                return valeursJoueur.get(valeurRecherche);
            }
        }
        return null;
    }

    public void mettreAJourValeurPourJoueur(Player joueur, String cle, int nouvelleValeur) {
        HashMap<String, Integer> hashMapJoueur = playerMap.get(joueur);
        if (hashMapJoueur != null) {
            hashMapJoueur.put(cle, nouvelleValeur);
        }
    }

    public HashMap<String, Integer> getHashMapForJoueur(Player joueur) {
        return playerMap.get(joueur);
    }

    public void supprimerJoueur(Player joueur) {
        playerMap.remove(joueur);
    }
}
