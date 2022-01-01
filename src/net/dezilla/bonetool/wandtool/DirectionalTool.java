package net.dezilla.bonetool.wandtool;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.block.data.BlockData;
import org.bukkit.block.data.Directional;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;

import net.dezilla.bonetool.Util;

public class DirectionalTool extends WandTool{

	@Override
	public boolean isServerCompatible() {
		return checkClass("org.bukkit.block.data.Directional");
	}

	@Override
	public boolean isBlockCompatible(Block block) {
		return block.getBlockData() instanceof Directional;
	}

	@Override
	public ItemStack getIcon(Block block) {
		String name = "Direction";
		if(block != null)
			name = "Direction: "+ChatColor.YELLOW+getFacing(block);
		return Util.setName(new ItemStack(Material.COMPASS), name);
	}
	
	@Override
	public void onLeftClick(InventoryClickEvent event, Block block) {
		toggleFacing(block);
	}
	
	@Override
	public boolean canToggle() {
		return true;
	}
	
	@Override
	public void toggle(Block block) {
		toggleFacing(block);
	}
	
	public static BlockFace getFacing(Directional data) {
		return data.getFacing();
	}
	
	public static BlockFace getFacing(BlockData data) {
		if(data instanceof Directional)
			return ((Directional) data).getFacing();
		return BlockFace.NORTH;
	}
	
	public static BlockFace getFacing(Block block) {
		if(block.getBlockData() instanceof Directional)
			return getFacing(block.getBlockData());
		return BlockFace.NORTH;
	}
	
	public static Set<BlockFace> getFaces(Directional data){
		return data.getFaces();
	}
	
	public static Set<BlockFace> getFaces(BlockData data){
		if(data instanceof Directional)
			return ((Directional) data).getFaces();
		return new HashSet<BlockFace>();
	}
	
	public static Set<BlockFace> getFaces(Block block){
		if(block.getBlockData() instanceof Directional)
			return getFaces(block.getBlockData());
		return new HashSet<BlockFace>();
	}
	
	public static Directional setFacing(Directional data, BlockFace face) {
		data.setFacing(face);
		return data;
	}
	
	public static BlockData setFacing(BlockData data, BlockFace face) {
		if(data instanceof Directional)
			((Directional) data).setFacing(face);
		return data;
	}
	
	public static Block setFacing(Block block, BlockFace face) {
		if(block.getBlockData() instanceof Directional)
			block.setBlockData(setFacing(block.getBlockData(), face));
		return block;
	}
	
	public static Directional toggleFacing(Directional data) {
		List<BlockFace> faces = new ArrayList<BlockFace>(data.getFaces());
		BlockFace face;
		if(faces.indexOf(data.getFacing())>= faces.size()-1)
			face = faces.get(0);
		else
			face = faces.get(faces.indexOf(data.getFacing())+1);

		data.setFacing(face);
		return data;
	}
	
	public static BlockData toggleFacing(BlockData data) {
		if(data instanceof Directional)
			toggleFacing((Directional) data);
		return data;
	}
	
	public static Block toggleFacing(Block block) {
		if(block.getBlockData() instanceof Directional)
			block.setBlockData(toggleFacing(block.getBlockData()));
		return block;
	}

}
