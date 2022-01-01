package net.dezilla.bonetool.wandtool;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.data.type.Bed;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;

import net.dezilla.bonetool.Util;

public class BedTool extends WandTool{

	@Override
	public boolean isServerCompatible() {
		return checkClass("org.bukkit.block.data.type.Bed");
	}

	@Override
	public boolean isBlockCompatible(Block block) {
		return isBed(block);
	}

	@Override
	public ItemStack getIcon(Block block) {
		String name = "Bed Part";
		if(block != null)
			name = "Bed Part: "+ChatColor.YELLOW+getBedPart(block);
		return Util.setName(new ItemStack(Material.RED_BED), name);
	}
	
	@Override
	public void onLeftClick(InventoryClickEvent event, Block block) {
		toggleBedPart(block);
	}
	
	@Override
	public boolean canToggle() {
		return true;
	}
	
	@Override
	public void toggle(Block block) {
		toggleBedPart(block);
	}
	
	public static boolean isBed(Block block) {
		return(block.getBlockData() instanceof Bed);
	}
	
	public static Bed.Part getBedPart(Block block){
		if(block.getBlockData() instanceof Bed) {
			Bed bed = (Bed) block.getBlockData();
			return bed.getPart();
		}
		return Bed.Part.FOOT;
	}
	
	public static Block setBedPart(Block block, Bed.Part part) {
		if(block.getBlockData() instanceof Bed) {
			Bed bed = (Bed) block.getBlockData();
			bed.setPart(part);
			block.setBlockData(bed);
		}
		return block;
	}
	
	public static Block toggleBedPart(Block block) {
		if(block.getBlockData() instanceof Bed) {
			Bed bed = (Bed) block.getBlockData();
			if(bed.getPart() == Bed.Part.FOOT)
				bed.setPart(Bed.Part.HEAD);
			else
				bed.setPart(Bed.Part.FOOT);
			block.setBlockData(bed);
		}
		return block;
	}

}
