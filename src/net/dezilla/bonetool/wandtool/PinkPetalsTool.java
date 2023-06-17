package net.dezilla.bonetool.wandtool;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.data.type.PinkPetals;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;

import net.dezilla.bonetool.ToolUser;
import net.dezilla.bonetool.Util;
import net.dezilla.bonetool.util.Locale;

public class PinkPetalsTool extends WandTool{
	
	@Override
	public boolean isServerCompatible() {
		return checkClass("org.bukkit.block.data.type.PinkPetals");
	}

	@Override
	public boolean isBlockCompatible(Block block) {
		return block.getBlockData() instanceof PinkPetals;
	}

	@Override
	public ItemStack getIcon(Block block, ToolUser user) {
		String name = Locale.parse(user, "pinkpetals");
		if(block != null) {
			PinkPetals p = (PinkPetals) block.getBlockData();
			name+=": "+ChatColor.YELLOW+p.getFlowerAmount();
		}
		ItemStack icon = Util.setName(new ItemStack(Material.PINK_PETALS), name);
		if(block!=null) {
			Util.setLore(icon, getIntLore(user));
		}
		return icon;
	}
	
	@Override
	public void onLeftClick(InventoryClickEvent event, Block block) {
		PinkPetals p = (PinkPetals) block.getBlockData();
		int i = getIntValue(p.getFlowerAmount(), p.getMaximumFlowerAmount(), event.getClick());
		if(i == 0)
			i = 1;
		p.setFlowerAmount(i);
		block.setBlockData(p);
	}
	
	@Override
	public boolean canToggle() {
		return true;
	}
	
	@Override
	public void toggle(Block block) {
		PinkPetals p = (PinkPetals) block.getBlockData();
		if(p.getFlowerAmount() == p.getMaximumFlowerAmount())
			p.setFlowerAmount(1);
		else
			p.setFlowerAmount(p.getFlowerAmount()+1);
		block.setBlockData(p);
	}

}
