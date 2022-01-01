package net.dezilla.bonetool.gui;

import org.apache.commons.lang.StringUtils;
import org.bukkit.DyeColor;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import net.dezilla.bonetool.listener.BlockUpdateListener;
import net.dezilla.bonetool.ToolUser;
import net.dezilla.bonetool.Util;
import net.dezilla.bonetool.wandtool.ColorTool;

public class ColorSelectGui extends GuiPage{

	public ColorSelectGui(Player player, Block block) {
		super(2, player);
		addItems(block);
	}
	
	public ColorSelectGui(Player player) {
		super(2, player);
		addItems(null);
	}
	
	private void addItems(Block block) {
		int row = 0;
		int col = 0;
		setName("Select a color");
		for(DyeColor dye : DyeColor.values()) {
			ItemStack icon = Util.setName(new ItemStack(ColorTool.getColorMaterial(dye)), dye+"");
			GuiItem item = new GuiItem(icon).setRun((e) -> {
				if(block != null) {
					BlockUpdateListener.protectBlock(block, 3);
					ColorTool.setColor(block, dye);
					new BlockOptionGui(getPlayer(), block).display();
				} else {
					ToolUser.getUser(getPlayer()).setToolColor(dye);
					Util.sendNotification(getPlayer(), "Color selected: "+Util.DyetoChat(dye)+StringUtils.capitalize(dye.toString().replace("_", " ").toLowerCase()));
					getPlayer().closeInventory();
				}
			});
			setItem(row, col++, item);
			if(col>8) {
				row=1;
				col=1;
			}
		}
	}
	
	

}
