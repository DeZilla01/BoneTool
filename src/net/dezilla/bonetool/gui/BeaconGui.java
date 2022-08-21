package net.dezilla.bonetool.gui;

import org.apache.commons.lang.StringUtils;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.block.Beacon;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffectType;

import net.dezilla.bonetool.ToolUser;
import net.dezilla.bonetool.Util;
import net.dezilla.bonetool.util.Locale;

public class BeaconGui extends GuiPage{
	Beacon beacon = null;

	public BeaconGui(Player player, Block beacon) {
		super(1, player);
		if(beacon.getState() instanceof Beacon)
			this.beacon = (Beacon) beacon.getState();
		addItems();
		setName(Locale.parse(ToolUser.getUser(player), "beaconpotioneffects"));
	}
	
	public void addItems() {
		if(beacon == null)
			return;
		ItemStack first = (beacon.getPrimaryEffect() == null ? new ItemStack(Material.POTION) : Util.getPotionIcon(beacon.getPrimaryEffect().getType()));
		ItemStack second = (beacon.getSecondaryEffect() == null ? new ItemStack(Material.POTION) : Util.getPotionIcon(beacon.getSecondaryEffect().getType()));
		Util.setName(first, Locale.parse(ToolUser.getUser(getPlayer()), "setfirsteffect"));
		Util.setName(second, Locale.parse(ToolUser.getUser(getPlayer()), "setsecondeffect"));
		if(beacon.getPrimaryEffect() != null)
			Util.setLore(first, ChatColor.GRAY+Locale.parse(ToolUser.getUser(getPlayer()), "currenteffect")+": "+ChatColor.YELLOW+name(beacon.getPrimaryEffect().getType()));
		if(beacon.getSecondaryEffect() != null)
			Util.setLore(second, ChatColor.GRAY+Locale.parse(ToolUser.getUser(getPlayer()), "currenteffect")+": "+ChatColor.YELLOW+name(beacon.getSecondaryEffect().getType()));
		GuiItem firstItem = new GuiItem(first);
		GuiItem secondItem = new GuiItem(second);
		firstItem.setRun((event) -> {
			new PotionEffectSelectGui(getPlayer(), (type) -> {
				beacon.setPrimaryEffect(type);
				beacon.update();
				new BeaconGui(getPlayer(), beacon.getBlock()).display();
			}).display();;
		});
		secondItem.setRun((event) -> {
			new PotionEffectSelectGui(getPlayer(), (type) -> {
				beacon.setSecondaryEffect(type);
				beacon.update();
				new BeaconGui(getPlayer(), beacon.getBlock()).display();
			}).display();;
		});
		setItem(0, 3, firstItem);
		setItem(0, 5, secondItem);
	}
	
	private String name(PotionEffectType type) {
		String name = type.getName().replace("_", " ").toLowerCase();
		return StringUtils.capitalize(name);
	}

}
