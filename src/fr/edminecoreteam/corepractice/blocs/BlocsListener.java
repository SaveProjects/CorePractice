package fr.edminecoreteam.corepractice.blocs;

import fr.edminecoreteam.corepractice.Core;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.entity.EntityExplodeEvent;
import org.bukkit.event.world.WorldUnloadEvent;

import java.util.ArrayList;
import java.util.List;

public class BlocsListener implements Listener
{
    private final Core core = Core.getInstance();

    @EventHandler
    public void onBlockBreak(BlockBreakEvent event) {

        Player p = event.getPlayer();

        if (core.getInPreDuel().contains(p) || core.getInEndDuel().contains(p))
        {
            event.setCancelled(true);
        }

        if (core.getInDuel().contains(p))
        {
            if (!core.getBlocsToWorld().getPlayerBlocks(p).contains(event.getBlock()))
            {
                Player oppenant = core.getMatchOppenant().getMatchOppenant(p);
                if (!core.getBlocsToWorld().getPlayerBlocks(p).contains(event.getBlock()))
                {
                    event.setCancelled(true);
                }
            }
        }
    }

    @EventHandler
    public void onBlockPlace(BlockPlaceEvent event) {
        Player p = event.getPlayer();
        Block block = event.getBlock();

        if (core.getInPreDuel().contains(p) || core.getInEndDuel().contains(p))
        {
            event.setCancelled(true);
        }

        if (core.getInDuel().contains(p))
        {
            if (core.getBlocsToWorld().getPlayerBlocks().containsKey(p)) {
                core.getBlocsToWorld().getPlayerBlocks().get(p).add(block);
            } else {
                List<Block> blocks = new ArrayList<>();
                blocks.add(block);
                core.getBlocsToWorld().getPlayerBlocks().put(p, blocks);
            }
        }
    }

}
