package net.dezilla.bonetool.wandtool;

import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.Sign;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;

import net.dezilla.bonetool.Util;

public class SignTool extends WandTool{

	@Override
	public boolean isServerCompatible() {
		return checkClass("org.bukkit.block.Sign");
	}

	@Override
	public boolean isBlockCompatible(Block block) {
		return block.getState() instanceof Sign;
	}

	@Override
	public ItemStack getIcon(Block block) {
		String name = "Edit Sign";
		return Util.setName(new ItemStack(Material.OAK_SIGN), name);
	}
	
	@Override
	public void onLeftClick(InventoryClickEvent event, Block block) {
		Player p = (Player) event.getWhoClicked();
		Util.editSign(p, block);
	}

}
