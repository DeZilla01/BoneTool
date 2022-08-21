package net.dezilla.bonetool.wandtool;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.data.BlockData;
import org.bukkit.block.data.Snowable;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;

import net.dezilla.bonetool.ToolUser;
import net.dezilla.bonetool.Util;
import net.dezilla.bonetool.util.Locale;

public class SnowableTool extends WandTool{

	@Override
	public boolean isServerCompatible() {
		return checkClass("org.bukkit.block.data.Snowable");
	}

	@Override
	public boolean isBlockCompatible(Block block) {
		return block.getBlockData() instanceof Snowable;
	}

	@Override
	public ItemStack getIcon(Block block, ToolUser user) {
		String name = Locale.parse(user, "snowy");
		if(block != null)
			name += ": "+(isSnowy(block) ? ChatColor.GREEN+Locale.parse(user, "true"):ChatColor.RED+Locale.parse(user, "false"));
		return Util.setName(new ItemStack(Material.SNOWBALL), name);
	}
	
	@Override
	public void onLeftClick(InventoryClickEvent event, Block block) {
		toggleSnowy(block);
	}
	
	@Override
	public boolean canToggle() {
		return true;
	}
	
	@Override
	public void toggle(Block block) {
		toggleSnowy(block);
	}
	
	public static boolean isSnowy(Block block) {
		if(block.getBlockData() instanceof Snowable) {
			Snowable snow = (Snowable) block.getBlockData();
			return isSnowy(snow);
		}
		return false;
	}
	
	public static boolean isSnowy(BlockData data) {
		if(data instanceof Snowable) {
			Snowable snow = (Snowable) data;
			return isSnowy(snow);
		}
		return false;
	}
	
	public static boolean isSnowy(Snowable data) {
		return data.isSnowy();
	}
	
	public static Block setSnowy(Block block, boolean value) {
		if(block.getBlockData() instanceof Snowable) {
			Snowable snow = (Snowable) block.getBlockData();
			snow.setSnowy(value);
			block.setBlockData(snow);
		}
		return block;
	}
	
	public static BlockData setSnowy(BlockData data, boolean value) {
		if(data instanceof Snowable) {
			((Snowable) data).setSnowy(value);
		}
		return data;
	}
	
	public static Snowable setSnowy(Snowable data, boolean value) {
		data.setSnowy(value);
		return data;
	}
	
	public static Snowable toggleSnowy(Snowable data) {
		if(isSnowy(data))
			setSnowy(data, false);
		else
			setSnowy(data, true);
		return data;
	}
	
	public static BlockData toggleSnowy(BlockData data) {
		if(isSnowy(data))
			setSnowy(data, false);
		else
			setSnowy(data, true);
		return data;
	}

	
	public static Block toggleSnowy(Block block) {
		if(isSnowy(block))
			setSnowy(block, false);
		else
			setSnowy(block, true);
		return block;
	}

}
