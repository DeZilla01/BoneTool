package net.dezilla.bonetool.wandtool;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.data.type.Scaffolding;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;

import net.dezilla.bonetool.Util;

public class ScaffoldingDistanceTool extends WandTool {
	
	@Override
	public boolean isServerCompatible() {
		return checkClass("org.bukkit.block.data.type.Scaffolding");
	}

	@Override
	public boolean isBlockCompatible(Block block) {
		return block.getBlockData() instanceof Scaffolding;
	}

	@Override
	public ItemStack getIcon(Block block) {
		String name = "Scaffolding Distance";
		if(block!=null) {
			name += ": " + ChatColor.YELLOW + getDistance(block);
		}
		ItemStack icon = Util.setName(new ItemStack(Material.SCAFFOLDING), name);
		if(block!=null)
			Util.setLore(icon, intLore);
		return icon;
	}
	
	@Override
	public void onLeftClick(InventoryClickEvent event, Block block) {
		Scaffolding s = (Scaffolding) block.getBlockData();
		s.setDistance(getIntValue(s.getDistance(), s.getMaximumDistance(), event.getClick()));
		block.setBlockData(s);
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
		if(!(block.getBlockData() instanceof Scaffolding))
			return 0;
		Scaffolding s = (Scaffolding) block.getBlockData();
		return s.getDistance();
	}
	
	public static void toggleDistance(Block block) {
		if(!(block.getBlockData() instanceof Scaffolding))
			return;
		
		Scaffolding s = (Scaffolding) block.getBlockData();
		if(s.getDistance() >= s.getMaximumDistance())
			s.setDistance(0);
		else
			s.setDistance(s.getDistance()+1);
		block.setBlockData(s);
	}

}
