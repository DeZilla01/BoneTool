package net.dezilla.bonetool.listener;

import java.util.Arrays;
import java.util.HashSet;

import org.bukkit.Art;
import org.bukkit.Axis;
import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.entity.Painting;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.hanging.HangingPlaceEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;

import net.dezilla.bonetool.listener.BlockUpdateListener;
import net.dezilla.bonetool.Util;
import net.dezilla.bonetool.wandtool.DirectionalTool;
import net.dezilla.bonetool.wandtool.OrientableTool;
import net.dezilla.bonetool.wandtool.PistonHeadTool;
import net.dezilla.bonetool.util.ToolConfig;
import net.dezilla.bonetool.wandtool.LightableTool;

public class SpecialBlockListener implements Listener{
	
	@EventHandler(ignoreCancelled=true)
	public void onItemUser(PlayerInteractEvent e) {
		if(e.getClickedBlock() == null || e.getItem() == null || e.getItem().getType() != Material.PLAYER_HEAD 
				|| e.getAction() != Action.RIGHT_CLICK_BLOCK || e.getPlayer().getGameMode() != GameMode.CREATIVE)
			return;
		if(!Util.permCheck(e.getPlayer(), "bonetool.blocks.use"))
			return;
		
		if(e.getClickedBlock().getRelative(e.getBlockFace()).getType() != Material.AIR)
			return;
		
		String itemName = e.getItem().getItemMeta().getDisplayName();
		if(itemName.contains("Nether Portal Block") && ToolConfig.netherPortal) {
			e.setCancelled(true);
			Block block = e.getClickedBlock().getRelative(e.getBlockFace());
			block.setType(Material.NETHER_PORTAL);
			BlockFace face = Util.getBlockFacing(e.getPlayer(), new HashSet<BlockFace>(Arrays.asList(BlockFace.NORTH, BlockFace.SOUTH, BlockFace.EAST, BlockFace.WEST)));
			switch(face) {
				case NORTH: case SOUTH:
					OrientableTool.setAxis(block, Axis.X);break;
				case EAST: case WEST:
					OrientableTool.setAxis(block, Axis.Z);break;
				default:{}
			}
		}else if(itemName.contains("End Portal Block") && ToolConfig.endPortal) {
			e.setCancelled(true);
			Block block = e.getClickedBlock().getRelative(e.getBlockFace());
			block.setType(Material.END_PORTAL);
		}else if(itemName.contains("Piston Head Block") && ToolConfig.pistonHead) {
			e.setCancelled(true);
			Block block = e.getClickedBlock().getRelative(e.getBlockFace());
			block.setType(Material.PISTON_HEAD);
			DirectionalTool.setFacing(block, Util.getBlockFacing(e.getPlayer(), DirectionalTool.getFaces(block)));
			PistonHeadTool.setPistonHeadShot(block, true);
		}else if(itemName.contains("Double Ladder Head") && ToolConfig.doubleLadder) {
			e.setCancelled(true);
			Block block = e.getClickedBlock().getRelative(e.getBlockFace());
			BlockUpdateListener.protectBlock(block, 1);
			block.setType(Material.LADDER);
			DirectionalTool.setFacing(block, Util.getBlockFacing(e.getPlayer(), DirectionalTool.getFaces(block)));
			Block block2 = block.getRelative(Util.getBlockFacing(e.getPlayer(), DirectionalTool.getFaces(block)).getOppositeFace());
			if(block2.getType() == Material.AIR) {
				BlockUpdateListener.protectBlock(block2, 1);
				block2.setType(Material.LADDER);
				DirectionalTool.setFacing(block2, Util.getBlockFacing(e.getPlayer(), DirectionalTool.getFaces(block2)).getOppositeFace());
			}
		}else if(itemName.contains("Lit Redstone Lamp") && ToolConfig.litRedstoneLamp) {
			e.setCancelled(true);
			Block block = e.getClickedBlock().getRelative(e.getBlockFace());
			BlockUpdateListener.protectBlock(block, 1);
			block.setType(Material.REDSTONE_LAMP);
			LightableTool.setLit(block, true);
		}
	}
	
	@EventHandler(ignoreCancelled = true)
	public void onPaintingPlace(HangingPlaceEvent event) {
		if(!(event.getEntity() instanceof Painting) || !ToolConfig.paintingByName)
			return;
		
		ItemStack item = event.getPlayer().getInventory().getItemInMainHand();
		if(item.getType() != Material.PAINTING || item.getItemMeta().getDisplayName() == null)
			return;
		
		Painting painting = (Painting) event.getEntity();
		
		String name = item.getItemMeta().getDisplayName().replace(" ", "_").toUpperCase();
		for(Art a : Art.values()) {
			if(name.equalsIgnoreCase(a.toString())) {
				painting.setArt(a);
				return;
			}
		}
	}
}
