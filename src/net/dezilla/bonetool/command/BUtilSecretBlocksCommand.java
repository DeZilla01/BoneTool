package net.dezilla.bonetool.command;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

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
import net.dezilla.bonetool.util.NewBU;
import net.dezilla.bonetool.util.ToolConfig;

@Deprecated
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
			List<Material> banned = bannedItems();
			new SecretBlockMenuProvider().init(player, contents);
			for(int i = 0; i < 9; i++) {
				Optional<ClickableItem> item = contents.get(0, i);
				if(item == null)
					continue;
				if(banned.contains(item.get().getItem().getType()))
					contents.set(0, i, null);
			}
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
			if(ToolConfig.paintingByName && Util.permCheck(player, "bonetool.blocks.paintings") && ToolMain.getVersionNumber() < 20)
				contents.set(ORIGINAL_MENU_SIZE, 8, ClickableItem.of(Util.setName(new ItemStack(Material.PAINTING), "Paintings"), inventoryClickEvent -> new PaintingGui(player).display()));
			if(Util.permCheck(player, "bonetool.blocks.lightblock") && ToolMain.getVersionNumber() >= 17)
				contents.set(ORIGINAL_MENU_SIZE+1, 8, ClickableItem.of(Util.setName(new ItemStack(Material.LIGHT), "Light Block"), inventoryClickEvent -> new LightBlockGui(player).display()));
		}
		
	}
	
	static JavaPlugin getMainClass() {
		return NewBU.getMainClass();
	}
	
	static String getNoPermMsg() {
		return NewBU.getNoPermMsg();
	}
	
	static InventoryManager getInvManager() {
		return NewBU.getInvManager();
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
	
	static List<Material> bannedItems(){
		List<Material> m = new ArrayList<Material>();
		if(!ToolConfig.sculkSensor && ToolMain.getVersionNumber()>=17)
			m.add(Material.SCULK_SENSOR);
		if(!ToolConfig.jigsaw)
			m.add(Material.JIGSAW);
		if(!ToolConfig.debugStick)
			m.add(Material.DEBUG_STICK);
		if(!ToolConfig.spawner)
			m.add(Material.SPAWNER);
		if(!ToolConfig.barrier)
			m.add(Material.BARRIER);
		if(!ToolConfig.structureVoid)
			m.add(Material.STRUCTURE_VOID);
		if(!ToolConfig.dragonEgg)
			m.add(Material.DRAGON_EGG);
		if(!ToolConfig.structureBlock)
			m.add(Material.STRUCTURE_BLOCK);
		if(!ToolConfig.lightBlock && ToolMain.getVersionNumber()>=17)
			m.add(Material.LIGHT);
		return m;
	}
}
