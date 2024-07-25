package net.dezilla.bonetool.listener;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.block.data.Directional;
import org.bukkit.entity.Player;
import org.bukkit.event.Event.Result;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.util.BlockIterator;

import com.google.common.collect.Sets;

import net.dezilla.bonetool.ToolMain;
import net.dezilla.bonetool.ToolUser;
import net.dezilla.bonetool.Util;
import net.dezilla.bonetool.util.PlotSquaredUtil;
import net.dezilla.bonetool.util.ToolConfig;
import net.dezilla.bonetool.wandtool.DirectionalTool;

public class SnipePlaceListener implements Listener {
	static private List<Material> StuffThatPlayersCanPlaceBlocksOver = new ArrayList<Material>();
	
	static {
		StuffThatPlayersCanPlaceBlocksOver.add(Material.AIR);
		StuffThatPlayersCanPlaceBlocksOver.add(Material.WATER);
		StuffThatPlayersCanPlaceBlocksOver.add(Material.LAVA);
		if(ToolMain.getVersionNumber() < 21)
			StuffThatPlayersCanPlaceBlocksOver.add(Material.valueOf("GRASS"));
		else {
			StuffThatPlayersCanPlaceBlocksOver.add(Material.SHORT_GRASS);
			StuffThatPlayersCanPlaceBlocksOver.add(Material.TALL_GRASS);
		}
	}
	
	@EventHandler(priority = EventPriority.LOW)
	public void onInteract(PlayerInteractEvent event) {
		if(event.useItemInHand() == Result.DENY)
			return;
		ToolUser user = ToolUser.getUser(event.getPlayer());
		if(!user.isSnipePlacing())
			return;
		if(event.getItem() != null && event.getItem().getType() == user.getWandMaterial())
			return;
		if(event.getAction() != Action.LEFT_CLICK_AIR && event.getAction() != Action.RIGHT_CLICK_AIR)
			return;
		Block targetted = event.getPlayer().getTargetBlock(Sets.newHashSet(Material.AIR), 50);
		BlockFace face = getTargetFace(event.getPlayer(), targetted);
		//destroy
		if(event.getAction() == Action.LEFT_CLICK_AIR) {
			BlockBreakEvent breakEvent = new BlockBreakEvent(targetted, event.getPlayer());
			Bukkit.getPluginManager().callEvent(breakEvent);
			if(breakEvent.isCancelled())
				return;
			if(ToolMain.isPlotEnabled()) {
				if(!ToolConfig.allowOutsidePlotArea || PlotSquaredUtil.inPlotArea(event.getPlayer())) {
					if(!PlotSquaredUtil.canEdit(event.getPlayer(), targetted)) {
						return;
					}
				}
			}
			targetted.setType(Material.AIR);
		}
		//place
		if(event.getItem() == null || !event.getItem().getType().isBlock())
			return;
		if(event.getAction() == Action.RIGHT_CLICK_AIR && face != null) {
			Block newBlock = targetted.getRelative(face);
			if(!StuffThatPlayersCanPlaceBlocksOver.contains(newBlock.getType()))
				return;
			BlockPlaceEvent placeEvent = new BlockPlaceEvent(newBlock, newBlock.getState(), targetted, event.getItem(), event.getPlayer(), true, event.getHand());
			Bukkit.getPluginManager().callEvent(placeEvent);
			if(placeEvent.isCancelled() || !placeEvent.canBuild())
				return;
			if(ToolMain.isPlotEnabled()) {
				if(!ToolConfig.allowOutsidePlotArea || PlotSquaredUtil.inPlotArea(event.getPlayer())) {
					if(!PlotSquaredUtil.canEdit(event.getPlayer(), newBlock)) {
						return;
					}
				}
			}
			newBlock.setType(event.getItem().getType());
			if(newBlock.getBlockData() instanceof Directional)
				DirectionalTool.setFacing(newBlock, Util.getBlockFacing(event.getPlayer(), DirectionalTool.getFaces(newBlock)));
		}
		
	}
	
	private static BlockFace getTargetFace(Player player, Block block) {
		Iterator<Block> itr = new BlockIterator(player, 50);
		Block previous = null;
		while(itr.hasNext()) {
			Block b = itr.next();
			if(b.equals(block))
				return b.getFace(previous);
			else
				previous = b;
		}
		return null;
	}

}
