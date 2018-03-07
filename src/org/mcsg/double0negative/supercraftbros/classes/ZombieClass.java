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

public class ZombieClass extends PlayerClassBase {
    
    public ZombieClass(Player p) {
        super(p);
        
    }
    
    @Override
	public String getName() {
        return "Zombie";
    }
    
    @Override
	public ClassType getType() {
        return ClassType.ZOMBIE;
    }
    
    @Override
	public ZombieClass newInstance(Player p) {
        return new ZombieClass(p);
    }
    
    @Override
	public void PlayerAttack(Player victim) {
        
    }
    
    @Override
	public void PlayerDamaged() {
        
    }
    
    @Override
	public void PlayerDeath() {
        
    }
    
    @Override
	public void PlayerInteract(Action action) {
        
    }
    
    @Override
	public void PlayerPlaceBlock(Block block) {
        
    }
    
    @Override
	public void PlayerShootArrow(Entity projectile) {
        
    }
    
    @Override
	@SuppressWarnings("deprecation")
    public void PlayerSpawn() {
        player.addPotionEffect(new PotionEffect(PotionEffectType.JUMP, 8000, 2));
        
        PlayerInventory i = player.getInventory();
        i.clear();
        
        i.setHelmet(new HeadCreator().getMobhead(SkullType.ZOMBIE, getType().getColor() + "Zombie Class"));
        i.setChestplate(Colorizer.setLeatherArmorColor(new ItemStack(Material.LEATHER_CHESTPLATE), 0, 100, 0));
        
        ItemStack legs = Colorizer.setLeatherArmorColor(new ItemStack(Material.LEATHER_LEGGINGS), 0, 100, 0);
        legs.addUnsafeEnchantment(Enchantment.PROTECTION_FALL, 10);
        legs.addUnsafeEnchantment(Enchantment.PROTECTION_ENVIRONMENTAL, 2);
        i.setLeggings(legs);
        
        ItemStack boot = Colorizer.setLeatherArmorColor(new ItemStack(Material.LEATHER_BOOTS), 0, 100, 0);
        boot.addUnsafeEnchantment(Enchantment.PROTECTION_FALL, 10);
        i.setBoots(boot);
        
        ItemStack i1 = new ItemStack(Material.IRON_SPADE, 1);
        i1.addUnsafeEnchantment(Enchantment.ARROW_KNOCKBACK, 1);
        i1.addUnsafeEnchantment(Enchantment.DAMAGE_ALL, 1);
        i.addItem(i1);
        player.setDisplayName(ChatColor.BLUE + "[Zombie]" + ChatColor.WHITE + player.getName());
        player.updateInventory();
    }
    
    @Override
	public void Smash() {
        
    }
    
}
