package fr.edminecoreteam.corepractice.utils;

import org.bukkit.enchantments.Enchantment;

public class EnchantSerializer
{
    public static Enchantment serialize(String value)
    {
        /*
          Enchantements Fight-list
         */
        if (value.equalsIgnoreCase("damage-all")) { return Enchantment.DAMAGE_ALL; }
        if (value.equalsIgnoreCase("fire-aspect")) { return Enchantment.FIRE_ASPECT; }
        if (value.equalsIgnoreCase("knockback")) { return Enchantment.KNOCKBACK; }

        /*
          Enchantements Item-list
         */
        if (value.equalsIgnoreCase("durability")) { return Enchantment.DURABILITY; }
        if (value.equalsIgnoreCase("luck")) { return Enchantment.LUCK; }

        /*
          Enchantements Protection-list
         */
        if (value.equalsIgnoreCase("protection")) { return Enchantment.PROTECTION_ENVIRONMENTAL; }
        if (value.equalsIgnoreCase("protection-explosions")) { return Enchantment.PROTECTION_EXPLOSIONS; }
        if (value.equalsIgnoreCase("protection-fall")) { return Enchantment.PROTECTION_FALL; }
        if (value.equalsIgnoreCase("protection-fire")) { return Enchantment.PROTECTION_FIRE; }
        if (value.equalsIgnoreCase("protection-projectile")) { return Enchantment.PROTECTION_PROJECTILE; }

        /*
          Enchantements Arrow-list
         */
        if (value.equalsIgnoreCase("arrow-damage")) { return Enchantment.ARROW_DAMAGE; }
        if (value.equalsIgnoreCase("arrow-fire")) { return Enchantment.ARROW_FIRE; }
        if (value.equalsIgnoreCase("arrow-infinite")) { return Enchantment.ARROW_INFINITE; }
        if (value.equalsIgnoreCase("arrow-knockback")) { return Enchantment.ARROW_KNOCKBACK; }

        return null;
    }
}
