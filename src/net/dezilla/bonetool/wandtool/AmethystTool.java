package net.dezilla.bonetool.wandtool;

import java.util.Arrays;
import java.util.List;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;

import net.dezilla.bonetool.ToolMain;
import net.dezilla.bonetool.ToolUser;
import net.dezilla.bonetool.Util;
import net.dezilla.bonetool.util.Locale;

public class AmethystTool extends WandTool {
	
	@Override
	public boolean isServerCompatible() {
		return ToolMain.getVersionNumber() >= 17;
	}

	@Override
	public boolean isBlockCompatible(Block block) {
		return amethystMaterial().contains(block.getType());
	}

	@Override
	public ItemStack getIcon(Block block, ToolUser user) {
		String name = Locale.parse(user, "amthystsize");
		Material mat = Material.AMETHYST_CLUSTER;
		if(block != null) {
			name += ": "+ChatColor.YELLOW+getNames(user).get(amethystMaterial().indexOf(block.getType()));
			mat = block.getType();
		}
		return Util.setName(new ItemStack(mat), name);
	}
	
	private List<String> getNames(ToolUser user){
		return Arrays.asList(
				Locale.parse(user, "small"),
				Locale.parse(user, "medium"),
				Locale.parse(user, "large"),
				Locale.parse(user, "cluster"));
	}
	
	@Override
	public void onLeftClick(InventoryClickEvent event, Block block) {
		toggleAmethyst(block);
	}
	
	@Override
	public boolean canToggle() {
		return true;
	}
	
	@Override
	public void toggle(Block block) {
		toggleAmethyst(block);
	}
	
	public static void toggleAmethyst(Block block) {
		if(!amethystMaterial().contains(block.getType()))
			return;
		boolean water = WaterloggedTool.isWaterlogged(block);
		BlockFace face = DirectionalTool.getFacing(block);
		if(amethystMaterial().indexOf(block.getType())+1>=amethystMaterial().size())
			block.setType(amethystMaterial().get(0));
		else
			block.setType(amethystMaterial().get(amethystMaterial().indexOf(block.getType())+1));
		WaterloggedTool.setWaterlogged(block, water);
		DirectionalTool.setFacing(block, face);
	}
	
	private static List<Material> amethystMaterial(){
		return Arrays.asList(Material.SMALL_AMETHYST_BUD, Material.MEDIUM_AMETHYST_BUD, Material.LARGE_AMETHYST_BUD, Material.AMETHYST_CLUSTER);
	}

}
