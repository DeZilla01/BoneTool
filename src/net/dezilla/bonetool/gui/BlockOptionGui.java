package net.dezilla.bonetool.gui;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import net.dezilla.bonetool.ToolMain;
import net.dezilla.bonetool.Util;
import net.dezilla.bonetool.listener.BlockUpdateListener;
import net.dezilla.bonetool.wandtool.WandTool;

public class BlockOptionGui extends GuiPage{
	Block block;
	List<WandTool> tools;

	public BlockOptionGui(Player player, Block block) {
		super(6, player);
		this.block = block;
		tools = getTools();
		setName("Block Option: "+block.getType());
		int row = 0;
		int col = 0;
		List<GuiItem> options = new ArrayList<GuiItem>();
		options.addAll(getToolItems());
		
		for(GuiItem i : options) {
			setItem(row, col++, i);
			if(col>=9) {
				row++;
				col=0;
			}
		}
		this.removeEmptyRows();
	}
	
	public List<WandTool> getToolList(){
		return tools;
	}
	
	private List<GuiItem> getToolItems(){
		List<GuiItem> items = new ArrayList<GuiItem>();
		for(WandTool t : tools) {
			ItemStack icon = t.getIcon(block);
			GuiItem item = new GuiItem(icon);
			item.setRun((event) -> {
				BlockUpdateListener.protectBlock(block, 2);
				t.onLeftClick(event, block);
				item.setItem(t.getIcon(block));
				});
			items.add(item);
		}
		return items;
	}
	
	private List<WandTool> getTools(){
		List<WandTool> tools = new ArrayList<WandTool>();
		for(WandTool t : ToolMain.getTools()) {
			if(Util.isToolBlacklisted(t) && !Util.permCheck(getPlayer(), "bonetool.admin.bypass")) {
				continue;
			}
			if(t.isBlockCompatible(block)) {
				tools.add(t);
			}
		}
		return tools;
	}
}
