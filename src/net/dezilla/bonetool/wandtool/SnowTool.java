package net.dezilla.bonetool.wandtool;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.data.type.Snow;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;

import net.dezilla.bonetool.ToolUser;
import net.dezilla.bonetool.Util;
import net.dezilla.bonetool.util.Locale;

public class SnowTool extends WandTool{
	
	@Override
	public boolean isServerCompatible() {
		return checkClass("org.bukkit.block.data.type.Snow");
	}

	@Override
	public boolean isBlockCompatible(Block block) {
		return block.getBlockData() instanceof Snow;
	}

	@Override
	public ItemStack getIcon(Block block, ToolUser user) {
		String name = Locale.parse(user, "snowlayers");
		if(block!=null) {
			name += ": " + ChatColor.YELLOW + getSnowLayer(block);
		}
		ItemStack icon = Util.setName(new ItemStack(Material.SNOW_BLOCK), name);
		if(block!=null)
			Util.setLore(icon, getIntLore(user));
		return icon;
	}
	
	@Override
	public void onLeftClick(InventoryClickEvent event, Block block) {
		Snow s = (Snow) block.getBlockData();
		int newValue = getIntValue(s.getLayers(), s.getMaximumLayers(), event.getClick());
		if(newValue < 1)
			newValue = 1;
		s.setLayers(newValue);
		block.setBlockData(s);
	}
	
	@Override
	public boolean canToggle() {
		return true;
	}
	
	@Override
	public void toggle(Block block) {
		toggleSnowLayer(block);
	}
	
	public static int getSnowLayer(Block block) {
		if(!(block.getBlockData() instanceof Snow))
			return 0;
		Snow s = (Snow) block.getBlockData();
		return s.getLayers();
	}
	
	public static void toggleSnowLayer(Block block) {
		if(!(block.getBlockData() instanceof Snow))
			return;
		
		Snow s = (Snow) block.getBlockData();
		if(s.getLayers() >= s.getMaximumLayers())
			s.setLayers(1);
		else
			s.setLayers(s.getLayers()+1);
		block.setBlockData(s);
	}

}
