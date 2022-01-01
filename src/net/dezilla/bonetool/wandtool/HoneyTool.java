package net.dezilla.bonetool.wandtool;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.data.type.Beehive;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;

import net.dezilla.bonetool.Util;

public class HoneyTool extends WandTool{

	@Override
	public boolean isServerCompatible() {
		return checkClass("org.bukkit.block.data.type.Beehive");
	}

	@Override
	public boolean isBlockCompatible(Block block) {
		return block.getBlockData() instanceof Beehive;
	}

	@Override
	public ItemStack getIcon(Block block) {
		String name = "Honey Storage";
		if(block!=null) {
			name += ": " + ChatColor.YELLOW + getHoneyLevel(block);
		}
		ItemStack icon = Util.setName(new ItemStack(Material.HONEYCOMB), name);
		if(block!=null)
			Util.setLore(icon, intLore);
		return icon;
	}
	
	@Override
	public void onLeftClick(InventoryClickEvent event, Block block) {
		Beehive h = (Beehive) block.getBlockData();
		h.setHoneyLevel(getIntValue(h.getHoneyLevel(), h.getMaximumHoneyLevel(), event.getClick()));
		block.setBlockData(h);
	}
	
	@Override
	public boolean canToggle() {
		return true;
	}
	
	@Override
	public void toggle(Block block) {
		toggleHoney(block);
	}
	
	public static int getHoneyLevel(Block block) {
		if(!(block.getBlockData() instanceof Beehive))
			return 0;
		Beehive h = (Beehive) block.getBlockData();
		return h.getHoneyLevel();
	}
	
	public static void toggleHoney(Block block) {
		if(!(block.getBlockData() instanceof Beehive))
			return;
		
		Beehive hive = (Beehive) block.getBlockData();
		if(hive.getHoneyLevel() >= hive.getMaximumHoneyLevel())
			hive.setHoneyLevel(0);
		else
			hive.setHoneyLevel(hive.getHoneyLevel()+1);
		block.setBlockData(hive);
	}

}
