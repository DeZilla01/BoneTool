package net.dezilla.bonetool.wandtool;

import org.bukkit.ChatColor;
import org.bukkit.block.Block;
import org.bukkit.block.data.type.PistonHead;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;

import net.dezilla.bonetool.Util;

public class PistonHeadTool extends WandTool{

	@Override
	public boolean isServerCompatible() {
		return checkClass("org.bukkit.block.data.type.PistonHead");
	}

	@Override
	public boolean isBlockCompatible(Block block) {
		return block.getBlockData() instanceof PistonHead;
	}
	
	@Override
	public void onLeftClick(InventoryClickEvent event, Block block) {
		togglePistonHead(block);
	}

	@Override
	public ItemStack getIcon(Block block) {
		String name = "Piston Head Short";
		if(block != null) {
			PistonHead p = (PistonHead) block.getBlockData();
			name+=": "+ChatColor.YELLOW+(p.isShort()?ChatColor.GREEN+"True":ChatColor.RED+"False");
		}
		ItemStack icon = Util.setName(Util.getPistonHeadItem(), name);
		return icon;
	}
	
	@Override
	public boolean canToggle() {
		return true;
	}
	
	@Override
	public void toggle(Block block) {
		togglePistonHead(block);
	}
	
	public static void togglePistonHead(Block block) {
		if(!(block.getBlockData() instanceof PistonHead)) 
			return;
		
		PistonHead piston = (PistonHead) block.getBlockData();
		piston.setShort(!piston.isShort());
		block.setBlockData(piston);
	}
	
	public static void setPistonHeadShot(Block block, boolean isShort) {
		if(!(block.getBlockData() instanceof PistonHead)) 
			return;
		
		PistonHead piston = (PistonHead) block.getBlockData();
		piston.setShort(isShort);
		block.setBlockData(piston);
	}

}
