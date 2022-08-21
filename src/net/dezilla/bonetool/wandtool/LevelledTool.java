package net.dezilla.bonetool.wandtool;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.data.BlockData;
import org.bukkit.block.data.Levelled;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;

import net.dezilla.bonetool.ToolUser;
import net.dezilla.bonetool.Util;
import net.dezilla.bonetool.util.Locale;

public class LevelledTool extends WandTool{

	@Override
	public boolean isServerCompatible() {
		return checkClass("org.bukkit.block.data.Levelled");
	}

	@Override
	public boolean isBlockCompatible(Block block) {
		return block.getBlockData() instanceof Levelled;
	}

	@Override
	public ItemStack getIcon(Block block, ToolUser user) {
		String name = Locale.parse(user, "level");
		if(block != null)
			name += ": "+ChatColor.YELLOW+getLevel(block);
		ItemStack icon = Util.setName(new ItemStack(Material.BUCKET), name);
		if(block != null)
			icon = Util.setLore(icon, getIntLore(user));
		return icon;
	}
	
	@Override
	public void onLeftClick(InventoryClickEvent event, Block block) {
		setLevel(block, getIntValue(getLevel(block), getMaxLevel(block), event.getClick()));
	}
	
	@Override
	public boolean canToggle() {
		return true;
	}
	
	@Override
	public void toggle(Block block) {
		toggleLevel(block);
	}
	
	public static int getLevel(Levelled data) {
		return data.getLevel();
	}
	
	public static int getLevel(BlockData data) {
		if(data instanceof Levelled)
			return((Levelled) data).getLevel();
		return 0;
	}
	
	public static int getLevel(Block block) {
		if(block.getBlockData() instanceof Levelled)
			return getLevel(block.getBlockData());
		return 0;
	}
	
	public static int getMaxLevel(Levelled data) {
		return data.getMaximumLevel();
	}
	
	public static int getMaxLevel(BlockData data) {
		if(data instanceof Levelled)
			return((Levelled) data).getMaximumLevel();
		return 0;
	}
	
	public static int getMaxLevel(Block block) {
		if(block.getBlockData() instanceof Levelled)
			return getMaxLevel(block.getBlockData());
		return 0;
	}
	
	public static Levelled setLevel(Levelled data, int level) {
		data.setLevel(level);
		return data;
	}
	
	public static BlockData setLevel(BlockData data, int level) {
		if(data instanceof Levelled) {
			((Levelled) data).setLevel(level);
		}
		return data;
	}
	
	public static Block setLevel(Block block, int level) {
		if(block.getBlockData() instanceof Levelled)
			block.setBlockData(setLevel(block.getBlockData(), level));
		return block;
	}
	
	public static Levelled toggleLevel(Levelled data) {
		if(data.getLevel() >= data.getMaximumLevel()) {
			data.setLevel(0);
			return data;
		}
		data.setLevel(data.getLevel()+1);
		return data;
	}
	
	public static BlockData toggleLevel(BlockData data) {
		if(!(data instanceof Levelled))
			return data;
		toggleLevel((Levelled) data);
		return data;
	}
	
	public static Block toggleLevel(Block block) {
		if(!(block.getBlockData() instanceof Levelled)) 
			return block;
		block.setBlockData(toggleLevel(block.getBlockData()));
		return block;
	}

}
