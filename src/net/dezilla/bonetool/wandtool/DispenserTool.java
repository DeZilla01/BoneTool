package net.dezilla.bonetool.wandtool;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.data.type.Dispenser;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;

import net.dezilla.bonetool.Util;

public class DispenserTool extends WandTool{
	
	@Override
	public boolean isServerCompatible() {
		return checkClass("org.bukkit.block.data.type.Dispenser");
	}

	@Override
	public boolean isBlockCompatible(Block block) {
		return block.getBlockData() instanceof Dispenser;
	}

	@Override
	public ItemStack getIcon(Block block) {
		String name = "Dispenser Triggered";
		if(block != null) {
			Dispenser d = (Dispenser) block.getBlockData();
			name += ": "+(d.isTriggered() ? ChatColor.GREEN+"True":ChatColor.RED+"False");
		}
		return Util.setName(new ItemStack(Material.DISPENSER), name);
	}
	
	@Override
	public void onLeftClick(InventoryClickEvent event, Block block) {
		toggleTriggered(block);
	}
	
	@Override
	public boolean canToggle() {
		return true;
	}
	
	@Override
	public void toggle(Block block) {
		toggleTriggered(block);
	}
	
	public static void toggleTriggered(Block block) {
		if(!(block.getBlockData() instanceof Dispenser))
			return;
		Dispenser d = (Dispenser) block.getBlockData();
		d.setTriggered(!d.isTriggered());
		block.setBlockData(d);
	}

}
