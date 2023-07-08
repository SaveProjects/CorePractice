package fr.edminecoreteam.corepractice.blocs;

import fr.edminecoreteam.corepractice.Core;
import org.bukkit.block.Block;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.entity.EntityExplodeEvent;
import org.bukkit.event.world.WorldUnloadEvent;

public class BlocsListener implements Listener
{
    private Core core = Core.getInstance();

    @EventHandler
    public void onBlockBreak(BlockBreakEvent event) {
        if (core.getBlocsToWorld().isBlockProtected(event.getBlock())) {
            event.setCancelled(true);
        }
    }

    @EventHandler
    public void onEntityExplode(EntityExplodeEvent event) {
        for (Block block : event.blockList()) {
            if (core.getBlocsToWorld().isBlockProtected(block)) {
                event.setCancelled(true);
                return;
            }
        }
    }

    @EventHandler
    public void onWorldUnload(WorldUnloadEvent event) {
        core.getBlocsToWorld().removeBlocksFromWorld(event.getWorld().getName());
    }
}
