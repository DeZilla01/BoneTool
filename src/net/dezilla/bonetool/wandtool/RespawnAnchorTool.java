package net.dezilla.bonetool.wandtool;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.data.type.RespawnAnchor;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;

import net.dezilla.bonetool.Util;

public class RespawnAnchorTool extends WandTool{

	@Override
	public boolean isServerCompatible() {
		return checkClass("org.bukkit.block.data.type.RespawnAnchor");
	}

	@Override
	public boolean isBlockCompatible(Block block) {
		return block.getBlockData() instanceof RespawnAnchor;
	}

	@Override
	public ItemStack getIcon(Block block) {
		String name = "Respawn Anchor Charge";
		if(block != null) {	
			RespawnAnchor anchor = (RespawnAnchor) block.getBlockData();
			name+=": "+ChatColor.YELLOW+anchor.getCharges();
		}
		ItemStack icon = Util.setName(new ItemStack(Material.RESPAWN_ANCHOR), name);
		Util.setLore(icon, intLore);
		return icon;
	}
	
	@Override
	public void onLeftClick(InventoryClickEvent event, Block block) {
		RespawnAnchor anchor = (RespawnAnchor) block.getBlockData();
		anchor.setCharges(getIntValue(anchor.getCharges(), anchor.getMaximumCharges(), event.getClick()));
		block.setBlockData(anchor);
	}
	
	@Override
	public boolean canToggle() {
		return true;
	}
	
	@Override
	public void toggle(Block block) {
		toggleAnchor(block);
	}
	
	public static void toggleAnchor(Block block) {
		if(!(block.getBlockData() instanceof RespawnAnchor))
			return;
		RespawnAnchor anchor = (RespawnAnchor) block.getBlockData();
		if(anchor.getCharges()>=anchor.getMaximumCharges())
			anchor.setCharges(0);
		else
			anchor.setCharges(anchor.getCharges()+1);
		block.setBlockData(anchor);
	}

}
