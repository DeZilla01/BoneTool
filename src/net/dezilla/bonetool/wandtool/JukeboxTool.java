package net.dezilla.bonetool.wandtool;

import java.util.Arrays;
import java.util.List;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.block.Block;
import org.bukkit.block.Jukebox;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;

import net.dezilla.bonetool.ToolMain;
import net.dezilla.bonetool.Util;

public class JukeboxTool extends WandTool{

	@Override
	public boolean isServerCompatible() {
		return checkClass("org.bukkit.block.Jukebox");
	}

	@Override
	public boolean isBlockCompatible(Block block) {
		return block.getState() instanceof Jukebox;
	}

	@Override
	public ItemStack getIcon(Block block) {
		String name = "Jukebox Item";
		Material material = Material.JUKEBOX;
		if(block != null) {
			Jukebox juke = (Jukebox) block.getState();
			if(juke.getRecord()!=null && juke.getRecord().getType()!=Material.AIR) {
				name+=": "+ChatColor.YELLOW+juke.getRecord().getType().toString();
				material = juke.getRecord().getType();
			}
		}
		List<String> lore = Arrays.asList(
				ChatColor.GRAY+"Place an item here to set",
				ChatColor.GRAY+"On the record slot.",
				ChatColor.AQUA+"Shift + Right-Click"+ChatColor.WHITE+": Stop the music.");
		return Util.setLore(Util.setName(new ItemStack(material), name), lore);
	}
	
	@Override
	public void onLeftClick(InventoryClickEvent event, Block block) {
		Jukebox j = (Jukebox) block.getState();
		if(event.getClick()==ClickType.SHIFT_RIGHT) {
			j.stopPlaying();
			return;
		}
		if(event.getCursor()==null||event.getCursor().getType()==null||event.getCursor().getType()==Material.AIR)
			return;
		try {
			j.setRecord(event.getCursor().clone());
			j.update();
			event.getWhoClicked().getServer().getScheduler().scheduleSyncDelayedTask(ToolMain.getInstance(), () -> j.stopPlaying(), 2);
		}catch(Exception e) {}
		Player p = (Player) event.getWhoClicked();
		p.playSound(p.getLocation(), Sound.BLOCK_NOTE_BLOCK_PLING, 1f, 1f);
	}

}
