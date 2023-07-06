package fr.edminecoreteam.corepractice.utils;

import java.io.File;
import java.io.IOException;
import java.util.Random;

import org.apache.commons.io.FileUtils;
import org.bukkit.Bukkit;
import org.bukkit.WorldCreator;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;

public class LoadWorld implements Listener {
	
	public static String getRandomSubfolderName(String parentFolderPath) {
	    File parentFolder = new File(parentFolderPath);
	    if (!parentFolder.exists() || !parentFolder.isDirectory()) {
	        return null;
	    }

	    File[] subFolders = parentFolder.listFiles(File::isDirectory);
	    if (subFolders == null || subFolders.length == 0) {
	        return null;
	    }

	    int numSubFolders = subFolders.length;
	    int randomIndex = new Random().nextInt(numSubFolders);
	    return subFolders[randomIndex].getName();
	}
	
	public static String getSubfolderName(String parentFolderPath, String folderName) {
	    File parentFolder = new File(parentFolderPath);
	    if (!parentFolder.exists() || !parentFolder.isDirectory()) {
	        return null;
	    }

	    File[] subFolders = parentFolder.listFiles(File::isDirectory);
	    if (subFolders == null || subFolders.length == 0) {
	        return null;
	    }

	    for (File subFolder : subFolders) {
	        if (subFolder.getName().equalsIgnoreCase(folderName)) {
	            return subFolder.getName();
	        }
	    }

	    return null;
	}
	
	public static void createGameWorld(String world, String customName) {
    	
    	
    		File srcDir = new File("gameTemplate/" + world);
        	File destDir = new File(customName);
        	try {
        	    FileUtils.copyDirectory(srcDir, destDir);
        	} catch (IOException e) {
        	    e.printStackTrace();
        	}
        	File file = new File(customName + "/uid.dat");
        	file.delete();
        	Bukkit.getServer().createWorld(new WorldCreator(customName));
    	
    }
	
	public static void unloadWorldOfPlayer(Player p) {
	
		File fileDepend = new File(p.getName());
		Bukkit.getWorld(p.getName()).getWorldFolder().delete();
		fileDepend.delete();

	}
	
	public static String loadStringInfos(String dossier, String fichier, String cle) {
	    // Chemin du fichier
	    String chemin = dossier + File.separator + fichier;
	    
	    // Chargement du fichier Yaml
	    File file = new File(chemin);
	    YamlConfiguration config = YamlConfiguration.loadConfiguration(file);
	    
	    // Lecture de la valeur associée à la clé
	    String valeur = config.getString(cle);
	    
	    // Retourne la valeur trouvée ou null si la clé n'existe pas
	    return valeur;
	}
	
	public static int loadIntInfos(String dossier, String fichier, String cle) {
	    // Chemin du fichier
	    String chemin = dossier + File.separator + fichier;
	    
	    // Chargement du fichier Yaml
	    File file = new File(chemin);
	    YamlConfiguration config = YamlConfiguration.loadConfiguration(file);
	    
	    // Lecture de la valeur associée à la clé
	    int valeur = config.getInt(cle);
	    
	    // Retourne la valeur trouvée ou null si la clé n'existe pas
	    return valeur;
	}

}
