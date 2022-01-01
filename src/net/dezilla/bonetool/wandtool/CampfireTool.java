package net.dezilla.bonetool.wandtool;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.data.type.Campfire;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;

import net.dezilla.bonetool.Util;

public class CampfireTool extends WandTool{

	@Override
	public boolean isServerCompatible() {
		return checkClass("org.bukkit.block.data.type.Campfire");
	}

	@Override
	public boolean isBlockCompatible(Block block) {
		return block.getBlockData() instanceof Campfire;
	}

	@Override
	public ItemStack getIcon(Block block) {
		String name = "Signal Fire";
		if(block!=null) {
			Campfire c = (Campfire) block.getBlockData();
			name+=": "+(c.isSignalFire()?ChatColor.GREEN+"True":ChatColor.RED+"False");
		}
		return Util.setName(new ItemStack(Material.CAMPFIRE), name);
	}
	
	@Override
	public void onLeftClick(InventoryClickEvent event, Block block) {
		toggleSignalFire(block);
	}
	
	@Override
	public boolean canToggle() {
		return true;
	}
	
	@Override
	public void toggle(Block block) {
		toggleSignalFire(block);
	}
	
	public static void toggleSignalFire(Block block) {
		if(!(block.getBlockData() instanceof Campfire))
			return;
		Campfire c = (Campfire) block.getBlockData();
		c.setSignalFire(!c.isSignalFire());
		block.setBlockData(c);
	}

}
