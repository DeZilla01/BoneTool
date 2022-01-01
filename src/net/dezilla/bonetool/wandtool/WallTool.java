package net.dezilla.bonetool.wandtool;

import java.util.Arrays;
import java.util.List;

import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.block.data.type.Wall;
import org.bukkit.block.data.type.Wall.Height;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;

import net.dezilla.bonetool.Util;
import net.dezilla.bonetool.gui.WallGui;
import net.dezilla.bonetool.listener.BlockUpdateListener;

public class WallTool extends WandTool{
	
	@Override
	public boolean isServerCompatible() {
		return checkClass("org.bukkit.block.data.type.Wall");
	}

	@Override
	public boolean isBlockCompatible(Block block) {
		return block.getBlockData() instanceof Wall;
	}

	@Override
	public ItemStack getIcon(Block block) {
		String name = "Wall Facing";
		return Util.setName(new ItemStack(Material.COBBLESTONE_WALL), name);
	}
	
	@Override
	public void onLeftClick(InventoryClickEvent event, Block block) {
		new WallGui((Player) event.getWhoClicked(), block).display();;
	}
	
	@Override
	public boolean hasGui() {
		return true;
	}
	
	@Override
	public void openGui(Player player, Block block) {
		new WallGui(player, block).display();
	}
	
	public static void toggleWall(Block block, BlockFace face) {
		if(!(block.getBlockData() instanceof Wall))
			return;
		Wall wall = (Wall) block.getBlockData();
		if(face == BlockFace.UP) {
			wall.setUp(!wall.isUp());
			block.setBlockData(wall);
			return;
		}
		if(!Arrays.asList(BlockFace.NORTH, BlockFace.EAST, BlockFace.SOUTH, BlockFace.WEST).contains(face))
			return;
		List<Height> list = Arrays.asList(Height.values());
		if(list.indexOf(wall.getHeight(face))+1 >= list.size())
			wall.setHeight(face, list.get(0));
		else
			wall.setHeight(face, list.get(list.indexOf(wall.getHeight(face))+1));
		BlockUpdateListener.protectBlock(block, 2);
		block.setBlockData(wall);
	}
}
