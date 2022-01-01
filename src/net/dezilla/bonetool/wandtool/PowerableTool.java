package net.dezilla.bonetool.wandtool;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.data.BlockData;
import org.bukkit.block.data.Powerable;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;

import net.dezilla.bonetool.Util;

public class PowerableTool extends WandTool {

	@Override
	public boolean isServerCompatible() {
		return checkClass("org.bukkit.block.data.Powerable");
	}

	@Override
	public boolean isBlockCompatible(Block block) {
		return block.getBlockData() instanceof Powerable;
	}

	@Override
	public ItemStack getIcon(Block block) {
		String name = "Powered";
		if(block != null)
			name = "Powered: "+(isPowered(block) ? ChatColor.GREEN+"True":ChatColor.RED+"False");
		return Util.setName(new ItemStack(Material.REDSTONE_LAMP), name);
	}
	
	@Override
	public void onLeftClick(InventoryClickEvent event, Block block) {
		togglePowered(block);
	}
	
	@Override
	public boolean canToggle() {
		return true;
	}
	
	@Override
	public void toggle(Block block) {
		togglePowered(block);
	}
	
	public static boolean isPowered(Powerable data) {
		return data.isPowered();
	}
	
	public static boolean isPowered(BlockData data) {
		if(data instanceof Powerable)
			return ((Powerable) data).isPowered();
		return false;
	}
	
	public static boolean isPowered(Block block) {
		if(block.getBlockData() instanceof Powerable)
			return isPowered(block.getBlockData());
		return false;
	}
	
	public static Powerable setPowered(Powerable data, boolean value) {
		data.setPowered(value);
		return data;
	}
	
	public static BlockData setPowered(BlockData data, boolean value) {
		if(data instanceof Powerable)
			((Powerable) data).setPowered(value);
		return data;
	}
	
	public static Block setPowered(Block block, boolean value) {
		if(block.getBlockData() instanceof Powerable)
			block.setBlockData(setPowered(block.getBlockData(), value));
		return block;
	}
	
	public static Powerable togglePowered(Powerable data) {
		if(data.isPowered())
			data.setPowered(false);
		else
			data.setPowered(true);
		return data;
	}
	
	public static BlockData togglePowered(BlockData data) {
		if(data instanceof Powerable)
			togglePowered((Powerable) data);
		return data;
	}
	
	public static Block togglePowered(Block block) {
		block.setBlockData(togglePowered(block.getBlockData()));
		return block;
	}

}
