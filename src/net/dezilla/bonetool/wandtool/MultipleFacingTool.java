package net.dezilla.bonetool.wandtool;

import java.util.HashSet;
import java.util.Set;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.block.data.BlockData;
import org.bukkit.block.data.MultipleFacing;
import org.bukkit.block.data.type.Wall;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;

import net.dezilla.bonetool.Util;
import net.dezilla.bonetool.gui.MultipleFacingGui;
import net.dezilla.bonetool.listener.BlockUpdateListener;

public class MultipleFacingTool extends WandTool{

	@Override
	public boolean isServerCompatible() {
		return checkClass("org.bukkit.block.data.MultipleFacing");
	}

	@Override
	public boolean isBlockCompatible(Block block) {
		return block.getBlockData() instanceof MultipleFacing;
	}

	@Override
	public ItemStack getIcon(Block block) {
		String name = "Multiple Facing";
		ItemStack icon = Util.setName(new ItemStack(Material.OAK_FENCE), name);
		if(block == null)
			Util.setLore(icon, ChatColor.GRAY+"Change the facing of walls, fences and more.");
		return icon;
	}
	
	@Override
	public void onLeftClick(InventoryClickEvent event, Block block) {
		Player player = (Player) event.getWhoClicked();
		new MultipleFacingGui(player, block).display();
	}
	
	@Override
	public boolean isRightClickTool() {
		return true;
	}
	
	@Override
	public void onRightClick(PlayerInteractEvent event, Block block) {
		BlockFace pface = event.getBlockFace();
		if(block.getBlockData() instanceof Wall) {
			WallTool.toggleWall(block, pface);
			return;
		}
		if(!getAllowedFaces(block).contains(pface))
			return;
		BlockUpdateListener.protectBlock(block, 2);
		toggleFace(block, pface);
	}
	
	@Override
	public boolean hasGui() {
		return true;
	}
	
	@Override
	public void openGui(Player player, Block block) {
		new MultipleFacingGui(player, block).display();
	}
	
	public static Set<BlockFace> getFaces(MultipleFacing data){
		return data.getFaces();
	}
	
	public static Set<BlockFace> getFaces(BlockData data){
		if(data instanceof MultipleFacing)
			return ((MultipleFacing) data).getFaces();
		return new HashSet<BlockFace>();
	}
	
	public static Set<BlockFace> getFaces(Block block){
		if(block.getBlockData() instanceof MultipleFacing)
			return getFaces(block.getBlockData());
		return new HashSet<BlockFace>();
	}
	
	public static Set<BlockFace> getAllowedFaces(MultipleFacing data){
		return data.getAllowedFaces();
	}
	
	public static Set<BlockFace> getAllowedFaces(BlockData data){
		if(data instanceof MultipleFacing)
			return ((MultipleFacing) data).getAllowedFaces();
		return new HashSet<BlockFace>();
	}
	
	public static Set<BlockFace> getAllowedFaces(Block block){
		if(block.getBlockData() instanceof MultipleFacing)
			return getAllowedFaces(block.getBlockData());
		return new HashSet<BlockFace>();
	}
	
	public static boolean hasFace(MultipleFacing data, BlockFace face) {
		return data.hasFace(face);
	}
	
	public static boolean hasFace(BlockData data, BlockFace face) {
		if(data instanceof MultipleFacing)
			return ((MultipleFacing) data).hasFace(face);
		return false;
	}
	
	public static boolean hasFace(Block block, BlockFace face) {
		if(block.getBlockData() instanceof MultipleFacing)
			return hasFace((MultipleFacing) block.getBlockData(), face);
		return false;
	}
	
	public static MultipleFacing setFace(MultipleFacing data, BlockFace face, boolean value) {
		data.setFace(face, value);
		return data;
	}
	
	public static BlockData setFace(BlockData data, BlockFace face, boolean value) {
		if(data instanceof MultipleFacing)
			((MultipleFacing) data).setFace(face, value);
		return data;
	}
	
	public static Block setFace(Block block, BlockFace face, boolean value) {
		if(block.getBlockData() instanceof MultipleFacing)
			block.setBlockData(setFace(block.getBlockData(), face, value));
		return block;
	}
	
	public static MultipleFacing toggleFace(MultipleFacing data, BlockFace face) {
		if(data.hasFace(face))
			data.setFace(face, false);
		else
			data.setFace(face, true);
		return data;
	}
	
	public static BlockData toggleFace(BlockData data, BlockFace face) {
		if(data instanceof MultipleFacing)
			toggleFace((MultipleFacing) data, face);
		return data;
	}
	
	public static Block toggleFace(Block block, BlockFace face) {
		if(block.getBlockData() instanceof MultipleFacing)
			block.setBlockData(toggleFace(block.getBlockData(), face));
		return block;
	}

}
