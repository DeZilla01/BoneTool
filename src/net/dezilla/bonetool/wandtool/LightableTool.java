package net.dezilla.bonetool.wandtool;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.data.BlockData;
import org.bukkit.block.data.Lightable;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;

import net.dezilla.bonetool.ToolUser;
import net.dezilla.bonetool.Util;
import net.dezilla.bonetool.util.Locale;

public class LightableTool extends WandTool{

	@Override
	public boolean isServerCompatible() {
		return checkClass("org.bukkit.block.data.Lightable");
	}

	@Override
	public boolean isBlockCompatible(Block block) {
		return (block.getBlockData() instanceof Lightable);
	}

	@Override
	public ItemStack getIcon(Block block, ToolUser user) {
		String name = Locale.parse(user, "lit");
		if(block != null)
			name += ": "+(isLit(block) ? ChatColor.GREEN+Locale.parse(user, "true"):ChatColor.RED+Locale.parse(user, "false"));
		return Util.setName(new ItemStack(Material.TORCH), name);
	}
	
	@Override
	public void onLeftClick(InventoryClickEvent event, Block block) {
		toggleLit(block);
	}
	
	@Override
	public boolean canToggle() {
		return true;
	}
	
	@Override
	public void toggle(Block block) {
		toggleLit(block);
	}
	
	public static boolean isLit(Block block) {
		if(block.getBlockData() instanceof Lightable) {
			Lightable lit = (Lightable) block.getBlockData();
			return isLit(lit);
		}
		return false;
	}
	
	public static boolean isLit(BlockData data) {
		if(data instanceof Lightable) {
			Lightable lit = (Lightable) data;
			return isLit(lit);
		}
		return false;
	}
	
	public static boolean isLit(Lightable data) {
		return data.isLit();
	}
	
	public static Block setLit(Block block, boolean value) {
		if(block.getBlockData() instanceof Lightable) {
			Lightable lit = (Lightable) block.getBlockData();
			lit.setLit(value);
			block.setBlockData(lit);
		}
		return block;
	}
	
	public static BlockData setLit(BlockData data, boolean value) {
		if(data instanceof Lightable) {
			((Lightable) data).setLit(value);
		}
		return data;
	}
	
	public static Lightable setLit(Lightable data, boolean value) {
		data.setLit(value);
		return data;
	}
	
	public static Lightable toggleLit(Lightable data) {
		if(isLit(data))
			setLit(data, false);
		else
			setLit(data, true);
		return data;
	}
	
	public static BlockData toggleLit(BlockData data) {
		if(isLit(data))
			setLit(data, false);
		else
			setLit(data, true);
		return data;
	}

	
	public static Block toggleLit(Block block) {
		if(isLit(block))
			setLit(block, false);
		else
			setLit(block, true);
		return block;
	}

}
