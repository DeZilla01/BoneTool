package net.dezilla.bonetool.wandtool;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.inventory.ItemStack;

import net.dezilla.bonetool.ToolUser;
import net.dezilla.bonetool.Util;
import net.dezilla.bonetool.util.Locale;

public class NoneTool extends WandTool{

	@Override
	public boolean isServerCompatible() {
		return true;
	}

	@Override
	public boolean isBlockCompatible(Block block) {
		return false;
	}

	@Override
	public ItemStack getIcon(Block block, ToolUser user) {
		return Util.setLore(Util.setName(new ItemStack(Material.BARRIER), Locale.parse(user, "none")), ChatColor.GRAY+Locale.parse(user, "selectrightclicktool"));
	}
	
	@Override
	public boolean isRightClickTool() {
		return true;
	}

}
