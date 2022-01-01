package net.dezilla.bonetool.wandtool;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.data.BlockData;
import org.bukkit.block.data.Waterlogged;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;

import net.dezilla.bonetool.Util;

public class WaterloggedTool extends WandTool{

	@Override
	public boolean isServerCompatible() {
		return checkClass("org.bukkit.block.data.Waterlogged");
	}

	@Override
	public boolean isBlockCompatible(Block block) {
		return block.getBlockData() instanceof Waterlogged;
	}

	@Override
	public ItemStack getIcon(Block block) {
		String name = "Waterlogged";
		if(block != null)
			name = "Waterlogged: "+(isWaterlogged(block) ? ChatColor.GREEN+"True":ChatColor.RED+"False");
		return Util.setName(new ItemStack(Material.WATER_BUCKET), name);
	}
	
	@Override
	public void onLeftClick(InventoryClickEvent event, Block block) {
		toggleWaterlogged(block);
	}
	
	@Override
	public boolean canToggle() {
		return true;
	}
	
	@Override
	public void toggle(Block block) {
		toggleWaterlogged(block);
	}
	
	public static boolean isWaterlogged(Waterlogged data) {
		return data.isWaterlogged();
	}
	
	public static boolean isWaterlogged(BlockData data) {
		if(data instanceof Waterlogged)
			return ((Waterlogged) data).isWaterlogged();
		return false;
	}
	
	public static boolean isWaterlogged(Block block) {
		if(block.getBlockData() instanceof Waterlogged)
			return ((Waterlogged) block.getBlockData()).isWaterlogged();
		return false;
	}
	
	public static Waterlogged setWaterlogged(Waterlogged data, boolean value) {
		data.setWaterlogged(value);
		return data;
	}
	
	public static BlockData setWaterlogged(BlockData data, boolean value) {
		if(data instanceof Waterlogged)
			((Waterlogged) data).setWaterlogged(value);
		return data;
	}
	
	public static Block setWaterlogged(Block block, boolean value) {
		if(block.getBlockData() instanceof Waterlogged) {
			Waterlogged data = (Waterlogged) block.getBlockData();
			data.setWaterlogged(value);
			block.setBlockData(data);
		}
		return block;
	}
	
	public static Waterlogged toggleWaterlogged(Waterlogged data) {
		if(isWaterlogged(data))
			setWaterlogged(data, false);
		else
			setWaterlogged(data, true);
		return data;
	}
	
	public static BlockData toggleWaterlogged(BlockData data) {
		if(isWaterlogged(data))
			setWaterlogged(data, false);
		else
			setWaterlogged(data, true);
		return data;
	}
	
	public static Block toggleWaterlogged(Block block) {
		if(isWaterlogged(block))
			setWaterlogged(block, false);
		else
			setWaterlogged(block, true);
		return block;
	}

}
