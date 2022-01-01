package net.dezilla.bonetool.wandtool;

import java.util.Arrays;
import java.util.List;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.block.Block;
import org.bukkit.block.BlockState;
import org.bukkit.block.Lockable;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;

import net.dezilla.bonetool.Util;

public class LockTool extends WandTool{

	@Override
	public boolean isServerCompatible() {
		return checkClass("org.bukkit.block.Lockable");
	}

	@Override
	public boolean isBlockCompatible(Block block) {
		return block.getState() instanceof Lockable;
	}

	@Override
	public ItemStack getIcon(Block block) {
		String name = "Lock";
		if(block != null) {
			Lockable l = (Lockable) block.getState();
			name+=": "+(l.isLocked()?ChatColor.YELLOW+l.getLock():""+ChatColor.YELLOW+ChatColor.ITALIC+"Unlocked");
		}
		List<String> lore = Arrays.asList(ChatColor.GRAY+"Set a password on the container.",
				ChatColor.GRAY+"An item with the password as it's name ", ChatColor.GRAY+"will be required to open the container.",
				ChatColor.GRAY+"To set a password, place an item with", ChatColor.GRAY+"the desired password here.",
				ChatColor.AQUA+"Shift + Right-Click"+ChatColor.WHITE+": Remove password.");
		return Util.setLore(Util.setName(new ItemStack(Material.IRON_NUGGET), name), lore);
	}
	
	@Override
	public void onLeftClick(InventoryClickEvent event, Block block) {
		Lockable l = (Lockable) block.getState();
		if(event.getClick()==ClickType.SHIFT_RIGHT) {
			l.setLock(null);
			BlockState s = (BlockState) l;
			s.update();
			return;
		}
		if(event.getCursor()==null||event.getCursor().getType()==null||event.getCursor().getType()==Material.AIR)
			return;
		String str = event.getCursor().getItemMeta().getDisplayName();
		if(str==null || str.isEmpty())
			return;
		l.setLock(str);
		BlockState s = (BlockState) l;
		s.update();
		Player p = (Player) event.getWhoClicked();
		p.playSound(p.getLocation(), Sound.BLOCK_NOTE_BLOCK_PLING, 1f, 1f);
	}

}
