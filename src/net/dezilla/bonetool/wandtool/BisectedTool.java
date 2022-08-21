package net.dezilla.bonetool.wandtool;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.data.Bisected;
import org.bukkit.block.data.BlockData;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;

import net.dezilla.bonetool.ToolUser;
import net.dezilla.bonetool.Util;
import net.dezilla.bonetool.util.Locale;

public class BisectedTool extends WandTool{

	@Override
	public boolean isServerCompatible() {
		return checkClass("org.bukkit.block.data.Bisected");
	}

	@Override
	public boolean isBlockCompatible(Block block) {
		return block.getBlockData() instanceof Bisected;
	}

	@Override
	public ItemStack getIcon(Block block, ToolUser user) {
		String name = Locale.parse(user, "bisected");
		if(block != null)
			name = Locale.parse(user, "bisected")+": "+ChatColor.YELLOW+getHalf(block);
		return Util.setName(new ItemStack(Material.IRON_DOOR), name);
	}
	
	@Override
	public void onLeftClick(InventoryClickEvent event, Block block) {
		toggleHalf(block);
	}
	
	@Override
	public boolean canToggle() {
		return true;
	}
	
	@Override
	public void toggle(Block block) {
		toggleHalf(block);
	}
	
	public static Bisected.Half getHalf(Bisected data) {
		return data.getHalf();
	}
	
	public static Bisected.Half getHalf(BlockData data){
		if(data instanceof Bisected)
			return ((Bisected) data).getHalf();
		return Bisected.Half.BOTTOM;
	}
	
	public static Bisected.Half getHalf(Block block){
		if(block.getBlockData() instanceof Bisected)
			return getHalf(block.getBlockData());
		return Bisected.Half.BOTTOM;
	}
	
	public static Bisected setHalf(Bisected data, Bisected.Half half) {
		data.setHalf(half);
		return data;
	}
	
	public static BlockData setHalf(BlockData data, Bisected.Half half) {
		if(data instanceof Bisected)
			((Bisected) data).setHalf(half);
		return data;
	}
	
	public static Block setHalf(Block block, Bisected.Half half) {
		if(block.getBlockData() instanceof Bisected)
			block.setBlockData(setHalf(block.getBlockData(), half));
		return block;
	}
	
	public static Bisected toggleHalf(Bisected data) {
		if(data.getHalf() == Bisected.Half.TOP)
			data.setHalf(Bisected.Half.BOTTOM);
		else
			data.setHalf(Bisected.Half.TOP);
		return data;
	}
	
	public static BlockData toggleHalf(BlockData data) {
		if(data instanceof Bisected)
			toggleHalf((Bisected) data);
		return data;
	}
	
	public static Block toggleHalf(Block block) {
		if(block.getBlockData() instanceof Bisected)
			block.setBlockData(toggleHalf(block.getBlockData()));
		return block;
	}

}
