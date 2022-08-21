package net.dezilla.bonetool.wandtool;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.data.BlockData;
import org.bukkit.block.data.Rail;
import org.bukkit.block.data.Rail.Shape;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;

import net.dezilla.bonetool.ToolUser;
import net.dezilla.bonetool.Util;
import net.dezilla.bonetool.util.Locale;

public class RailTool extends WandTool {

	@Override
	public boolean isServerCompatible() {
		return checkClass("org.bukkit.block.data.Rail");
	}

	@Override
	public boolean isBlockCompatible(Block block) {
		return block.getBlockData() instanceof Rail;
	}

	@Override
	public ItemStack getIcon(Block block, ToolUser user) {
		String name = Locale.parse(user, "rail");
		if(block != null)
			name += ": "+ChatColor.YELLOW+getRailShape(block);
		return Util.setName(new ItemStack(Material.RAIL), name);
	}
	
	@Override
	public void onLeftClick(InventoryClickEvent event, Block block) {
		toggleRailShape(block);
	}
	
	@Override
	public boolean canToggle() {
		return true;
	}
	
	@Override
	public void toggle(Block block) {
		toggleRailShape(block);
	}
	
	public static Shape getRailShape(Rail data) {
		return data.getShape();
	}
	
	public static Shape getRailShape(BlockData data) {
		if(data instanceof Rail)
			return ((Rail) data).getShape();
		return Shape.NORTH_SOUTH;
	}
	
	public static Shape getRailShape(Block block) {
		return getRailShape(block.getBlockData());
	}
	
	public static Set<Shape> getRailShapes(Rail data){
		return data.getShapes();
	}
	
	public static Set<Shape> getRailShapes(BlockData data){
		if(data instanceof Rail)
			return ((Rail) data).getShapes();
		return new HashSet<Shape>();
	}
	
	public static Set<Shape> getRailShapes(Block block){
		return getRailShapes(block.getBlockData());
	}
	
	public static Rail setRailShape(Rail data, Shape shape) {
		data.setShape(shape);
		return data;
	}
	
	public static BlockData setRailShape(BlockData data, Shape shape) {
		if(data instanceof Rail)
			((Rail) data).setShape(shape);
		return data;
	}
	
	public static Block setRailShape(Block block, Shape shape) {
		block.setBlockData(setRailShape(block.getBlockData(), shape));
		return block;
	}
	
	public static Rail toggleRailShape(Rail data) {
		List<Shape> shapes = new ArrayList<Shape>(data.getShapes());
		if(shapes.indexOf(data.getShape())+1 >= shapes.size())
			data.setShape(shapes.get(0));
		else
			data.setShape(shapes.get(shapes.indexOf(data.getShape())+1));
		return data;
	}
	
	public static BlockData toggleRailShape(BlockData data) {
		if(data instanceof Rail)
			toggleRailShape((Rail) data);
		return data;
	}
	
	public static Block toggleRailShape(Block block) {
		block.setBlockData(toggleRailShape(block.getBlockData()));
		return block;
	}

}
