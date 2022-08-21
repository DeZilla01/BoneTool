package net.dezilla.bonetool.wandtool;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.data.type.Leaves;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;

import net.dezilla.bonetool.ToolUser;
import net.dezilla.bonetool.Util;
import net.dezilla.bonetool.util.Locale;

public class LeavesPersistanceTool extends WandTool {
	
	@Override
	public boolean isServerCompatible() {
		return checkClass("org.bukkit.block.data.type.Leaves");
	}

	@Override
	public boolean isBlockCompatible(Block block) {
		return block.getBlockData() instanceof Leaves;
	}

	@Override
	public ItemStack getIcon(Block block, ToolUser user) {
		String name = Locale.parse(user, "leavespersistant");
		if(block!=null) {
			name += ": " + (isPersistant(block) ? ChatColor.GREEN+Locale.parse(user, "true"):ChatColor.RED+Locale.parse(user, "false"));
		}
		return Util.setName(new ItemStack(Material.OAK_LEAVES), name);
	}
	
	@Override
	public void onLeftClick(InventoryClickEvent event, Block block) {
		togglePersistant(block);
	}
	
	@Override
	public boolean canToggle() {
		return true;
	}
	
	@Override
	public void toggle(Block block) {
		togglePersistant(block);
	}
	
	public static boolean isPersistant(Block block) {
		if(!(block.getBlockData() instanceof Leaves))
			return false;
		Leaves l = (Leaves) block.getBlockData();
		return l.isPersistent();
	}
	
	public static void togglePersistant(Block block) {
		if(!(block.getBlockData() instanceof Leaves))
			return;
		
		Leaves l = (Leaves) block.getBlockData();
		l.setPersistent(!l.isPersistent());
		block.setBlockData(l);
	}

}
