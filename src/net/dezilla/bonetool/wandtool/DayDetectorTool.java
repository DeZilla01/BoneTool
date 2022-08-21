package net.dezilla.bonetool.wandtool;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.data.type.DaylightDetector;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;

import net.dezilla.bonetool.ToolUser;
import net.dezilla.bonetool.Util;
import net.dezilla.bonetool.util.Locale;

public class DayDetectorTool extends WandTool {
	
	@Override
	public boolean isServerCompatible() {
		return checkClass("org.bukkit.block.data.type.DaylightDetector");
	}

	@Override
	public boolean isBlockCompatible(Block block) {
		return block.getBlockData() instanceof DaylightDetector;
	}

	@Override
	public ItemStack getIcon(Block block, ToolUser user) {
		String name = Locale.parse(user, "daylightdetectorinverted");
		if(block != null)
			name += ": "+(isInverted(block) ? ChatColor.GREEN+Locale.parse(user, "true"):ChatColor.RED+Locale.parse(user, "false"));
		return Util.setName(new ItemStack(Material.DAYLIGHT_DETECTOR), name);
	}
	
	@Override
	public void onLeftClick(InventoryClickEvent event, Block block) {
		toggleInverted(block);
	}
	
	@Override
	public boolean canToggle() {
		return true;
	}
	
	@Override
	public void toggle(Block block) {
		toggleInverted(block);
	}
	
	public static boolean isInverted(Block block) {
		if(!(block.getBlockData() instanceof DaylightDetector))
			return false;
		DaylightDetector detec = (DaylightDetector) block.getBlockData();
		return detec.isInverted();
	}
	
	public static void toggleInverted(Block block) {
		if(!(block.getBlockData() instanceof DaylightDetector))
			return;
		DaylightDetector detec = (DaylightDetector) block.getBlockData();
		detec.setInverted(!detec.isInverted());
		block.setBlockData(detec);
	}
}
