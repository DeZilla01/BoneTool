package net.dezilla.bonetool.wandtool;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.block.data.BlockData;
import org.bukkit.block.data.Rotatable;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;

import net.dezilla.bonetool.Util;
import net.dezilla.bonetool.gui.BlockFaceSelectGui;
import net.dezilla.bonetool.gui.ToolGui;

public class RotatableTool extends WandTool{

	@Override
	public boolean isServerCompatible() {
		return checkClass("org.bukkit.block.data.Rotatable");
	}

	@Override
	public boolean isBlockCompatible(Block block) {
		return block.getBlockData() instanceof Rotatable;
	}

	@Override
	public ItemStack getIcon(Block block) {
		String name = "Rotation";
		if(block != null)
			name = "Rotation: "+ChatColor.YELLOW+getRotation(block);
		ItemStack icon = Util.setName(new ItemStack(Material.REPEATER), name);
		
		if(block != null) {
			List<String> lore = Arrays.asList(
					ChatColor.AQUA+"Left Click: "+ChatColor.WHITE+"Choose Rotation",
					ChatColor.AQUA+"Right Click: "+ChatColor.WHITE+"Toggle Rotation",
					ChatColor.AQUA+"Shift + Right Click: "+ChatColor.WHITE+"Player's Facing");
			icon = Util.setLore(icon, lore);
		}
		return icon;
	}
	
	@Override
	public void onLeftClick(InventoryClickEvent event, Block block) {
		if(event.getClick() == ClickType.LEFT) {
			new BlockFaceSelectGui((Player) event.getWhoClicked(), getCompatibleFaces(block), (face) -> {
				if(block.getBlockData() instanceof Rotatable) {
					setRotation(block, face);
					new ToolGui((Player) event.getWhoClicked()).display();
				}
			}).display();
		} else if (event.getClick() == ClickType.RIGHT) {
			toggleRotation(block);
		} else if (event.getClick() == ClickType.SHIFT_RIGHT) {
			BlockFace face = Util.getBlockFacing((Player) event.getWhoClicked(), new HashSet<BlockFace>(RotatableTool.getCompatibleFaces(block)));
			if(block.getType().toString().contains("SKULL") || block.getType().toString().contains("HEAD"))
				face = face.getOppositeFace();
			RotatableTool.setRotation(block, face);
		}
	}
	
	@Override
	public boolean canToggle() {
		return true;
	}
	
	@Override
	public void toggle(Block block) {
		toggleRotation(block);
	}
	
	public static BlockFace getRotation(Rotatable data) {
		return data.getRotation();
	}
	
	public static BlockFace getRotation(BlockData data) {
		if(data instanceof Rotatable)
			return ((Rotatable) data).getRotation();
		return BlockFace.NORTH;
	}
	
	public static BlockFace getRotation(Block block) {
		if(block.getBlockData() instanceof Rotatable)
			return getRotation(block.getBlockData());
		return BlockFace.NORTH;
	}
	
	public static Rotatable setRotation(Rotatable data, BlockFace face) {
		data.setRotation(face);
		return data;
	}
	
	public static BlockData setRotation(BlockData data, BlockFace face) {
		if(data instanceof Rotatable)
			((Rotatable) data).setRotation(face);
		return data;
	}
	
	public static Block setRotation(Block block, BlockFace face) {
		block.setBlockData(setRotation(block.getBlockData(), face));
		return block;
	}
	
	public static Rotatable toggleRotation(Rotatable data) {
		List<BlockFace> faces = getCompatibleFaces(data);
		if(faces.indexOf(data.getRotation())+1 >= faces.size())
			data.setRotation(faces.get(0));
		else
			data.setRotation(faces.get(faces.indexOf(data.getRotation())+1));
		return data;
	}
	
	public static BlockData toggleRotation(BlockData data) {
		if(data instanceof Rotatable)
			return toggleRotation((Rotatable) data);
		return data;
	}
	
	public static Block toggleRotation(Block block) {
		block.setBlockData(toggleRotation(block.getBlockData()));
		return block;
	}
	
	public static List<BlockFace> getCompatibleFaces(Block block){
		if(!(block.getBlockData() instanceof Rotatable))
			return new ArrayList<BlockFace>();
		return getCompatibleFaces((Rotatable) block.getBlockData());
	}
	
	public static List<BlockFace> getCompatibleFaces(Rotatable data){
		List<BlockFace> faces = new ArrayList<BlockFace>();
		BlockFace original = data.getRotation();
		for(BlockFace f : Util.BLOCKFACE_LIST) {
			try {
				data.setRotation(f);
				faces.add(f);
			}catch(Exception e) {
				continue;
			}
		}
		data.setRotation(original);
		return faces;
	}
}
