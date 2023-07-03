package fr.edminecoreteam.corepractice.listeners;

import fr.edminecoreteam.corepractice.Core;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class ItemListeners implements Listener
{

    private static Core core = Core.getInstance();

    @EventHandler
    public void onDamage(EntityDamageEvent e)
    {
        if (e.getEntity() instanceof Player)
        {
            Player p = ((Player) e.getEntity()).getPlayer();
            if (core.getInLobby().contains(p) || core.getInEditor().contains(p))
            {
                e.setCancelled(true);
            }
        }
    }

    @EventHandler
    public void onDrop(PlayerDropItemEvent e)
    {
        Player p = e.getPlayer();
        if (core.getInLobby().contains(p) || core.getInEditor().contains(p))
        {
            e.setCancelled(true);
        }
    }

    public static void getLobbyItems(Player p)
    {
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

        ItemStack kitEditor = new ItemStack(Material.BOOK, 1);
        ItemMeta kitEditorM = kitEditor.getItemMeta();
        kitEditorM.setDisplayName("§d§lKit Editor §7• Clique");
        kitEditor.setItemMeta(rankedM);
        p.getInventory().setItem(8, kitEditor);
    }
}
