package net.dezilla.bonetool.wandtool;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.data.type.TurtleEgg;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;

import net.dezilla.bonetool.ToolUser;
import net.dezilla.bonetool.Util;
import net.dezilla.bonetool.util.Locale;

public class TurtleEggHatchTool extends WandTool{

	@Override
	public boolean isServerCompatible() {
		return checkClass("org.bukkit.block.data.type.TurtleEgg");
	}

	@Override
	public boolean isBlockCompatible(Block block) {
		return block.getBlockData() instanceof TurtleEgg;
	}

	@Override
	public ItemStack getIcon(Block block, ToolUser user) {
		String name = Locale.parse(user, "turtleegghatch");
		if(block!=null) {
			TurtleEgg egg = (TurtleEgg) block.getBlockData();
			name+=": "+ChatColor.YELLOW+egg.getHatch();
		}
		return Util.setName(new ItemStack(Material.TURTLE_EGG), name);
	}
	
	@Override
	public void onLeftClick(InventoryClickEvent event, Block block) {
		toggleEggHatch(block);
	}
	
	@Override
	public boolean canToggle() {
		return true;
	}
	
	@Override
	public void toggle(Block block) {
		toggleEggHatch(block);
	}
	
	public static void toggleEggHatch(Block block) {
		if(!(block.getBlockData() instanceof TurtleEgg))
			return;
		TurtleEgg t = (TurtleEgg) block.getBlockData();
		if(t.getHatch()>=t.getMaximumHatch())
			t.setHatch(0);
		else
			t.setHatch(t.getHatch()+1);
		block.setBlockData(t);
	}

}
