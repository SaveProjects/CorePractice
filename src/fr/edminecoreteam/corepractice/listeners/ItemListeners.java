package fr.edminecoreteam.corepractice.listeners;

import fr.edminecoreteam.corepractice.Core;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.SkullMeta;

public class ItemListeners implements Listener
{

    private static Core core = Core.getInstance();

    @EventHandler
    public void onDrop(PlayerDropItemEvent e)
    {
        Player p = e.getPlayer();
        if (core.getInLobby().contains(p) || core.getInEditor().contains(p))
        {
            e.setCancelled(true);
        }
    }

    @EventHandler
    public void onInventoryClick(InventoryClickEvent e)
    {
        Player p = (Player) e.getWhoClicked();
        if (core.getInLobby().contains(p))
        {
            e.setCancelled(true);
        }
    }

    public static void getLobbyItems(Player p)
    {
        p.getInventory().clear();

        ItemStack unranked = new ItemStack(Material.IRON_SWORD, 1);
        ItemMeta unrankedM = unranked.getItemMeta();
        unrankedM.setDisplayName("§f§lUnranked §7• Clique");
        unranked.setItemMeta(unrankedM);
        p.getInventory().setItem(0, unranked);

        ItemStack ranked = new ItemStack(Material.DIAMOND_SWORD, 1);
        ItemMeta rankedM = ranked.getItemMeta();
        rankedM.setDisplayName("§b§lRanked §7• Clique");
        ranked.setItemMeta(rankedM);
        p.getInventory().setItem(1, ranked);

        ItemStack ffa = new ItemStack(Material.GOLD_AXE, 1);
        ItemMeta ffaM = ffa.getItemMeta();
        ffaM.setDisplayName("§e§lFFA §7• Clique");
        ffa.setItemMeta(ffaM);
        p.getInventory().setItem(3, ffa);

        ItemStack party = new ItemStack(Material.SKULL_ITEM, 1, (short) 3);
        SkullMeta partyM = (SkullMeta) party.getItemMeta();
        partyM.setDisplayName("§a§lCréer une partie §7• Clique");
        partyM.setOwner(p.getName());
        party.setItemMeta(partyM);
        p.getInventory().setItem(5, party);

        ItemStack settings = new ItemStack(Material.REDSTONE_COMPARATOR, 1);
        ItemMeta settingsM = settings.getItemMeta();
        settingsM.setDisplayName("§9§lRéglages §7• Clique");
        settings.setItemMeta(settingsM);
        p.getInventory().setItem(6, settings);

        ItemStack kitEditor = new ItemStack(Material.BOOK, 1);
        ItemMeta kitEditorM = kitEditor.getItemMeta();
        kitEditorM.setDisplayName("§d§lKit Editor §7• Clique");
        kitEditor.setItemMeta(kitEditorM);
        p.getInventory().setItem(8, kitEditor);
    }
}
