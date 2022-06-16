package net.dezilla.bonetool.wandtool;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.data.Hangable;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;

import net.dezilla.bonetool.Util;

public class HangingTool extends WandTool{
	
	@Override
	public boolean isServerCompatible() {
		return checkClass("org.bukkit.block.data.Hangable");
	}

	@Override
	public boolean isBlockCompatible(Block block) {
		return block.getBlockData() instanceof Hangable;
	}

	@Override
	public ItemStack getIcon(Block block) {
		String name = "Hangable";
		if(block!=null) {
			Hangable h  = (Hangable) block.getBlockData();
			name+=": "+(h.isHanging()?ChatColor.GREEN+"True":ChatColor.RED+"False");
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
		if(!(block.getBlockData() instanceof Hangable))
			return;
		Hangable h  = (Hangable) block.getBlockData();
		h.setHanging(!h.isHanging());
		block.setBlockData(h);
	}

}
