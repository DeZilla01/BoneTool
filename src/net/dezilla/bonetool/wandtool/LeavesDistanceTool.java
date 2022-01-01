package net.dezilla.bonetool.wandtool;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.data.type.Leaves;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;

import net.dezilla.bonetool.Util;

public class LeavesDistanceTool extends WandTool {
	
	@Override
	public boolean isServerCompatible() {
		return checkClass("org.bukkit.block.data.type.Leaves");
	}

	@Override
	public boolean isBlockCompatible(Block block) {
		return block.getBlockData() instanceof Leaves;
	}

	@Override
	public ItemStack getIcon(Block block) {
		String name = "Leaves Distance";
		if(block!=null) {
			name += ": " + ChatColor.YELLOW + getDistance(block);
		}
		ItemStack icon = Util.setName(new ItemStack(Material.OAK_LEAVES), name);
		if(block!=null)
			Util.setLore(icon, intLore);
		return icon;
	}
	
	@Override
	public void onLeftClick(InventoryClickEvent event, Block block) {
		Leaves l = (Leaves) block.getBlockData();
		int newValue = getIntValue(l.getDistance(), 7, event.getClick());
		if(newValue<1)
			newValue=1;
		l.setDistance(newValue);
		block.setBlockData(l);
	}
	
	@Override
	public boolean canToggle() {
		return true;
	}
	
	@Override
	public void toggle(Block block) {
		toggleDistance(block);
	}
	
	public static int getDistance(Block block) {
		if(!(block.getBlockData() instanceof Leaves))
			return 0;
		Leaves l = (Leaves) block.getBlockData();
		return l.getDistance();
	}
	
	public static void toggleDistance(Block block) {
		if(!(block.getBlockData() instanceof Leaves))
			return;
		
		Leaves l = (Leaves) block.getBlockData();
		if(l.getDistance() >= 7)
			l.setDistance(1);
		else
			l.setDistance(l.getDistance()+1);
		block.setBlockData(l);
	}

}
