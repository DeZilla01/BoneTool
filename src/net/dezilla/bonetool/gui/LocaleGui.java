package net.dezilla.bonetool.gui;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import net.dezilla.bonetool.ToolUser;
import net.dezilla.bonetool.Util;
import net.dezilla.bonetool.util.Locale;

public class LocaleGui extends GuiPage{

	public LocaleGui(Player player) {
		super(6, player);
		setName(Locale.parse(ToolUser.getUser(player), "selectlanguage"));
		int col = 0;
		int row = 0;
		ItemStack defaultIcon = Util.setName(new ItemStack(Material.PAPER), Locale.parse(ToolUser.getUser(player), "default"));
		GuiItem defaultItem = new GuiItem(defaultIcon);
		defaultItem.setRun((event) -> {
			ToolUser user = ToolUser.getUser(player);
			user.setLocale("default");
			new ToolGui(player).display();
		});
		setItem(row, col++, defaultItem);
		for(File file : Locale.getLocaleFolder().listFiles()) {
			if(file.exists() && file.getName().endsWith(".yml")) {
				try {
					YamlConfiguration yaml = YamlConfiguration.loadConfiguration(new InputStreamReader(new FileInputStream(file)));
					String key = file.getName().replace(".yml", "");
					String name = yaml.getString("name");
					String credits = yaml.getString("credits");
					if(name == null || credits == null)
						continue;
					ItemStack icon = Util.getLocaleIcon(key);
					Util.setName(icon, name);
					Util.setLore(icon, ChatColor.GRAY+credits);
					GuiItem item = new GuiItem(icon);
					item.setRun((event) -> {
						ToolUser user = ToolUser.getUser(player);
						user.setLocale(key);
						new ToolGui(player).display();
					});
					setItem(row, col++, item);
					if(col>8) {
						row++;
						col = 0;
					}
				} catch (Exception e) {
					continue;
				}
			}
		}
		this.removeEmptyRows();
	}

}
