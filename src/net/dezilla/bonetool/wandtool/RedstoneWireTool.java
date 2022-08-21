package net.dezilla.bonetool.wandtool;

import java.util.Arrays;
import java.util.List;

import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.block.data.type.RedstoneWire;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;

import net.dezilla.bonetool.ToolUser;
import net.dezilla.bonetool.Util;
import net.dezilla.bonetool.gui.RedstoneWireGui;
import net.dezilla.bonetool.util.Locale;

public class RedstoneWireTool extends WandTool {
	
	@Override
	public boolean isServerCompatible() {
		return checkClass("org.bukkit.block.data.type.RedstoneWire");
	}

	@Override
	public boolean isBlockCompatible(Block block) {
		return block.getBlockData() instanceof RedstoneWire;
	}

	@Override
	public ItemStack getIcon(Block block, ToolUser user) {
		String name = Locale.parse(user, "redstoneconnection");
		return Util.setName(new ItemStack(Material.REDSTONE), name);
	}
	
	@Override
	public void onLeftClick(InventoryClickEvent event, Block block) {
		new RedstoneWireGui((Player) event.getWhoClicked(), block).display();
	}
	
	@Override
	public boolean hasGui() {
		return true;
	}
	
	@Override
	public void openGui(Player player, Block block) {
		new RedstoneWireGui(player, block).display();
	}
	
	public static void toggleConnection(Block block, BlockFace face) {
		if(!(block.getBlockData() instanceof RedstoneWire))
			return;
		RedstoneWire wire = (RedstoneWire) block.getBlockData();
		if(!wire.getAllowedFaces().contains(face))
			return;
		List<RedstoneWire.Connection> list = Arrays.asList(RedstoneWire.Connection.values());
		if(list.indexOf(wire.getFace(face))+1 >= list.size())
			wire.setFace(face, list.get(0));
		else
			wire.setFace(face, list.get(list.indexOf(wire.getFace(face))+1));
		block.setBlockData(wire);
	}

}
