package net.dezilla.bonetool.wandtool;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.data.type.EndPortalFrame;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;

import net.dezilla.bonetool.Util;

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
	public ItemStack getIcon(Block block) {
		String name = "End Portal Frame Eye";
		if(block != null)
			name += ": "+(hasEye(block) ? ChatColor.GREEN+"True":ChatColor.RED+"False");
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
