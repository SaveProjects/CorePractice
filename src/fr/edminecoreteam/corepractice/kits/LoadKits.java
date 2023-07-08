package fr.edminecoreteam.corepractice.kits;

import fr.edminecoreteam.corepractice.Core;
import fr.edminecoreteam.corepractice.utils.EnchantSerializer;
import fr.edminecoreteam.corepractice.utils.ItemStackSerializer;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.List;

public class LoadKits
{
    private Player p;
    private static Core core = Core.getInstance();

    public LoadKits(Player p)
    {
        this.p = p;
    }

    public List<String> getUnrankedKitList()
    {
        List<String> list = new ArrayList<String>();

        for(String sKitName : core.getConfig().getConfigurationSection("kits.unranked").getKeys(false))
        {
            list.add(sKitName);
        }

        return list;
    }

    public void equipUnrankedDefaultKit(String kitName)
    {
        for(String sKitName : core.getConfig().getConfigurationSection("kits.1vs1").getKeys(false)) {
            if (kitName.equals(sKitName)) {
                p.sendMessage("§aKit " + sKitName + " reçu.");
                for (String content : core.getConfig().getConfigurationSection("kits.1vs1." + sKitName + ".content.items").getKeys(false)) {
                    if (core.getConfig().getString("kits.1vs1." + sKitName + ".content.items." + content + ".specialid").equalsIgnoreCase("no")) {
                        ItemStack items = new ItemStack(ItemStackSerializer.deserialize(core.getConfig().getString("kits.1vs1." + sKitName + ".content.items." + content + ".item")).getType(), core.getConfig().getInt("kits.1vs1." + sKitName + ".content.items." + content + ".amount"));
                        ItemMeta itemsM = items.getItemMeta();
                        if (core.getConfig().getString("kits.1vs1." + sKitName + ".content.items." + content + ".isenchant").equalsIgnoreCase("yes")) {
                            for (String enchants : core.getConfig().getConfigurationSection("kits.1vs1." + sKitName + ".content.items." + content + ".enchants").getKeys(false)) {
                                itemsM.addEnchant(EnchantSerializer.serialize(core.getConfig().getString("kits.1vs1." + sKitName + ".content.items." + content + ".enchants." + enchants + ".enchant")), core.getConfig().getInt("kits.1vs1." + sKitName + ".content.items." + content + ".enchants." + enchants + ".level"), true);
                            }
                        }
                        items.setItemMeta(itemsM);
                        for (int slots : core.getConfig().getIntegerList("kits.1vs1." + sKitName + ".content.items." + content + ".slots")) {
                            p.getInventory().setItem(slots, items);
                        }
                    }
                    if (core.getConfig().getString("kits.1vs1." + sKitName + ".content.items." + content + ".specialid").equalsIgnoreCase("yes")) {
                        ItemStack items = new ItemStack(ItemStackSerializer.deserialize(core.getConfig().getString("kits.1vs1." + sKitName + ".content.items." + content + ".item")).getType(), core.getConfig().getInt("kits.1vs1." + sKitName + ".content.items." + content + ".amount"), (short) core.getConfig().getInt("kits.1vs1." + sKitName + ".content.items." + content + ".id"));
                        ItemMeta itemsM = items.getItemMeta();
                        if (core.getConfig().getString("kits.1vs1." + sKitName + ".content.items." + content + ".isenchant").equalsIgnoreCase("yes")) {
                            for (String enchants : core.getConfig().getConfigurationSection("kits.1vs1." + sKitName + ".content.items." + content + ".enchants").getKeys(false)) {
                                itemsM.addEnchant(EnchantSerializer.serialize(core.getConfig().getString("kits.1vs1." + sKitName + ".content.items." + content + ".enchants." + enchants + ".enchant")), core.getConfig().getInt("kits.1vs1." + sKitName + ".content.items." + content + ".enchants." + enchants + ".level"), true);
                            }
                        }
                        items.setItemMeta(itemsM);
                        for (int slots : core.getConfig().getIntegerList("kits.1vs1." + sKitName + ".content.items." + content + ".slots")) {
                            p.getInventory().setItem(slots, items);
                        }
                    }
                }
                for (String armor : core.getConfig().getConfigurationSection("kits.1vs1." + sKitName + ".content.armor").getKeys(false)) {
                    ItemStack helmet = new ItemStack(ItemStackSerializer.deserialize(core.getConfig().getString("kits.1vs1." + sKitName + ".content.armor.helmet.item")).getType(), 1);
                    ItemMeta helmetM = helmet.getItemMeta();
                    if (core.getConfig().getString("kits.1vs1." + sKitName + ".content.armor.helmet.isenchant").equalsIgnoreCase("yes")) {
                        for (String enchants : core.getConfig().getConfigurationSection("kits.1vs1." + sKitName + ".content.armor.helmet.enchants").getKeys(false)) {
                            helmetM.addEnchant(EnchantSerializer.serialize(core.getConfig().getString("kits.1vs1." + sKitName + ".content.armor.helmet.enchants." + enchants + ".enchant")), core.getConfig().getInt("kits.1vs1." + sKitName + ".content.armor.helmet.enchants." + enchants + ".level"), true);
                        }
                    }
                    helmet.setItemMeta(helmetM);
                    p.getEquipment().setHelmet(helmet);

                    ItemStack chestplate = new ItemStack(ItemStackSerializer.deserialize(core.getConfig().getString("kits.1vs1." + sKitName + ".content.armor.chestplate.item")).getType(), 1);
                    ItemMeta chestplateM = chestplate.getItemMeta();
                    if (core.getConfig().getString("kits.1vs1." + sKitName + ".content.armor.chestplate.isenchant").equalsIgnoreCase("yes")) {
                        for (String enchants : core.getConfig().getConfigurationSection("kits.1vs1." + sKitName + ".content.armor.chestplate.enchants").getKeys(false)) {
                            chestplateM.addEnchant(EnchantSerializer.serialize(core.getConfig().getString("kits.1vs1." + sKitName + ".content.armor.chestplate.enchants." + enchants + ".enchant")), core.getConfig().getInt("kits.1vs1." + sKitName + ".content.armor.chestplate.enchants." + enchants + ".level"), true);
                        }
                    }
                    chestplate.setItemMeta(chestplateM);
                    p.getEquipment().setChestplate(chestplate);

                    ItemStack leggings = new ItemStack(ItemStackSerializer.deserialize(core.getConfig().getString("kits.1vs1." + sKitName + ".content.armor.leggings.item")).getType(), 1);
                    ItemMeta leggingsM = leggings.getItemMeta();
                    if (core.getConfig().getString("kits.1vs1." + sKitName + ".content.armor.leggings.isenchant").equalsIgnoreCase("yes")) {
                        for (String enchants : core.getConfig().getConfigurationSection("kits.1vs1." + sKitName + ".content.armor.leggings.enchants").getKeys(false)) {
                            leggingsM.addEnchant(EnchantSerializer.serialize(core.getConfig().getString("kits.1vs1." + sKitName + ".content.armor.leggings.enchants." + enchants + ".enchant")), core.getConfig().getInt("kits.1vs1." + sKitName + ".content.armor.leggings.enchants." + enchants + ".level"), true);
                        }
                    }
                    leggings.setItemMeta(leggingsM);
                    p.getEquipment().setLeggings(leggings);

                    ItemStack boots = new ItemStack(ItemStackSerializer.deserialize(core.getConfig().getString("kits.1vs1." + sKitName + ".content.armor.boots.item")).getType(), 1);
                    ItemMeta bootsM = boots.getItemMeta();
                    if (core.getConfig().getString("kits.1vs1." + sKitName + ".content.armor.boots.isenchant").equalsIgnoreCase("yes")) {
                        for (String enchants : core.getConfig().getConfigurationSection("kits.1vs1." + sKitName + ".content.armor.boots.enchants").getKeys(false)) {
                            bootsM.addEnchant(EnchantSerializer.serialize(core.getConfig().getString("kits.1vs1." + sKitName + ".content.armor.boots.enchants." + enchants + ".enchant")), core.getConfig().getInt("kits.1vs1." + sKitName + ".content.armor.boots.enchants." + enchants + ".level"), true);
                        }
                    }
                    boots.setItemMeta(bootsM);
                    p.getEquipment().setBoots(boots);
                }
            }
        }
    }
}
