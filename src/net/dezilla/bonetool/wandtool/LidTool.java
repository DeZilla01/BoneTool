package net.dezilla.bonetool.wandtool;

import java.util.Arrays;
import java.util.List;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.Lidded;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;

import net.dezilla.bonetool.ToolUser;
import net.dezilla.bonetool.Util;
import net.dezilla.bonetool.util.Locale;

public class LidTool extends WandTool{

	@Override
	public boolean isServerCompatible() {
		return checkClass("org.bukkit.block.Lidded");
	}

	@Override
	public boolean isBlockCompatible(Block block) {
		return block.getState() instanceof Lidded;
	}

	@Override
	public ItemStack getIcon(Block block, ToolUser user) {
		String name = Locale.parse(user, "lid");
		List<String> lore = Arrays.asList(
				ChatColor.AQUA+Locale.parse(user, "leftclick")+ChatColor.WHITE+": "+Locale.parse(user, "openlid"),
				ChatColor.AQUA+Locale.parse(user, "rightclick")+ChatColor.WHITE+": "+Locale.parse(user, "closelid"),
				ChatColor.GRAY+Locale.parse(user, "visualBugWarning"));
		return Util.setLore(Util.setName(new ItemStack(Material.CHEST), name), lore);
	}
	
	@Override
	public void onLeftClick(InventoryClickEvent event, Block block) {
		Lidded l = (Lidded) block.getState();
		if(event.getClick() == ClickType.LEFT)
			l.open();
		else if(event.getClick() == ClickType.RIGHT)
			l.close();
	}

}
