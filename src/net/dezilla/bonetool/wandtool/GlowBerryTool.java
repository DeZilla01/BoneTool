package net.dezilla.bonetool.wandtool;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.data.BlockData;
import org.bukkit.block.data.type.CaveVinesPlant;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;

import net.dezilla.bonetool.ToolUser;
import net.dezilla.bonetool.Util;
import net.dezilla.bonetool.util.Locale;

public class GlowBerryTool extends WandTool {
	
	@Override
	public boolean isServerCompatible() {
		return checkClass("org.bukkit.block.data.type.CaveVinesPlant");
	}

	@Override
	public boolean isBlockCompatible(Block block) {
		return block.getBlockData() instanceof CaveVinesPlant;
	}

	@Override
	public ItemStack getIcon(Block block, ToolUser user) {
		String name = Locale.parse(user, "hasberry");
		if(block != null)
			name += ": "+(hasBerry(block) ? ChatColor.GREEN+Locale.parse(user, "true"):ChatColor.RED+Locale.parse(user, "false"));
		return Util.setName(new ItemStack(Material.GLOW_BERRIES), name);
	}
	
	@Override
	public void onLeftClick(InventoryClickEvent event, Block block) {
		toggleBerry(block);
	}
	
	@Override
	public boolean canToggle() {
		return true;
	}
	
	@Override
	public void toggle(Block block) {
		toggleBerry(block);
	}
	
	public static boolean hasBerry(Block block) {
		if(!(block.getBlockData() instanceof CaveVinesPlant))
			return false;
		CaveVinesPlant plant = (CaveVinesPlant) block.getBlockData();
		return plant.isBerries();
	}
	
	public static void toggleBerry(Block block) {
		if(!(block.getBlockData() instanceof CaveVinesPlant))
			return;
		CaveVinesPlant plant = (CaveVinesPlant) block.getBlockData();
		plant.setBerries(!plant.isBerries());
		block.setBlockData((BlockData) plant);
	}

}
