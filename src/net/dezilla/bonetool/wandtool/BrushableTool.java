package net.dezilla.bonetool.wandtool;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.data.Brushable;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;

import net.dezilla.bonetool.ToolUser;
import net.dezilla.bonetool.Util;
import net.dezilla.bonetool.util.Locale;

public class BrushableTool extends WandTool{

	@Override
	public boolean isServerCompatible() {
		return checkClass("org.bukkit.block.data.Brushable");
	}

	@Override
	public boolean isBlockCompatible(Block block) {
		return block.getBlockData() instanceof Brushable;
	}
	
	@Override
	public void onLeftClick(InventoryClickEvent event, Block block) {
		Brushable b = (Brushable) block.getBlockData();
		b.setDusted(getIntValue(b.getDusted(), b.getMaximumDusted(), event.getClick()));
		block.setBlockData(b);
	}

	@Override
	public ItemStack getIcon(Block block, ToolUser user) {
		String name = Locale.parse(user, "brushable");
		if(block != null) {
			Brushable b = (Brushable) block.getBlockData();
			name += ": "+ChatColor.YELLOW+b.getDusted();
		}
		ItemStack icon = Util.setName(new ItemStack(Material.SUSPICIOUS_SAND), name);
		if(block!=null)
			Util.setLore(icon, getIntLore(user));
		return icon;
	}
	
	@Override
	public boolean canToggle() {
		return true;
	}
	
	@Override
	public void toggle(Block block) {
		Brushable b = (Brushable) block.getBlockData();
		if(b.getDusted() == b.getMaximumDusted())
			b.setDusted(0);
		else
			b.setDusted(b.getDusted()+1);
		block.setBlockData(b);
	}
	
	

}
