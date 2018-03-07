package org.mcsg.double0negative.supercraftbros.util;

import org.bukkit.Color;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.LeatherArmorMeta;

public class Colorizer {

    private Colorizer() {
    }

    public static ItemStack setLeatherArmorColor(ItemStack itemStack, int red, int green, int blue) {
	if (itemStack.getItemMeta() instanceof LeatherArmorMeta == false)
	    throw new UnsupportedOperationException("Only leather armor may be colored using this method. Provided material was: " + itemStack.getType().toString());
	LeatherArmorMeta leatherArmorMeta = (LeatherArmorMeta) itemStack.getItemMeta();
	leatherArmorMeta.setColor(Color.fromRGB(red, green, blue));
	itemStack.setItemMeta(leatherArmorMeta);
	return itemStack;
    }
}
