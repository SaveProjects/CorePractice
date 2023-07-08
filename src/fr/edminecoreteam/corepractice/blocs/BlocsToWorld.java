package fr.edminecoreteam.corepractice.blocs;

import org.bukkit.Bukkit;
import org.bukkit.Chunk;
import org.bukkit.World;
import org.bukkit.block.Block;

import java.util.ArrayList;
import java.util.List;

public class BlocsToWorld
{
    private final List<Block> protectedBlocks;

    public BlocsToWorld() {
        protectedBlocks = new ArrayList<>();
    }

    public void addBlocksToWorld(String worldName) {
        World world = Bukkit.getWorld(worldName);
        if (world != null) {
            int chunkCount = world.getLoadedChunks().length;
            for (int i = 0; i < chunkCount; i++) {
                Chunk chunk = world.getLoadedChunks()[i];
                for (int x = 0; x < 16; x++) {
                    for (int y = 0; y < world.getMaxHeight(); y++) {
                        for (int z = 0; z < 16; z++) {
                            Block block = chunk.getBlock(x, y, z);
                            protectedBlocks.add(block);
                        }
                    }
                }
            }
        } else {
            Bukkit.getLogger().warning("Le monde spécifié n'existe pas !");
        }
    }

    public void removeBlocksFromWorld(String worldName) {
        World world = Bukkit.getWorld(worldName);
        if (world != null) {
            protectedBlocks.removeIf(block -> block.getWorld().equals(world));
        } else {
            Bukkit.getLogger().warning("Le monde spécifié n'existe pas !");
        }
    }

    public boolean isBlockProtected(Block block) {
        return protectedBlocks.contains(block);
    }
}
