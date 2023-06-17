package net.dezilla.bonetool.gui;

import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.block.Block;
import org.bukkit.block.ChiseledBookshelf;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;
import org.bukkit.inventory.ItemStack;

import net.dezilla.bonetool.ToolUser;
import net.dezilla.bonetool.Util;
import net.dezilla.bonetool.util.Locale;

public class BookshelfGui extends GuiPage{
	Block block;

	public BookshelfGui(Player player, Block block) {
		super(1, player);
		setName(Locale.parse(ToolUser.getUser(player), "bookshelfcontents"));
		this.block = block;
		addItems();
	}
	
	public void refresh() {
		this.clear();
		this.addItems();
		this.display();
	}
	
	public void addItems() {
		ChiseledBookshelf b = (ChiseledBookshelf) block.getState();
		Inventory inv = b.getInventory();
		for(int i = 0; i < 6; i++) {
			ItemStack icon = new ItemStack(Material.BARRIER);
			Util.setName(icon, Locale.parse(ToolUser.getUser(getPlayer()), "empty"));
			if(inv.getItem(i) != null) {
				icon = inv.getItem(i).clone();
			}
			GuiItem item = new GuiItem(icon);
			final int I = i;
			item.setRun((event) -> {
				if(event.getCursor() == null || event.getCursor().getItemMeta() == null)
					return;
				((InventoryHolder) block.getState()).getInventory().setItem(I, event.getCursor().clone());
				ChiseledBookshelf shelf = (ChiseledBookshelf) block.getState();
				shelf.setLastInteractedSlot(I);
				shelf.update();
				getPlayer().playSound(getPlayer().getLocation(), Sound.BLOCK_NOTE_BLOCK_PLING, 1f, 1f);
				if(shelf.getInventory().getItem(I) != null)
					item.setItem(shelf.getInventory().getItem(I));
			});
			setItem(0, i, item);
		}
	}

}
