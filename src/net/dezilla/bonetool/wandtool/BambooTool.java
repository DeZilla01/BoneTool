package net.dezilla.bonetool.wandtool;

import java.util.Arrays;
import java.util.List;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.data.type.Bamboo;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;

import net.dezilla.bonetool.Util;

public class BambooTool extends WandTool{

	@Override
	public boolean isServerCompatible() {
		return checkClass("org.bukkit.block.data.type.Bamboo");
	}

	@Override
	public boolean isBlockCompatible(Block block) {
		return block.getBlockData() instanceof Bamboo;
	}
	
	@Override
	public void onLeftClick(InventoryClickEvent event, Block block) {
		toggleBamboo(block);
	}

	@Override
	public ItemStack getIcon(Block block) {
		String name = "Bamboo Leaves";
		if(block != null && block.getBlockData() instanceof Bamboo) {
			Bamboo b = (Bamboo) block.getBlockData();
			name+=": "+ChatColor.YELLOW+b.getLeaves();
		}
		ItemStack icon = Util.setName(new ItemStack(Material.BAMBOO), name);
		return icon;
	}
	
	@Override
	public boolean canToggle() {
		return true;
	}
	
	@Override
	public void toggle(Block block) {
		toggleBamboo(block);
	}
	
	public static void toggleBamboo(Block block) {
		if(!(block.getBlockData() instanceof Bamboo)) 
			return;
		
		Bamboo bamboo = (Bamboo) block.getBlockData();
		List<Bamboo.Leaves> leaves = Arrays.asList(Bamboo.Leaves.values());
		if(leaves.indexOf(bamboo.getLeaves())+1 >= leaves.size())
			bamboo.setLeaves(leaves.get(0));
		else
			bamboo.setLeaves(leaves.get(leaves.indexOf(bamboo.getLeaves())+1));
		block.setBlockData(bamboo);
	}

}
