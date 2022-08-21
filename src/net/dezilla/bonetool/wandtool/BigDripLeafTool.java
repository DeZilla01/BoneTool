package net.dezilla.bonetool.wandtool;

import java.util.Arrays;
import java.util.List;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.data.type.BigDripleaf;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;

import net.dezilla.bonetool.ToolUser;
import net.dezilla.bonetool.Util;
import net.dezilla.bonetool.util.Locale;

public class BigDripLeafTool extends WandTool {
	
	@Override
	public boolean isServerCompatible() {
		return checkClass("org.bukkit.block.data.type.BigDripleaf");
	}

	@Override
	public boolean isBlockCompatible(Block block) {
		return block.getBlockData() instanceof BigDripleaf;
	}

	@Override
	public ItemStack getIcon(Block block, ToolUser user) {
		String name = Locale.parse(user, "dripleadtilt");
		if(block!=null) {
			BigDripleaf leaf = (BigDripleaf) block.getBlockData();
			name+=": "+ChatColor.YELLOW+leaf.getTilt().toString();
		}
		return Util.setName(new ItemStack(Material.BIG_DRIPLEAF), name);
	}
	
	@Override
	public void onLeftClick(InventoryClickEvent event, Block block) {
		toggleTilt(block);
	}
	
	@Override
	public boolean canToggle() {
		return true;
	}
	
	@Override
	public void toggle(Block block) {
		toggleTilt(block);
	}
	
	public static void toggleTilt(Block block) {
		if(!(block.getBlockData() instanceof BigDripleaf))
			return;
		BigDripleaf leaf = (BigDripleaf) block.getBlockData();
		List<BigDripleaf.Tilt> list = Arrays.asList(BigDripleaf.Tilt.values());
		if(list.indexOf(leaf.getTilt())+1>=list.size())
			leaf.setTilt(list.get(0));
		else
			leaf.setTilt(list.get(list.indexOf(leaf.getTilt())+1));
		block.setBlockData(leaf);
	}

}
