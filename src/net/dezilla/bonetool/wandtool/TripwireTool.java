package net.dezilla.bonetool.wandtool;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.data.type.Tripwire;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;

import net.dezilla.bonetool.ToolUser;
import net.dezilla.bonetool.Util;
import net.dezilla.bonetool.util.Locale;

public class TripwireTool extends WandTool{

	@Override
	public boolean isServerCompatible() {
		return checkClass("org.bukkit.block.data.type.Tripwire");
	}

	@Override
	public boolean isBlockCompatible(Block block) {
		return block.getBlockData() instanceof Tripwire;
	}

	@Override
	public ItemStack getIcon(Block block, ToolUser user) {
		String name = Locale.parse(user, "tripwiredisarmed");
		if(block!=null) {
			Tripwire t = (Tripwire) block.getBlockData();
			name+=": "+(t.isDisarmed()?ChatColor.GREEN+Locale.parse(user, "true"):ChatColor.RED+Locale.parse(user, "false"));
		}
		return Util.setName(new ItemStack(Material.STRING), name);
	}
	
	@Override
	public void onLeftClick(InventoryClickEvent event, Block block) {
		toggleTripwire(block);
	}
	
	@Override
	public boolean canToggle() {
		return true;
	}
	
	@Override
	public void toggle(Block block) {
		toggleTripwire(block);
	}
	
	public static void toggleTripwire(Block block) {
		if(!(block.getBlockData() instanceof Tripwire))
			return;
		Tripwire t = (Tripwire) block.getBlockData();
		t.setDisarmed(!t.isDisarmed());
		block.setBlockData(t);
	}

}
