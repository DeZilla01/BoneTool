package net.dezilla.bonetool.wandtool;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.data.type.SculkShrieker;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;

import net.dezilla.bonetool.ToolUser;
import net.dezilla.bonetool.Util;
import net.dezilla.bonetool.util.Locale;

public class ShriekerSummonTool extends WandTool{
	
	@Override
	public boolean isServerCompatible() {
		return checkClass("org.bukkit.block.data.type.SculkShrieker");
	}

	@Override
	public boolean isBlockCompatible(Block block) {
		return block.getBlockData() instanceof SculkShrieker;
	}

	@Override
	public ItemStack getIcon(Block block, ToolUser user) {
		String name = Locale.parse(user, "shriekersummon");
		if(block!=null) {
			SculkShrieker s  = (SculkShrieker) block.getBlockData();
			name+=": "+(s.isCanSummon()?ChatColor.GREEN+Locale.parse(user, "true"):ChatColor.RED+Locale.parse(user, "false"));
		}
		return Util.setName(new ItemStack(Material.SCULK_SHRIEKER), name);
	}
	
	@Override
	public void onLeftClick(InventoryClickEvent event, Block block) {
		toggleSummon(block);
	}
	
	@Override
	public boolean canToggle() {
		return true;
	}
	
	@Override
	public void toggle(Block block) {
		toggleSummon(block);
	}
	
	public static void toggleSummon(Block block) {
		if(!(block.getBlockData() instanceof SculkShrieker))
			return;
		SculkShrieker s  = (SculkShrieker) block.getBlockData();
		s.setCanSummon(!s.isCanSummon());
		block.setBlockData(s);
	}

}
