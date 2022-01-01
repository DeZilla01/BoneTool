package net.dezilla.bonetool.wandtool;

import java.util.Arrays;
import java.util.List;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.data.type.Door;
import org.bukkit.block.data.type.Door.Hinge;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;

import net.dezilla.bonetool.Util;

public class DoorHingeTool extends WandTool {
	
	@Override
	public boolean isServerCompatible() {
		return checkClass("org.bukkit.block.data.type.Door");
	}

	@Override
	public boolean isBlockCompatible(Block block) {
		return block.getBlockData() instanceof Door;
	}
	
	@Override
	public void onLeftClick(InventoryClickEvent event, Block block) {
		toggleHinge(block);
	}

	@Override
	public ItemStack getIcon(Block block) {
		String name = "Door Hinge";
		if(block != null) {
			Door d = (Door) block.getBlockData();
			name+=": "+ChatColor.YELLOW+d.getHinge().toString();
		}
		ItemStack icon = Util.setName(new ItemStack(Material.OAK_DOOR), name);
		return icon;
	}
	
	@Override
	public boolean canToggle() {
		return true;
	}
	
	@Override
	public void toggle(Block block) {
		toggleHinge(block);
	}
	
	public static void toggleHinge(Block block) {
		if(!(block.getBlockData() instanceof Door))
			return;
		Door door = (Door) block.getBlockData();
		List<Hinge> list = Arrays.asList(Hinge.values());
		if(list.indexOf(door.getHinge())+1 >= list.size())
			door.setHinge(list.get(0));
		else
			door.setHinge(list.get(list.indexOf(door.getHinge())+1));
		block.setBlockData(door);
	}

}
