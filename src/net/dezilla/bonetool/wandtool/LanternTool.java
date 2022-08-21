package net.dezilla.bonetool.wandtool;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.data.type.Lantern;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;

import net.dezilla.bonetool.ToolUser;
import net.dezilla.bonetool.Util;
import net.dezilla.bonetool.util.Locale;

public class LanternTool extends WandTool{
	
	@Override
	public boolean isServerCompatible() {
		if(checkClass("org.bukkit.block.data.Hangable"))
			return false;
		return checkClass("org.bukkit.block.data.type.Lantern");
	}

	@Override
	public boolean isBlockCompatible(Block block) {
		return block.getBlockData() instanceof Lantern;
	}

	@Override
	public ItemStack getIcon(Block block, ToolUser user) {
		String name = Locale.parse(user, "lanternhanging");
		if(block!=null) {
			Lantern l  = (Lantern) block.getBlockData();
			name+=": "+(l.isHanging()?ChatColor.GREEN+Locale.parse(user, "true"):ChatColor.RED+Locale.parse(user, "false"));
		}
		return Util.setName(new ItemStack(Material.LANTERN), name);
	}
	
	@Override
	public void onLeftClick(InventoryClickEvent event, Block block) {
		toggleHanging(block);
	}
	
	@Override
	public boolean canToggle() {
		return true;
	}
	
	@Override
	public void toggle(Block block) {
		toggleHanging(block);
	}
	
	public static void toggleHanging(Block block) {
		if(!(block.getBlockData() instanceof Lantern))
			return;
		Lantern l  = (Lantern) block.getBlockData();
		l.setHanging(!l.isHanging());
		block.setBlockData(l);
	}

}
