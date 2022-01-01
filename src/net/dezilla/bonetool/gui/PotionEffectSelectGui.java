package net.dezilla.bonetool.gui;

import org.apache.commons.lang.StringUtils;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffectType;

import net.dezilla.bonetool.Util;
import net.dezilla.bonetool.util.PotionTypeRunnable;

public class PotionEffectSelectGui extends GuiPage{
	PotionTypeRunnable run;

	public PotionEffectSelectGui(Player player, PotionTypeRunnable run) {
		super(6, player);
		this.run = run;
		setName("Select Potion Type");
		int col = 0;
		int row = 0;
		ItemStack noneIcon = Util.setName(new ItemStack(Material.POTION), "None");
		GuiItem noneItem = new GuiItem(noneIcon);
		noneItem.setRun((event) -> run.run(null));
		setItem(row, col++, noneItem);
		for(PotionEffectType eff : PotionEffectType.values()) {
			ItemStack icon = Util.setName(Util.getPotionIcon(eff), name(eff));
			GuiItem item = new GuiItem(icon);
			item.setRun((event) -> run.run(eff));
			setItem(row, col++, item);
			if(col>8) {
				row++;
				col=0;
			}
		}
		this.removeEmptyRows();
	}
	
	private String name(PotionEffectType type) {
		String name = type.getName().replace("_", " ").toLowerCase();
		return StringUtils.capitalize(name);
	}

}
