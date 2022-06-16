package net.dezilla.bonetool.wandtool;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.data.type.SculkShrieker;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;

import net.dezilla.bonetool.Util;

public class SculkShriekerTool extends WandTool{
	
	@Override
	public boolean isServerCompatible() {
		return checkClass("org.bukkit.block.data.type.SculkShrieker");
	}

	@Override
	public boolean isBlockCompatible(Block block) {
		return block.getBlockData() instanceof SculkShrieker;
	}

	@Override
	public ItemStack getIcon(Block block) {
		String name = "Shrieking";
		if(block!=null) {
			SculkShrieker s  = (SculkShrieker) block.getBlockData();
			name+=": "+(s.isShrieking()?ChatColor.GREEN+"True":ChatColor.RED+"False");
		}
		return Util.setName(new ItemStack(Material.SCULK_SHRIEKER), name);
	}
	
	@Override
	public void onLeftClick(InventoryClickEvent event, Block block) {
		toggleShrieking(block);
	}
	
	@Override
	public boolean canToggle() {
		return true;
	}
	
	@Override
	public void toggle(Block block) {
		toggleShrieking(block);
	}
	
	public static void toggleShrieking(Block block) {
		if(!(block.getBlockData() instanceof SculkShrieker))
			return;
		SculkShrieker s  = (SculkShrieker) block.getBlockData();
		s.setShrieking(!s.isShrieking());
		block.setBlockData(s);
	}

}
