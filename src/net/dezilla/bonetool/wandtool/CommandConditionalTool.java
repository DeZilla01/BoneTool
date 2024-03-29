package net.dezilla.bonetool.wandtool;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.data.type.CommandBlock;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;

import net.dezilla.bonetool.ToolUser;
import net.dezilla.bonetool.Util;
import net.dezilla.bonetool.util.Locale;

public class CommandConditionalTool extends WandTool {
	
	@Override
	public boolean isServerCompatible() {
		return checkClass("org.bukkit.block.data.type.CommandBlock");
	}

	@Override
	public boolean isBlockCompatible(Block block) {
		return block.getBlockData() instanceof CommandBlock;
	}

	@Override
	public ItemStack getIcon(Block block, ToolUser user) {
		String name = ChatColor.WHITE+Locale.parse(user, "commandconditional");
		if(block != null)
			name += ": "+(isConditional(block) ? ChatColor.GREEN+Locale.parse(user, "true"):ChatColor.RED+Locale.parse(user, "false"));
		return Util.setName(new ItemStack(Material.COMMAND_BLOCK), name);
	}
	
	@Override
	public void onLeftClick(InventoryClickEvent event, Block block) {
		toggleConditional(block);
	}
	
	@Override
	public boolean canToggle() {
		return true;
	}
	
	@Override
	public void toggle(Block block) {
		toggleConditional(block);
	}
	
	public static boolean isConditional(Block block) {
		if(!(block.getBlockData() instanceof CommandBlock))
			return false;
		CommandBlock cb = (CommandBlock) block.getBlockData();
		return cb.isConditional();
	}
	
	public static void toggleConditional(Block block) {
		if(!(block.getBlockData() instanceof CommandBlock))
			return;
		CommandBlock cb = (CommandBlock) block.getBlockData();
		cb.setConditional(!cb.isConditional());
		block.setBlockData(cb);
	}

}
