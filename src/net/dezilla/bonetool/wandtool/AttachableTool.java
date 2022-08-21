package net.dezilla.bonetool.wandtool;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.data.Attachable;
import org.bukkit.block.data.BlockData;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;

import net.dezilla.bonetool.ToolUser;
import net.dezilla.bonetool.Util;
import net.dezilla.bonetool.util.Locale;

public class AttachableTool extends WandTool{

	@Override
	public boolean isServerCompatible() {
		return checkClass("org.bukkit.block.data.Attachable");
	}

	@Override
	public boolean isBlockCompatible(Block block) {
		return block.getBlockData() instanceof Attachable;
	}
	
	@Override
	public void onLeftClick(InventoryClickEvent event, Block block) {
		toggleAttach(block);
	}

	@Override
	public ItemStack getIcon(Block block, ToolUser user) {
		String name = Locale.parse(user, "attached");
		if(block != null)
			name += ": "+(isAttached(block) ? ChatColor.GREEN+"True":ChatColor.RED+"False");
		return Util.setName(new ItemStack(Material.STRING), name);
	}
	
	@Override
	public boolean canToggle() {
		return true;
	}
	
	@Override
	public void toggle(Block block) {
		toggleAttach(block);
	}
	
	public static boolean isAttached(Block block) {
		if(block.getBlockData() instanceof Attachable) {
			Attachable attach = (Attachable) block.getBlockData();
			return isAttached(attach);
		}
		return false;
	}
	
	public static boolean isAttached(BlockData data) {
		if(data instanceof Attachable) {
			Attachable attach = (Attachable) data;
			return isAttached(attach);
		}
		return false;
	}
	
	public static boolean isAttached(Attachable data) {
		return data.isAttached();
	}
	
	public static Block setAttached(Block block, boolean value) {
		if(block.getBlockData() instanceof Attachable) {
			Attachable attach = (Attachable) block.getBlockData();
			attach.setAttached(value);
			block.setBlockData(attach);
		}
		return block;
	}
	
	public static BlockData setAttached(BlockData data, boolean value) {
		if(data instanceof Attachable) {
			((Attachable) data).setAttached(value);
		}
		return data;
	}
	
	public static Attachable setAttached(Attachable data, boolean value) {
		data.setAttached(value);
		return data;
	}
	
	public static Attachable toggleAttach(Attachable data) {
		if(isAttached(data))
			setAttached(data, false);
		else
			setAttached(data, true);
		return data;
	}
	
	public static BlockData toggleAttach(BlockData data) {
		if(isAttached(data))
			setAttached(data, false);
		else
			setAttached(data, true);
		return data;
	}

	
	public static Block toggleAttach(Block block) {
		if(isAttached(block))
			setAttached(block, false);
		else
			setAttached(block, true);
		return block;
	}

}
