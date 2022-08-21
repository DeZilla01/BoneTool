package net.dezilla.bonetool.wandtool;

import org.bukkit.ChatColor;
import org.bukkit.block.Block;
import org.bukkit.block.data.type.BubbleColumn;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;

import net.dezilla.bonetool.ToolUser;
import net.dezilla.bonetool.Util;
import net.dezilla.bonetool.util.Locale;

//this tool has not been tested
public class BubbleTool extends WandTool{

	@Override
	public boolean isServerCompatible() {
		return checkClass("org.bukkit.block.data.type.BubbleColumn");
	}

	@Override
	public boolean isBlockCompatible(Block block) {
		return block.getBlockData() instanceof BubbleColumn;
	}

	@Override
	public ItemStack getIcon(Block block, ToolUser user) {
		ItemStack icon = Util.createTexturedHead("eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvYWU0MGZlNmE5ZGIyMmYyYjEyZTYwNWU0OTI5OTViZDQ2YWM5MzY3YjI2YjhhYjg1ZTA3MjY2ODAxYmVjZjcxZCJ9fX0=");
		String name = Locale.parse(user, "bubblecolumndrag");
		if(block!=null) {
			BubbleColumn c = (BubbleColumn) block.getBlockData();
			name+=": "+(c.isDrag()?ChatColor.GREEN+Locale.parse(user, "true"):ChatColor.RED+Locale.parse(user, "false"));
		}
		return Util.setName(icon, name);
	}
	
	@Override
	public void onLeftClick(InventoryClickEvent event, Block block) {
		toggleBubble(block);
	}
	
	@Override
	public boolean canToggle() {
		return true;
	}
	
	@Override
	public void toggle(Block block) {
		toggleBubble(block);
	}
	
	public static void toggleBubble(Block block) {
		if(!(block.getBlockData() instanceof BubbleColumn))
			return;
		BubbleColumn c = (BubbleColumn) block.getBlockData();
		c.setDrag(!c.isDrag());
		block.setBlockData(c);
	}

}
