package net.dezilla.bonetool.wandtool;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.data.type.Piston;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;

import net.dezilla.bonetool.Util;

public class PistonTool extends WandTool{

	@Override
	public boolean isServerCompatible() {
		return checkClass("org.bukkit.block.data.type.Piston");
	}

	@Override
	public boolean isBlockCompatible(Block block) {
		return block.getBlockData() instanceof Piston;
	}
	
	@Override
	public void onLeftClick(InventoryClickEvent event, Block block) {
		togglePistonExtension(block);
	}

	@Override
	public ItemStack getIcon(Block block) {
		String name = "Piston Extended";
		if(block != null) {
			Piston p = (Piston) block.getBlockData();
			name+=": "+ChatColor.YELLOW+(p.isExtended()?ChatColor.GREEN+"True":ChatColor.RED+"False");
		}
		ItemStack icon = Util.setName(new ItemStack(Material.PISTON), name);
		return icon;
	}
	
	@Override
	public boolean canToggle() {
		return true;
	}
	
	@Override
	public void toggle(Block block) {
		togglePistonExtension(block);
	}
	
	public static void togglePistonExtension(Block block) {
		if(!(block.getBlockData() instanceof Piston)) 
			return;
		
		Piston piston = (Piston) block.getBlockData();
		piston.setExtended(!piston.isExtended());
		block.setBlockData(piston);
	}

}
