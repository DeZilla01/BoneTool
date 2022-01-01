package net.dezilla.bonetool.wandtool;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.inventory.ItemStack;

import net.dezilla.bonetool.Util;

public class NoneTool extends WandTool{

	@Override
	public boolean isServerCompatible() {
		return true;
	}

	@Override
	public boolean isBlockCompatible(Block block) {
		return false;
	}

	@Override
	public ItemStack getIcon(Block block) {
		return Util.setLore(Util.setName(new ItemStack(Material.BARRIER), "None"), ChatColor.GRAY+"Select a tool to bind to right-click.");
	}
	
	@Override
	public boolean isRightClickTool() {
		return true;
	}

}
