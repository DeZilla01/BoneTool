package net.dezilla.bonetool.gui;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataType;

public class GuiPage {
	//Static stuff
	private static List<GuiPage> PAGES = new ArrayList<GuiPage>();
	
	public static GuiPage getPage(Player player){
		clean();
		for(GuiPage page : PAGES) {
			if(page.getPlayer().equals(player))
				return page;
		}
		return null;
	}
	
	private static void clean() {
		List<GuiPage> toRemove = new ArrayList<GuiPage>();
		for(GuiPage page : PAGES) {
			if(!page.getPlayer().isOnline() 
					|| page.getPlayer().getOpenInventory() == null 
					|| page.getPlayer().getOpenInventory().getTopInventory() == null 
					|| !page.getPlayer().getOpenInventory().getTopInventory().equals(page.getInventory())) {
				toRemove.add(page);
			}
		}
		for(GuiPage page : toRemove)
			PAGES.remove(page);
	}
	
	
	//GuiPage Object
	private int size;
	private Player player;
	private String name = "";
	private List<GuiItem[]> rows = new ArrayList<GuiItem[]>();
	private Inventory inventory;
	
	public GuiPage(int size, Player player) {
		this.size = size;
		this.player = player;
		for(int i = 0 ; i < size ; i++) {
			GuiItem[] is = new GuiItem[9];
			rows.add(is);
		}
		PAGES.add(this);
	}
	
	public void setItem(int row, int col, GuiItem item) {
		GuiItem [] is = rows.get(row);
		is[col] = item;
		rows.set(row, is);
	}
	
	public GuiItem getItem(int row, int col) {
		return rows.get(row)[col];
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public String getName() {
		return name;
	}
	
	public Player getPlayer() {
		return player;
	}
	
	public Inventory getInventory() {
		return inventory;
	}
	
	public void multiPageFix() {
		PAGES.add(this);
	}
	
	public GuiItem getGuiItem(ItemStack item) {
		ItemMeta meta = item.getItemMeta();
		if(!meta.getPersistentDataContainer().has(GuiItem.getKey(), PersistentDataType.INTEGER))
			return null;
		int id = meta.getPersistentDataContainer().get(GuiItem.getKey(), PersistentDataType.INTEGER);
		for(GuiItem[] array : rows) {
			for(GuiItem guiItem : array) {
				if(guiItem == null)
					continue;
				if(guiItem.getId() == id)
					return guiItem;
			}
		}
		return null;
	}
	
	public void display() {
		Inventory inv = Bukkit.getServer().createInventory(new GuiHolder(), size*9, name);
		int i = 0;
		for(GuiItem[] is : rows) {
			for(GuiItem item : is) {
				if(item != null)
					inv.setItem(i, item.getItem());
				i++;
			}
		}
		this.inventory = inv;
		player.openInventory(inv);
	}
	
	public void clear() {
		for(GuiItem[] l : rows) {
			for(int i = 0; i < l.length ; i++) {
				l[i] = null;
			}
		}
	}
	
	public void removeEmptyRows() {
		List<GuiItem[]> toRemove = new ArrayList<GuiItem[]>();
		for(GuiItem[] row : rows) {
			boolean empty = true;
			for(GuiItem i : row) {
				if(i!=null)
					empty = false;
			}
			if(empty) {
				toRemove.add(row);
			}
		}
		for(GuiItem[] row : toRemove) {
			if(size>1) {
				rows.remove(row);
				size--;
			}
		}
	}
	
}
