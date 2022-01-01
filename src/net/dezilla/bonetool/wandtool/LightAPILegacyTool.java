package net.dezilla.bonetool.wandtool;

import java.util.Arrays;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;

import net.dezilla.bonetool.Util;
import net.dezilla.bonetool.util.ToolConfig;
import ru.beykerykt.lightapi.LightAPI;
import ru.beykerykt.lightapi.LightType;
import ru.beykerykt.lightapi.chunks.ChunkInfo;

public class LightAPILegacyTool extends WandTool{
	//This version was made for LightAPI 3

	@Override
	public boolean isServerCompatible() {
		if(!ToolConfig.LightAPI)
			return false;
		if(Bukkit.getServer().getPluginManager().isPluginEnabled("LightAPI")) {
			return Bukkit.getServer().getPluginManager().getPlugin("LightAPI").getDescription().getVersion().startsWith("3");
		}
		return false;
	}

	@Override
	public boolean isBlockCompatible(Block block) {
		return true;
	}

	@Override
	public ItemStack getIcon(Block block) {
		if(block == null) {
			ItemStack icon = Util.setLore(Util.setName(new ItemStack(Material.NETHER_STAR), "Add Light"), Arrays.asList(
					ChatColor.GRAY+"Make the block become a light source",
					ChatColor.GRAY+"This will set the light level of the block at 15 (max)"
					));
			return icon;
		}
		String name = ChatColor.WHITE+"Light Level: "+ChatColor.YELLOW+block.getLightLevel();
		ItemStack icon = Util.setName(new ItemStack(Material.NETHER_STAR), name);
		if(block!=null)
			Util.setLore(icon, intLore);
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
		LightAPI.deleteLight(block.getLocation(), LightType.BLOCK, false);
		if(lvl != 0)
			LightAPI.createLight(block.getLocation(), LightType.BLOCK, lvl, false);
		
		for(ChunkInfo c : LightAPI.collectChunks(block.getWorld(), block.getX(), block.getY(), block.getZ(), LightType.BLOCK, lvl))
			LightAPI.updateChunk(c, LightType.BLOCK);
	}
	

}
