package net.dezilla.bonetool.wandtool;

import java.util.Arrays;
import java.util.List;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.data.type.Comparator;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;

import net.dezilla.bonetool.ToolUser;
import net.dezilla.bonetool.Util;
import net.dezilla.bonetool.util.Locale;

public class ComparatorTool extends WandTool {
	
	@Override
	public boolean isServerCompatible() {
		return checkClass("org.bukkit.block.data.type.Comparator");
	}

	@Override
	public boolean isBlockCompatible(Block block) {
		return block.getBlockData() instanceof Comparator;
	}

	@Override
	public ItemStack getIcon(Block block, ToolUser user) {
		String name = Locale.parse(user, "comparatormode");
		if(block!=null) {
			Comparator comp = (Comparator) block.getBlockData();
			name+=": "+ChatColor.YELLOW+comp.getMode().toString();
		}
		return Util.setName(new ItemStack(Material.COMPARATOR), name);
	}
	
	@Override
	public void onLeftClick(InventoryClickEvent event, Block block) {
		toggleMode(block);
	}
	
	@Override
	public boolean canToggle() {
		return true;
	}
	
	@Override
	public void toggle(Block block) {
		toggleMode(block);
	}
	
	public static void toggleMode(Block block) {
		if(!(block.getBlockData() instanceof Comparator))
			return;
		Comparator comp = (Comparator) block.getBlockData();
		List<Comparator.Mode> list = Arrays.asList(Comparator.Mode.values());
		if(list.indexOf(comp.getMode())+1 >= list.size())
			comp.setMode(list.get(0));
		else
			comp.setMode(list.get(list.indexOf(comp.getMode())+1));
		block.setBlockData(comp);
	}

}
