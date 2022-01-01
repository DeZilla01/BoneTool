package net.dezilla.bonetool.wandtool;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.SculkSensor;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;

import net.dezilla.bonetool.Util;

public class SculkSensorTool extends WandTool{

	@Override
	public boolean isServerCompatible() {
		return checkClass("org.bukkit.block.SculkSensor");
	}

	@Override
	public boolean isBlockCompatible(Block block) {
		return block.getState() instanceof SculkSensor;
	}

	@Override
	public ItemStack getIcon(Block block) {
		String name = ChatColor.RESET+"Sculk Sensor Vibration Frequency";
		if(block != null) {
			SculkSensor sensor = (SculkSensor) block.getState();
			name +=": "+ChatColor.YELLOW+sensor.getLastVibrationFrequency();
		}
		ItemStack icon = Util.setName(new ItemStack(Material.SCULK_SENSOR), name);
		if(block!=null)
			Util.setLore(icon, intLore);
		return icon;
	}
	
	@Override
	public void onLeftClick(InventoryClickEvent event, Block block) {
		if(!(block.getState() instanceof SculkSensor))
			return;
		SculkSensor sensor = (SculkSensor) block.getState();
		sensor.setLastVibrationFrequency(getIntValue(sensor.getLastVibrationFrequency(), 15, event.getClick()));
		sensor.update();
	}
	
	@Override
	public boolean canToggle() {
		return true;
	}
	
	@Override
	public void toggle(Block block) {
		if(!(block.getState() instanceof SculkSensor))
			return;
		SculkSensor sensor = (SculkSensor) block.getState();
		int newValue = sensor.getLastVibrationFrequency()+1;
		if(newValue>15)
			newValue=0;
		sensor.setLastVibrationFrequency(newValue);
		sensor.update();
	}

}
