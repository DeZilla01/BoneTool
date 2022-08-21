package net.dezilla.bonetool.gui;

import org.bukkit.Material;
import org.bukkit.block.data.BlockData;
import org.bukkit.block.data.Levelled;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.BlockDataMeta;
import org.bukkit.inventory.meta.ItemMeta;

import net.dezilla.bonetool.ToolUser;
import net.dezilla.bonetool.util.Locale;

public class LightBlockGui extends GuiPage{
	
	public LightBlockGui(Player player) {
		super(2, player);
		setName(Locale.parse(ToolUser.getUser(player), "lightblock"));
		int row = 0;
		int col = 0;
		for(int i = 0; i < 16 ; i++) {
			ItemStack icon = new ItemStack(Material.LIGHT);
		    ItemMeta meta = icon.getItemMeta();
		    BlockData data = Material.LIGHT.createBlockData();
		    ((Levelled) data).setLevel(i);
		    ((BlockDataMeta) meta).setBlockData(data);
		    icon.setItemMeta(meta);
			GuiItem item = new GuiItem(icon);
			item.setRun((event) -> {
				event.getWhoClicked().getInventory().addItem(icon);
			});
			setItem(row, col++, item);
			if(col>8) {
				row+=1;
				col=1;
			}
		}
	}

}
