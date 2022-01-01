package net.dezilla.bonetool.wandtool;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.data.BlockData;
import org.bukkit.block.data.FaceAttachable;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;

import net.dezilla.bonetool.Util;

public class FaceAttachableTool extends WandTool{

	@Override
	public boolean isServerCompatible() {
		return checkClass("org.bukkit.block.data.FaceAttachable");
	}

	@Override
	public boolean isBlockCompatible(Block block) {
		return block.getBlockData() instanceof FaceAttachable;
	}

	@Override
	public ItemStack getIcon(Block block) {
		String name = "Attachable";
		if(block != null)
			name = "Attachable: "+ChatColor.YELLOW+getAttachedFace(block);
		return Util.setName(new ItemStack(Material.COBWEB), name);
	}
	
	@Override
	public void onLeftClick(InventoryClickEvent event, Block block) {
		toggleAttachedFace(block);
	}
	
	@Override
	public boolean canToggle() {
		return true;
	}
	
	@Override
	public void toggle(Block block) {
		toggleAttachedFace(block);
	}
	
	public static FaceAttachable.AttachedFace getAttachedFace(FaceAttachable data) {
		return data.getAttachedFace();
	}
	
	public static FaceAttachable.AttachedFace getAttachedFace(BlockData data){
		if(data instanceof FaceAttachable)
			return ((FaceAttachable) data).getAttachedFace();
		return FaceAttachable.AttachedFace.FLOOR;
	}
	
	public static FaceAttachable.AttachedFace getAttachedFace(Block block){
		if(block.getBlockData() instanceof FaceAttachable)
			return getAttachedFace(block.getBlockData());
		return FaceAttachable.AttachedFace.FLOOR;
	}
	
	public static FaceAttachable setAttachedFace(FaceAttachable data, FaceAttachable.AttachedFace face) {
		data.setAttachedFace(face);
		return data;
	}
	public static BlockData setAttachedFace(BlockData data, FaceAttachable.AttachedFace face) {
		if(data instanceof FaceAttachable)
			((FaceAttachable) data).setAttachedFace(face);
		return data;
	}
	
	public static Block setAttachedFace(Block block, FaceAttachable.AttachedFace face) {
		if(block.getBlockData() instanceof FaceAttachable)
			setAttachedFace(block.getBlockData(), face);
		return block;
	}
	
	public static FaceAttachable toggleAttachedFace(FaceAttachable data) {
		switch(data.getAttachedFace()) {
			case FLOOR:
				data.setAttachedFace(FaceAttachable.AttachedFace.WALL);break;
			case WALL:
				data.setAttachedFace(FaceAttachable.AttachedFace.CEILING);break;
			case CEILING:
				data.setAttachedFace(FaceAttachable.AttachedFace.FLOOR);break;
		}
		return data;
	}
	
	public static BlockData toggleAttachedFace(BlockData data) {
		if(data instanceof FaceAttachable)
			toggleAttachedFace((FaceAttachable) data);
		return data;
	}
	
	public static Block toggleAttachedFace(Block block) {
		if(block.getBlockData() instanceof FaceAttachable)
			block.setBlockData(toggleAttachedFace(block.getBlockData()));
		return block;
	}

}
