package net.dezilla.bonetool.wandtool;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.block.Block;
import org.bukkit.block.BrushableBlock;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;

import net.dezilla.bonetool.ToolUser;
import net.dezilla.bonetool.Util;
import net.dezilla.bonetool.util.Locale;

public class BrushableItemTool extends WandTool{
	
	@Override
	public boolean isServerCompatible() {
		return checkClass("org.bukkit.block.BrushableBlock");
	}

	@Override
	public boolean isBlockCompatible(Block block) {
		return block.getState() instanceof BrushableBlock;
	}
	
	@Override
	public void onLeftClick(InventoryClickEvent event, Block block) {
		BrushableBlock b = (BrushableBlock) block.getState();
		if(event.getCursor() != null && event.getCursor().getItemMeta() != null) {
			b.setItem(event.getCursor().clone());
			Player p = (Player) event.getWhoClicked();
			p.playSound(p.getLocation(), Sound.BLOCK_NOTE_BLOCK_PLING, 1f, 1f);
			b.update();
		}
	}

	@Override
	public ItemStack getIcon(Block block, ToolUser user) {
		String name = Locale.parse(user, "brushableitem");
		ItemStack icon = new ItemStack(Material.SUSPICIOUS_SAND);
		if(block != null) {
			BrushableBlock b = (BrushableBlock) block.getState();
			if(b.getItem() != null && b.getItem().getItemMeta() != null)
				icon = b.getItem().clone();
			List<String> lore = new ArrayList<String>();
			lore.add(ChatColor.GRAY+"Place an item here to ");
			lore.add(ChatColor.GRAY+"place it inside the block.");
			Util.setLore(icon, lore);
		}
		Util.setName(icon, name);
		return icon;
	}

}
