package net.dezilla.bonetool.gui;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.block.data.MultipleFacing;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import net.dezilla.bonetool.ToolUser;
import net.dezilla.bonetool.Util;
import net.dezilla.bonetool.util.Locale;
import net.dezilla.bonetool.wandtool.MultipleFacingTool;

public class MultipleFacingGui extends GuiPage{
	Block block;
	final List<BlockFace> flist = Arrays.asList(BlockFace.NORTH, BlockFace.EAST, BlockFace.SOUTH, BlockFace.WEST);
	
	public MultipleFacingGui(Player player, Block block) {
		super(3, player);
		this.block = block;
		setName(Locale.parse(ToolUser.getUser(player), "multifaceseditor"));
		addItems();
	}
	
	public void refresh() {
		this.clear();
		this.addItems();
		this.display();
	}
	
	private void addItems() {
		if(!(block.getBlockData() instanceof MultipleFacing))
			return;
		BlockFace pface = Util.getBlockFacing(getPlayer(), new HashSet<>(flist)).getOppositeFace();
		
		for(BlockFace f : MultipleFacingTool.getAllowedFaces(block)) {
			int col = 0;
			int row = 0;
			switch(f) {
			case NORTH: case SOUTH: case WEST: case EAST:
				BlockFace F = pface;
				if(F == f) {
					col=2;break;
				}F=next(F);
				if(F == f) {
					row=1;col=3;break;
				}F=next(F);
				if(F == f) {
					row=2;col=2;break;
				}F=next(F);
				if(F == f) {
					row=1;col=1;break;
				}break;
				case UP: col=5;break;
				case DOWN: row=2;col=5;break;
				default:{}
			}
			String name = StringUtils.capitalize(f.toString().toLowerCase())+": "+(MultipleFacingTool.hasFace(block, f) ? ChatColor.GREEN+"True":ChatColor.RED+"False");
			ItemStack icon = new ItemStack((MultipleFacingTool.hasFace(block, f) ? Material.EMERALD_BLOCK:Material.REDSTONE_BLOCK));
			Util.setName(icon, name);
			GuiItem item = new GuiItem(icon);
			item.setRun((event) -> {
				MultipleFacingTool.setFace(block, f, !MultipleFacingTool.hasFace(block, f));
				refresh();
			});
			setItem(row, col, item);
		}
	}
	
	private BlockFace next(BlockFace face) {
		if(flist.indexOf(face)+1>=flist.size())
			return flist.get(0);
		else
			return flist.get(flist.indexOf(face)+1);
	}

}
