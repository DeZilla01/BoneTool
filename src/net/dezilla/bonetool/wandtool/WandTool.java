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

import net.dezilla.bonetool.ToolUser;
import net.dezilla.bonetool.util.Locale;

public abstract class WandTool {
	
	public abstract boolean isServerCompatible();
	
	public abstract boolean isBlockCompatible(Block block);
	
	public ItemStack getIcon() {return getIcon(null, null);}
	public abstract ItemStack getIcon(Block block, ToolUser user);
	
	public void onLeftClick(InventoryClickEvent event, Block block) {};
	
	public void onRightClick(PlayerInteractEvent event, Block block) {};
	
	public boolean isRightClickTool() {return false;}
	
	public boolean canToggle() {return false;}
	
	public void toggle(Block block) {}
	
	public boolean hasGui() {return false;}
	
	public void openGui(Player player, Block block) {}
	
	protected List<String> getIntLore(ToolUser user){
		return Arrays.asList(
				ChatColor.AQUA+Locale.parse(user, "leftclick")+": "+ChatColor.WHITE+"-1",
				ChatColor.AQUA+Locale.parse(user, "rightclick")+": "+ChatColor.WHITE+"+1",
				ChatColor.AQUA+Locale.parse(user, "shift")+" + "+Locale.parse(user, "leftclick")+": "+ChatColor.WHITE+"Minimum (0)",
				ChatColor.AQUA+Locale.parse(user, "shift")+" + "+Locale.parse(user, "rightclick")+": "+ChatColor.WHITE+"Maximum");
	}
	
	/*protected List<String> intLore = Arrays.asList(
			ChatColor.AQUA+Locale.parse("leftclick")+": "+ChatColor.WHITE+"-1",
			ChatColor.AQUA+Locale.parse("rightclick")+": "+ChatColor.WHITE+"+1",
			ChatColor.AQUA+Locale.parse("shift")+" + "+Locale.parse("leftclick")+": "+ChatColor.WHITE+"Minimum (0)",
			ChatColor.AQUA+Locale.parse("shift")+" + "+Locale.parse("rightclick")+": "+ChatColor.WHITE+"Maximum");*/
	
	protected int getIntValue(int current, int max, ClickType click) {
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
	
	protected boolean checkClass(String classPkg) {
		try {
			Class.forName(classPkg);
			return true;
		}catch(Exception e) {
			return false;
		}
	}

}
