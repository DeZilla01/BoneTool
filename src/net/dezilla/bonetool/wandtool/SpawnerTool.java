package net.dezilla.bonetool.wandtool;

import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.CreatureSpawner;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;

import net.dezilla.bonetool.ToolUser;
import net.dezilla.bonetool.Util;
import net.dezilla.bonetool.gui.SpawnerGui;
import net.dezilla.bonetool.util.Locale;

public class SpawnerTool extends WandTool{

	@Override
	public boolean isServerCompatible() {
		return checkClass("org.bukkit.block.CreatureSpawner");
	}

	@Override
	public boolean isBlockCompatible(Block block) {
		return block.getState() instanceof CreatureSpawner;
	}

	@Override
	public ItemStack getIcon(Block block, ToolUser user) {
		String name = Locale.parse(user, "spawnereditor");
		ItemStack icon = new ItemStack(Material.SPAWNER);
		return Util.setName(icon, name);
	}
	
	@Override
	public void onLeftClick(InventoryClickEvent event, Block block) {
		Player p = (Player) event.getWhoClicked();
		new SpawnerGui(p, block).display();
	}
	
	@Override
	public boolean hasGui() {
		return true;
	}
	
	@Override
	public void openGui(Player player, Block block) {
		new SpawnerGui(player, block).display();
	}

}
