package net.dezilla.bonetool.wandtool;

import java.util.Arrays;
import java.util.List;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.data.type.Slab;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;

import net.dezilla.bonetool.Util;

public class SlabTool extends WandTool {
	
	@Override
	public boolean isServerCompatible() {
		return checkClass("org.bukkit.block.data.type.Slab");
	}

	@Override
	public boolean isBlockCompatible(Block block) {
		return block.getBlockData() instanceof Slab;
	}

	@Override
	public ItemStack getIcon(Block block) {
		String name = "Slab type";
		if(block!=null) {
			Slab s = (Slab) block.getBlockData();
			name+=": "+ChatColor.YELLOW+s.getType().toString();
		}
		return Util.setName(new ItemStack(Material.SMOOTH_STONE_SLAB), name);
	}
	
	@Override
	public void onLeftClick(InventoryClickEvent event, Block block) {
		toggleSlabType(block);
	}
	
	@Override
	public boolean canToggle() {
		return true;
	}
	
	@Override
	public void toggle(Block block) {
		toggleSlabType(block);
	}
	
	public static void toggleSlabType(Block block) {
		if(!(block.getBlockData() instanceof Slab))
			return;
		Slab s = (Slab) block.getBlockData();
		List<Slab.Type> list = Arrays.asList(Slab.Type.values());
		if(list.indexOf(s.getType())+1>=list.size())
			s.setType(list.get(0));
		else
			s.setType(list.get(list.indexOf(s.getType())+1));
		block.setBlockData(s);
	}

}
