package net.dezilla.bonetool.wandtool;

import org.bukkit.Material;
import org.bukkit.block.Beacon;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;

import net.dezilla.bonetool.Util;
import net.dezilla.bonetool.gui.BeaconGui;

public class BeaconTool extends WandTool{

	@Override
	public boolean isServerCompatible() {
		return checkClass("org.bukkit.block.Beacon");
	}

	@Override
	public boolean isBlockCompatible(Block block) {
		return block.getState() instanceof Beacon;
	}

	@Override
	public ItemStack getIcon(Block block) {
		ItemStack icon = Util.setName(new ItemStack(Material.BEACON), "Beacon Potion Effect");
		return icon;
	}
	
	@Override
	public void onLeftClick(InventoryClickEvent event, Block block) {
		if(!(event.getWhoClicked() instanceof Player) || !isBlockCompatible(block))
			return;
		Player p = (Player) event.getWhoClicked();
		new BeaconGui(p, block).display();;
	}
	
	@Override
	public boolean hasGui() {
		return true;
	}
	
	@Override
	public void openGui(Player player, Block block) {
		new BeaconGui(player, block).display();
	}

}
