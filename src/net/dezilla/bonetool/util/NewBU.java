package net.dezilla.bonetool.util;

import org.bukkit.plugin.java.JavaPlugin;

import net.arcaniax.buildersutilities.BuildersUtilities;
import net.arcaniax.buildersutilities.menus.inv.InventoryManager;

public class NewBU {
	
	public static JavaPlugin getMainClass() {
		return BuildersUtilities.getInstance();
	}
	
	public static String getNoPermMsg() {
		return BuildersUtilities.MSG_NO_PERMISSION;
	}
	
	public static InventoryManager getInvManager() {
		return BuildersUtilities.getInstance().getInventoryManager();
	}

}
