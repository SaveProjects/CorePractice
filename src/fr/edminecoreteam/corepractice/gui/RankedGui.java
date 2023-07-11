package fr.edminecoreteam.corepractice.gui;

import fr.edminecoreteam.corepractice.Core;
import fr.edminecoreteam.corepractice.kits.LoadKits;
import fr.edminecoreteam.corepractice.listeners.ItemListeners;
import fr.edminecoreteam.corepractice.matchmaking.RankedMatchMaking;
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

public class RankedGui implements Listener
{
    private static Core core = Core.getInstance();
    private static ItemStack getSkull(String url) { return SkullNBT.getSkull(url); }

    @EventHandler
    public void inventoryClick(InventoryClickEvent e) {
        if (e.getView().getTopInventory().getTitle().equals("§8Ranked ┃ 1vs1")) {
            Player p = (Player)e.getWhoClicked();
            if (e.getCurrentItem().getType() != Material.AIR || e.getCurrentItem().getType() != null) {
                ItemStack it = e.getCurrentItem();
                ItemMeta itM = it.getItemMeta();
                if (it.getType() == Material.STAINED_GLASS_PANE) { e.setCancelled(true); }
                if (it.getType() == Material.SKULL_ITEM && itM.getDisplayName().equalsIgnoreCase("§b§l2vs2")) { e.setCancelled(true); gui(p, "2vs2"); }

                for (String gameMode : core.getConfig().getConfigurationSection("kits.normal").getKeys(false))
                {
                    if (core.getConfig().getString("kits.normal." + gameMode + ".name").replace("&", "§").equalsIgnoreCase(itM.getDisplayName()))
                    {
                        e.setCancelled(true);
                        RankedMatchMaking matchMaking = new RankedMatchMaking(p);
                        matchMaking.start(core.getConfig().getString("kits.normal." + gameMode + ".id"));
                        ItemListeners.foundGameItems(p);
                        core.getIfSoloOrDuo().putIfSoloOrDuo(p, "solo");
                        p.closeInventory();
                    }
                }
            }
        }
        if (e.getView().getTopInventory().getTitle().equals("§8Ranked ┃ 2vs2")) {
            Player p = (Player)e.getWhoClicked();
            if (e.getCurrentItem().getType() != Material.AIR || e.getCurrentItem().getType() != null) {
                ItemStack it = e.getCurrentItem();
                ItemMeta itM = it.getItemMeta();
                if (it.getType() == Material.STAINED_GLASS_PANE) { e.setCancelled(true); }
                if (it.getType() == Material.SKULL_ITEM && itM.getDisplayName().equalsIgnoreCase("§b§l1vs1")) { e.setCancelled(true); gui(p, "1vs1"); }

                for (String gameMode : core.getConfig().getConfigurationSection("kits.normal").getKeys(false))
                {
                    if (core.getConfig().getString("kits.normal." + gameMode + ".name").replace("&", "§").equalsIgnoreCase(itM.getDisplayName()))
                    {
                        e.setCancelled(true);
                        RankedMatchMaking matchMaking = new RankedMatchMaking(p);
                        matchMaking.start(core.getConfig().getString("kits.normal." + gameMode + ".id"));
                        ItemListeners.foundGameItems(p);
                        core.getIfSoloOrDuo().putIfSoloOrDuo(p, "duo");
                        p.closeInventory();
                    }
                }
            }
        }
    }

    public static void gui(Player p, String type) {

        if (type.equalsIgnoreCase("1vs1"))
        {
            Inventory inv = Bukkit.createInventory(null, 54, "§8Ranked ┃ 1vs1");

            ItemStack deco = new ItemStack(Material.STAINED_GLASS_PANE, 1, (short)3);
            ItemMeta decoM = deco.getItemMeta();
            decoM.addEnchant(Enchantment.DAMAGE_ALL, 1, true);
            decoM.addItemFlags(new ItemFlag[] { ItemFlag.HIDE_ENCHANTS });
            decoM.setDisplayName("§r");
            deco.setItemMeta(decoM);
            inv.setItem(0, deco); inv.setItem(8, deco); inv.setItem(9, deco); inv.setItem(17, deco);
            inv.setItem(45, deco); inv.setItem(53, deco); inv.setItem(36, deco); inv.setItem(44, deco);

            p.openInventory(inv);
            p.playSound(p.getLocation(), Sound.NOTE_PLING, 1.0f, 2.0f);

            ItemStack game1vs1 = getSkull("http://textures.minecraft.net/texture/aebd85b7f9e6222b395451adb46b76a62f7e95da6fbf7efe715c783455140");
            ItemMeta game1vs1M = game1vs1.getItemMeta();
            game1vs1M.setDisplayName("§b§l1vs1");
            ArrayList<String> loregame1vs1 = new ArrayList<String>();
            loregame1vs1.add("");
            loregame1vs1.add(" §aDescription:");
            loregame1vs1.add(" §f▶ §7Match: §a1vs1");
            loregame1vs1.add("");
            loregame1vs1.add("§8➡ §fCliquez pour y accéder.");
            game1vs1M.setLore(loregame1vs1);
            game1vs1.setItemMeta(game1vs1M);
            inv.setItem(18, game1vs1);

            ItemStack game2vs2 = getSkull("http://textures.minecraft.net/texture/e4b1e1d426123ce40cd6a54b0f876ad30c08539cf5a6ea63e847dc507950ff");
            ItemMeta game2vs2M = game2vs2.getItemMeta();
            game2vs2M.setDisplayName("§b§l2vs2");
            ArrayList<String> loregame2vs2 = new ArrayList<String>();
            loregame2vs2.add("");
            loregame2vs2.add(" §aDescription:");
            loregame2vs2.add(" §f▶ §7Match: §a2vs2");
            loregame2vs2.add("");
            loregame2vs2.add("§8➡ §fCliquez pour y accéder.");
            game2vs2M.setLore(loregame2vs2);
            game2vs2.setItemMeta(game2vs2M);
            inv.setItem(27, game2vs2);

            ItemStack duel = getSkull("http://textures.minecraft.net/texture/27e8abb6786cf0c8b7f83da36bf5a452edf54d20e230963298ea77b8c3f2d015");
            ItemMeta duelM = duel.getItemMeta();
            duelM.setDisplayName("§a§lDuel");
            ArrayList<String> loreduel = new ArrayList<String>();
            loreduel.add("");
            loreduel.add(" §aDescription:");
            loreduel.add(" §f▶ §7Cliquez ici pour demander");
            loreduel.add(" §f  §7un joueur en duel.");
            loreduel.add("");
            loreduel.add("§8➡ §fCliquez pour y accéder.");
            duelM.setLore(loreduel);
            duel.setItemMeta(duelM);
            inv.setItem(26, duel);

            ItemStack soon = getSkull("http://textures.minecraft.net/texture/81fb8ce6408a5851384e1c2ef753851eac18ba4018266cdd669dc944873d42");
            ItemMeta soonM = soon.getItemMeta();
            soonM.setDisplayName("§7...");
            ArrayList<String> loresoon = new ArrayList<String>();
            loresoon.add("");
            soonM.setLore(loresoon);
            soon.setItemMeta(soonM);
            inv.setItem(35, soon);
            new BukkitRunnable() {
                int t = 0;
                public void run() {

                    if (!p.getOpenInventory().getTitle().contains("§8Ranked ┃ 1vs1")) { cancel(); }
                    LoadKits kits = new LoadKits(p);

                    for (String gameMode : kits.getNormalKitList())
                    {
                        if (core.getConfig().getString("kits.normal." + gameMode + ".icon").equalsIgnoreCase("potion"))
                        {
                            ItemStack gamemode = new ItemStack(ItemStackSerializer.deserialize(core.getConfig().getString("kits.normal." + gameMode + ".icon")).getType(), core.getGameType().getListWhereGame(core.getConfig().getString("kits.normal." + gameMode + ".id"), "ranked", "solo"), (short)core.getConfig().getInt("kits.normal." + gameMode + ".potionid"));
                            ItemMeta gamemodeM = gamemode.getItemMeta();
                            gamemodeM.setDisplayName(core.getConfig().getString("kits.normal." + gameMode + ".name").replace("&", "§"));
                            ArrayList<String> loregamemode = new ArrayList<String>();
                            loregamemode.add("");
                            loregamemode.add(" §dInformation:");
                            loregamemode.add(" §f▶ §7En attente: §e" + core.getGameCheck().getListWhereGame(core.getConfig().getString("kits.normal." + gameMode + ".id"), "ranked", "solo"));
                            loregamemode.add(" §f▶ §7En jeu: §e" + core.getGameType().getListWhereGame(core.getConfig().getString("kits.normal." + gameMode + ".id"), "ranked", "solo"));
                            loregamemode.add("");
                            loregamemode.add("§8➡ §fCliquez pour rejoindre.");
                            gamemodeM.setLore(loregamemode);
                            gamemode.setItemMeta(gamemodeM);
                            inv.setItem(core.getConfig().getInt("kits.normal." + gameMode + ".slot"), gamemode);
                        }
                        else
                        {
                            ItemStack gamemode = new ItemStack(ItemStackSerializer.deserialize(core.getConfig().getString("kits.normal." + gameMode + ".icon")).getType(), core.getGameType().getListWhereGame(core.getConfig().getString("kits.normal." + gameMode + ".id"), "ranked", "solo"));
                            ItemMeta gamemodeM = gamemode.getItemMeta();
                            gamemodeM.setDisplayName(core.getConfig().getString("kits.normal." + gameMode + ".name").replace("&", "§"));
                            ArrayList<String> loregamemode = new ArrayList<String>();
                            loregamemode.add("");
                            loregamemode.add(" §dInformation:");
                            loregamemode.add(" §f▶ §7En attente: §e" + core.getGameCheck().getListWhereGame(core.getConfig().getString("kits.normal." + gameMode + ".id"), "ranked", "solo"));
                            loregamemode.add(" §f▶ §7En jeu: §e" + core.getGameType().getListWhereGame(core.getConfig().getString("kits.normal." + gameMode + ".id"), "ranked", "solo"));
                            loregamemode.add("");
                            loregamemode.add("§8➡ §fCliquez pour rejoindre.");
                            gamemodeM.setLore(loregamemode);
                            gamemode.setItemMeta(gamemodeM);
                            inv.setItem(core.getConfig().getInt("kits.normal." + gameMode + ".slot"), gamemode);
                        }
                    }

                    ++t;
                    if (t == 10) {
                        t = 0;
                    }
                }
            }.runTaskTimer((Plugin)core, 0L, 10L);
        }
        if (type.equalsIgnoreCase("2vs2"))
        {
            Inventory inv = Bukkit.createInventory(null, 54, "§8Ranked ┃ 2vs2");

            ItemStack deco = new ItemStack(Material.STAINED_GLASS_PANE, 1, (short)3);
            ItemMeta decoM = deco.getItemMeta();
            decoM.addEnchant(Enchantment.DAMAGE_ALL, 1, true);
            decoM.addItemFlags(new ItemFlag[] { ItemFlag.HIDE_ENCHANTS });
            decoM.setDisplayName("§r");
            deco.setItemMeta(decoM);
            inv.setItem(0, deco); inv.setItem(8, deco); inv.setItem(9, deco); inv.setItem(17, deco);
            inv.setItem(45, deco); inv.setItem(53, deco); inv.setItem(36, deco); inv.setItem(44, deco);

            p.openInventory(inv);
            p.playSound(p.getLocation(), Sound.NOTE_PLING, 1.0f, 2.0f);

            ItemStack game1vs1 = getSkull("http://textures.minecraft.net/texture/caf1b280cab59f4469dab9f1a2af7927ed96a81df1e24d50a8e3984abfe4044");
            ItemMeta game1vs1M = game1vs1.getItemMeta();
            game1vs1M.setDisplayName("§b§l1vs1");
            ArrayList<String> loregame1vs1 = new ArrayList<String>();
            loregame1vs1.add("");
            loregame1vs1.add(" §aDescription:");
            loregame1vs1.add(" §f▶ §7Match: §a1vs1");
            loregame1vs1.add("");
            loregame1vs1.add("§8➡ §fCliquez pour y accéder.");
            game1vs1M.setLore(loregame1vs1);
            game1vs1.setItemMeta(game1vs1M);
            inv.setItem(18, game1vs1);

            ItemStack game2vs2 = getSkull("http://textures.minecraft.net/texture/502eba83a6a953917f1d991bb379b36e7ff04a496d17f5424d04aa12efbab");
            ItemMeta game2vs2M = game2vs2.getItemMeta();
            game2vs2M.setDisplayName("§b§l2vs2");
            ArrayList<String> loregame2vs2 = new ArrayList<String>();
            loregame2vs2.add("");
            loregame2vs2.add(" §aDescription:");
            loregame2vs2.add(" §f▶ §7Match: §a2vs2");
            loregame2vs2.add("");
            loregame2vs2.add("§8➡ §fCliquez pour y accéder.");
            game2vs2M.setLore(loregame2vs2);
            game2vs2.setItemMeta(game2vs2M);
            inv.setItem(27, game2vs2);

            ItemStack duel = getSkull("http://textures.minecraft.net/texture/27e8abb6786cf0c8b7f83da36bf5a452edf54d20e230963298ea77b8c3f2d015");
            ItemMeta duelM = duel.getItemMeta();
            duelM.setDisplayName("§a§lDuel");
            ArrayList<String> loreduel = new ArrayList<String>();
            loreduel.add("");
            loreduel.add(" §aDescription:");
            loreduel.add(" §f▶ §7Cliquez ici pour demander");
            loreduel.add(" §f  §7un joueur en duel.");
            loreduel.add("");
            loreduel.add("§8➡ §fCliquez pour y accéder.");
            duelM.setLore(loreduel);
            duel.setItemMeta(duelM);
            inv.setItem(26, duel);

            ItemStack soon = getSkull("http://textures.minecraft.net/texture/81fb8ce6408a5851384e1c2ef753851eac18ba4018266cdd669dc944873d42");
            ItemMeta soonM = soon.getItemMeta();
            soonM.setDisplayName("§7...");
            soon.setItemMeta(soonM);
            inv.setItem(35, soon);

            new BukkitRunnable() {
                int t = 0;
                public void run() {

                    if (!p.getOpenInventory().getTitle().contains("§8Ranked ┃ 2vs2")) { cancel(); }
                    LoadKits kits = new LoadKits(p);

                    for (String gameMode : kits.getNormalKitList())
                    {
                        if (core.getConfig().getString("kits.normal." + gameMode + ".icon").equalsIgnoreCase("potion"))
                        {
                            ItemStack gamemode = new ItemStack(ItemStackSerializer.deserialize(core.getConfig().getString("kits.normal." + gameMode + ".icon")).getType(), core.getGameType().getListWhereGame(core.getConfig().getString("kits.normal." + gameMode + ".id"), "ranked", "duo"), (short)core.getConfig().getInt("kits.normal." + gameMode + ".potionid"));
                            ItemMeta gamemodeM = gamemode.getItemMeta();
                            gamemodeM.setDisplayName(core.getConfig().getString("kits.normal." + gameMode + ".name").replace("&", "§"));
                            ArrayList<String> loregamemode = new ArrayList<String>();
                            loregamemode.add("");
                            loregamemode.add(" §dInformation:");
                            loregamemode.add(" §f▶ §7En attente: §e" + core.getGameCheck().getListWhereGame(core.getConfig().getString("kits.normal." + gameMode + ".id"), "ranked", "duo"));
                            loregamemode.add(" §f▶ §7En jeu: §e" + core.getGameType().getListWhereGame(core.getConfig().getString("kits.normal." + gameMode + ".id"), "ranked", "duo"));
                            loregamemode.add("");
                            loregamemode.add("§8➡ §fCliquez pour rejoindre.");
                            gamemodeM.setLore(loregamemode);
                            gamemode.setItemMeta(gamemodeM);
                            inv.setItem(core.getConfig().getInt("kits.normal." + gameMode + ".slot"), gamemode);
                        }
                        else
                        {
                            ItemStack gamemode = new ItemStack(ItemStackSerializer.deserialize(core.getConfig().getString("kits.normal." + gameMode + ".icon")).getType(), core.getGameType().getListWhereGame(core.getConfig().getString("kits.normal." + gameMode + ".id"), "ranked", "duo"));
                            ItemMeta gamemodeM = gamemode.getItemMeta();
                            gamemodeM.setDisplayName(core.getConfig().getString("kits.normal." + gameMode + ".name").replace("&", "§"));
                            ArrayList<String> loregamemode = new ArrayList<String>();
                            loregamemode.add("");
                            loregamemode.add(" §dInformation:");
                            loregamemode.add(" §f▶ §7En attente: §e" + core.getGameCheck().getListWhereGame(core.getConfig().getString("kits.normal." + gameMode + ".id"), "ranked", "duo"));
                            loregamemode.add(" §f▶ §7En jeu: §e" + core.getGameType().getListWhereGame(core.getConfig().getString("kits.normal." + gameMode + ".id"), "ranked", "duo"));
                            loregamemode.add("");
                            loregamemode.add("§8➡ §fCliquez pour rejoindre.");
                            gamemodeM.setLore(loregamemode);
                            gamemode.setItemMeta(gamemodeM);
                            inv.setItem(core.getConfig().getInt("kits.normal." + gameMode + ".slot"), gamemode);
                        }
                    }

                    ++t;
                    if (t == 10) {
                        t = 0;
                    }
                }
            }.runTaskTimer((Plugin)core, 0L, 10L);
        }
        if (type.equalsIgnoreCase("duel"))
        {
            p.closeInventory();
        }
    }
}
