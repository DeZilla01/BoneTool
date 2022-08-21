package net.dezilla.bonetool.listener;

import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;

import org.bukkit.Art;
import org.bukkit.Axis;
import org.bukkit.Bukkit;
import org.bukkit.Color;
import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.Particle;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.entity.Entity;
import org.bukkit.entity.ItemFrame;
import org.bukkit.entity.Painting;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.hanging.HangingPlaceEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerItemHeldEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataType;

import net.dezilla.bonetool.ToolMain;
import net.dezilla.bonetool.Util;
import net.dezilla.bonetool.wandtool.DirectionalTool;
import net.dezilla.bonetool.wandtool.OrientableTool;
import net.dezilla.bonetool.wandtool.PistonHeadTool;
import net.dezilla.bonetool.util.PlotSquaredUtil;
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
		
		Block block = e.getClickedBlock().getRelative(e.getBlockFace());
		if(block.getType() != Material.AIR)
			return;
		
		if(ToolMain.isPlotEnabled()) {
			if(!ToolConfig.allowOutsidePlotArea || PlotSquaredUtil.inPlotArea(e.getPlayer())) {
				if(!PlotSquaredUtil.canEdit(e.getPlayer(), block)) {
					return;
				}
			}
		}
		
		ItemMeta meta = e.getItem().getItemMeta();
		if(!meta.getPersistentDataContainer().has(Util.specialBlockKey, PersistentDataType.STRING))
			return;
		String blockType = meta.getPersistentDataContainer().get(Util.specialBlockKey, PersistentDataType.STRING);
		if(blockType.equals("netherPortal") && ToolConfig.netherPortal) {
			e.setCancelled(true);
			block.setType(Material.NETHER_PORTAL);
			BlockFace face = Util.getBlockFacing(e.getPlayer(), new HashSet<BlockFace>(Arrays.asList(BlockFace.NORTH, BlockFace.SOUTH, BlockFace.EAST, BlockFace.WEST)));
			switch(face) {
				case NORTH: case SOUTH:
					OrientableTool.setAxis(block, Axis.X);break;
				case EAST: case WEST:
					OrientableTool.setAxis(block, Axis.Z);break;
				default:{}
			}
		}else if(blockType.equals("endPortal") && ToolConfig.endPortal) {
			e.setCancelled(true);
			block.setType(Material.END_PORTAL);
		}else if(blockType.equals("pistonHead") && ToolConfig.pistonHead) {
			e.setCancelled(true);
			block.setType(Material.PISTON_HEAD);
			DirectionalTool.setFacing(block, Util.getBlockFacing(e.getPlayer(), DirectionalTool.getFaces(block)));
			PistonHeadTool.setPistonHeadShot(block, true);
		}else if(blockType.equals("doubleLadder") && ToolConfig.doubleLadder) {
			e.setCancelled(true);
			BlockUpdateListener.protectBlock(block, 1);
			block.setType(Material.LADDER);
			DirectionalTool.setFacing(block, Util.getBlockFacing(e.getPlayer(), DirectionalTool.getFaces(block)));
			Block block2 = block.getRelative(Util.getBlockFacing(e.getPlayer(), DirectionalTool.getFaces(block)).getOppositeFace());
			if(block2.getType() == Material.AIR) {
				BlockUpdateListener.protectBlock(block2, 1);
				block2.setType(Material.LADDER);
				DirectionalTool.setFacing(block2, Util.getBlockFacing(e.getPlayer(), DirectionalTool.getFaces(block2)).getOppositeFace());
			}
		}else if(blockType.equals("litRedstoneLamp") && ToolConfig.litRedstoneLamp) {
			e.setCancelled(true);
			BlockUpdateListener.protectBlock(block, 1);
			block.setType(Material.REDSTONE_LAMP);
			LightableTool.setLit(block, true);
		}
	}
	
	private Map<Player, Integer> taskids = new HashMap<Player, Integer>();
	
	@EventHandler(ignoreCancelled = true)
	public void playerChangeSlot(PlayerItemHeldEvent event) {
		ItemStack item = event.getPlayer().getInventory().getItem(event.getNewSlot());
		if(item != null && item.getType() == Material.ITEM_FRAME) {
			ItemMeta meta = item.getItemMeta();
			if(meta.getPersistentDataContainer().has(Util.specialBlockKey, PersistentDataType.STRING) && meta.getPersistentDataContainer().get(Util.specialBlockKey, PersistentDataType.STRING).equals("invisFrame")) {
				int taskid = Bukkit.getScheduler().scheduleSyncRepeatingTask(ToolMain.getInstance(), () -> {
					ItemStack current = event.getPlayer().getInventory().getItemInMainHand();
					if(!event.getPlayer().isOnline() || current == null || !current.equals(item)) {
						Bukkit.getScheduler().cancelTask(taskids.get(event.getPlayer()));
						taskids.remove(event.getPlayer());
						return;
					}
					for(Entity e : event.getPlayer().getWorld().getNearbyEntities(event.getPlayer().getLocation(), 20, 20, 20)) {
						if(e instanceof ItemFrame) {
							ItemFrame i = (ItemFrame) e;
							if(!i.isVisible())
								event.getPlayer().spawnParticle(Particle.REDSTONE, i.getLocation(), 1, 0, 0, 0, 1, new Particle.DustOptions(Color.ORANGE, 1f));
						}
					}
				}, 1, 8);
				if(taskids.containsKey(event.getPlayer())) {
					Bukkit.getScheduler().cancelTask(taskids.get(event.getPlayer()));
				}
				taskids.put(event.getPlayer(), taskid);
			}
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
