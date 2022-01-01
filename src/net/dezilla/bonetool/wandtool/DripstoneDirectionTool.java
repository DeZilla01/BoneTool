package net.dezilla.bonetool.wandtool;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.block.data.type.PointedDripstone;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;

import net.dezilla.bonetool.Util;

public class DripstoneDirectionTool extends WandTool {
	
	@Override
	public boolean isServerCompatible() {
		return checkClass("org.bukkit.block.data.type.PointedDripstone");
	}

	@Override
	public boolean isBlockCompatible(Block block) {
		return block.getBlockData() instanceof PointedDripstone;
	}

	@Override
	public ItemStack getIcon(Block block) {
		String name = "Dripstone Direction";
		if(block!=null) {
			PointedDripstone dp = (PointedDripstone) block.getBlockData();
			name+=": "+ChatColor.YELLOW+dp.getVerticalDirection().toString();
		}
		return Util.setName(new ItemStack(Material.POINTED_DRIPSTONE), name);
	}
	
	@Override
	public void onLeftClick(InventoryClickEvent event, Block block) {
		toggleDirection(block);
	}
	
	@Override
	public boolean canToggle() {
		return true;
	}
	
	@Override
	public void toggle(Block block) {
		toggleDirection(block);
	}
	
	public static void toggleDirection(Block block) {
		if(!(block.getBlockData() instanceof PointedDripstone))
			return;
		PointedDripstone pd = (PointedDripstone) block.getBlockData();
		List<BlockFace> list = new ArrayList<BlockFace>(pd.getVerticalDirections());
		if(list.indexOf(pd.getVerticalDirection())+1 >= list.size())
			pd.setVerticalDirection(list.get(0));
		else
			pd.setVerticalDirection(list.get(list.indexOf(pd.getVerticalDirection())+1));
		block.setBlockData(pd);
	}

}
