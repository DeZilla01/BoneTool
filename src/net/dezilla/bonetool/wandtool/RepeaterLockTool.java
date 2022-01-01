package net.dezilla.bonetool.wandtool;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.data.type.Repeater;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;

import net.dezilla.bonetool.Util;

public class RepeaterLockTool extends WandTool {
	
	@Override
	public boolean isServerCompatible() {
		return checkClass("org.bukkit.block.data.type.Repeater");
	}

	@Override
	public boolean isBlockCompatible(Block block) {
		return block.getBlockData() instanceof Repeater;
	}

	@Override
	public ItemStack getIcon(Block block) {
		String name = "Repeater lock";
		if(block != null) {
			Repeater r = (Repeater) block.getBlockData();
			name += ": "+(r.isLocked() ? ChatColor.GREEN+"True":ChatColor.RED+"False");
		}
		return Util.setName(new ItemStack(Material.REPEATER), name);
	}
	
	@Override
	public void onLeftClick(InventoryClickEvent event, Block block) {
		toggleLock(block);
	}
	
	@Override
	public boolean canToggle() {
		return true;
	}
	
	@Override
	public void toggle(Block block) {
		toggleLock(block);
	}
	
	public static void toggleLock(Block block) {
		if(!(block.getBlockData() instanceof Repeater))
			return;
		Repeater r = (Repeater) block.getBlockData();
		r.setLocked(!r.isLocked());
		block.setBlockData(r);
	}

}
