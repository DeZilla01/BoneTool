package net.dezilla.bonetool.wandtool;

import java.util.Arrays;
import java.util.List;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.data.type.Switch;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;

import net.dezilla.bonetool.ToolUser;
import net.dezilla.bonetool.Util;
import net.dezilla.bonetool.util.Locale;

public class SwitchTool extends WandTool{

	@Override
	public boolean isServerCompatible() {
		return checkClass("org.bukkit.block.data.type.Switch") && !checkClass("org.bukkit.block.data.FaceAttachable");
	}

	@Override
	public boolean isBlockCompatible(Block block) {
		return block.getBlockData() instanceof Switch;
	}
	
	@Override
	public void onLeftClick(InventoryClickEvent event, Block block) {
		toggleSwitchFace(block);
	}

	@SuppressWarnings("deprecation")
	@Override
	public ItemStack getIcon(Block block, ToolUser user) {
		String name = Locale.parse(user, "switchface");
		if(block != null) {
			Switch s = (Switch) block.getBlockData();
			name+=": "+ChatColor.YELLOW+s.getFace().toString();
		}
		ItemStack icon = Util.setName(new ItemStack(Material.LEVER), name);
		return icon;
	}
	
	@Override
	public boolean canToggle() {
		return true;
	}
	
	@Override
	public void toggle(Block block) {
		toggleSwitchFace(block);
	}
	
	@SuppressWarnings("deprecation")
	public static void toggleSwitchFace(Block block) {
		if(!(block.getBlockData() instanceof Switch)) 
			return;
		
		Switch s = (Switch) block.getBlockData();
		List<Switch.Face> faces = Arrays.asList(Switch.Face.values());
		if(faces.indexOf(s.getFace())+1 >= faces.size())
			s.setFace(faces.get(0));
		else
			s.setFace(faces.get(faces.indexOf(s.getFace())+1));
		block.setBlockData(s);
		
	}

}
