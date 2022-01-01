package net.dezilla.bonetool.gui;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.block.data.type.Wall;
import org.bukkit.block.data.type.Wall.Height;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import net.dezilla.bonetool.Util;
import net.dezilla.bonetool.wandtool.WallTool;

public class WallGui extends GuiPage{
	Block block;
	Wall wall;
	final List<BlockFace> flist = Arrays.asList(BlockFace.NORTH, BlockFace.EAST, BlockFace.SOUTH, BlockFace.WEST);

	public WallGui(Player player, Block block) {
		super(3, player);
		this.block = block;
		if(block.getBlockData() instanceof Wall) {
			addItems();
		}
	}
	
	public void refresh() {
		this.clear();
		this.addItems();
		this.display();
	}
	
	private void addItems() {
		this.wall = (Wall) block.getBlockData();
		BlockFace pface = Util.getBlockFacing(getPlayer(), new HashSet<BlockFace>(flist)).getOppositeFace();
		for(BlockFace f : flist) {
			BlockFace F = pface;
			int row = 0;
			int col = 0;
			if(F == f) {
				col = 4;
			}F = next(F);
			if(F == f) {
				row = 1;
				col = 5;
			}F = next(F);
			if(F == f) {
				row = 2;
				col = 4;
			}F = next(F);
			if(F == f) {
				row = 1;
				col = 3;
			}
			ItemStack icon = new ItemStack(wall.getHeight(f) == Height.NONE ? Material.REDSTONE_BLOCK : (wall.getHeight(f) == Height.LOW ? Material.GOLD_BLOCK : Material.EMERALD_BLOCK));
			Util.setName(icon, ChatColor.RESET+f.toString()+": "+ChatColor.YELLOW+wall.getHeight(f).toString());
			GuiItem item = new GuiItem(icon);
			item.setRun((ev) -> {
				WallTool.toggleWall(block, f);
				refresh();
			});
			setItem(row,col,item);
		}
		ItemStack upicon = new ItemStack(wall.isUp() ? Material.EMERALD_BLOCK : Material.REDSTONE_BLOCK);
		Util.setName(upicon, ChatColor.YELLOW+"UP");
		GuiItem upitem = new GuiItem(upicon);
		upitem.setRun((ev) -> {
			WallTool.toggleWall(block, BlockFace.UP);
			refresh();
		});
		setItem(1,4,upitem);
	}
	
	private BlockFace next(BlockFace face) {
		if(flist.indexOf(face)+1>=flist.size())
			return flist.get(0);
		else
			return flist.get(flist.indexOf(face)+1);
	}

}
