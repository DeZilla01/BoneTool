package net.dezilla.bonetool.wandtool;

import org.bukkit.ChatColor;
import org.bukkit.block.Block;
import org.bukkit.block.data.type.PistonHead;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;

import net.dezilla.bonetool.ToolUser;
import net.dezilla.bonetool.Util;
import net.dezilla.bonetool.util.Locale;

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
	public ItemStack getIcon(Block block, ToolUser user) {
		String name = Locale.parse(user, "pistonheadshort");
		if(block != null) {
			PistonHead p = (PistonHead) block.getBlockData();
			name+=": "+ChatColor.YELLOW+(p.isShort()?ChatColor.GREEN+Locale.parse(user, "true"):ChatColor.RED+Locale.parse(user, "false"));
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
