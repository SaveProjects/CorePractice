package fr.edminecoreteam.corepractice.gui;

import fr.edminecoreteam.corepractice.Core;
import fr.edminecoreteam.corepractice.kits.LoadKits;
import fr.edminecoreteam.corepractice.listeners.ItemListeners;
import fr.edminecoreteam.corepractice.matchmaking.UnrankedMatchMaking;
import fr.edminecoreteam.corepractice.utils.ItemStackSerializer;
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

public class ProfileGui implements Listener
{
    private static final Core core = Core.getInstance();

    @EventHandler
    public void inventoryClick(InventoryClickEvent e) {
        if (e.getView().getTopInventory().getTitle().equals("§8Profil")) {
            e.setCancelled(true);
        }
    }

    public static void gui(Player p) {

        Inventory inv = Bukkit.createInventory(null, 54, "§8Profil");

        ItemStack deco = new ItemStack(Material.STAINED_GLASS_PANE, 1, (short)14);
        ItemMeta decoM = deco.getItemMeta();
        decoM.addEnchant(Enchantment.DAMAGE_ALL, 1, true);
        decoM.addItemFlags(new ItemFlag[] { ItemFlag.HIDE_ENCHANTS });
        decoM.setDisplayName("§r");
        deco.setItemMeta(decoM);
        inv.setItem(0, deco); inv.setItem(8, deco); inv.setItem(9, deco); inv.setItem(17, deco);
        inv.setItem(45, deco); inv.setItem(53, deco); inv.setItem(36, deco); inv.setItem(44, deco);

        p.openInventory(inv);
        p.playSound(p.getLocation(), Sound.NOTE_PLING, 1.0f, 2.0f);
        new BukkitRunnable() {
            int t = 0;
            public void run() {

                if (!p.getOpenInventory().getTitle().contains("§8Profil")) { cancel(); }

                ItemStack unranked = new ItemStack(Material.IRON_SWORD, 1);
                ItemMeta unrankedM = unranked.getItemMeta();
                unrankedM.setDisplayName("§f§lUnranked");
                ArrayList<String> loreunranked = new ArrayList<String>();
                loreunranked.add("");
                loreunranked.add(" §dInformation:");
                loreunranked.add(" §f▶ §7Matchs joués: §e" + core.getUnrankedPlayedDataManager().getData(p.getUniqueId()));
                loreunranked.add(" §f▶ §7Matchs gagnés: §a" + core.getUnrankedWinDataManager().getData(p.getUniqueId()));
                loreunranked.add(" §f▶ §7Matchs perdus: §c" + core.getUnrankedLoseDataManager().getData(p.getUniqueId()));
                loreunranked.add("");
                unrankedM.setLore(loreunranked);
                unranked.setItemMeta(unrankedM);
                inv.setItem(29, unranked);

                ItemStack elo = new ItemStack(Material.DOUBLE_PLANT, 1);
                ItemMeta eloM = elo.getItemMeta();
                eloM.setDisplayName("§e§lElo");
                ArrayList<String> loreelo = new ArrayList<String>();
                loreelo.add("");
                loreelo.add(" §dInformation:");
                loreelo.add(" §f▶ §7Votre elo: §e" + core.getPlayerEloDataManager().getData(p.getUniqueId()));
                loreelo.add(" §f▶ §7Classement: §e");
                loreelo.add("");
                eloM.setLore(loreelo);
                elo.setItemMeta(eloM);
                inv.setItem(22, elo);

                ItemStack ranked = new ItemStack(Material.DIAMOND_SWORD, 1);
                ItemMeta rankedM = ranked.getItemMeta();
                rankedM.setDisplayName("§b§lRanked");
                ArrayList<String> loreranked = new ArrayList<String>();
                loreranked.add("");
                loreranked.add(" §dInformation:");
                loreranked.add(" §f▶ §7Matchs joués: §e" + core.getRankedPlayedDataManager().getData(p.getUniqueId()));
                loreranked.add(" §f▶ §7Matchs gagnés: §a" + core.getRankedWinDataManager().getData(p.getUniqueId()));
                loreranked.add(" §f▶ §7Matchs perdus: §c" + core.getRankedLoseDataManager().getData(p.getUniqueId()));
                loreranked.add("");
                rankedM.setLore(loreranked);
                ranked.setItemMeta(rankedM);
                inv.setItem(33, ranked);

                ++t;
                if (t == 10) {
                    t = 0;
                }
            }
        }.runTaskTimer((Plugin)core, 0L, 10L);
    }
}
