package net.dezilla.bonetool.wandtool;

import java.util.Arrays;
import java.util.List;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;

import net.dezilla.bonetool.ToolUser;
import net.dezilla.bonetool.Util;
import net.dezilla.bonetool.listener.BlockUpdateListener;

public class DebugTool extends WandTool{

	@Override
	public boolean isServerCompatible() {
		return true;
	}

	@Override
	public boolean isBlockCompatible(Block block) {
		return false;
	}

	@Override
	public ItemStack getIcon(Block block) {
		String name = "Debug Tool";
		List<String> lore = Arrays.asList(ChatColor.GRAY+"A similar tool to the vanilla's debug stick",
				ChatColor.GRAY+"Right Click to toggle property",
				ChatColor.GRAY+"Shift + Right Click to switch property");
		return Util.setLore(Util.setName(new ItemStack(Material.DEBUG_STICK), name), lore);
	}
	
	@Override
	public boolean isRightClickTool() {
		return true;
	}
	
	final static String noToolMsg = "Block cannot be toggled";
	
	@Override
	public void onRightClick(PlayerInteractEvent event, Block block) {
		Player p = event.getPlayer();
		ToolUser u = ToolUser.getUser(p);
		String m = "";
		if(p.isSneaking()) {
			WandTool t = u.toggleRightClickToolFor(block);
			if(t == null) m = noToolMsg;
			else m = t.getIcon(block).getItemMeta().getDisplayName();
		} else {
			WandTool t = u.getRightClickToolFor(block);
			if(t == null) m = noToolMsg;
			else {
				BlockUpdateListener.protectBlock(block, 2);
				t.toggle(block);
				m = t.getIcon(block).getItemMeta().getDisplayName();
			}
		}
		Util.sendNotification(u, m);
	}

}
