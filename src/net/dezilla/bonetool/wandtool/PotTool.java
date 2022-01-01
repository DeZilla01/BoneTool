package net.dezilla.bonetool.wandtool;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;

import net.dezilla.bonetool.Util;
import net.dezilla.bonetool.gui.PotSelectGui;

public class PotTool extends WandTool {

	@Override
	public boolean isServerCompatible() {
		return true;
	}

	@Override
	public boolean isBlockCompatible(Block block) {
		return isPot(block.getType());
	}

	@Override
	public ItemStack getIcon(Block block) {
		ItemStack icon = Util.setName(new ItemStack(Material.FLOWER_POT), "Flower Pot");
		if(block != null) {
			icon = Util.setName(getItemIcon(block.getType()), "Flower Pot: "+ChatColor.YELLOW+getItemIcon(block.getType()).getItemMeta().getDisplayName());
		}
		return icon;
	}
	
	@Override
	public void onLeftClick(InventoryClickEvent event, Block block) {
		new PotSelectGui((Player) event.getWhoClicked(), block).display();
	}
	
	@Override
	public boolean canToggle() {
		return true;
	}
	
	@Override
	public void toggle(Block block) {
		togglePot(block);
	}
	
	@Override
	public boolean hasGui() {
		return true;
	}
	
	@Override
	public void openGui(Player player, Block block) {
		new PotSelectGui(player, block).display();
	}
	
	public static boolean isPot(Material material) {
		return material.toString().startsWith("POTTED_") || material == Material.FLOWER_POT;
	}
	
	public static ItemStack getItemIcon(Material material) {
		if(!isPot(material))
			return Util.setName(new ItemStack(Material.BARRIER), "Not a pot");
		else if(material == Material.FLOWER_POT)
			return Util.setName(new ItemStack(Material.FLOWER_POT), "Empty");
		try {
			Material m = Material.STONE;
			if(material.toString().equalsIgnoreCase("POTTED_AZALEA_BUSH"))
				m = Material.valueOf("AZALEA");
			else if(material.toString().equalsIgnoreCase("POTTED_FLOWERING_AZALEA_BUSH"))
				m = Material.valueOf("FLOWERING_AZALEA");
			else
				m = Material.valueOf(material.toString().replace("POTTED_", ""));
			return Util.setName(new ItemStack(m), Util.matName(m));
		}catch(Exception e) {
			return Util.setName(new ItemStack(Material.STONE), Util.matName(material));
		}
		
	}
	
	public static void togglePot(Block block) {
		if(!isPot(block.getType()))
			return;
		
		List<Material> materials = new ArrayList<Material>();
		for(Material m : Material.values())
			if(isPot(m))
				materials.add(m);
		
		if(materials.indexOf(block.getType())+1 >= materials.size())
			block.setType(materials.get(0));
		else
			block.setType(materials.get(materials.indexOf(block.getType())+1));
	}

}
