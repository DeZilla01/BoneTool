package net.dezilla.bonetool.wandtool;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.data.type.Candle;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;

import net.dezilla.bonetool.ToolUser;
import net.dezilla.bonetool.Util;
import net.dezilla.bonetool.util.Locale;

public class CandleTool extends WandTool{

	@Override
	public boolean isServerCompatible() {
		return checkClass("org.bukkit.block.data.type.Candle");
	}

	@Override
	public boolean isBlockCompatible(Block block) {
		return block.getBlockData() instanceof Candle;
	}

	@Override
	public ItemStack getIcon(Block block, ToolUser user) {
		String name = Locale.parse(user, "candleamount");
		if(block!=null) {
			name += ": " + ChatColor.YELLOW + getCandleAmount(block);
		}
		ItemStack icon = Util.setName(new ItemStack(Material.CANDLE), name);
		if(block!=null)
			Util.setLore(icon, getIntLore(user));
		return icon;
	}
	
	@Override
	public void onLeftClick(InventoryClickEvent event, Block block) {
		Candle c = (Candle) block.getBlockData();
		int value = getIntValue(c.getCandles(), c.getMaximumCandles(), event.getClick());
		c.setCandles(value == 0 ? 1 : value);
		block.setBlockData(c);
	}
	
	@Override
	public boolean canToggle() {
		return true;
	}
	
	@Override
	public void toggle(Block block) {
		toggleCandle(block);
	}
	
	public static int getCandleAmount(Block block) {
		if(!(block.getBlockData() instanceof Candle))
			return 0;
		Candle c = (Candle) block.getBlockData();
		return c.getCandles();
	}
	
	public static void toggleCandle(Block block) {
		if(!(block.getBlockData() instanceof Candle))
			return;
		
		Candle candle = (Candle) block.getBlockData();
		if(candle.getCandles() >= candle.getMaximumCandles())
			candle.setCandles(1);
		else
			candle.setCandles(candle.getCandles()+1);
		block.setBlockData(candle);
	}

}
