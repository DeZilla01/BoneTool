package net.dezilla.bonetool.wandtool;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.data.type.TurtleEgg;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;

import net.dezilla.bonetool.Util;

public class TurtleEggAmountTool extends WandTool{

	@Override
	public boolean isServerCompatible() {
		return checkClass("org.bukkit.block.data.type.TurtleEgg");
	}

	@Override
	public boolean isBlockCompatible(Block block) {
		return block.getBlockData() instanceof TurtleEgg;
	}

	@Override
	public ItemStack getIcon(Block block) {
		String name = "Turtle Egg Amount";
		if(block!=null) {
			TurtleEgg egg = (TurtleEgg) block.getBlockData();
			name+=": "+ChatColor.YELLOW+egg.getEggs();
		}
		return Util.setName(new ItemStack(Material.TURTLE_EGG), name);
	}
	
	@Override
	public void onLeftClick(InventoryClickEvent event, Block block) {
		toggleEggAmount(block);
	}
	
	@Override
	public boolean canToggle() {
		return true;
	}
	
	@Override
	public void toggle(Block block) {
		toggleEggAmount(block);
	}
	
	public static void toggleEggAmount(Block block) {
		if(!(block.getBlockData() instanceof TurtleEgg))
			return;
		TurtleEgg t = (TurtleEgg) block.getBlockData();
		if(t.getEggs()>=t.getMaximumEggs())
			t.setEggs(t.getMinimumEggs());
		else
			t.setEggs(t.getEggs()+1);
		block.setBlockData(t);
	}

}
