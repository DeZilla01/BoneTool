package net.dezilla.bonetool.wandtool;

import java.util.Arrays;
import java.util.List;

import org.bukkit.ChatColor;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;

public abstract class WandTool {
	
	public abstract boolean isServerCompatible();
	
	public abstract boolean isBlockCompatible(Block block);
	
	public ItemStack getIcon() {return getIcon(null);}
	public abstract ItemStack getIcon(Block block);
	
	public void onLeftClick(InventoryClickEvent event, Block block) {};
	
	public void onRightClick(PlayerInteractEvent event, Block block) {};
	
	public boolean isRightClickTool() {return false;}
	
	public boolean canToggle() {return false;}
	
	public void toggle(Block block) {}
	
	public boolean hasGui() {return false;}
	
	public void openGui(Player player, Block block) {}
	
	List<String> intLore = Arrays.asList(
			ChatColor.AQUA+"Left Click: "+ChatColor.WHITE+"-1",
			ChatColor.AQUA+"Right Click: "+ChatColor.WHITE+"+1",
			ChatColor.AQUA+"Shift + Left Click: "+ChatColor.WHITE+"Minimum (0)",
			ChatColor.AQUA+"Shift + Right Click: "+ChatColor.WHITE+"Maximum");
	
	int getIntValue(int current, int max, ClickType click) {
		int lvl = current;
		if(click == ClickType.LEFT)
			lvl-=1;
		else if(click == ClickType.RIGHT)
			lvl+=1;
		
		if(lvl<0 || click == ClickType.SHIFT_LEFT)
			lvl = 0;
		else if(lvl > max || click == ClickType.SHIFT_RIGHT)
			lvl = max;
		return lvl;
	}
	
	boolean checkClass(String classPkg) {
		try {
			Class.forName(classPkg);
			return true;
		}catch(Exception e) {
			return false;
		}
	}

}
