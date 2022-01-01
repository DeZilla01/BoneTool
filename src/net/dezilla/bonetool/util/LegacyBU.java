package net.dezilla.bonetool.util;

import org.bukkit.plugin.java.JavaPlugin;

import net.arcaniax.buildersutilities.Main;
import net.arcaniax.buildersutilities.menus.inv.InventoryManager;

public class LegacyBU {
	
	public static JavaPlugin getMainClass() {
		return Main.getInstance();
	}
	
	public static String getNoPermMsg() {
		return Main.MSG_NO_PERMISSION;
	}
	
	public static InventoryManager getInvManager() {
		return Main.getInstance().getInventoryManager();
	}

}
