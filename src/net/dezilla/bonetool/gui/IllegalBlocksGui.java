package net.dezilla.bonetool.gui;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import net.dezilla.bonetool.ToolMain;
import net.dezilla.bonetool.Util;
import net.dezilla.bonetool.util.InventoryRunnable;
import net.dezilla.bonetool.util.ToolConfig;

public class IllegalBlocksGui extends GuiPage{

	public IllegalBlocksGui(Player player) {
		super(6, player);
		setName("BoneTool Secret Blocks");
		int row = 0;
		int col = 0;
		for(ItemStack item : getBlocks()) {
			setItem(row,col++, new GuiItem(item).setRun(getRun(item)));
			if(col>=7) {
				col=0;
				row++;
			}
		}
		if(ToolConfig.paintingByName && Util.permCheck(player, "bonetool.blocks.paintings")) {
			GuiItem paintings = new GuiItem(Util.setName(new ItemStack(Material.PAINTING), "Paintings"));
			paintings.setRun((event) -> new PaintingGui(player).display());
			setItem(0, 8, paintings);
		}
		if(ToolConfig.lightBlock && Util.permCheck(player, "bonetool.blocks.lightblock") && ToolMain.getVersionNumber()>=17) {
			GuiItem lightBlocks = new GuiItem(Util.setName(new ItemStack(Material.LIGHT), "Light Block"));
			lightBlocks.setRun((event) -> new LightBlockGui(player).display());
			setItem(1, 8, lightBlocks);
		}
		this.removeEmptyRows();
	}
	
	private InventoryRunnable getRun(ItemStack item) {
		return ((event) -> event.getWhoClicked().getInventory().addItem(item));
	}
	
	private List<ItemStack> getBlocks(){
		List<ItemStack> list = new ArrayList<ItemStack>();
		if(ToolConfig.netherPortal)
			list.add(Util.getNetherPortalItem());
		if(ToolConfig.endPortal)
			list.add(Util.GetEndPortalItem());
		if(ToolConfig.pistonHead)
			list.add(Util.getPistonHeadItem());
		if(ToolConfig.doubleLadder)
			list.add(Util.getDoubleLadderItem());
		if(ToolConfig.litRedstoneLamp)
			list.add(Util.getLitLampItem());
		if(ToolConfig.debugStick)
			list.add(new ItemStack(Material.DEBUG_STICK));
		if(ToolConfig.commandBlockMinecart)
			list.add(new ItemStack(Material.COMMAND_BLOCK_MINECART));
		if(ToolConfig.commandBlocks) {
			list.add(new ItemStack(Material.COMMAND_BLOCK));
			list.add(new ItemStack(Material.CHAIN_COMMAND_BLOCK));
			list.add(new ItemStack(Material.REPEATING_COMMAND_BLOCK));
		}
		if(ToolConfig.spawner)
			list.add(new ItemStack(Material.SPAWNER));
		if(ToolConfig.barrier)
			list.add(new ItemStack(Material.BARRIER));
		if(ToolConfig.structureVoid)
			list.add(new ItemStack(Material.STRUCTURE_VOID));
		if(ToolConfig.dragonEgg)
			list.add(new ItemStack(Material.DRAGON_EGG));
		if(ToolConfig.structureBlock)
			list.add(new ItemStack(Material.STRUCTURE_BLOCK));
		if(ToolConfig.sculkSensor && ToolMain.getVersionNumber() < 19)
			list.add(new ItemStack(Material.SCULK_SENSOR));
		if(ToolConfig.jigsaw)
			list.add(new ItemStack(Material.JIGSAW));
		if(ToolConfig.invisFrame)
			list.add(Util.getInvisibleFrame());
		return list;
	}

}
