package net.dezilla.bonetool.wandtool;

import java.util.Arrays;
import java.util.List;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.data.type.Bell;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;

import net.dezilla.bonetool.Util;

public class BellTool extends WandTool{

	@Override
	public boolean isServerCompatible() {
		return checkClass("org.bukkit.block.data.type.Bell");
	}

	@Override
	public boolean isBlockCompatible(Block block) {
		return block.getBlockData() instanceof Bell;
	}

	@Override
	public ItemStack getIcon(Block block) {
		String name = "Bell Attachment";
		if(block!=null) {
			Bell bell = (Bell) block.getBlockData();
			name+=": "+ChatColor.YELLOW+bell.getAttachment().toString();
		}
		return Util.setName(new ItemStack(Material.BELL), name);
	}
	
	@Override
	public void onLeftClick(InventoryClickEvent event, Block block) {
		toggleBell(block);
	}
	
	@Override
	public boolean canToggle() {
		return true;
	}
	
	@Override
	public void toggle(Block block) {
		toggleBell(block);
	}
	
	public static void toggleBell(Block block) {
		if(!(block.getBlockData() instanceof Bell))
			return;
		Bell bell = (Bell) block.getBlockData();
		List<Bell.Attachment> list = Arrays.asList(Bell.Attachment.values());
		if(list.indexOf(bell.getAttachment())+1>=list.size())
			bell.setAttachment(list.get(0));
		else
			bell.setAttachment(list.get(list.indexOf(bell.getAttachment())+1));
		block.setBlockData(bell);
	}

}
