package net.dezilla.bonetool.wandtool;

import java.util.Arrays;
import java.util.List;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.data.type.Stairs;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;

import net.dezilla.bonetool.ToolUser;
import net.dezilla.bonetool.Util;
import net.dezilla.bonetool.util.Locale;

public class StairTool extends WandTool {
	
	@Override
	public boolean isServerCompatible() {
		return checkClass("org.bukkit.block.data.type.Stairs");
	}

	@Override
	public boolean isBlockCompatible(Block block) {
		return block.getBlockData() instanceof Stairs;
	}

	@Override
	public ItemStack getIcon(Block block, ToolUser user) {
		String name = Locale.parse(user, "stairshape");
		if(block != null && block.getBlockData() instanceof Stairs) {
			Stairs stair = (Stairs) block.getBlockData();
			name+=": "+ChatColor.YELLOW+stair.getShape().toString();
		}
		return Util.setName(new ItemStack(Material.OAK_STAIRS), name);
	}
	
	@Override
	public void onLeftClick(InventoryClickEvent event, Block block) {
		toggleStairShape(block);
	}
	
	@Override
	public boolean canToggle() {
		return true;
	}
	
	@Override
	public void toggle(Block block) {
		toggleStairShape(block);
	}
	
	public static void toggleStairShape(Block block) {
		if(!(block.getBlockData() instanceof Stairs))
			return;
		Stairs stairs = (Stairs) block.getBlockData();
		List<Stairs.Shape> list = Arrays.asList(Stairs.Shape.values());
		if(list.indexOf(stairs.getShape())+1>=list.size())
			stairs.setShape(list.get(0));
		else
			stairs.setShape(list.get(list.indexOf(stairs.getShape())+1));
		block.setBlockData(stairs);
	}

}
