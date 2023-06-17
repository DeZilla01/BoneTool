package net.dezilla.bonetool.gui;

import org.bukkit.Instrument;
import org.bukkit.block.data.type.NoteBlock;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import net.dezilla.bonetool.ToolUser;
import net.dezilla.bonetool.util.Locale;

public class NoteBlockMobGui extends GuiPage{

	public NoteBlockMobGui(Player player, NoteGui gui) {
		super(6, player);
		setName(Locale.parse(ToolUser.getUser(player), "noteblockmobselect"));
		int row = 0;
		int col = 0;
		
		NoteBlock n = (NoteBlock) gui.getBlock().getBlockData();
		
		for(String s : NoteGui.heads) {
			Instrument i = Instrument.valueOf(s);
			ItemStack icon = gui.getIcon(i);
			GuiItem item = new GuiItem(icon);
			item.setRun((event) -> {
				n.setInstrument(i);
				gui.getBlock().setBlockData(n);
				gui.play();
				new NoteGui(player, gui.getBlock()).display();
			});
			setItem(row, col++, item);
			if(col>8) {
				row++;
				col=0;
			}
		}
		this.removeEmptyRows();
	}

}
