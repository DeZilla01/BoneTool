package net.dezilla.bonetool.wandtool;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.data.AnaloguePowerable;
import org.bukkit.block.data.BlockData;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;

import net.dezilla.bonetool.Util;

public class AnaloguePowerableTool extends WandTool{

	@Override
	public boolean isServerCompatible() {
		return checkClass("org.bukkit.block.data.AnaloguePowerable");
	}

	@Override
	public boolean isBlockCompatible(Block block) {
		return block.getBlockData() instanceof AnaloguePowerable;
	}

	@Override
	public ItemStack getIcon(Block block) {
		String name = "Power";
		if(block != null)
			name = "Power: "+ChatColor.YELLOW+getPower(block);
		ItemStack icon = Util.setName(new ItemStack(Material.REDSTONE), name);
		if(block!=null)
			Util.setLore(icon, intLore);
		return icon;
	}
	
	@Override
	public void onLeftClick(InventoryClickEvent event, Block block) {
		setPower(block, getIntValue(getPower(block), getMaxPower(block), event.getClick()));
	}
	
	@Override
	public boolean canToggle() {
		return true;
	}
	
	@Override
	public void toggle(Block block) {
		togglePower(block);
	}
	
	public static int getPower(AnaloguePowerable data) {
		return data.getPower();
	}
	
	public static int getPower(BlockData data) {
		if(data instanceof AnaloguePowerable)
			return((AnaloguePowerable) data).getPower();
		return 0;
	}
	
	public static int getPower(Block block) {
		if(block.getBlockData() instanceof AnaloguePowerable)
			return getPower(block.getBlockData());
		return 0;
	}
	
	public static int getMaxPower(AnaloguePowerable data) {
		return data.getMaximumPower();
	}
	
	public static int getMaxPower(BlockData data) {
		if(data instanceof AnaloguePowerable)
			return((AnaloguePowerable) data).getMaximumPower();
		return 0;
	}
	
	public static int getMaxPower(Block block) {
		if(block.getBlockData() instanceof AnaloguePowerable)
			return getMaxPower(block.getBlockData());
		return 0;
	}
	
	public static AnaloguePowerable setPower(AnaloguePowerable data, int power) {
		data.setPower(power);
		return data;
	}
	
	public static BlockData setPower(BlockData data, int power) {
		if(data instanceof AnaloguePowerable) {
			((AnaloguePowerable) data).setPower(power);
		}
		return data;
	}
	
	public static Block setPower(Block block, int power) {
		if(block.getBlockData() instanceof AnaloguePowerable)
			block.setBlockData(setPower(block.getBlockData(), power));
		return block;
	}
	
	public static AnaloguePowerable togglePower(AnaloguePowerable data) {
		if(data.getPower() >= data.getMaximumPower()) {
			data.setPower(0);
			return data;
		}
		data.setPower(data.getPower()+1);
		return data;
	}
	
	public static BlockData togglePower(BlockData data) {
		if(!(data instanceof AnaloguePowerable))
			return data;
		togglePower((AnaloguePowerable) data);
		return data;
	}
	
	public static Block togglePower(Block block) {
		if(!(block.getBlockData() instanceof AnaloguePowerable)) 
			return block;
		block.setBlockData(togglePower(block.getBlockData()));
		return block;
	}

}
