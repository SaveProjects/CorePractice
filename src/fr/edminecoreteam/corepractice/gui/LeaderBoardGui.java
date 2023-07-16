package fr.edminecoreteam.corepractice.gui;

import fr.edminecoreteam.corepractice.Core;
import fr.edminecoreteam.corepractice.data.LeaderBoardData;
import fr.edminecoreteam.corepractice.kits.LoadKits;
import fr.edminecoreteam.corepractice.listeners.ItemListeners;
import fr.edminecoreteam.corepractice.matchmaking.UnrankedMatchMaking;
import fr.edminecoreteam.corepractice.utils.ItemStackSerializer;
import fr.edminecoreteam.corepractice.utils.SkullNBT;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.plugin.Plugin;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class LeaderBoardGui implements Listener
{

    private static Core core = Core.getInstance();
    private static ItemStack getSkull(String url) { return SkullNBT.getSkull(url); }

    @EventHandler
    public void inventoryClick(InventoryClickEvent e) {
        if (e.getView().getTopInventory().getTitle().equals("§8LeaderBoard ┃ Unranked")) {
            Player p = (Player)e.getWhoClicked();
            if (e.getCurrentItem().getType() != Material.AIR && e.getCurrentItem().getType() != null) {
                ItemStack it = e.getCurrentItem();
                ItemMeta itM = it.getItemMeta();
                if (it.getType() == Material.STAINED_GLASS_PANE) { e.setCancelled(true); }
                if (it.getType() == Material.SKULL_ITEM && itM.getDisplayName().equalsIgnoreCase("§a§lRanked")) { e.setCancelled(true); gui(p, "ranked"); }

            }
        }
        if (e.getView().getTopInventory().getTitle().equals("§8LeaderBoard ┃ Ranked")) {
            Player p = (Player)e.getWhoClicked();
            if (e.getCurrentItem().getType() != Material.AIR && e.getCurrentItem().getType() != null) {
                ItemStack it = e.getCurrentItem();
                ItemMeta itM = it.getItemMeta();
                if (it.getType() == Material.STAINED_GLASS_PANE) { e.setCancelled(true); }
                if (it.getType() == Material.SKULL_ITEM && itM.getDisplayName().equalsIgnoreCase("§a§lUnranked")) { e.setCancelled(true); gui(p, "unranked"); }
            }
        }
    }

    public static void gui(Player p, String type) {

        if (type.equalsIgnoreCase("unranked")) {
            Inventory inv = Bukkit.createInventory(null, 54, "§8LeaderBoard ┃ Unranked");

            ItemStack deco = new ItemStack(Material.STAINED_GLASS_PANE, 1, (short) 5);
            ItemMeta decoM = deco.getItemMeta();
            decoM.addEnchant(Enchantment.DAMAGE_ALL, 1, true);
            decoM.addItemFlags(new ItemFlag[]{ItemFlag.HIDE_ENCHANTS});
            decoM.setDisplayName("§r");
            deco.setItemMeta(decoM);
            inv.setItem(0, deco);
            inv.setItem(8, deco);
            inv.setItem(9, deco);
            inv.setItem(17, deco);
            inv.setItem(45, deco);
            inv.setItem(53, deco);
            inv.setItem(36, deco);
            inv.setItem(44, deco);

            ItemStack unranked = getSkull("http://textures.minecraft.net/texture/1f5efe243611d748e6509617429d7d84afcb85c5e2516d8bd5a011b5deb0b373");
            ItemMeta unrankedM = unranked.getItemMeta();
            unrankedM.setDisplayName("§a§lUnranked");
            ArrayList<String> loreunranked = new ArrayList<String>();
            loreunranked.add("");
            loreunranked.add("§8➡ §fCliquez pour y accéder.");
            unrankedM.setLore(loreunranked);
            unranked.setItemMeta(unrankedM);
            inv.setItem(18, unranked);

            ItemStack ranked = getSkull("http://textures.minecraft.net/texture/50f0fdd5d8dc6b54eab98f6f85ac14dbb3b588632962ec3d1a4c24c50bd4e3");
            ItemMeta rankedM = ranked.getItemMeta();
            rankedM.setDisplayName("§a§lRanked");
            ArrayList<String> loreranked = new ArrayList<String>();
            loreranked.add("");
            loreranked.add("§8➡ §fCliquez pour y accéder.");
            rankedM.setLore(loreranked);
            ranked.setItemMeta(rankedM);
            inv.setItem(27, ranked);

            ItemStack guilds = getSkull("http://textures.minecraft.net/texture/5593da74e9688413c237f3ce324d7085aca88dfa4b7257c2da0bdfc34563077");
            ItemMeta guildsM = guilds.getItemMeta();
            guildsM.setDisplayName("§a§lGuilds");
            ArrayList<String> loreguilds = new ArrayList<String>();
            loreguilds.add("");
            loreguilds.add("§8➡ §fCliquez pour y accéder.");
            guildsM.setLore(loreguilds);
            guilds.setItemMeta(guildsM);
            inv.setItem(26, guilds);

            ItemStack tournament = getSkull("http://textures.minecraft.net/texture/367f15e2a8e9c0cbdd479810f7ee355c404c979f424d699679bb613cdf71758");
            ItemMeta tournamentM = tournament.getItemMeta();
            tournamentM.setDisplayName("§a§lTournois Officiels");
            ArrayList<String> loretournament = new ArrayList<String>();
            loretournament.add("");
            loretournament.add("§8➡ §fCliquez pour y accéder.");
            tournamentM.setLore(loretournament);
            tournament.setItemMeta(tournamentM);
            inv.setItem(35, tournament);

            LoadKits kits = new LoadKits(p);

            LeaderBoardData leadData = new LeaderBoardData();

            for (String gameMode : kits.getNormalKitList()) {

                final String stats = gameMode + "_unranked_win";
                final List<String> listLead = leadData.getTopPlayers(stats);
                int count = 0;

                if (core.getConfig().getString("kits.normal." + gameMode + ".icon").equalsIgnoreCase("potion")) {

                    ItemStack gamemode = new ItemStack(ItemStackSerializer.deserialize(core.getConfig().getString("kits.normal." + gameMode + ".icon")).getType(), core.getGameType().getListWhereGame(core.getConfig().getString("kits.normal." + gameMode + ".id"), "ranked", "solo"), (short) core.getConfig().getInt("kits.normal." + gameMode + ".potionid"));
                    ItemMeta gamemodeM = gamemode.getItemMeta();
                    gamemodeM.setDisplayName(core.getConfig().getString("kits.normal." + gameMode + ".name").replace("&", "§"));
                    ArrayList<String> loregamemode = new ArrayList<String>();
                    loregamemode.add("");
                    loregamemode.add(" §dTop 10:");
                    for (String stringLead : listLead)
                    {
                        ++count;
                        LeaderBoardData lLeadData = new LeaderBoardData(stringLead);
                        loregamemode.add(" §f▶ §a#" + count + " §7" + stringLead + ": §e" + lLeadData.getGameData(gameMode + "_unranked_win") + " matchs gagnées.");
                    }
                    loregamemode.add("");
                    gamemodeM.setLore(loregamemode);
                    gamemode.setItemMeta(gamemodeM);
                    inv.setItem(core.getConfig().getInt("kits.normal." + gameMode + ".slot"), gamemode);
                } else {

                    ItemStack gamemode = new ItemStack(ItemStackSerializer.deserialize(core.getConfig().getString("kits.normal." + gameMode + ".icon")).getType(), core.getGameType().getListWhereGame(core.getConfig().getString("kits.normal." + gameMode + ".id"), "ranked", "solo"));
                    ItemMeta gamemodeM = gamemode.getItemMeta();
                    gamemodeM.setDisplayName(core.getConfig().getString("kits.normal." + gameMode + ".name").replace("&", "§"));
                    ArrayList<String> loregamemode = new ArrayList<String>();
                    loregamemode.add("");
                    loregamemode.add(" §dTop 10:");
                    for (String stringLead : listLead)
                    {
                        ++count;
                        LeaderBoardData lLeadData = new LeaderBoardData(stringLead);
                        loregamemode.add(" §f▶ §a#" + count + " §7" + stringLead + ": §e" + lLeadData.getGameData(gameMode + "_unranked_win") + " matchs gagnées.");
                    }
                    loregamemode.add("");
                    gamemodeM.setLore(loregamemode);
                    gamemode.setItemMeta(gamemodeM);
                    inv.setItem(core.getConfig().getInt("kits.normal." + gameMode + ".slot"), gamemode);
                }
            }

            p.openInventory(inv);
            p.playSound(p.getLocation(), Sound.NOTE_PLING, 1.0f, 2.0f);
        }
        if (type.equalsIgnoreCase("ranked")) {
            Inventory inv = Bukkit.createInventory(null, 54, "§8LeaderBoard ┃ Ranked");

            ItemStack deco = new ItemStack(Material.STAINED_GLASS_PANE, 1, (short) 5);
            ItemMeta decoM = deco.getItemMeta();
            decoM.addEnchant(Enchantment.DAMAGE_ALL, 1, true);
            decoM.addItemFlags(new ItemFlag[]{ItemFlag.HIDE_ENCHANTS});
            decoM.setDisplayName("§r");
            deco.setItemMeta(decoM);
            inv.setItem(0, deco);
            inv.setItem(8, deco);
            inv.setItem(9, deco);
            inv.setItem(17, deco);
            inv.setItem(45, deco);
            inv.setItem(53, deco);
            inv.setItem(36, deco);
            inv.setItem(44, deco);

            ItemStack unranked = getSkull("http://textures.minecraft.net/texture/28158d853c948f3df72ed1d8938172c8ac8824b31d42ce4b5f048bd2b5679");
            ItemMeta unrankedM = unranked.getItemMeta();
            unrankedM.setDisplayName("§a§lUnranked");
            ArrayList<String> loreunranked = new ArrayList<String>();
            loreunranked.add("");
            loreunranked.add("§8➡ §fCliquez pour y accéder.");
            unrankedM.setLore(loreunranked);
            unranked.setItemMeta(unrankedM);
            inv.setItem(18, unranked);

            ItemStack ranked = getSkull("http://textures.minecraft.net/texture/66362ba4b2c7b05314bf25e95f760b99fe4b341dc841770a27c244bcebfd5e8");
            ItemMeta rankedM = ranked.getItemMeta();
            rankedM.setDisplayName("§a§lRanked");
            ArrayList<String> loreranked = new ArrayList<String>();
            loreranked.add("");
            loreranked.add("§8➡ §fCliquez pour y accéder.");
            rankedM.setLore(loreranked);
            ranked.setItemMeta(rankedM);
            inv.setItem(27, ranked);

            ItemStack guilds = getSkull("http://textures.minecraft.net/texture/5593da74e9688413c237f3ce324d7085aca88dfa4b7257c2da0bdfc34563077");
            ItemMeta guildsM = guilds.getItemMeta();
            guildsM.setDisplayName("§a§lGuilds");
            ArrayList<String> loreguilds = new ArrayList<String>();
            loreguilds.add("");
            loreguilds.add("§8➡ §fCliquez pour y accéder.");
            guildsM.setLore(loreguilds);
            guilds.setItemMeta(guildsM);
            inv.setItem(26, guilds);

            ItemStack tournament = getSkull("http://textures.minecraft.net/texture/367f15e2a8e9c0cbdd479810f7ee355c404c979f424d699679bb613cdf71758");
            ItemMeta tournamentM = tournament.getItemMeta();
            tournamentM.setDisplayName("§a§lTournois Officiels");
            ArrayList<String> loretournament = new ArrayList<String>();
            loretournament.add("");
            loretournament.add("§8➡ §fCliquez pour y accéder.");
            tournamentM.setLore(loretournament);
            tournament.setItemMeta(tournamentM);
            inv.setItem(35, tournament);

            LoadKits kits = new LoadKits(p);

            LeaderBoardData leadData = new LeaderBoardData();

            for (String gameMode : kits.getNormalKitList()) {

                final String stats = gameMode + "_elo";
                final List<String> listLead = leadData.getTopPlayers(stats);
                int count = 0;

                if (core.getConfig().getString("kits.normal." + gameMode + ".icon").equalsIgnoreCase("potion")) {

                    ItemStack gamemode = new ItemStack(ItemStackSerializer.deserialize(core.getConfig().getString("kits.normal." + gameMode + ".icon")).getType(), core.getGameType().getListWhereGame(core.getConfig().getString("kits.normal." + gameMode + ".id"), "ranked", "solo"), (short) core.getConfig().getInt("kits.normal." + gameMode + ".potionid"));
                    ItemMeta gamemodeM = gamemode.getItemMeta();
                    gamemodeM.setDisplayName(core.getConfig().getString("kits.normal." + gameMode + ".name").replace("&", "§"));
                    ArrayList<String> loregamemode = new ArrayList<String>();
                    loregamemode.add("");
                    loregamemode.add(" §dTop 10:");
                    for (String stringLead : listLead)
                    {
                        ++count;
                        LeaderBoardData lLeadData = new LeaderBoardData(stringLead);
                        loregamemode.add(" §f▶ §a#" + count + " §7" + stringLead + ": §e" + lLeadData.getGameData(gameMode + "_elo") + " elo.");
                    }
                    loregamemode.add("");
                    gamemodeM.setLore(loregamemode);
                    gamemode.setItemMeta(gamemodeM);
                    inv.setItem(core.getConfig().getInt("kits.normal." + gameMode + ".slot"), gamemode);
                } else {

                    ItemStack gamemode = new ItemStack(ItemStackSerializer.deserialize(core.getConfig().getString("kits.normal." + gameMode + ".icon")).getType(), core.getGameType().getListWhereGame(core.getConfig().getString("kits.normal." + gameMode + ".id"), "ranked", "solo"));
                    ItemMeta gamemodeM = gamemode.getItemMeta();
                    gamemodeM.setDisplayName(core.getConfig().getString("kits.normal." + gameMode + ".name").replace("&", "§"));
                    ArrayList<String> loregamemode = new ArrayList<String>();
                    loregamemode.add("");
                    loregamemode.add(" §dTop 10:");
                    for (String stringLead : listLead)
                    {
                        ++count;
                        LeaderBoardData lLeadData = new LeaderBoardData(stringLead);
                        loregamemode.add(" §f▶ §a#" + count + " §7" + stringLead + ": §e" + lLeadData.getGameData(gameMode + "_elo") + " elo.");
                    }
                    loregamemode.add("");
                    gamemodeM.setLore(loregamemode);
                    gamemode.setItemMeta(gamemodeM);
                    inv.setItem(core.getConfig().getInt("kits.normal." + gameMode + ".slot"), gamemode);
                }
            }
            p.openInventory(inv);
            p.playSound(p.getLocation(), Sound.NOTE_PLING, 1.0f, 2.0f);
        }
    }
}
