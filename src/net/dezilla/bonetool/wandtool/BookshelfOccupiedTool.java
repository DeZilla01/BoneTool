package net.dezilla.bonetool.wandtool;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.data.type.ChiseledBookshelf;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;

import net.dezilla.bonetool.ToolUser;
import net.dezilla.bonetool.Util;
import net.dezilla.bonetool.util.Locale;

public class BookshelfOccupiedTool extends WandTool {
	private Map<ToolUser, Boolean> secondRow = new HashMap<ToolUser, Boolean>();

	@Override
	public boolean isServerCompatible() {
		return checkClass("org.bukkit.block.data.type.ChiseledBookshelf");
	}

	@Override
	public boolean isBlockCompatible(Block block) {
		return block.getBlockData() instanceof ChiseledBookshelf;
	}
	
	@Override
	public void onLeftClick(InventoryClickEvent event, Block block) {
		ToolUser u = ToolUser.getUser((Player) event.getWhoClicked());
		if(!secondRow.containsKey(u))
			secondRow.put(u, false);
		boolean row2 = secondRow.get(u);
		ChiseledBookshelf b = (ChiseledBookshelf) block.getBlockData();
		switch(event.getClick()) {
			case LEFT:{
				if(row2)
					b.setSlotOccupied(3, !b.isSlotOccupied(3));
				else
					b.setSlotOccupied(0, !b.isSlotOccupied(0));
				break;
			}
			case RIGHT:{
				if(row2)
					b.setSlotOccupied(4, !b.isSlotOccupied(4));
				else
					b.setSlotOccupied(1, !b.isSlotOccupied(1));
				break;
			}
			case SHIFT_LEFT:{
				if(row2)
					b.setSlotOccupied(5, !b.isSlotOccupied(5));
				else
					b.setSlotOccupied(2, !b.isSlotOccupied(2));
				break;
			}
			case SHIFT_RIGHT:{
				secondRow.put(u, !row2);
				break;
			}
			default:{}
		}
		block.setBlockData(b);
	}

	@Override
	public ItemStack getIcon(Block block, ToolUser user) {
		String name = Locale.parse(user, "bookshelfoccupied");
		ItemStack icon = new ItemStack(Material.CHISELED_BOOKSHELF);
		if(block != null) {
			ChiseledBookshelf b = (ChiseledBookshelf) block.getBlockData();
			List<String> lore1 =  Arrays.asList(
					ChatColor.AQUA+Locale.parse(user, "leftclick")+": "+ChatColor.WHITE+Locale.parse(user, "slot")+" 1: "+(b.isSlotOccupied(0) ? ChatColor.GREEN+Locale.parse(user, "true") : ChatColor.RED+Locale.parse(user, "false")),
					ChatColor.AQUA+Locale.parse(user, "rightclick")+": "+ChatColor.WHITE+Locale.parse(user, "slot")+" 2: "+(b.isSlotOccupied(1) ? ChatColor.GREEN+Locale.parse(user, "true") : ChatColor.RED+Locale.parse(user, "false")),
					ChatColor.AQUA+Locale.parse(user, "shift")+" + "+Locale.parse(user, "leftclick")+": "+ChatColor.WHITE+Locale.parse(user, "slot")+" 3: "+(b.isSlotOccupied(2) ? ChatColor.GREEN+Locale.parse(user, "true") : ChatColor.RED+Locale.parse(user, "false")),
					ChatColor.AQUA+Locale.parse(user, "shift")+" + "+Locale.parse(user, "rightclick")+": "+ChatColor.WHITE+Locale.parse(user, "switchtosecondrow"));
			List<String> lore2 =  Arrays.asList(
					ChatColor.AQUA+Locale.parse(user, "leftclick")+": "+ChatColor.WHITE+Locale.parse(user, "slot")+": 4 "+(b.isSlotOccupied(3) ? ChatColor.GREEN+Locale.parse(user, "true") : ChatColor.RED+Locale.parse(user, "false")),
					ChatColor.AQUA+Locale.parse(user, "rightclick")+": "+ChatColor.WHITE+Locale.parse(user, "slot")+": 5 "+(b.isSlotOccupied(4) ? ChatColor.GREEN+Locale.parse(user, "true") : ChatColor.RED+Locale.parse(user, "false")),
					ChatColor.AQUA+Locale.parse(user, "shift")+" + "+Locale.parse(user, "leftclick")+": "+ChatColor.WHITE+Locale.parse(user, "slot")+" 6: "+(b.isSlotOccupied(5) ? ChatColor.GREEN+Locale.parse(user, "true") : ChatColor.RED+Locale.parse(user, "false")),
					ChatColor.AQUA+Locale.parse(user, "shift")+" + "+Locale.parse(user, "rightclick")+": "+ChatColor.WHITE+Locale.parse(user, "switchtofirstrow"));
			if(!secondRow.containsKey(user))
				secondRow.put(user, false);
			if(secondRow.get(user))
				Util.setLore(icon, lore2);
			else
				Util.setLore(icon, lore1);
		}
		return Util.setName(icon, name);
	}

}
