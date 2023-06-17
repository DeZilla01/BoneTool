package net.dezilla.bonetool.wandtool;

import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.CreatureSpawner;
import org.bukkit.entity.EntityType;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;

import net.dezilla.bonetool.ToolMain;
import net.dezilla.bonetool.ToolUser;
import net.dezilla.bonetool.Util;

//Ignore this, I test shit sometimes
public class TestTool extends WandTool{

	@Override
	public boolean isServerCompatible() {
		return ToolMain.getVersionNumber() >= 20;
	}

	@Override
	public boolean isBlockCompatible(Block block) {
		return block.getState() instanceof CreatureSpawner;
	}
	
	@Override
	public void onLeftClick(InventoryClickEvent event, Block block) {
		CreatureSpawner s = (CreatureSpawner) block.getState();
		s.setSpawnedType(EntityType.UNKNOWN);
		s.update();
	}

	@Override
	public ItemStack getIcon(Block block, ToolUser user) {
		String name = "TEST";
		return Util.setName(new ItemStack(Material.STONE), name);
	}

}
