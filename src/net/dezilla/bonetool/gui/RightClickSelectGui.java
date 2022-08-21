package net.dezilla.bonetool.gui;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.entity.Player;

import net.dezilla.bonetool.ToolMain;
import net.dezilla.bonetool.ToolUser;
import net.dezilla.bonetool.Util;
import net.dezilla.bonetool.util.Locale;
import net.dezilla.bonetool.wandtool.WandTool;

public class RightClickSelectGui extends GuiPage{

	public RightClickSelectGui(Player player) {
		super(6, player);
		int col = 0, row = 0;
		for(GuiItem i : getItems()) {
			this.setItem(row, col++, i);
			if(col>8) {
				row++;
				col=0;
			}
		}
		setName(Locale.parse(ToolUser.getUser(player), "rightclickaction"));
		this.removeEmptyRows();
	}
	
	private List<GuiItem> getItems(){
		List<GuiItem> items = new ArrayList<GuiItem>();
		for(WandTool t : ToolMain.getTools()) {
			if(t.isRightClickTool()) {
				if(Util.isToolBlacklisted(t) && !Util.permCheck(getPlayer(), "bonetool.admin.bypass")) {
					continue;
				}
				ToolUser u = ToolUser.getUser(getPlayer());
				GuiItem item = new GuiItem(t.getIcon(null, u));
				item.setRun(event -> {
					u.setRightClickTool(t);
					new ToolGui(getPlayer()).display();
				});
				items.add(item);
			}
		}
		return items;
	}

}
