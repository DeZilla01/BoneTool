package net.dezilla.bonetool.wandtool;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.data.Ageable;
import org.bukkit.block.data.BlockData;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;

import net.dezilla.bonetool.ToolUser;
import net.dezilla.bonetool.Util;
import net.dezilla.bonetool.util.Locale;

public class AgeableTool extends WandTool{

	@Override
	public boolean isServerCompatible() {
		return checkClass("org.bukkit.block.data.Ageable");
	}

	@Override
	public boolean isBlockCompatible(Block block) {
		return block.getBlockData() instanceof Ageable;
	}
	
	@Override
	public void onLeftClick(InventoryClickEvent event, Block block) {
		setAge(block, getIntValue(getAge(block), getMaxAge(block), event.getClick()));
	}

	@Override
	public ItemStack getIcon(Block block, ToolUser user) {
		String name = Locale.parse(user, "age");
		if(block != null)
			name += ": "+ChatColor.YELLOW+getAge(block);
		ItemStack icon = Util.setName(new ItemStack(Material.CLOCK), name);
		if(block!=null)
			Util.setLore(icon, getIntLore(user));
		return icon;
	}
	
	@Override
	public boolean canToggle() {
		return true;
	}
	
	@Override
	public void toggle(Block block) {
		toggleAge(block);
	}
	
	public static int getAge(Ageable data) {
		return data.getAge();
	}
	
	public static int getAge(BlockData data) {
		if(data instanceof Ageable)
			return((Ageable) data).getAge();
		return 0;
	}
	
	public static int getAge(Block block) {
		if(block.getBlockData() instanceof Ageable)
			return getAge(block.getBlockData());
		return 0;
	}
	
	public static int getMaxAge(Ageable data) {
		return data.getMaximumAge();
	}
	
	public static int getMaxAge(BlockData data) {
		if(data instanceof Ageable)
			return((Ageable) data).getMaximumAge();
		return 0;
	}
	
	public static int getMaxAge(Block block) {
		if(block.getBlockData() instanceof Ageable)
			return getMaxAge(block.getBlockData());
		return 0;
	}
	
	public static Ageable setAge(Ageable data, int age) {
		data.setAge(age);
		return data;
	}
	
	public static BlockData setAge(BlockData data, int age) {
		if(data instanceof Ageable) {
			((Ageable) data).setAge(age);
		}
		return data;
	}
	
	public static Block setAge(Block block, int age) {
		if(block.getBlockData() instanceof Ageable)
			block.setBlockData(setAge(block.getBlockData(), age));
		return block;
	}
	
	public static Ageable toggleAge(Ageable data) {
		if(data.getAge() >= data.getMaximumAge()) {
			data.setAge(0);
			return data;
		}
		data.setAge(data.getAge()+1);
		return data;
	}
	
	public static BlockData toggleAge(BlockData data) {
		if(!(data instanceof Ageable))
			return data;
		toggleAge((Ageable) data);
		return data;
	}
	
	public static Block toggleAge(Block block) {
		if(!(block.getBlockData() instanceof Ageable)) 
			return block;
		block.setBlockData(toggleAge(block.getBlockData()));
		return block;
	}

}
