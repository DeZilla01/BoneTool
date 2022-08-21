package net.dezilla.bonetool.wandtool;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.data.type.Cake;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;

import net.dezilla.bonetool.ToolUser;
import net.dezilla.bonetool.Util;
import net.dezilla.bonetool.util.Locale;

public class CakeTool extends WandTool{

	@Override
	public boolean isServerCompatible() {
		return checkClass("org.bukkit.block.data.type.Cake");
	}

	@Override
	public boolean isBlockCompatible(Block block) {
		return block.getBlockData() instanceof Cake;
	}

	@Override
	public ItemStack getIcon(Block block, ToolUser user) {
		String name = Locale.parse(user, "cakebites");
		if(block != null && block.getBlockData() instanceof Cake) {
			Cake cake = (Cake) block.getBlockData();
			name+=": "+ChatColor.YELLOW+cake.getBites();
		}
		ItemStack icon = Util.setName(new ItemStack(Material.CAKE), name);
		if(block!=null) {
			Util.setLore(icon, getIntLore(user));
		}
		return icon;
	}
	
	@Override
	public void onLeftClick(InventoryClickEvent event, Block block) {
		if(!(block.getBlockData() instanceof Cake))
			return;
		Cake cake = (Cake) block.getBlockData();
		cake.setBites(getIntValue(cake.getBites(), cake.getMaximumBites(), event.getClick()));
		block.setBlockData(cake);
	}
	
	@Override
	public boolean canToggle() {
		return true;
	}
	
	@Override
	public void toggle(Block block) {
		toggleCakeBites(block);
	}
	
	public static void toggleCakeBites(Block block) {
		if(!(block.getBlockData() instanceof Cake))
			return;
		
		Cake cake = (Cake) block.getBlockData();
		int bites = cake.getBites();
		if(bites >= cake.getMaximumBites())
			cake.setBites(0);
		else
			cake.setBites(bites+1);
		block.setBlockData(cake);
	}

}
