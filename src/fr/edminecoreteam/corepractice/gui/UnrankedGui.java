package fr.edminecoreteam.corepractice.gui;

import fr.edminecoreteam.corepractice.Core;
import fr.edminecoreteam.corepractice.kits.LoadKits;
import fr.edminecoreteam.corepractice.listeners.ItemListeners;
import fr.edminecoreteam.corepractice.matchmaking.UnrankedMatchMaking;
import fr.edminecoreteam.corepractice.utils.ItemStackSerializer;
import org.bukkit.Bukkit;
import org.bukkit.Location;
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
import org.bukkit.inventory.meta.SkullMeta;
import org.bukkit.plugin.Plugin;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.ArrayList;

public class UnrankedGui implements Listener
{
    private static Core core = Core.getInstance();

    @EventHandler
    public void inventoryClick(InventoryClickEvent e) {
        if (e.getView().getTopInventory().getTitle().equals("§8Unranked")) {
            Player p = (Player)e.getWhoClicked();
            if (e.getCurrentItem().getType() == null) {
                return;
            }

            ItemStack it = e.getCurrentItem();
            if (it.getType() == Material.STAINED_GLASS_PANE) { e.setCancelled(true); }

            for (String gameMode : core.getConfig().getConfigurationSection("kits.1vs1").getKeys(false))
            {
                ItemMeta itM = it.getItemMeta();
                if (it == ItemStackSerializer.deserialize(core.getConfig().getString("kits.1vs1." + gameMode + ".icon")))
                {
                    if (core.getConfig().getString("kits.1vs1." + gameMode + ".name").replace("&", "§").equalsIgnoreCase(itM.getDisplayName()))
                    {
                        e.setCancelled(true);
                        UnrankedMatchMaking matchMaking = new UnrankedMatchMaking(p);
                        matchMaking.start(core.getConfig().getString("kits.1vs1." + gameMode + ".id"));
                        ItemListeners.foundGameItems(p);
                        p.closeInventory();
                    }
                    else
                    {
                        return;
                    }
                }
            }
        }
    }

    public static void gui(Player p) {

        Inventory inv = Bukkit.createInventory(null, 54, "§8Unranked");

        ItemStack deco = new ItemStack(Material.STAINED_GLASS_PANE, 1, (short)0);
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

                if (!p.getOpenInventory().getTitle().contains("§8Unranked")) { cancel(); }
                LoadKits kits = new LoadKits(p);

                for (String gameMode : kits.getUnrankedKitList())
                {
                    if (core.getConfig().getString("kits.1vs1." + gameMode + ".icon").equalsIgnoreCase("potion"))
                    {
                        ItemStack gamemode = new ItemStack(ItemStackSerializer.deserialize(core.getConfig().getString("kits.1vs1." + gameMode + ".icon")).getType(), core.getGameType().getListWhereGame(core.getConfig().getString("kits.1vs1." + gameMode + ".id")), (short)core.getConfig().getInt("kits.1vs1." + gameMode + ".potionid"));
                        ItemMeta gamemodeM = gamemode.getItemMeta();
                        gamemodeM.setDisplayName(core.getConfig().getString("kits.1vs1." + gameMode + ".name").replace("&", "§"));
                        ArrayList<String> loregamemode = new ArrayList<String>();
                        loregamemode.add("");
                        loregamemode.add(" §dInformation:");
                        loregamemode.add(" §f▶ §7En attente: §e" + core.getGameCheck().getListWhereGame(core.getConfig().getString("kits.1vs1." + gameMode + ".id"), "unranked"));
                        loregamemode.add(" §f▶ §7En jeu: §e" + core.getGameType().getListWhereGame(core.getConfig().getString("kits.1vs1." + gameMode + ".id")));
                        loregamemode.add("");
                        loregamemode.add("§8➡ §fCliquez pour rejoindre.");
                        gamemodeM.setLore(loregamemode);
                        gamemode.setItemMeta(gamemodeM);
                        inv.setItem(core.getConfig().getInt("kits.1vs1." + gameMode + ".slot"), gamemode);
                    }
                    else
                    {
                        ItemStack gamemode = new ItemStack(ItemStackSerializer.deserialize(core.getConfig().getString("kits.1vs1." + gameMode + ".icon")).getType(), core.getGameType().getListWhereGame(core.getConfig().getString("kits.1vs1." + gameMode + ".id")));
                        ItemMeta gamemodeM = gamemode.getItemMeta();
                        gamemodeM.setDisplayName(core.getConfig().getString("kits.1vs1." + gameMode + ".name").replace("&", "§"));
                        ArrayList<String> loregamemode = new ArrayList<String>();
                        loregamemode.add("");
                        loregamemode.add(" §dInformation:");
                        loregamemode.add(" §f▶ §7En attente: §e" + core.getGameCheck().getListWhereGame(core.getConfig().getString("kits.1vs1." + gameMode + ".id"), "unranked"));
                        loregamemode.add(" §f▶ §7En jeu: §e" + core.getGameType().getListWhereGame(core.getConfig().getString("kits.1vs1." + gameMode + ".id")));
                        loregamemode.add("");
                        loregamemode.add("§8➡ §fCliquez pour rejoindre.");
                        gamemodeM.setLore(loregamemode);
                        gamemode.setItemMeta(gamemodeM);
                        inv.setItem(core.getConfig().getInt("kits.1vs1." + gameMode + ".slot"), gamemode);
                    }
                }

                ++t;
                if (t == 10) {
                    t = 0;
                }
            }
        }.runTaskTimer((Plugin)core, 0L, 10L);
    }
}
