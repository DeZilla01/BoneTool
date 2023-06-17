package net.dezilla.bonetool.gui;

import java.util.Arrays;

import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.block.Block;
import org.bukkit.block.EndGateway;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataType;

import net.dezilla.bonetool.ToolUser;
import net.dezilla.bonetool.Util;
import net.dezilla.bonetool.util.Locale;

public class EndGatewayGui extends GuiPage{
	Block block;

	public EndGatewayGui(Player player, Block block) {
		super(1, player);
		this.block = block;
		setName(Locale.parse(ToolUser.getUser(player), "endgateway"));
		addItems();
	}
	
	private void refresh() {
		this.clear();
		this.addItems();
		this.display();
	}
	
	private void addItems() {
		if(!(block.getState() instanceof EndGateway))
			return;
		EndGateway gateway = (EndGateway) block.getState();
		ToolUser user = ToolUser.getUser(getPlayer());
		//logger
		ItemStack loggerIcon = Util.setName(locationLogger(), Locale.parse(user, "getlocationlogger"));
		GuiItem loggerItem = new GuiItem(loggerIcon);
		loggerItem.setRun((event) -> {
			 getPlayer().getInventory().addItem(locationLogger());
		});
		setItem(0,2,loggerItem);
		//age
		ItemStack ageIcon = Util.setName(new ItemStack(Material.CLOCK), Locale.parse(user, "age")+": "+ChatColor.YELLOW+gateway.getAge());
		Util.setLore(ageIcon, ChatColor.GRAY+Locale.parse(user, "ageinticks"));
		GuiItem ageItem = new GuiItem(ageIcon);
		setItem(0,8,ageItem);
		//exactTeleport
		ItemStack exactIcon = Util.setName(new ItemStack(Material.EMERALD_BLOCK), Locale.parse(user, "exactteleport")+": "+(gateway.isExactTeleport() ? ChatColor.GREEN+Locale.parse(user, "true") : ChatColor.RED+Locale.parse(user, "false")));
		if(gateway.isExactTeleport()) {
			ItemMeta m = exactIcon.getItemMeta();
			m.addEnchant(Enchantment.MENDING, 1, true);
			exactIcon.setItemMeta(m);
		}
		GuiItem exactItem = new GuiItem(exactIcon);
		exactItem.setRun((event)->{
			gateway.setExactTeleport(!gateway.isExactTeleport());
			gateway.update();
			refresh();
		});
		setItem(0,0,exactItem);
		//exit location
		ItemStack exitIcon = Util.setName(new ItemStack(Material.ENDER_PEARL), Locale.parse(user, "setexitlocation"));
		Location loc = gateway.getExitLocation();
		String exLocation = (loc != null ? ""+loc.getBlockX()+" "+loc.getBlockY()+" "+loc.getBlockZ() : Locale.parse(user, "nolocationset"));
		Util.setLore(exitIcon, Arrays.asList(
				ChatColor.GRAY+Locale.parse(user, "exitlocinstructions1"),
				ChatColor.GRAY+Locale.parse(user, "exitlocinstructions2"),
				ChatColor.GRAY+Locale.parse(user, "exitlocinstructions3"),
				ChatColor.AQUA+exLocation));
		GuiItem exitItem = new GuiItem(exitIcon);
		exitItem.setRun((event) -> {
			if(event.getClick() == ClickType.SHIFT_RIGHT) {
				gateway.setExitLocation(null);
				gateway.update();
				refresh();
				return;
			}
			if(event.getCursor() == null || event.getCursor().getItemMeta() == null)
				return;
			ItemStack cursor = event.getCursor();
			if(cursor.getItemMeta().getPersistentDataContainer().has(Util.locationKey, PersistentDataType.STRING)) {
				String[] locStrings = cursor.getItemMeta().getPersistentDataContainer().get(Util.locationKey, PersistentDataType.STRING).split(":");
				double x = Double.parseDouble(locStrings[0]);
				double y = Double.parseDouble(locStrings[1]);
				double z = Double.parseDouble(locStrings[2]);
				float yaw = Float.parseFloat(locStrings[3]);
				float pitch = Float.parseFloat(locStrings[4]);
				Location l = new Location(gateway.getWorld(), x, y, z);
				l.setYaw(yaw);
				l.setPitch(pitch);
				gateway.setExitLocation(l);
				gateway.update();
				getPlayer().playSound(getPlayer().getLocation(), Sound.BLOCK_NOTE_BLOCK_PLING, 1f, 1f);
				refresh();
			}
		});
		setItem(0,1,exitItem);
		
	}
	
	private ItemStack locationLogger() {
		ItemStack item = Util.setName(new ItemStack(Material.ENDER_EYE), Locale.parse("locationlogger"));
		Util.setLore(item, Arrays.asList(ChatColor.GRAY+Locale.parse("locloggerinstructions1"),ChatColor.GRAY+Locale.parse("locloggerinstructions2")));
		return Util.setSpecialBlockType(item, "locationLogger");
	}

}
