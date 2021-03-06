package net.dezilla.bonetool.gui;

import java.util.Arrays;
import java.util.List;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.inventory.ItemStack;

import net.dezilla.bonetool.ToolMain;
import net.dezilla.bonetool.ToolUser;
import net.dezilla.bonetool.ToolUser.PlacingState;
import net.dezilla.bonetool.Util;
import net.dezilla.bonetool.wandtool.NoneTool;
import net.dezilla.bonetool.wandtool.WandTool;

public class ToolGui extends GuiPage{
	ToolUser user;

	public ToolGui(Player player) {
		super(2, player);
		user = ToolUser.getUser(player);
		setName(""+ChatColor.WHITE+ChatColor.BOLD+"BoneTool");
		addItems();
	}
	
	private void addItems() {
		//
		setItem(1, 0, getRightClickItem());
		setItem(0, 0, getGlass(ToolUser.getUser(getPlayer()).getRightClickTool() instanceof NoneTool ? false : true));
		//
		if(Util.permCheck(getPlayer(), "bonetool.tool.snipeplace")) {
			setItem(1, 3, getSnipePlacingItem());
			setItem(0, 3, getGlass(user.getSnipePlaceState()));
		} else {
			setItem(1, 3, noAccess("Snipe Placing"));
			setItem(0, 3, getGlass(false));
		}
		//
		if(Util.permCheck(getPlayer(), "bonetool.tool.nobupdate")) {
			setItem(1, 1, getNoBlockUpdateItem());
			setItem(0, 1, getGlass(user.getBlockUpdateState()));
		} else {
			setItem(1, 1, noAccess("No Block Update"));
			setItem(0, 1, getGlass(false));
		}
		//
		if(Util.permCheck(getPlayer(), "bonetool.tool.forceplace")) {
			setItem(1, 2, getForcePlacingItem());
			setItem(0, 2, getGlass(user.getForcePlacingState()));
		} else {
			setItem(1, 2, noAccess("Force Placing"));
			setItem(0, 2, getGlass(false));
		}
		//
		if(Util.permCheck(getPlayer(), "bonetool.tool.flyspeed")) {
			setItem(1, 4, getFlySpeedItem());
			setItem(0, 4, getGlass(user.getEditFlySpeed()));
		} else {
			setItem(1, 4, noAccess("Fly Speed"));
			setItem(0, 4, getGlass(false));
		}
		//
		if(Util.permCheck(getPlayer(), "bonetool.tool.signedit")) {
			setItem(1, 5, getSignItem());
			setItem(0, 5, getGlass(user.getEditSign()));
		} else {
			setItem(1, 5, noAccess("Edit Sign"));
			setItem(0, 5, getGlass(false));
		}
		//
		if(Util.permCheck(getPlayer(), "bonetool.tool.nolitter")) {
			setItem(1, 6, getLitterItem());
			setItem(0, 6, getGlass(user.getNoLitter()));
		} else {
			setItem(1, 6, noAccess("No Litter"));
			setItem(0, 6, getGlass(false));
		}
		//
		setItem(1, 8, getWandItem());
		setItem(0, 8, getNotificationItem());
	}
	
	private void refresh() {
		clear();
		addItems();
		display();
	}
	
	public GuiItem getRightClickItem() {
		WandTool t = ToolUser.getUser(getPlayer()).getRightClickTool();
		GuiItem item = new GuiItem(t.getIcon());
		item.setRun(event -> {
			new RightClickSelectGui(getPlayer()).display();
		});
		return item;
	}
	
	public GuiItem getFlySpeedItem() {
		ToolUser user = ToolUser.getUser(getPlayer());
		String name = ChatColor.WHITE+"Edit Fly Speed: "+(user.getEditFlySpeed() ? ChatColor.GREEN+"True":ChatColor.RED+"False");
		ItemStack icon = Util.setName(new ItemStack(Material.ELYTRA), name);
		Util.setLore(icon, Arrays.asList(
				ChatColor.GRAY+"Allow the user to edit fly speed",
				ChatColor.GRAY+"by double sneaking and scrolling."));
		GuiItem item = new GuiItem(icon);
		item.setRun(event -> {
			user.setEditFlySpeed(!user.getEditFlySpeed());
			refresh();
		});
		return item;
	}
	
	public GuiItem getSignItem() {
		ToolUser user = ToolUser.getUser(getPlayer());
		String name = ChatColor.WHITE+"Edit Signs: "+(user.getEditSign() ? ChatColor.GREEN+"True":ChatColor.RED+"False");
		ItemStack icon = Util.setName(new ItemStack(Material.OAK_SIGN), name);
		Util.setLore(icon, Arrays.asList(
				ChatColor.GRAY+"Allow the user to edit signs by sneaking",
				ChatColor.GRAY+"and right clicking with bare hands."));
		GuiItem item = new GuiItem(icon);
		item.setRun(event -> {
			user.setEditSign(!user.getEditSign());
			refresh();
		});
		return item;
	}
	
	public GuiItem getLitterItem() {
		ToolUser user = ToolUser.getUser(getPlayer());
		String name = ChatColor.WHITE+"No Litter: "+(user.getNoLitter() ? ChatColor.GREEN+"True": ChatColor.RED+"False");
		ItemStack icon = Util.setName(new ItemStack(Material.BONE_MEAL), name);
		Util.setLore(icon, ChatColor.GRAY+"Automatically delete item when dropped.");
		GuiItem item = new GuiItem(icon);
		item.setRun(event -> {
			user.setNoLitter(!user.getNoLitter());
			refresh();
		});
		return item;
	}
	
	private GuiItem getNotificationItem() {
		ToolUser user = ToolUser.getUser(getPlayer());
		String name = ChatColor.WHITE+"Notification: "+user.getNotificationLoc();
		ItemStack icon = Util.setName(new ItemStack(Material.WHITE_DYE), name);
		GuiItem item = new GuiItem(icon);
		item.setRun(event -> {
			user.toggleNotificationLoc();
			refresh();
		});
		return item;
	}
	
	private GuiItem getWandItem() {
		ToolUser user = ToolUser.getUser(getPlayer());
		ItemStack icon = Util.setName(new ItemStack(user.getWandMaterial()), "Wand Material");
		icon = Util.setLore(icon, Arrays.asList(ChatColor.GRAY+"Drag an item here to set as your wand.", ChatColor.GRAY+"Shift + Right Click to spawn a wand in your inventory."));
		GuiItem item = new GuiItem(icon);
		item.setRun(event -> {
			if(event.getClick() == ClickType.SHIFT_RIGHT) {
				ItemStack wand = Util.setName(new ItemStack(user.getWandMaterial()), "BoneTool");
				getPlayer().getInventory().addItem(Util.setLore(wand, ChatColor.GRAY+"A tool to edit block properties and more."));
				return;
			}
			if(event.getCursor()==null||event.getCursor().getType()==null||event.getCursor().getType()==Material.AIR)
				return;
			user.setWandMaterial(event.getCursor().getType());
			ToolMain.getInstance().getServer().getScheduler().scheduleSyncDelayedTask(ToolMain.getInstance(), () -> refresh(), 2);
		});
		return item;
	}
	
	private GuiItem getNoBlockUpdateItem() {
		GuiItem item = new GuiItem(blockupdateicon());
		item.setRun(event -> {
			List<PlacingState> states = Arrays.asList(PlacingState.values());
			if(states.indexOf(user.getBlockUpdateState())+1 >= states.size())
				user.setNoBlockUpdate(states.get(0));
			else
				user.setNoBlockUpdate(states.get(states.indexOf(user.getBlockUpdateState())+1));
			refresh();
		});
		return item;
	}
	
	private ItemStack blockupdateicon() {
		String name = "No Block Update: ";
		switch(user.getBlockUpdateState()) {
			case DISABLED: name+=ChatColor.RED+"Disabled";break;
			case ENABLED: name+=ChatColor.GREEN+"Enabled";break;
			case ONSNEAK: name+=ChatColor.AQUA+"On Sneak";break;
			case SNEAKTOGGLE: name+=ChatColor.YELLOW+"Sneak Toggle";break;
		}
		return Util.setName(new ItemStack((user.getBlockUpdateState() == PlacingState.DISABLED ? Material.SAND : Material.SANDSTONE)), name);
	}
	
	private GuiItem getForcePlacingItem() {
		GuiItem item = new GuiItem(forceplaceitem());
		item.setRun(event -> {
			List<PlacingState> states = Arrays.asList(PlacingState.values());
			if(states.indexOf(user.getForcePlacingState())+1 >= states.size())
				user.setForcePlace(states.get(0));
			else
				user.setForcePlace(states.get(states.indexOf(user.getForcePlacingState())+1));
			refresh();
		});
		return item;
	}
	
	private GuiItem getSnipePlacingItem() {
		GuiItem item = new GuiItem(snipeplaceitem());
		item.setRun(event -> {
			List<PlacingState> states = Arrays.asList(PlacingState.values());
			if(states.indexOf(user.getSnipePlaceState())+1 >= states.size())
				user.setSnipePlace(states.get(0));
			else
				user.setSnipePlace(states.get(states.indexOf(user.getSnipePlaceState())+1));
			refresh();
		});
		return item;
	}
	
	private ItemStack forceplaceitem() {
		String name = "Force Placing: ";
		switch(user.getForcePlacingState()) {
			case DISABLED: name+=ChatColor.RED+"Disabled";break;
			case ENABLED: name+=ChatColor.GREEN+"Enabled";break;
			case ONSNEAK: name+=ChatColor.AQUA+"On Sneak";break;
			case SNEAKTOGGLE: name+=ChatColor.YELLOW+"Sneak Toggle";break;
		}
		return Util.setName(new ItemStack((user.getForcePlacingState() == PlacingState.DISABLED ? Material.WITHER_ROSE : Material.BLUE_ORCHID)), name);
	}
	
	private ItemStack snipeplaceitem() {
		String name = "Snipe Placing: ";
		switch(user.getSnipePlaceState()) {
			case DISABLED: name+=ChatColor.RED+"Disabled";break;
			case ENABLED: name+=ChatColor.GREEN+"Enabled";break;
			case ONSNEAK: name+=ChatColor.AQUA+"On Sneak";break;
			case SNEAKTOGGLE: name+=ChatColor.YELLOW+"Sneak Toggle";break;
		}
		return Util.setName(new ItemStack((user.getSnipePlaceState() == PlacingState.DISABLED ? Material.WOODEN_PICKAXE : Material.DIAMOND_PICKAXE)), name);
	}
	
	private GuiItem getGlass(boolean value) {
		if(value) {
			return getGlass(PlacingState.ENABLED);
		}
		return getGlass(PlacingState.DISABLED);
	}
	
	private GuiItem getGlass(PlacingState state) {
		ItemStack icon = Util.setName(new ItemStack(Material.GLASS_PANE), "");
		switch(state) {
			case ENABLED:icon = Util.setName(new ItemStack(Material.LIME_STAINED_GLASS_PANE),"");break;
			case DISABLED:icon = Util.setName(new ItemStack(Material.RED_STAINED_GLASS_PANE),"");break;
			case ONSNEAK:icon = Util.setName(new ItemStack(Material.LIGHT_BLUE_STAINED_GLASS_PANE),"");break;
			case SNEAKTOGGLE:icon = Util.setName(new ItemStack(Material.YELLOW_STAINED_GLASS_PANE),"");break;
		}
		return new GuiItem(icon);
	}
	
	private GuiItem noAccess(String feature) {
		ItemStack icon = Util.setName(new ItemStack(Material.BARRIER), ChatColor.RED+"You do not have access to this feature.");
		Util.setLore(icon, ChatColor.GRAY+feature);
		return new GuiItem(icon);
	}
	

}
