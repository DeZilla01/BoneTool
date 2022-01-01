package net.dezilla.bonetool.wandtool;

import java.util.Arrays;
import java.util.List;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.data.type.StructureBlock;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;

import net.dezilla.bonetool.Util;

public class StructureTool extends WandTool {
	
	@Override
	public boolean isServerCompatible() {
		return checkClass("org.bukkit.block.data.type.StructureBlock");
	}

	@Override
	public boolean isBlockCompatible(Block block) {
		return block.getBlockData() instanceof StructureBlock;
	}

	@Override
	public ItemStack getIcon(Block block) {
		String name = "Structure Mode";
		if(block != null && block.getBlockData() instanceof StructureBlock) {
			StructureBlock struc = (StructureBlock) block.getBlockData();
			name+=": "+ChatColor.YELLOW+struc.getMode().toString();
		}
		return Util.setName(new ItemStack(Material.STRUCTURE_BLOCK), name);
	}
	
	@Override
	public void onLeftClick(InventoryClickEvent event, Block block) {
		toggleMode(block);
	}
	
	@Override
	public boolean canToggle() {
		return true;
	}
	
	@Override
	public void toggle(Block block) {
		toggleMode(block);
	}
	
	public static void toggleMode(Block block) {
		if(!(block.getBlockData() instanceof StructureBlock))
			return;
		StructureBlock struc = (StructureBlock) block.getBlockData();
		List<StructureBlock.Mode> list = Arrays.asList(StructureBlock.Mode.values());
		if(list.indexOf(struc.getMode())+1 >= list.size())
			struc.setMode(list.get(0));
		else
			struc.setMode(list.get(list.indexOf(struc.getMode())+1));
		block.setBlockData(struc);
	}

}
