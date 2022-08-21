package net.dezilla.bonetool.wandtool;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.data.type.Repeater;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;

import net.dezilla.bonetool.ToolUser;
import net.dezilla.bonetool.Util;
import net.dezilla.bonetool.util.Locale;

public class RepeaterDelayTool extends WandTool {

	@Override
	public boolean isServerCompatible() {
		return checkClass("org.bukkit.block.data.type.Repeater");
	}

	@Override
	public boolean isBlockCompatible(Block block) {
		return block.getBlockData() instanceof Repeater;
	}

	@Override
	public ItemStack getIcon(Block block, ToolUser user) {
		String name = Locale.parse(user, "repeaterdelay");
		if(block != null) {
			Repeater r = (Repeater) block.getBlockData();
			name += ": "+ChatColor.YELLOW+r.getDelay();
		}
		return Util.setName(new ItemStack(Material.REPEATER), name);
	}
	
	@Override
	public void onLeftClick(InventoryClickEvent event, Block block) {
		toggleDelay(block);
	}
	
	@Override
	public boolean canToggle() {
		return true;
	}
	
	@Override
	public void toggle(Block block) {
		toggleDelay(block);
	}
	
	public static void toggleDelay(Block block) {
		if(!(block.getBlockData() instanceof Repeater))
			return;
		Repeater r = (Repeater) block.getBlockData();
		if(r.getDelay()>=r.getMaximumDelay())
			r.setDelay(r.getMinimumDelay());
		else
			r.setDelay(r.getDelay()+1);
		block.setBlockData(r);
	}

}
