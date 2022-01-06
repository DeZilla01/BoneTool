package net.dezilla.bonetool.listener;

import java.sql.Timestamp;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.Sign;
import org.bukkit.entity.Player;
import org.bukkit.event.Event.Result;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.SignChangeEvent;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.event.player.PlayerInteractEvent;

import net.dezilla.bonetool.ToolMain;
import net.dezilla.bonetool.ToolUser;
import net.dezilla.bonetool.Util;
import net.dezilla.bonetool.gui.BlockOptionGui;
import net.dezilla.bonetool.gui.GuiHolder;
import net.dezilla.bonetool.gui.ToolGui;
import net.dezilla.bonetool.util.PlotSquaredUtil;
import net.dezilla.bonetool.util.ToolConfig;

public class ToolListener implements Listener{
	final static List<Material> UNSELECTABLES = Arrays.asList(
			Material.WATER,
			Material.LAVA,
			Material.FIRE,
			Material.VINE);
	
	@EventHandler
	public void onItemUse(PlayerInteractEvent event) {
		Player p = event.getPlayer();
		ToolUser u = ToolUser.getUser(p);
		
		//just some checks
		if(Util.getItemInHand(p) == null || Util.getItemInHand(p).getType() != u.getWandMaterial())
			return;
		
		//perm check
		if(!Util.permCheck(p, "bonetool.tool.use"))
			return;
				
		Timestamp now = new Timestamp(new Date().getTime());
		event.setCancelled(true);
		event.setUseItemInHand(Result.DENY);
		
		//1.16 Gui fix
		if(p.getOpenInventory() != null && p.getOpenInventory().getTopInventory() != null && p.getOpenInventory().getTopInventory().getHolder() instanceof GuiHolder)
			return;
		
		//Just a check to make sure someone doesn't use the tool twice on same tick
		if(now.getTime()-u.getLastUse().getTime() < 10) {
			return;
		}
		if(ToolMain.isPlotEnabled() && event.getClickedBlock()!=null) {
			if(!ToolConfig.allowOutsidePlotArea || PlotSquaredUtil.inPlotArea(p)) {
				Block block = event.getClickedBlock();
				if(!PlotSquaredUtil.canEdit(p, block)) {
					u.setLastUseNow();
					return;
				}
			}
		}
		
		if(event.getAction() == Action.LEFT_CLICK_BLOCK) {
			Block block = event.getClickedBlock();
			BlockOptionGui optionGui = new BlockOptionGui(p, block);
			if(optionGui.getToolList().size() == 0)
				Util.sendNotification(p, "No tool for this block");
			else if(optionGui.getToolList().size() == 1 && optionGui.getToolList().get(0).hasGui())
				optionGui.getToolList().get(0).openGui(p, block);
			else
				optionGui.display();
		} else if(event.getAction() == Action.RIGHT_CLICK_BLOCK) {
			Block block = event.getClickedBlock();
			u.getRightClickTool().onRightClick(event, block);
		} else if(event.getAction() == Action.LEFT_CLICK_AIR) {
			if(now.getTime()-u.getLastUse().getTime() > 30)
				new ToolGui(p).display();
		}
		
		u.setLastUseNow();
	}
	
	@EventHandler(ignoreCancelled=true)
	public void onSignInteract(PlayerInteractEvent event) {
		if(event.getItem() != null || event.getAction() != Action.RIGHT_CLICK_BLOCK || event.getClickedBlock() == null || !(event.getClickedBlock().getState() instanceof Sign) ||!event.getPlayer().isSneaking())
			return;
		ToolUser user = ToolUser.getUser(event.getPlayer());
		if(!user.getEditSign())
			return;
		
		if(ToolMain.isPlotEnabled()) {
			if(!ToolConfig.allowOutsidePlotArea || PlotSquaredUtil.inPlotArea(event.getPlayer())) {
				Block block = event.getClickedBlock();
				if(!PlotSquaredUtil.canEdit(event.getPlayer(), block)) {
					return;
				}
			}
		}
		
		Block block = event.getClickedBlock();
		Util.editSign(user.getPlayer(), block);
	}
	
	@EventHandler
	public void onSignChange(SignChangeEvent event) {
		Player p = event.getPlayer();
		if(p.getOpenInventory() != null && p.getOpenInventory().getTopInventory() != null && p.getOpenInventory().getTopInventory().getHolder() instanceof GuiHolder) {
			p.closeInventory();
		}
		if(ToolConfig.allowColorOnSign && event.getBlock().getState() instanceof Sign) {
			for(int i = 0; i <= 3; i++) {
	            String line = event.getLine(i);
	            line = ChatColor.translateAlternateColorCodes('&', line);
	            event.setLine(i, line);
	        }
		}
	}
	
	@EventHandler(ignoreCancelled=true)
	public void onDrop(PlayerDropItemEvent event) {
		Player p = event.getPlayer();
		ToolUser user = ToolUser.getUser(event.getPlayer());
		if(p.getOpenInventory() != null && p.getOpenInventory().getTopInventory() != null && p.getOpenInventory().getTopInventory().getHolder() instanceof GuiHolder)
			event.setCancelled(true);
		if(user.getNoLitter())
			event.getItemDrop().remove();
	}

}
