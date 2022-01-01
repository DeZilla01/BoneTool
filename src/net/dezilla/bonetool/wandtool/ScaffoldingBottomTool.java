package net.dezilla.bonetool.wandtool;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.data.type.Scaffolding;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;

import net.dezilla.bonetool.Util;

public class ScaffoldingBottomTool extends WandTool {
	
	@Override
	public boolean isServerCompatible() {
		return checkClass("org.bukkit.block.data.type.Scaffolding");
	}

	@Override
	public boolean isBlockCompatible(Block block) {
		return block.getBlockData() instanceof Scaffolding;
	}

	@Override
	public ItemStack getIcon(Block block) {
		String name = "Scaffolding Bottom";
		if(block!=null) {
			name += ": " + (isBottom(block) ? ChatColor.GREEN+"True":ChatColor.RED+"False");
		}
		return Util.setName(new ItemStack(Material.SCAFFOLDING), name);
	}
	
	@Override
	public void onLeftClick(InventoryClickEvent event, Block block) {
		toggleBottom(block);
	}
	
	@Override
	public boolean canToggle() {
		return true;
	}
	
	@Override
	public void toggle(Block block) {
		toggleBottom(block);
	}
	
	public static boolean isBottom(Block block) {
		if(!(block.getBlockData() instanceof Scaffolding))
			return false;
		Scaffolding s = (Scaffolding) block.getBlockData();
		return s.isBottom();
	}
	
	public static void toggleBottom(Block block) {
		if(!(block.getBlockData() instanceof Scaffolding))
			return;
		
		Scaffolding s = (Scaffolding) block.getBlockData();
		s.setBottom(!s.isBottom());
		block.setBlockData(s);
	}

}
