package net.dezilla.bonetool.wandtool;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.SculkShrieker;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;

import net.dezilla.bonetool.Util;
import net.dezilla.bonetool.wandtool.WandTool;

public class ShriekerWarningTool extends WandTool{

	@Override
	public boolean isServerCompatible() {
		return checkClass("org.bukkit.block.SculkShrieker");
	}

	@Override
	public boolean isBlockCompatible(Block block) {
		return block.getState() instanceof SculkShrieker;
	}
	
	@Override
	public void onLeftClick(InventoryClickEvent event, Block block) {
		SculkShrieker shrieker = (SculkShrieker) block.getState();
		shrieker.setWarningLevel(getIntValue(shrieker.getWarningLevel(), 5, event.getClick()));
		shrieker.update();
	}

	@Override
	public ItemStack getIcon(Block block) {
		String name = "Warning Level";
		if(block != null) {
			SculkShrieker shrieker = (SculkShrieker) block.getState();
			name = "Warning Level: "+ChatColor.YELLOW+shrieker.getWarningLevel();
		}
		ItemStack icon = Util.setName(new ItemStack(Material.SCULK_SHRIEKER), name);
		if(block!=null)
			Util.setLore(icon, intLore);
		return icon;
	}
	
	@Override
	public boolean canToggle() {
		return true;
	}
	
	@Override
	public void toggle(Block block) {
		SculkShrieker shrieker = (SculkShrieker) block.getState();
		if(shrieker.getWarningLevel()>=5)
			shrieker.setWarningLevel(0);
		else
			shrieker.setWarningLevel(shrieker.getWarningLevel()+1);
		shrieker.update();
	}

}
