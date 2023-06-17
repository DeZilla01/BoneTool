package net.dezilla.bonetool.wandtool;

import org.bukkit.block.Block;
import org.bukkit.block.EndGateway;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;

import net.dezilla.bonetool.ToolUser;
import net.dezilla.bonetool.Util;
import net.dezilla.bonetool.gui.ColorSelectGui;
import net.dezilla.bonetool.gui.EndGatewayGui;
import net.dezilla.bonetool.util.Locale;

public class EndGatewayTool extends WandTool{

	@Override
	public boolean isServerCompatible() {
		return checkClass("org.bukkit.block.EndGateway");
	}

	@Override
	public boolean isBlockCompatible(Block block) {
		return block.getState() instanceof EndGateway;
	}
	
	@Override
	public void onLeftClick(InventoryClickEvent event, Block block) {
		new EndGatewayGui((Player) event.getWhoClicked(), block).display();
	}

	@Override
	public ItemStack getIcon(Block block, ToolUser user) {
		ItemStack icon = Util.getEndGateway();
		String name = Locale.parse(user, "endgateway");
		return Util.setName(icon, name);
	}
	
	@Override
	public boolean hasGui() {
		return true;
	}
	
	@Override
	public void openGui(Player player, Block block) {
		new EndGatewayGui(player, block).display();
	}

}
