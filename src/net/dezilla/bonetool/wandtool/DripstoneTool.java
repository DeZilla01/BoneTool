package net.dezilla.bonetool.wandtool;

import java.util.Arrays;
import java.util.List;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.data.type.PointedDripstone;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;

import net.dezilla.bonetool.ToolUser;
import net.dezilla.bonetool.Util;
import net.dezilla.bonetool.util.Locale;

public class DripstoneTool extends WandTool {
	
	@Override
	public boolean isServerCompatible() {
		return checkClass("org.bukkit.block.data.type.PointedDripstone");
	}

	@Override
	public boolean isBlockCompatible(Block block) {
		return block.getBlockData() instanceof PointedDripstone;
	}

	@Override
	public ItemStack getIcon(Block block, ToolUser user) {
		String name = Locale.parse(user, "dripstonethickness");
		if(block!=null) {
			PointedDripstone dp = (PointedDripstone) block.getBlockData();
			name+=": "+ChatColor.YELLOW+dp.getThickness().toString();
		}
		return Util.setName(new ItemStack(Material.POINTED_DRIPSTONE), name);
	}
	
	@Override
	public void onLeftClick(InventoryClickEvent event, Block block) {
		toggleThickness(block);
	}
	
	@Override
	public boolean canToggle() {
		return true;
	}
	
	@Override
	public void toggle(Block block) {
		toggleThickness(block);
	}
	
	public static void toggleThickness(Block block) {
		if(!(block.getBlockData() instanceof PointedDripstone))
			return;
		PointedDripstone pd = (PointedDripstone) block.getBlockData();
		List<PointedDripstone.Thickness> list = Arrays.asList(PointedDripstone.Thickness.values());
		if(list.indexOf(pd.getThickness())+1 >= list.size())
			pd.setThickness(list.get(0));
		else
			pd.setThickness(list.get(list.indexOf(pd.getThickness())+1));
		block.setBlockData(pd);
	}

}
