package net.dezilla.bonetool.gui;

import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;

import net.dezilla.bonetool.util.InventoryRunnable;

public class GuiItem {
	
	private ItemStack item;
	private InventoryRunnable runnable= null;
	
	public GuiItem(ItemStack item) {
		this.item = item;
	}
	
	public GuiItem setRun(InventoryRunnable runnable) {
		this.runnable = runnable;
		return this;
	}
	
	public void run(InventoryClickEvent event) {
		if(runnable != null) {
			runnable.run(event);
		}
	}
	
	public GuiItem setItem(ItemStack item) {
		this.item = item;
		return this;
	}
	
	public ItemStack getItem() {
		return item;
	}
	
	public boolean isDead() {
		if(item.getAmount()==0)
			return true;
		return false;
	}
}
