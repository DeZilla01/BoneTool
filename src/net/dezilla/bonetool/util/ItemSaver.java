package net.dezilla.bonetool.util;

import java.io.File;
import java.io.IOException;

import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import net.dezilla.bonetool.ToolMain;

//This class is only used for development purpose.
public class ItemSaver {
	
	public static void saveHeldItem(Player p) {
		File f = new File(ToolMain.getInstance().getDataFolder(), "item.yml");
		YamlConfiguration y = new YamlConfiguration();
		ItemStack i = p.getInventory().getItemInMainHand();
		y.set("1", i);
		try {
			y.save(f);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
