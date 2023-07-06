package fr.edminecoreteam.corepractice.utils;

import java.io.File;

import org.bukkit.Bukkit;
import org.bukkit.Server;

public class UnloadWorld 
{
	public static void deleteWorld(String worldName) {
	    // Récupère l'instance du serveur
	    Server server = Bukkit.getServer();

	    // Vérifie si le monde existe
	    if (!server.getWorlds().contains(server.getWorld(worldName))) {
	        return; // Quitte la méthode si le monde n'existe pas
	    }

	    // Arrête le monde si il est en cours d'utilisation
	    if (server.getWorld(worldName) != null) {
	        server.unloadWorld(worldName, true);
	    }

	    // Supprime le dossier du monde
	    File worldFolder = new File(server.getWorldContainer(), worldName);
	    deleteFolder(worldFolder);
	    System.out.println("World of " + worldName + " removed.");
	}

	// Méthode récursive pour supprimer un dossier et tous ses fichiers et sous-dossiers
	private static void deleteFolder(File folder) {
	    if (folder.isDirectory()) {
	        File[] files = folder.listFiles();
	        if (files != null) {
	            for (File file : files) {
	                deleteFolder(file);
	            }
	        }
	    }
	    folder.delete();
	}
}
