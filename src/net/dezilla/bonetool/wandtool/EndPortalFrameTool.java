package net.dezilla.bonetool.wandtool;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.data.type.EndPortalFrame;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;

import net.dezilla.bonetool.ToolUser;
import net.dezilla.bonetool.Util;
import net.dezilla.bonetool.util.Locale;

public class EndPortalFrameTool extends WandTool{
	
	@Override
	public boolean isServerCompatible() {
		return checkClass("org.bukkit.block.data.type.EndPortalFrame");
	}

	@Override
	public boolean isBlockCompatible(Block block) {
		return block.getBlockData() instanceof EndPortalFrame;
	}

	@Override
	public ItemStack getIcon(Block block, ToolUser user) {
		String name = Locale.parse(user, "endportaleye");
		if(block != null)
			name += ": "+(hasEye(block) ? ChatColor.GREEN+Locale.parse(user, "true"):ChatColor.RED+Locale.parse(user, "false"));
		return Util.setName(new ItemStack(Material.ENDER_EYE), name);
	}
	
	@Override
	public void onLeftClick(InventoryClickEvent event, Block block) {
		toggleEye(block);
	}
	
	@Override
	public boolean canToggle() {
		return true;
	}
	
	@Override
	public void toggle(Block block) {
		toggleEye(block);
	}
	
	public static boolean hasEye(Block block) {
		if(!(block.getBlockData() instanceof EndPortalFrame))
			return false;
		EndPortalFrame frame = (EndPortalFrame) block.getBlockData();
		return frame.hasEye();
	}
	
	public static void toggleEye(Block block) {
		if(!(block.getBlockData() instanceof EndPortalFrame))
			return;
		EndPortalFrame frame = (EndPortalFrame) block.getBlockData();
		frame.setEye(!frame.hasEye());
		block.setBlockData(frame);
	}

}
