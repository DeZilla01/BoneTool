package net.dezilla.bonetool.gui;

import org.bukkit.Art;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import net.dezilla.bonetool.ToolUser;
import net.dezilla.bonetool.Util;
import net.dezilla.bonetool.util.Locale;

public class PaintingGui extends GuiPage{

	public PaintingGui(Player player) {
		super(6, player);
		setName(Locale.parse(ToolUser.getUser(player), "paintings"));
		int row = 0;
		int col = 0;
		for(Art art : Art.values()) {
			GuiItem item = new GuiItem(Util.getPaintingIcon(art));
			item.setRun((event) -> {
				ItemStack i = Util.setName(new ItemStack(Material.PAINTING), art.toString());
				event.getWhoClicked().getInventory().addItem(i);
			});
			setItem(row, col++, item);
			if(col>8) {
				row+=1;
				col=0;
			}
		}
		this.removeEmptyRows();
	}

}
