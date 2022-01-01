package net.dezilla.bonetool.listener;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.block.Block;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockFadeEvent;
import org.bukkit.event.block.BlockFormEvent;
import org.bukkit.event.block.BlockFromToEvent;
import org.bukkit.event.block.BlockGrowEvent;
import org.bukkit.event.block.BlockIgniteEvent;
import org.bukkit.event.block.BlockPhysicsEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.block.BlockSpreadEvent;
import org.bukkit.event.entity.EntityChangeBlockEvent;
import org.bukkit.event.player.PlayerInteractEvent;

import net.dezilla.bonetool.ToolMain;
import net.dezilla.bonetool.ToolUser;

public class BlockUpdateListener implements Listener{
	
	public static List<Block> protected_blocks = new ArrayList<Block>();
	
	public static void protectBlock(Block block, int ticks) {
		for(int x = -1; x < 2 ; x++) {
			for(int y = -1; y < 2 ; y++) {
				for(int z = -1; z < 2 ; z++) {
					Block b = block.getLocation().add(x, y, z).getBlock();
					protected_blocks.add(b);
					Bukkit.getScheduler().scheduleSyncDelayedTask(ToolMain.getInstance(), () -> protected_blocks.remove(b), ticks);
				}
			}
		}
	}
	
	public static boolean isProtected(Block block) {
		return protected_blocks.contains(block);
	}
	
	@EventHandler(ignoreCancelled = true)
	public void onBlockFromTo(BlockFromToEvent e) {
		if(isProtected(e.getBlock()) || isProtected(e.getToBlock()))
			e.setCancelled(true);
	}
	
	@EventHandler(ignoreCancelled = true)
	public void onBlockUpdate(BlockPhysicsEvent e) {
		if(isProtected(e.getBlock()) || isProtected(e.getSourceBlock()))
			e.setCancelled(true);
	}
	
	@EventHandler(ignoreCancelled = true)
	public void onSandFall(EntityChangeBlockEvent e) {
		if(isProtected(e.getBlock())) {
			e.setCancelled(true);
			e.getBlock().getState().update(false, false);
		}
	}
	
	@EventHandler(ignoreCancelled = true)
	public void onBlockSpread(BlockSpreadEvent e) {
		if(isProtected(e.getBlock()) || isProtected(e.getSource()))
			e.setCancelled(true);
	}
	
	@EventHandler(ignoreCancelled = true)
	public void onBlockForm(BlockFormEvent e) {
		if(isProtected(e.getBlock()))
			e.setCancelled(true);
	}
	
	@EventHandler(ignoreCancelled = true)
	public void onBlockGrow(BlockGrowEvent e) {
		if(isProtected(e.getBlock()))
			e.setCancelled(true);
	}
	
	@EventHandler(ignoreCancelled = true)
	public void onBlockIgnite(BlockIgniteEvent e) {
		if(isProtected(e.getBlock()) || isProtected(e.getIgnitingBlock()))
			e.setCancelled(true);
	}
	
	@EventHandler(ignoreCancelled = true)
	public void onBlockFade(BlockFadeEvent e) {
		if(isProtected(e.getBlock()))
			e.setCancelled(true);
	}
	
	@EventHandler(ignoreCancelled = true)
	public void onBlockBreak(BlockBreakEvent e) {
		ToolUser user = ToolUser.getUser(e.getPlayer());
		if(user.isBlockUpdateProtected()) {
			protectBlock(e.getBlock(), 3);
		}
	}
	
	@EventHandler(ignoreCancelled = true)
	public void onBlockPlace(BlockPlaceEvent e) {
		ToolUser user = ToolUser.getUser(e.getPlayer());
		if(user.isBlockUpdateProtected()) {
			protectBlock(e.getBlock(), 3);
		}
	}
	
	@EventHandler(ignoreCancelled = true)
	public void onBlockInteract(PlayerInteractEvent e) {
		ToolUser user = ToolUser.getUser(e.getPlayer());
		if(user.isBlockUpdateProtected() && e.getClickedBlock() != null)
			protectBlock(e.getClickedBlock(), 3);
	}

}
