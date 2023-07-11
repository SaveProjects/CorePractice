package fr.edminecoreteam.corepractice.scoreboard;

import org.bukkit.Bukkit;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftPlayer;
import org.bukkit.entity.Player;

import fr.edminecoreteam.corepractice.Core;

import java.util.HashMap;
import java.util.UUID;

/*
 * This file is part of SamaGamesAPI.
 *
 * SamaGamesAPI is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * SamaGamesAPI is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with SamaGamesAPI.  If not, see <http://www.gnu.org/licenses/>.
 */
public class PersonalScoreboard {

    private static Core core = Core.getInstance();
    private Player player;
    private final UUID uuid;
    private final ObjectiveSign objectiveSign;

    PersonalScoreboard(Player player){
        this.player = player;
        uuid = player.getUniqueId();
        objectiveSign = new ObjectiveSign("sidebar", "Edmine");

        reloadData();
        objectiveSign.addReceiver(player);
    }

    public String convertTime(int timeInSeconds)
    {
        int minutes = timeInSeconds / 60;
        int seconds = timeInSeconds % 60;
        return String.format("%02dm %02ds", minutes, seconds);
    }

    public void reloadData(){}

    public void setLines(String ip){
        objectiveSign.setDisplayName("§8● §6§lPractice §8●");

        if (core.getInLobby().contains(player))
        {
            int inLobby = core.getInLobby().size();
            int inMatchs = core.getInDuel().size() + core.getInPreDuel().size() + core.getInEndDuel().size();

            objectiveSign.setLine(0, "§1§l§8");
            objectiveSign.setLine(1, " §f➡ §d§lInformations:");
            objectiveSign.setLine(2, "  §8• §7Compte: §f" + player.getName());
            objectiveSign.setLine(3, "  §8• §7Elo: §e" + core.getPlayerEloDataManager().getData(player.getUniqueId()));
            objectiveSign.setLine(4, "  §8• §7Classement: §f");
            objectiveSign.setLine(5, "  §8• §7Votre ping: §b" + ((CraftPlayer) player).getHandle().ping);
            objectiveSign.setLine(6, "§2§7§6");
            objectiveSign.setLine(7, "  §8• §7Lobby: §a" + inLobby);
            objectiveSign.setLine(8, "  §8• §7En match: §d" + inMatchs);
            objectiveSign.setLine(9, "§3§4");
            objectiveSign.setLine(10, " §8➡ " + ip);

            objectiveSign.updateLines();
        }
        if (core.getInLobby().contains(player) && core.getInWaiting().contains(player))
        {
            objectiveSign.setLine(0, "§1§4");
            objectiveSign.setLine(1, " §f➡ §b§lRecherche:");
            objectiveSign.setLine(2, "  §8• §7Kit: §6" + Render(core.getGameCheck().getGame(player)));
            objectiveSign.setLine(3, "  §8• §7MatchMaking: §a" + Render(core.getGameIs().getGameIs(player)));
            objectiveSign.setLine(4, "  §8• §7Mode: §9" + Render(core.getIfSoloOrDuo().getIfSoloOrDuo(player)));
            objectiveSign.setLine(5, "§2§b§l");
            objectiveSign.setLine(6, "  §8• §7Attente: §a" + convertTime(core.getTime(player)));
            objectiveSign.setLine(7, "§4§l§2§4");
            objectiveSign.setLine(8, " §8➡ " + ip);

            objectiveSign.updateLines();
        }
        if (core.getInDuel().contains(player) || core.getInPreDuel().contains(player))
        {
            if (core.getIfSoloOrDuo().getIfSoloOrDuo(player).equalsIgnoreCase("solo"))
            {
                Player oppenant = core.getMatchOppenant().getMatchOppenant(player);

                objectiveSign.setLine(0, "§1§2§3");
                objectiveSign.setLine(1, " §f➡ §b§lMatch en cours:");
                objectiveSign.setLine(2, "  §8• §7Adversaire: §c" + oppenant.getName());
                objectiveSign.setLine(3, "  §8• §7Votre ping: §b" + ((CraftPlayer) player).getHandle().ping);
                objectiveSign.setLine(4, "  §8• §7Ping adversaire: §b" + ((CraftPlayer) oppenant).getHandle().ping);
                objectiveSign.setLine(5, "§2§4");
                objectiveSign.setLine(6, "  §8• §7Durée: §a" + convertTime(core.getTime(player)));
                objectiveSign.setLine(7, "  §8• §7Carte: §9" + core.getWorldName().getWorldName(player));
                objectiveSign.setLine(8, "§3§l§m");
                objectiveSign.setLine(9, " §8➡ " + ip);

                objectiveSign.updateLines();
            }
            if (core.getIfSoloOrDuo().getIfSoloOrDuo(player).equalsIgnoreCase("duo"))
            {
                /*int gameID = core.getGameID().getGameID(player);
                HashMap<Player, String> getTeams = core.getMatchDuoOppenant().getHashMapForTeam(gameID);

                for (Player pS : getTeams.keySet())
                {

                }

                objectiveSign.setLine(0, "§1");
                objectiveSign.setLine(1, " §f➡ §b§lMatch en cours:");
                objectiveSign.setLine(2, "  §8• §7Adversaires: §c" + oppenant.getName());
                objectiveSign.setLine(3, "  §8• §7Vos pings: §b" + ((CraftPlayer) player).getHandle().ping);
                objectiveSign.setLine(4, "  §8• §7Ping adversaire: §b" + ((CraftPlayer) oppenant).getHandle().ping);
                objectiveSign.setLine(5, "§2");
                objectiveSign.setLine(6, "  §8• §7Durée: §a" + convertTime(core.getTime(player)));
                objectiveSign.setLine(7, "  §8• §7Carte: §9" + core.getWorldName().getWorldName(player));
                objectiveSign.setLine(8, "§3");
                objectiveSign.setLine(9, " §8➡ " + ip);*/
            }
        }
    }

    public void onLogout(){
        objectiveSign.removeReceiver(Bukkit.getServer().getOfflinePlayer(uuid));
    }

    private String Render(String chaine) {
        if (chaine == null || chaine.isEmpty()) {
            return chaine;  // Retourne la chaîne inchangée si elle est vide ou nulle
        }

        char premierCaractere = Character.toUpperCase(chaine.charAt(0));
        return premierCaractere + chaine.substring(1);
    }
}