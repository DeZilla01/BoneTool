package net.dezilla.bonetool.wandtool;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.data.type.Gate;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;

import net.dezilla.bonetool.Util;

public class GateTool extends WandTool {
	
	@Override
	public boolean isServerCompatible() {
		return checkClass("org.bukkit.block.data.type.Gate");
	}

	@Override
	public boolean isBlockCompatible(Block block) {
		return block.getBlockData() instanceof Gate;
	}

	@Override
	public ItemStack getIcon(Block block) {
		String name = "In Wall";
		if(block != null)
			name += ": "+(inWall(block) ? ChatColor.GREEN+"True":ChatColor.RED+"False");
		return Util.setName(new ItemStack(Material.OAK_FENCE_GATE), name);
	}
	
	@Override
	public void onLeftClick(InventoryClickEvent event, Block block) {
		toggleGate(block);
	}
	
	@Override
	public boolean canToggle() {
		return true;
	}
	
	@Override
	public void toggle(Block block) {
		toggleGate(block);
	}
	
	public static boolean inWall(Block block) {
		if(!(block.getBlockData() instanceof Gate))
			return false;
		Gate gate = (Gate) block.getBlockData();
		return gate.isInWall();
	}
	
	public static void toggleGate(Block block) {
		if(!(block.getBlockData() instanceof Gate))
			return;
		Gate gate = (Gate) block.getBlockData();
		gate.setInWall(!gate.isInWall());
		block.setBlockData(gate);
	}

}
