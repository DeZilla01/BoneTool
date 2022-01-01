package net.dezilla.bonetool.wandtool;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.data.type.Lantern;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;

import net.dezilla.bonetool.Util;

public class LanternTool extends WandTool{
	
	@Override
	public boolean isServerCompatible() {
		return checkClass("org.bukkit.block.data.type.Lantern");
	}

	@Override
	public boolean isBlockCompatible(Block block) {
		return block.getBlockData() instanceof Lantern;
	}

	@Override
	public ItemStack getIcon(Block block) {
		String name = "Lantern Hanging";
		if(block!=null) {
			Lantern l  = (Lantern) block.getBlockData();
			name+=": "+(l.isHanging()?ChatColor.GREEN+"True":ChatColor.RED+"False");
		}
		return Util.setName(new ItemStack(Material.LANTERN), name);
	}
	
	@Override
	public void onLeftClick(InventoryClickEvent event, Block block) {
		toggleHanging(block);
	}
	
	@Override
	public boolean canToggle() {
		return true;
	}
	
	@Override
	public void toggle(Block block) {
		toggleHanging(block);
	}
	
	public static void toggleHanging(Block block) {
		if(!(block.getBlockData() instanceof Lantern))
			return;
		Lantern l  = (Lantern) block.getBlockData();
		l.setHanging(!l.isHanging());
		block.setBlockData(l);
	}

}
