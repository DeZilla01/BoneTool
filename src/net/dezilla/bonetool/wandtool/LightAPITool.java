package net.dezilla.bonetool.wandtool;

import java.util.Arrays;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;

import net.dezilla.bonetool.ToolUser;
import net.dezilla.bonetool.Util;
import net.dezilla.bonetool.util.Locale;
import net.dezilla.bonetool.util.ToolConfig;
import ru.beykerykt.minecraft.lightapi.common.LightAPI;

public class LightAPITool extends WandTool{
	//This version was made for LightAPI 3

	@Override
	public boolean isServerCompatible() {
		if(!ToolConfig.LightAPI)
			return false;
		if(Bukkit.getServer().getPluginManager().isPluginEnabled("LightAPI")) {
			try {
				return Bukkit.getServer().getPluginManager().getPlugin("LightAPI").getDescription().getVersion().startsWith("bukkit-5");
			}
			catch(Exception e) {
				return false;
			}
		}
		return false;
	}

	@Override
	public boolean isBlockCompatible(Block block) {
		return true;
	}

	@Override
	public ItemStack getIcon(Block block, ToolUser user) {
		if(block == null) {
			ItemStack icon = Util.setLore(Util.setName(new ItemStack(Material.NETHER_STAR), Locale.parse(user, "addlight")), Arrays.asList(
					ChatColor.GRAY+Locale.parse(user, "lightinstructions1"),
					ChatColor.GRAY+Locale.parse(user, "lightinstructions2")
					));
			return icon;
		}
		int lightlvl = LightAPI.get().getLightLevel(block.getWorld().getName(), block.getX(), block.getY(), block.getZ());
		String name = ChatColor.WHITE+Locale.parse(user, "lightlevel")+": "+ChatColor.YELLOW+lightlvl;
		ItemStack icon = Util.setName(new ItemStack(Material.NETHER_STAR), name);
		if(block!=null)
			Util.setLore(icon, getIntLore(user));
		return icon;
	}
	
	@Override
	public void onLeftClick(InventoryClickEvent event, Block block) {
		int lvl = getIntValue(block.getLightFromBlocks(), 15, event.getClick());
		
		applyLight(block, lvl);
	}
	
	@Override
	public boolean isRightClickTool() {
		return true;
	}
	
	@Override
	public void onRightClick(PlayerInteractEvent event, Block block) {
		applyLight(block, 15);
	}
	
	private void applyLight(Block block, int lvl) {
		LightAPI.get().setLightLevel(block.getWorld().getName(), block.getX(), block.getY(), block.getZ(), lvl);
	}
	

}
