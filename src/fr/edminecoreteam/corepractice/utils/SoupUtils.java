package fr.edminecoreteam.corepractice.utils;

import fr.edminecoreteam.corepractice.Core;
import fr.edminecoreteam.corepractice.matchmaking.GameCheck;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class SoupUtils implements Listener
{
    private final Core core = Core.getInstance();

    @EventHandler
    private void soupEvent(PlayerInteractEvent e)
    {
        Player p = e.getPlayer();
        Action a = e.getAction();
        ItemStack it = e.getItem();
        if (core.getInPreDuel().contains(p))
        {
            if (it.getType() == Material.MUSHROOM_SOUP && (a == Action.RIGHT_CLICK_AIR || a == Action.RIGHT_CLICK_BLOCK)) {
                e.setCancelled(true);
            }
        }
        if (core.getInDuel().contains(p))
        {
            if (it.getType() == Material.MUSHROOM_SOUP && (a == Action.RIGHT_CLICK_AIR || a == Action.RIGHT_CLICK_BLOCK)) {
                if (p.getHealth() == 20)
                {
                    if (p.getFoodLevel() == 20)
                    {
                        e.setCancelled(true);
                    }
                    else if (p.getFoodLevel() < 20)
                    {
                        e.setCancelled(true);
                        int newFood= (int) (p.getFoodLevel() + 6);
                        if (newFood > 20)
                        {
                            newFood = 20;
                        }
                        p.setFoodLevel(newFood);

                        int inventoryslot = p.getInventory().getHeldItemSlot();
                        ItemStack soup = new ItemStack(Material.BOWL, 1);
                        p.getInventory().setItem(inventoryslot, soup);
                    }
                }
                else if (p.getHealth() < 20)
                {
                    e.setCancelled(true);
                    int newHealth = (int) (p.getHealth() + 4);
                    if (newHealth > 20)
                    {
                        newHealth = 20;
                    }
                    p.setHealth(newHealth);

                    int newFood= (int) (p.getFoodLevel() + 6);
                    if (p.getFoodLevel() < 20)
                    {
                        if (newFood > 20)
                        {
                            newFood = 20;
                        }
                        p.setFoodLevel(newFood);
                    }

                    int inventoryslot = p.getInventory().getHeldItemSlot();
                    ItemStack soup = new ItemStack(Material.BOWL, 1);
                    p.getInventory().setItem(inventoryslot, soup);
                }
            }
        }
    }

}
