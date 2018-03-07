package org.mcsg.double0negative.supercraftbros.classes;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.SkullType;
import org.bukkit.block.Block;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.block.Action;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.mcsg.double0negative.supercraftbros.util.Colorizer;

import com.gmail.Jacob6816.scb.utils.HeadCreator;

public class WitherClass extends PlayerClassBase {
    
    public WitherClass(Player p) {
        super(p);
        
    }
    
    @Override
	public String getName() {
        return "Wither";
    }
    
    public ChatColor getPrefix() {
        return ChatColor.DARK_PURPLE;
    }
    
    @Override
	public ClassType getType() {
        return ClassType.WITHER;
    }
    
    @Override
	public WitherClass newInstance(Player p) {
        return new WitherClass(p);
    }
    
    @Override
	public void PlayerAttack(Player victim) {
        player.addPotionEffect(new PotionEffect(PotionEffectType.WITHER, 3, 4));
        
    }
    
    @Override
	public void PlayerDamaged() {
        // TODO Auto-generated method stub
        
    }
    
    @Override
	public void PlayerDeath() {
        // TODO Auto-generated method stub
        
    }
    
    @Override
	public void PlayerInteract(Action action) {
        // TODO Auto-generated method stub
        
    }
    
    @Override
	public void PlayerPlaceBlock(Block block) {
        // TODO Auto-generated method stub
        
    }
    
    @Override
	public void PlayerShootArrow(Entity pro) {
        
    }
    
    @Override
	@SuppressWarnings("deprecation")
    public void PlayerSpawn() {
        player.addPotionEffect(new PotionEffect(PotionEffectType.JUMP, 8000, 4));
        
        PlayerInventory i = player.getInventory();
        i.clear();
        
        i.setHelmet(new HeadCreator().getMobhead(SkullType.WITHER, getType().getColor() + "Wither Head"));
        
        ItemStack chest = Colorizer.setLeatherArmorColor(new ItemStack(Material.LEATHER_CHESTPLATE), 50, 50, 50);
        chest.addUnsafeEnchantment(Enchantment.PROTECTION_ENVIRONMENTAL, 3);
        i.setChestplate(chest);
        
        ItemStack legs = Colorizer.setLeatherArmorColor(new ItemStack(Material.LEATHER_LEGGINGS), 50, 50, 50);
        legs.addUnsafeEnchantment(Enchantment.PROTECTION_ENVIRONMENTAL, 2);
        i.setLeggings(legs);
        
        ItemStack boot = Colorizer.setLeatherArmorColor(new ItemStack(Material.LEATHER_BOOTS), 50, 50, 50);
        boot.addUnsafeEnchantment(Enchantment.PROTECTION_FALL, 10);
        i.setBoots(boot);
        
        ItemStack i1 = new ItemStack(Material.BOW);
        i1.addUnsafeEnchantment(Enchantment.ARROW_INFINITE, 1);
        i1.addUnsafeEnchantment(Enchantment.ARROW_KNOCKBACK, 1);
        i.addItem(i1);
        
        i.addItem(new ItemStack(Material.ARROW));
        player.setDisplayName(ChatColor.AQUA + "[Wither]" + ChatColor.WHITE + player.getName());
        
        player.updateInventory();
        
    }
    
    @Override
	public void Smash() {
        // TODO Auto-generated method stub
        
    }
    
}
