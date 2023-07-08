package fr.edminecoreteam.corepractice.blocs;

import org.bukkit.block.Block;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class BlocsToWorld
{
    private Map<Player, List<Block>> playerBlocks;

    public BlocsToWorld() {
        playerBlocks = new HashMap<>();
    }

    public boolean isBlockPlaced(Block block) {
        for (List<Block> blocks : playerBlocks.values()) {
            if (blocks.contains(block)) {
                return true;
            }
        }
        return false;
    }

    public List<Block> getPlayerBlocks(Player player) {
        return playerBlocks.getOrDefault(player, new ArrayList<>());
    }

    public Map<Player, List<Block>> getPlayerBlocks()
    {
        return playerBlocks;
    }
}
