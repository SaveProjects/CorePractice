package fr.edminecoreteam.corepractice.kits;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class StartGameKits implements Listener
{
    public static void startGameItems(Player p)
    {
        p.getInventory().clear();

        ItemStack defaultKit = new ItemStack(Material.BOOK, 1);
        ItemMeta defaultKitM = defaultKit.getItemMeta();
        defaultKitM.setDisplayName("§d§lKit par défaut §7• Clique");
        defaultKit.setItemMeta(defaultKitM);
        p.getInventory().setItem(8, defaultKit);
    }
}
