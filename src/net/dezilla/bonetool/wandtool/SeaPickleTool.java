package net.dezilla.bonetool.wandtool;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.data.type.SeaPickle;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;

import net.dezilla.bonetool.ToolUser;
import net.dezilla.bonetool.Util;
import net.dezilla.bonetool.util.Locale;

public class SeaPickleTool extends WandTool {
	
	@Override
	public boolean isServerCompatible() {
		return checkClass("org.bukkit.block.data.type.SeaPickle");
	}

	@Override
	public boolean isBlockCompatible(Block block) {
		return block.getBlockData() instanceof SeaPickle;
	}

	@Override
	public ItemStack getIcon(Block block, ToolUser user) {
		String name = Locale.parse(user, "seapickles");
		if(block != null && block.getBlockData() instanceof SeaPickle) {
			SeaPickle pickle = (SeaPickle) block.getBlockData();
			name+=": "+ChatColor.YELLOW+pickle.getPickles();
		}
		ItemStack icon = Util.setName(new ItemStack(Material.SEA_PICKLE), name);
		if(block!=null) {
			Util.setLore(icon, getIntLore(user));
		}
		return icon;
	}
	
	@Override
	public void onLeftClick(InventoryClickEvent event, Block block) {
		if(!(block.getBlockData() instanceof SeaPickle))
			return;
		SeaPickle pickle = (SeaPickle) block.getBlockData();
		int value = getIntValue(pickle.getPickles(), pickle.getMaximumPickles(), event.getClick());
		if(value < pickle.getMinimumPickles())
			value = pickle.getMinimumPickles();
		pickle.setPickles(value);
		block.setBlockData(pickle);
	}
	
	@Override
	public boolean canToggle() {
		return true;
	}
	
	@Override
	public void toggle(Block block) {
		togglePickles(block);
	}
	
	public static void togglePickles(Block block) {
		if(!(block.getBlockData() instanceof SeaPickle))
			return;
		
		SeaPickle pickle = (SeaPickle) block.getBlockData();
		if(pickle.getPickles() >= pickle.getMaximumPickles())
			pickle.setPickles(pickle.getMinimumPickles());
		else
			pickle.setPickles(pickle.getPickles()+1);
		block.setBlockData(pickle);
	}

}
