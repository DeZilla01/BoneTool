package net.dezilla.bonetool.gui;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.block.data.type.RedstoneWire;
import org.bukkit.block.data.type.RedstoneWire.Connection;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import net.dezilla.bonetool.ToolUser;
import net.dezilla.bonetool.Util;
import net.dezilla.bonetool.util.Locale;
import net.dezilla.bonetool.wandtool.RedstoneWireTool;

public class RedstoneWireGui extends GuiPage {
	Block block;
	final List<BlockFace> flist = Arrays.asList(BlockFace.NORTH, BlockFace.EAST, BlockFace.SOUTH, BlockFace.WEST);

	public RedstoneWireGui(Player player, Block block) {
		super(3, player);
		this.block = block;
		setName(Locale.parse(ToolUser.getUser(player), "redstoneconnection"));
		if(block.getBlockData() instanceof RedstoneWire)
			addItems();
	}
	
	public void refresh() {
		this.clear();
		this.addItems();
		this.display();
	}
	
	private void addItems() {
		RedstoneWire wire = (RedstoneWire) block.getBlockData();
		BlockFace pface = Util.getBlockFacing(getPlayer(), new HashSet<BlockFace>(wire.getAllowedFaces()));
		if(flist.contains(pface))
			pface = pface.getOppositeFace();
		for(BlockFace f : wire.getAllowedFaces()) {
			int row = 0;
			int col = 0;
			if(pface == BlockFace.UP || pface == BlockFace.DOWN) {
				switch(pface) {
					case UP: col = 7;break;
					case DOWN: row = 2; col = 7; break;
					default:{}
				}
			} else {
				BlockFace F = pface;
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
			}
			ItemStack icon = new ItemStack(wire.getFace(f) == Connection.NONE ? Material.REDSTONE_BLOCK : (wire.getFace(f) == Connection.SIDE ? Material.GOLD_BLOCK : Material.EMERALD_BLOCK));
			Util.setName(icon, ChatColor.RESET+f.toString()+": "+ChatColor.YELLOW+wire.getFace(f).toString());
			GuiItem item = new GuiItem(icon);
			item.setRun((ev) -> {
				RedstoneWireTool.toggleConnection(block, f);
				refresh();
			});
			setItem(row,col,item);
		}
	}
	
	private BlockFace next(BlockFace face) {
		if(flist.indexOf(face)+1>=flist.size())
			return flist.get(0);
		else
			return flist.get(flist.indexOf(face)+1);
	}

}
