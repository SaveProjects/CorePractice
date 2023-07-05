package fr.edminecoreteam.corepractice.kits;

import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.PotionMeta;
import org.bukkit.potion.Potion;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.potion.PotionType;

import java.util.concurrent.TimeUnit;

public class DefaultNodebuff
{
    public void getDefaultKit(Player p, String forEditor)
    {
        if (forEditor == "no")
        {
            p.getInventory().clear();

            ItemStack helmet = new ItemStack(Material.DIAMOND_HELMET, 1);
            ItemMeta helmetM = helmet.getItemMeta();
            helmetM.addEnchant(Enchantment.PROTECTION_ENVIRONMENTAL, 4, true);
            helmetM.addEnchant(Enchantment.DURABILITY, 3, true);
            helmet.setItemMeta(helmetM);
            p.getEquipment().setHelmet(helmet);

            ItemStack chestplate = new ItemStack(Material.DIAMOND_CHESTPLATE, 1);
            ItemMeta chestplateM = chestplate.getItemMeta();
            chestplateM.addEnchant(Enchantment.PROTECTION_ENVIRONMENTAL, 4, true);
            chestplateM.addEnchant(Enchantment.DURABILITY, 3, true);
            chestplate.setItemMeta(chestplateM);
            p.getEquipment().setChestplate(chestplate);

            ItemStack leggings = new ItemStack(Material.DIAMOND_LEGGINGS, 1);
            ItemMeta leggingsM = leggings.getItemMeta();
            leggingsM.addEnchant(Enchantment.PROTECTION_ENVIRONMENTAL, 4, true);
            leggingsM.addEnchant(Enchantment.DURABILITY, 3, true);
            leggings.setItemMeta(leggingsM);
            p.getEquipment().setLeggings(leggings);

            ItemStack boots = new ItemStack(Material.DIAMOND_BOOTS, 1);
            ItemMeta bootsM = boots.getItemMeta();
            bootsM.addEnchant(Enchantment.PROTECTION_ENVIRONMENTAL, 4, true);
            bootsM.addEnchant(Enchantment.DURABILITY, 3, true);
            boots.setItemMeta(bootsM);
            p.getEquipment().setBoots(boots);

            ItemStack sword = new ItemStack(Material.DIAMOND_SWORD, 1);
            ItemMeta swordM = sword.getItemMeta();
            swordM.addEnchant(Enchantment.DAMAGE_ALL, 5, true);
            swordM.addEnchant(Enchantment.FIRE_ASPECT, 2, true);
            sword.setItemMeta(swordM);
            p.getInventory().addItem(sword);

            p.getInventory().addItem(new ItemStack(Material.ENDER_PEARL, 16));
            p.getInventory().addItem(new ItemStack(Material.COOKED_CHICKEN, 32));
            giveInstantHealthPotion(p);

            int duration = (int) TimeUnit.MINUTES.toSeconds(10) * 20;
            int amplifier = 2;
            PotionEffect fireResistanceEffect = new PotionEffect(
                    PotionEffectType.FIRE_RESISTANCE,
                    duration,
                    0
            );
            p.addPotionEffect(fireResistanceEffect);

            PotionEffect speedEffect = new PotionEffect(
                    PotionEffectType.SPEED,
                    duration,
                    amplifier
            );
            p.addPotionEffect(speedEffect);
        }
        else
        {
            ItemStack sword = new ItemStack(Material.DIAMOND_SWORD, 1);
            ItemMeta swordM = sword.getItemMeta();
            swordM.addEnchant(Enchantment.DAMAGE_ALL, 5, true);
            swordM.addEnchant(Enchantment.FIRE_ASPECT, 2, true);
            sword.setItemMeta(swordM);
            p.getInventory().addItem(sword);

            p.getInventory().addItem(new ItemStack(Material.ENDER_PEARL, 16));
            p.getInventory().addItem(new ItemStack(Material.COOKED_CHICKEN, 32));
            giveInstantHealthPotion(p);
        }
    }

    private void giveInstantHealthPotion(Player player) {
        // Créer un ItemStack de potion
        ItemStack potion = new ItemStack(Material.POTION, 33);

        // Obtenir la méta-donnée de l'ItemStack
        ItemMeta itemMeta = potion.getItemMeta();

        // Vérifier si la méta-donnée est une instance de PotionMeta
        if (itemMeta instanceof PotionMeta) {
            PotionMeta potionMeta = (PotionMeta) itemMeta;

            // Créer une potion de soin instantané de niveau 2
            Potion potionEffect = new Potion(PotionType.INSTANT_HEAL, 2);
            potionEffect.setSplash(true);

            // Appliquer les propriétés de la potion à la méta-donnée
            potionEffect.apply((ItemStack) potionMeta);

            // Appliquer les modifications à la méta-donnée de l'ItemStack
            potion.setItemMeta(potionMeta);

            // Donner la potion au joueur
            player.getInventory().addItem(potion);
        }
    }
}
