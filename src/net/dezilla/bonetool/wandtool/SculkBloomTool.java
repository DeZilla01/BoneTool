package net.dezilla.bonetool.wandtool;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.data.type.SculkCatalyst;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;

import net.dezilla.bonetool.ToolUser;
import net.dezilla.bonetool.Util;
import net.dezilla.bonetool.util.Locale;

public class SculkBloomTool extends WandTool{
	
	@Override
	public boolean isServerCompatible() {
		return checkClass("org.bukkit.block.data.type.SculkCatalyst");
	}

	@Override
	public boolean isBlockCompatible(Block block) {
		return block.getBlockData() instanceof SculkCatalyst;
	}

	@Override
	public ItemStack getIcon(Block block, ToolUser user) {
		String name = Locale.parse(user, "bloom");
		if(block!=null) {
			SculkCatalyst s = (SculkCatalyst) block.getBlockData();
			name+=": "+(s.isBloom()?ChatColor.GREEN+Locale.parse(user, "true"):ChatColor.RED+Locale.parse(user, "false"));
		}
		return Util.setName(new ItemStack(Material.SCULK_CATALYST), name);
	}
	
	@Override
	public void onLeftClick(InventoryClickEvent event, Block block) {
		toggleBloom(block);
	}
	
	@Override
	public boolean canToggle() {
		return true;
	}
	
	@Override
	public void toggle(Block block) {
		toggleBloom(block);
	}
	
	public static void toggleBloom(Block block) {
		if(!(block.getBlockData() instanceof SculkCatalyst))
			return;
		SculkCatalyst s = (SculkCatalyst) block.getBlockData();
		s.setBloom(!s.isBloom());
		block.setBlockData(s);
	}

}
