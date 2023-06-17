package net.dezilla.bonetool.wandtool;

import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.Sign;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;

import net.dezilla.bonetool.ToolMain;
import net.dezilla.bonetool.ToolUser;
import net.dezilla.bonetool.Util;
import net.dezilla.bonetool.util.Locale;
import net.md_5.bungee.api.ChatColor;

public class SignEditableTool extends WandTool{

	@Override
	public boolean isServerCompatible() {
		return ToolMain.getVersionNumber() >= 20;
	}

	@Override
	public boolean isBlockCompatible(Block block) {
		return block.getState() instanceof Sign;
	}
	
	@Override
	public void onLeftClick(InventoryClickEvent event, Block block) {
		toggle(block);
	}

	@Override
	public ItemStack getIcon(Block block, ToolUser user) {
		String name = Locale.parse(user, "editSigns");
		if(block != null) {
			Sign sign = (Sign) block.getState();
			name+=": ";
			name+=(sign.isEditable() ? ChatColor.GREEN+Locale.parse(user, "true") : ChatColor.RED+Locale.parse(user, "false"));
		}
		return Util.setName(new ItemStack(Material.OAK_SIGN), name);
	}
	
	@Override
	public boolean canToggle() {
		return true;
	}
	
	@Override
	public void toggle(Block block) {
		Sign sign = (Sign) block.getState();
		sign.setEditable(!sign.isEditable());
		sign.update();
	}

}
