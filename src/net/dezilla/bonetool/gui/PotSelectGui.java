package net.dezilla.bonetool.gui;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import net.dezilla.bonetool.ToolUser;
import net.dezilla.bonetool.util.Locale;
import net.dezilla.bonetool.wandtool.PotTool;

public class PotSelectGui extends GuiPage{

	public PotSelectGui(Player player, Block block) {
		super(6, player);
		setName(Locale.parse(ToolUser.getUser(player), "selectflower"));
		int row = 0;
		int col = 0;
		for(Material m : getMaterials()) {
			ItemStack icon = PotTool.getItemIcon(m, ToolUser.getUser(player));
			GuiItem item = new GuiItem(icon);
			item.setRun((event) -> {
				block.setType(m);
				event.getWhoClicked().closeInventory();
			});
			setItem(row, col++, item);
			if(col>8) {
				row++;
				col=0;
			}
		}
		this.removeEmptyRows();
	}
	
	private static List<Material> getMaterials() {
		List<Material> mat = new ArrayList<Material>();
		mat.add(Material.FLOWER_POT);
		for(Material m : Material.values()) {
			if(m.toString().startsWith("POTTED_"))
				mat.add(m);
		}
		return mat;
	}

}
