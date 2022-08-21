package net.dezilla.bonetool.wandtool;

import java.util.Arrays;
import java.util.List;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.data.type.Jigsaw;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;

import net.dezilla.bonetool.ToolUser;
import net.dezilla.bonetool.Util;
import net.dezilla.bonetool.util.Locale;

public class JigsawTool extends WandTool {
	
	@Override
	public boolean isServerCompatible() {
		return checkClass("org.bukkit.block.data.type.Jigsaw");
	}

	@Override
	public boolean isBlockCompatible(Block block) {
		return block.getBlockData() instanceof Jigsaw;
	}
	
	@Override
	public void onLeftClick(InventoryClickEvent event, Block block) {
		toggleJigsaw(block);
	}

	@Override
	public ItemStack getIcon(Block block, ToolUser user) {
		String name = Locale.parse(user, "jigsaworientation");
		if(block != null) {
			Jigsaw j = (Jigsaw) block.getBlockData();
			name+=": "+ChatColor.YELLOW+j.getOrientation().toString();
		}
		ItemStack icon = Util.setName(new ItemStack(Material.JIGSAW), name);
		return icon;
	}
	
	@Override
	public boolean canToggle() {
		return true;
	}
	
	@Override
	public void toggle(Block block) {
		toggleJigsaw(block);
	}
	
	public static void toggleJigsaw(Block block) {
		if(!(block.getBlockData() instanceof Jigsaw))
			return;
		Jigsaw j = (Jigsaw) block.getBlockData();
		List<Jigsaw.Orientation> list = Arrays.asList(Jigsaw.Orientation.values());
		if(list.indexOf(j.getOrientation())+1 >= list.size())
			j.setOrientation(list.get(0));
		else
			j.setOrientation(list.get(list.indexOf(j.getOrientation())+1));
		block.setBlockData(j);
	}

}
