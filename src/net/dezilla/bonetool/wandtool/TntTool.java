package net.dezilla.bonetool.wandtool;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.data.type.TNT;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;

import net.dezilla.bonetool.Util;

public class TntTool extends WandTool{

	@Override
	public boolean isServerCompatible() {
		return checkClass("org.bukkit.block.data.type.TNT");
	}

	@Override
	public boolean isBlockCompatible(Block block) {
		return block.getBlockData() instanceof TNT;
	}

	@Override
	public ItemStack getIcon(Block block) {
		String name = "TNT Unstable";
		if(block!=null) {
			TNT tnt = (TNT) block.getBlockData();
			name+=": "+ChatColor.YELLOW+(tnt.isUnstable()?ChatColor.GREEN+"True":ChatColor.RED+"False");
		}
		return Util.setName(new ItemStack(Material.TNT), name);
	}
	
	@Override
	public void onLeftClick(InventoryClickEvent event, Block block) {
		toggleTntUnstable(block);
	}
	
	@Override
	public boolean canToggle() {
		return true;
	}
	
	@Override
	public void toggle(Block block) {
		toggleTntUnstable(block);
	}
	
	public static void toggleTntUnstable(Block block) {
		if(!(block.getBlockData() instanceof TNT))
			return;
		TNT tnt = (TNT) block.getBlockData();
		tnt.setUnstable(!tnt.isUnstable());
		block.setBlockData(tnt);
	}

}
