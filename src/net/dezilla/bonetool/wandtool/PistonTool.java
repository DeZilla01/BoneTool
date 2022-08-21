package net.dezilla.bonetool.wandtool;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.data.type.Piston;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;

import net.dezilla.bonetool.ToolUser;
import net.dezilla.bonetool.Util;
import net.dezilla.bonetool.util.Locale;

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
	public ItemStack getIcon(Block block, ToolUser user) {
		String name = Locale.parse(user, "pistonextended");
		if(block != null) {
			Piston p = (Piston) block.getBlockData();
			name+=": "+ChatColor.YELLOW+(p.isExtended()?ChatColor.GREEN+Locale.parse(user, "true"):ChatColor.RED+Locale.parse(user, "false"));
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
