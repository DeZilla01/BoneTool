package net.dezilla.bonetool.wandtool;

import java.util.Arrays;
import java.util.List;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.data.type.SculkSensor;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;

import net.dezilla.bonetool.Util;

public class SculkSensorPhaseTool extends WandTool {
	
	@Override
	public boolean isServerCompatible() {
		return checkClass("org.bukkit.block.data.type.SculkSensor");
	}

	@Override
	public boolean isBlockCompatible(Block block) {
		return block.getBlockData() instanceof SculkSensor;
	}

	@Override
	public ItemStack getIcon(Block block) {
		String name = "Sculk Sensor Phase";
		if(block!=null) {
			SculkSensor sensor = (SculkSensor) block.getBlockData();
			name+=": "+ChatColor.YELLOW+sensor.getPhase().toString();
		}
		return Util.setName(new ItemStack(Material.SCULK_SENSOR), name);
	}
	
	@Override
	public void onLeftClick(InventoryClickEvent event, Block block) {
		toggleSensor(block);
	}
	
	@Override
	public boolean canToggle() {
		return true;
	}
	
	@Override
	public void toggle(Block block) {
		toggleSensor(block);
	}
	
	public static void toggleSensor(Block block) {
		if(!(block.getBlockData() instanceof SculkSensor))
			return;
		SculkSensor sensor = (SculkSensor) block.getBlockData();
		List<SculkSensor.Phase> list = Arrays.asList(SculkSensor.Phase.values());
		if(list.indexOf(sensor.getPhase())+1>=list.size())
			sensor.setPhase(list.get(0));
		else
			sensor.setPhase(list.get(list.indexOf(sensor.getPhase())+1));
		block.setBlockData(sensor);
	}

}
