package net.dezilla.bonetool.util;

import org.bukkit.block.Block;
import org.bukkit.entity.Player;

import com.plotsquared.core.api.PlotAPI;
import com.plotsquared.core.location.Location;
import com.plotsquared.core.player.PlotPlayer;
import com.plotsquared.core.plot.Plot;

import net.dezilla.bonetool.Util;

public class PlotSquaredUtil {
	
	public static boolean canEdit(Player player, Block block) {
		if(Util.permCheck(player, "bonetool.admin.plotbypass"))
			return true;
		PlotAPI api = new PlotAPI();
		PlotPlayer<?> pl = api.wrapPlayer(player.getName());
		Location l = new Location(block.getWorld().getName(), block.getX(), block.getY(), block.getZ());
		Plot plot = Plot.getPlot(l);
		if(plot == null)
			return false;
		
		return plot.isAdded(pl.getUUID());
	}
	
	public static boolean inPlotArea(Player player) {
		PlotAPI api = new PlotAPI();
		PlotPlayer<?> pl = api.wrapPlayer(player.getName());
		return pl.getApplicablePlotArea() != null;
	}

}
