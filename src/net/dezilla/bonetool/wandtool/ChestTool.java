package net.dezilla.bonetool.wandtool;

import java.util.Arrays;
import java.util.List;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.data.type.Chest;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;

import net.dezilla.bonetool.ToolUser;
import net.dezilla.bonetool.Util;
import net.dezilla.bonetool.util.Locale;

public class ChestTool extends WandTool{
	
	@Override
	public boolean isServerCompatible() {
		return checkClass("org.bukkit.block.data.type.Chest");
	}

	@Override
	public boolean isBlockCompatible(Block block) {
		return block.getBlockData() instanceof Chest;
	}

	@Override
	public ItemStack getIcon(Block block, ToolUser user) {
		String name = Locale.parse(user, "chesttype");
		if(block!=null) {
			Chest chest = (Chest) block.getBlockData();
			name+=": "+ChatColor.YELLOW+chest.getType().toString();
		}
		return Util.setName(new ItemStack(Material.CHEST), name);
	}
	
	@Override
	public void onLeftClick(InventoryClickEvent event, Block block) {
		toggleChest(block);
	}
	
	@Override
	public boolean canToggle() {
		return true;
	}
	
	@Override
	public void toggle(Block block) {
		toggleChest(block);
	}
	
	public static void toggleChest(Block block) {
		if(!(block.getBlockData() instanceof Chest))
			return;
		Chest chest = (Chest) block.getBlockData();
		List<Chest.Type> list = Arrays.asList(Chest.Type.values());
		if(list.indexOf(chest.getType())+1>=list.size())
			chest.setType(list.get(0));
		else
			chest.setType(list.get(list.indexOf(chest.getType())+1));
		block.setBlockData(chest);
	}

}
