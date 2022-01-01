package net.dezilla.bonetool.wandtool;

import java.util.Arrays;
import java.util.List;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.data.type.TechnicalPiston;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;

import net.dezilla.bonetool.Util;

public class PistonTypeTool extends WandTool{

	@Override
	public boolean isServerCompatible() {
		return checkClass("org.bukkit.block.data.type.TechnicalPiston");
	}

	@Override
	public boolean isBlockCompatible(Block block) {
		return block.getBlockData() instanceof TechnicalPiston;
	}
	
	@Override
	public void onLeftClick(InventoryClickEvent event, Block block) {
		togglePistonType(block);
	}

	@Override
	public ItemStack getIcon(Block block) {
		String name = "Piston Type";
		if(block != null) {
			TechnicalPiston p = (TechnicalPiston) block.getBlockData();
			name+=": "+ChatColor.YELLOW+p.getType();
		}
		ItemStack icon = Util.setName(new ItemStack(Material.PISTON), name);
		return icon;
	}
	
	@Override
	public boolean canToggle() {
		return true;
	}
	
	@Override
	public void toggle(Block block) {
		togglePistonType(block);
	}
	
	public static void togglePistonType(Block block) {
		if(!(block.getBlockData() instanceof TechnicalPiston)) 
			return;
		
		TechnicalPiston piston = (TechnicalPiston) block.getBlockData();
		List<TechnicalPiston.Type> types = Arrays.asList(TechnicalPiston.Type.values());
		if(types.indexOf(piston.getType())+1 >= types.size())
			piston.setType(types.get(0));
		else
			piston.setType(types.get(types.indexOf(piston.getType())+1));
		block.setBlockData(piston);
	}

}
