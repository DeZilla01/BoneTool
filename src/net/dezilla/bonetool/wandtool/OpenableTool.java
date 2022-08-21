package net.dezilla.bonetool.wandtool;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.data.BlockData;
import org.bukkit.block.data.Openable;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;

import net.dezilla.bonetool.ToolUser;
import net.dezilla.bonetool.Util;
import net.dezilla.bonetool.util.Locale;

public class OpenableTool extends WandTool {

	@Override
	public boolean isServerCompatible() {
		return checkClass("org.bukkit.block.data.Openable");
	}

	@Override
	public boolean isBlockCompatible(Block block) {
		return block.getBlockData() instanceof Openable;
	}

	@Override
	public ItemStack getIcon(Block block, ToolUser user) {
		String name = Locale.parse(user, "opened");
		if(block != null)
			name += ": "+(isOpen(block) ? ChatColor.GREEN+Locale.parse(user, "true"):ChatColor.RED+Locale.parse(user, "false"));
		return Util.setName(new ItemStack(Material.OAK_DOOR), name);
	}
	
	@Override
	public void onLeftClick(InventoryClickEvent event, Block block) {
		toggleOpen(block);
	}
	
	@Override
	public boolean canToggle() {
		return true;
	}
	
	@Override
	public void toggle(Block block) {
		toggleOpen(block);
	}
	
	public static boolean isOpen(Block block) {
		if(block.getBlockData() instanceof Openable) {
			Openable open = (Openable) block.getBlockData();
			return isOpen(open);
		}
		return false;
	}
	
	public static boolean isOpen(BlockData data) {
		if(data instanceof Openable) {
			Openable open = (Openable) data;
			return isOpen(open);
		}
		return false;
	}
	
	public static boolean isOpen(Openable data) {
		return data.isOpen();
	}
	
	public static Block setOpen(Block block, boolean value) {
		if(block.getBlockData() instanceof Openable) {
			Openable open = (Openable) block.getBlockData();
			open.setOpen(value);
			block.setBlockData(open);
		}
		return block;
	}
	
	public static BlockData setOpen(BlockData data, boolean value) {
		if(data instanceof Openable) {
			((Openable) data).setOpen(value);
		}
		return data;
	}
	
	public static Openable setOpen(Openable data, boolean value) {
		data.setOpen(value);
		return data;
	}
	
	public static Openable toggleOpen(Openable data) {
		if(isOpen(data))
			setOpen(data, false);
		else
			setOpen(data, true);
		return data;
	}
	
	public static BlockData toggleOpen(BlockData data) {
		if(isOpen(data))
			setOpen(data, false);
		else
			setOpen(data, true);
		return data;
	}

	
	public static Block toggleOpen(Block block) {
		if(isOpen(block))
			setOpen(block, false);
		else
			setOpen(block, true);
		return block;
	}

}
