package net.dezilla.bonetool.wandtool;

import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.data.type.BrewingStand;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;

import net.dezilla.bonetool.ToolUser;
import net.dezilla.bonetool.Util;
import net.dezilla.bonetool.gui.BrewingStandGui;
import net.dezilla.bonetool.util.Locale;

public class BrewingStandTool extends WandTool{

	@Override
	public boolean isServerCompatible() {
		return checkClass("org.bukkit.block.data.type.BrewingStand");
	}

	@Override
	public boolean isBlockCompatible(Block block) {
		return block.getBlockData() instanceof BrewingStand;
	}

	@Override
	public ItemStack getIcon(Block block, ToolUser user) {
		String name = Locale.parse(user, "brewstandpotions");
		return Util.setName(new ItemStack(Material.BREWING_STAND), name);
	}
	
	@Override
	public void onLeftClick(InventoryClickEvent event, Block block) {
		Player p = (Player) event.getWhoClicked();
		new BrewingStandGui(p, block).display();
	}
	
	@Override
	public boolean hasGui() {
		return true;
	}
	
	@Override
	public void openGui(Player player, Block block) {
		new BrewingStandGui(player, block).display();
	}

}
