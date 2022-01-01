package net.dezilla.bonetool.wandtool;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.data.type.Farmland;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;

import net.dezilla.bonetool.Util;

public class FarmlandTool extends WandTool {
	
	@Override
	public boolean isServerCompatible() {
		return checkClass("org.bukkit.block.data.type.Farmland");
	}

	@Override
	public boolean isBlockCompatible(Block block) {
		return block.getBlockData() instanceof Farmland;
	}

	@Override
	public ItemStack getIcon(Block block) {
		String name = "Moist Level";
		if(block!=null) {
			name += ": " + ChatColor.YELLOW + getMoistLevel(block);
		}
		ItemStack icon = Util.setName(new ItemStack(Material.FARMLAND), name);
		if(block!=null)
			Util.setLore(icon, intLore);
		return icon;
	}
	
	@Override
	public void onLeftClick(InventoryClickEvent event, Block block) {
		Farmland f = (Farmland) block.getBlockData();
		f.setMoisture(getIntValue(f.getMoisture(), f.getMaximumMoisture(), event.getClick()));
		block.setBlockData(f);
	}
	
	@Override
	public boolean canToggle() {
		return true;
	}
	
	@Override
	public void toggle(Block block) {
		toggleMoisture(block);
	}
	
	public static int getMoistLevel(Block block) {
		if(!(block.getBlockData() instanceof Farmland))
			return 0;
		Farmland f = (Farmland) block.getBlockData();
		return f.getMoisture();
	}
	
	public static void toggleMoisture(Block block) {
		if(!(block.getBlockData() instanceof Farmland))
			return;
		Farmland f = (Farmland) block.getBlockData();
		if(f.getMoisture() >= f.getMaximumMoisture())
			f.setMoisture(0);
		else
			f.setMoisture(f.getMoisture()+1);
		block.setBlockData(f);
	}

}
