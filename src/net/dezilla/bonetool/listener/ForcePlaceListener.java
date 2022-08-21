package net.dezilla.bonetool.listener;

import org.bukkit.block.Block;
import org.bukkit.block.data.Directional;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;

import net.dezilla.bonetool.ToolMain;
import net.dezilla.bonetool.ToolUser;
import net.dezilla.bonetool.Util;
import net.dezilla.bonetool.util.PlotSquaredUtil;
import net.dezilla.bonetool.util.ToolConfig;
import net.dezilla.bonetool.wandtool.DirectionalTool;

public class ForcePlaceListener implements Listener{
	
	@EventHandler(ignoreCancelled=true)
	public void onBlockInteract(PlayerInteractEvent e) {
		if(e.getClickedBlock() == null || e.getItem() == null || e.getAction() != Action.RIGHT_CLICK_BLOCK || !e.getItem().getType().isBlock())
			return;
		if(ToolMain.isPlotEnabled() && e.getClickedBlock() != null) {
			if(!ToolConfig.allowOutsidePlotArea || PlotSquaredUtil.inPlotArea(e.getPlayer())) {
				Block block = e.getClickedBlock();
				if(!PlotSquaredUtil.canEdit(e.getPlayer(), block)) {
					return;
				}
			}
		}
		ToolUser user = ToolUser.getUser(e.getPlayer());
		if(user.isForcePlacing()) {
			e.setCancelled(true);
			Block b = e.getClickedBlock().getRelative(e.getBlockFace());
			b.setType(e.getItem().getType());
			if(b.getBlockData() instanceof Directional)
				DirectionalTool.setFacing(b, Util.getBlockFacing(e.getPlayer(), DirectionalTool.getFaces(b)));
		}
	}

}
