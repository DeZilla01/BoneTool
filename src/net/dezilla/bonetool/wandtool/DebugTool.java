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
import net.dezilla.bonetool.util.Locale;

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
	public ItemStack getIcon(Block block, ToolUser user) {
		String name = Locale.parse(user, "debugtool");
		List<String> lore = Arrays.asList(ChatColor.GRAY+Locale.parse(user, "debugtooldescription"),
				ChatColor.GRAY+Locale.parse(user, "debugtoolinstructions1"),
				ChatColor.GRAY+Locale.parse(user, "debugtoolinstructions2"));
		return Util.setLore(Util.setName(new ItemStack(Material.DEBUG_STICK), name), lore);
	}
	
	@Override
	public boolean isRightClickTool() {
		return true;
	}
	
	@Override
	public void onRightClick(PlayerInteractEvent event, Block block) {
		Player p = event.getPlayer();
		ToolUser u = ToolUser.getUser(p);
		String m = "";
		if(p.isSneaking()) {
			WandTool t = u.toggleRightClickToolFor(block);
			if(t == null) m = Locale.parse(u, "blockcannotbetoggled");
			else m = t.getIcon(block, u).getItemMeta().getDisplayName();
		} else {
			WandTool t = u.getRightClickToolFor(block);
			if(t == null) m = Locale.parse(u, "blockcannotbetoggled");
			else {
				BlockUpdateListener.protectBlock(block, 2);
				t.toggle(block);
				m = t.getIcon(block, u).getItemMeta().getDisplayName();
			}
		}
		Util.sendNotification(u, m);
	}

}
