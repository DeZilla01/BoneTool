package net.dezilla.bonetool.command;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.java.JavaPlugin;

import net.arcaniax.buildersutilities.Settings;
import net.arcaniax.buildersutilities.menus.Menus;
import net.arcaniax.buildersutilities.menus.SecretBlockMenuProvider;
import net.arcaniax.buildersutilities.menus.inv.ClickableItem;
import net.arcaniax.buildersutilities.menus.inv.InventoryListener;
import net.arcaniax.buildersutilities.menus.inv.InventoryManager;
import net.arcaniax.buildersutilities.menus.inv.SmartInventory;
import net.arcaniax.buildersutilities.menus.inv.content.InventoryContents;
import net.arcaniax.buildersutilities.menus.inv.content.InventoryProvider;
import net.dezilla.bonetool.ToolMain;
import net.dezilla.bonetool.Util;
import net.dezilla.bonetool.gui.LightBlockGui;
import net.dezilla.bonetool.gui.PaintingGui;
import net.dezilla.bonetool.util.LegacyBU;
import net.dezilla.bonetool.util.NewBU;
import net.dezilla.bonetool.util.ToolConfig;

public class BUtilSecretBlocksCommand extends Command implements CommandExecutor{
	//This command takes over Builder's Utilities's command to add additional blocks.
	
	//Let's create our own menu
	private static final InventoryListener<InventoryCloseEvent> removeGhostItemsListener =
            new InventoryListener<>(InventoryCloseEvent.class, inventoryCloseEvent -> {
                Bukkit.getScheduler().runTaskLater(getMainClass(), () -> {
                    ((Player) inventoryCloseEvent.getPlayer()).updateInventory();
                }, 1L);
            });
	
	@Override
	public boolean onCommand(CommandSender sender, Command command, String commandLabel, String[] args) {
		return execute(sender, commandLabel, args);
	}

	@Override
	public boolean execute(CommandSender sender, String command, String[] args) {
		if(!(sender instanceof Player)) {
			sender.sendMessage("You must be a player to use this command.");
			return true;
		}
		Player player = (Player) sender;
		if (!player.hasPermission("builders.util.secretblocks")) {
            if (Settings.sendErrorMessages) {
                player.sendMessage(getNoPermMsg() + "builders.util.secretblocks");
            }
            return true;
        }

        SECRET_BLOCK_MENU.open(player);
        return true;
	}
	
	static final int ORIGINAL_MENU_SIZE = Menus.SECRET_BLOCK_MENU.getRows();
	
	public static final SmartInventory SECRET_BLOCK_MENU = SmartInventory.builder()
            .manager(getInvManager())
            .id("buildersutilssecretblock")
            .provider(new SecretBlockMenuProviderWithBlackjackAndHookers())
            .size(ORIGINAL_MENU_SIZE+2, 9)
            .listener(removeGhostItemsListener)
            .title(ChatColor.BLUE + "Secret Blocks")
            .closeable(true)
            .build();
	
	public BUtilSecretBlocksCommand() {
		super("blocks", "Opens secret blocks menu", "/blocks", Arrays.asList("secretblocks"));
	}
	
	static class SecretBlockMenuProviderWithBlackjackAndHookers implements InventoryProvider {
		
		@Override
		public void init(Player player, InventoryContents contents) {
			new SecretBlockMenuProvider().init(player, contents);
			if(!Util.permCheck(player, "bonetool.blocks.use"))
				return;
			int row = 0;
			int col = 0;
			for(ItemStack item : getBlocks()) {
				if(col>=7 || item.getType() == Material.COMMAND_BLOCK) {
					col = 0;
					row++;
				}
				contents.set(ORIGINAL_MENU_SIZE+row, col++, ClickableItem.of(item, inventoryClickEvent -> player.getInventory().addItem(item)));
			}
			if(ToolConfig.paintingByName && Util.permCheck(player, "bonetool.blocks.paintings"))
				contents.set(ORIGINAL_MENU_SIZE, 8, ClickableItem.of(Util.setName(new ItemStack(Material.PAINTING), "Paintings"), inventoryClickEvent -> new PaintingGui(player).display()));
			if(Util.permCheck(player, "bonetool.blocks.lightblock") && ToolMain.getVersionNumber() >= 17)
				contents.set(ORIGINAL_MENU_SIZE+1, 8, ClickableItem.of(Util.setName(new ItemStack(Material.LIGHT), "Light Block"), inventoryClickEvent -> new LightBlockGui(player).display()));
		}
		
	}
	
	static JavaPlugin getMainClass() {
		try {
			Class.forName("net.arcaniax.buildersutilities.Main");
			return LegacyBU.getMainClass();
		}catch(Exception e) {
			return NewBU.getMainClass();
		}
	}
	
	static String getNoPermMsg() {
		try {
			Class.forName("net.arcaniax.buildersutilities.Main");
			return LegacyBU.getNoPermMsg();
		}catch(Exception e) {
			return NewBU.getNoPermMsg();
		}
	}
	
	static InventoryManager getInvManager() {
		try {
			Class.forName("net.arcaniax.buildersutilities.Main");
			return LegacyBU.getInvManager();
		}catch(Exception e) {
			return NewBU.getInvManager();
		}
	}
	
	private static List<ItemStack> getBlocks() {
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
		if(ToolConfig.commandBlocks) {
			list.add(new ItemStack(Material.COMMAND_BLOCK));
			list.add(new ItemStack(Material.CHAIN_COMMAND_BLOCK));
			list.add(new ItemStack(Material.REPEATING_COMMAND_BLOCK));
		}
		if(ToolConfig.commandBlockMinecart)
			list.add(new ItemStack(Material.COMMAND_BLOCK_MINECART));
		if(ToolConfig.sculkSensor)
			list.add(new ItemStack(Material.SCULK_SENSOR));
		if(ToolConfig.jigsaw)
			list.add(new ItemStack(Material.JIGSAW));
		if(ToolConfig.invisFrame)
			list.add(Util.getInvisibleFrame());
		return list;
	}
}
