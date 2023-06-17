package net.dezilla.bonetool.util;

import java.lang.reflect.InvocationTargetException;

import org.bukkit.Bukkit;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;

import com.plotsquared.core.PlotAPI;
import com.plotsquared.core.location.Location;
import com.plotsquared.core.player.PlotPlayer;
import com.plotsquared.core.plot.Plot;
import com.sk89q.worldedit.math.BlockVector3;

import net.dezilla.bonetool.ToolMain;
import net.dezilla.bonetool.Util;

public class PlotSquaredUtil {
	
	public static boolean canEdit(Player player, Block block) {
		if(Util.permCheck(player, "bonetool.admin.plotbypass"))
			return true;
		if(Bukkit.getPluginManager().getPlugin("PlotSquared").getDescription().getVersion().startsWith("5"))
			try {
				return canEditPS5(player, block);
			}catch(Exception e) {
				ToolMain.getInstance().getLogger().info("[BoneTool] Please report this error to the plugin author.");
				e.printStackTrace();
				return false;
			}
		PlotAPI api = new PlotAPI();
		PlotPlayer<?> pl = api.wrapPlayer(player.getName());
		Location l = Location.at(block.getWorld().getName(), BlockVector3.at(block.getX(), block.getY(), block.getZ()));
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
	
	private static boolean canEditPS5(Player player, Block block) throws InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException, NoSuchMethodException, SecurityException {
		PlotAPI api = new PlotAPI();
		PlotPlayer<?> pl = api.wrapPlayer(player.getName());
		Location l = Location.class.getConstructor(String.class, Integer.class, Integer.class, Integer.class).newInstance(block.getWorld().getName(), block.getX(), block.getY(), block.getZ());
		//Location l = new Location(block.getWorld().getName(), block.getX(), block.getY(), block.getZ());
		Plot plot = Plot.getPlot(l);
		if(plot == null)
			return false;
		
		return plot.isAdded(pl.getUUID());
	}
}
