package net.dezilla.bonetool.wandtool;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.bukkit.Axis;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.data.BlockData;
import org.bukkit.block.data.Orientable;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;

import net.dezilla.bonetool.Util;

public class OrientableTool extends WandTool {

	@Override
	public boolean isServerCompatible() {
		return checkClass("org.bukkit.block.data.Orientable");
	}

	@Override
	public boolean isBlockCompatible(Block block) {
		return block.getBlockData() instanceof Orientable;
	}

	@Override
	public ItemStack getIcon(Block block) {
		String name = "Orientation";
		if(block != null)
			name = "Orientation: "+ChatColor.YELLOW+getAxis(block);
		return Util.setName(new ItemStack(Material.SUGAR), name);
	}
	
	@Override
	public void onLeftClick(InventoryClickEvent event, Block block) {
		toggleAxis(block);
	}
	
	@Override
	public boolean canToggle() {
		return true;
	}
	
	@Override
	public void toggle(Block block) {
		toggleAxis(block);
	}
	
	public static Axis getAxis(Orientable data) {
		return data.getAxis();
	}
	
	public static Axis getAxis(BlockData data) {
		if(data instanceof Orientable)
			return ((Orientable) data).getAxis();
		return Axis.values()[0];
	}
	
	public static Axis getAxis(Block block) {
		if(block.getBlockData() instanceof Orientable)
			return getAxis(block.getBlockData());
		return Axis.values()[0];
	}
	
	public static Set<Axis> getAxes(Orientable data) {
		return data.getAxes();
	}
	
	public static Set<Axis> getAxes(BlockData data){
		if(data instanceof Orientable) 
			return ((Orientable) data).getAxes();
		return new HashSet<Axis>();
	}
	
	public static Set<Axis> getAxes(Block block){
		if(block.getBlockData() instanceof Orientable)
			return getAxes(block.getBlockData());
		return new HashSet<Axis>();
	}
	
	public static Orientable setAxis(Orientable data, Axis axis) {
		data.setAxis(axis);
		return data;
	}
	
	public static BlockData setAxis(BlockData data, Axis axis) {
		if(data instanceof Orientable)
			((Orientable) data).setAxis(axis);
		return data;
	}
	
	public static Block setAxis(Block block, Axis axis) {
		if(block.getBlockData() instanceof Orientable)
			block.setBlockData(setAxis(block.getBlockData(), axis));
		return block;
	}
	
	public static Orientable toggleAxis(Orientable data) {
		List<Axis> axes = new ArrayList<Axis>(data.getAxes());
		if(axes.indexOf(data.getAxis()) >= axes.size()-1)
			data.setAxis(axes.get(0));
		else
			data.setAxis(axes.get(axes.indexOf(data.getAxis())+1));
		return data;
	}
	
	public static BlockData toggleAxis(BlockData data) {
		if(data instanceof Orientable)
			toggleAxis((Orientable) data);
		return data;
	}
	
	public static Block toggleAxis(Block block) {
		if(block.getBlockData() instanceof Orientable)
			block.setBlockData(toggleAxis(block.getBlockData()));
		return block;
	}

}
