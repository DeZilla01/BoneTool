package net.dezilla.bonetool.gui;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.bukkit.ChatColor;
import org.bukkit.block.Block;
import org.bukkit.block.CreatureSpawner;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.inventory.ItemStack;

import net.dezilla.bonetool.ToolUser;
import net.dezilla.bonetool.Util;
import net.dezilla.bonetool.util.IntegerRunnable;
import net.dezilla.bonetool.util.InventoryRunnable;
import net.dezilla.bonetool.util.Locale;
import net.dezilla.bonetool.util.ToolConfig;

public class SpawnerGui extends GuiPage{
	static final List<String> LORE = Arrays.asList(
			ChatColor.AQUA+"Left Click: "+ChatColor.WHITE+"-1",
			ChatColor.AQUA+"Shift + Left Click: "+ChatColor.WHITE+"-20",
			ChatColor.AQUA+"Right Click: "+ChatColor.WHITE+"+1",
			ChatColor.AQUA+"Shift + Right Click: "+ChatColor.WHITE+"+20");
	
	Block block;

	public SpawnerGui(Player player, Block block) {
		super(1, player);
		this.block = block;
		setName(Locale.parse(ToolUser.getUser(player), "spawnereditor"));
		if(!(block.getState() instanceof CreatureSpawner))
			return;
		addItems();
	}
	
	private void addItems() {
		CreatureSpawner spawner = (CreatureSpawner) block.getState();
		ToolUser u = ToolUser.getUser(getPlayer());
		//Change Type
		GuiItem typeItem = new GuiItem(Util.setLore(Util.setName(Util.getEntityIcon(spawner.getSpawnedType()), Locale.parse(u, "changeentitytype")), ChatColor.WHITE+"Current: "+spawner.getSpawnedType()));
		typeItem.setRun((event) -> {
			List<EntityType> eList = new ArrayList<EntityType>(Arrays.asList(EntityType.values()));
			eList.remove(EntityType.UNKNOWN);
			if(!Util.permCheck(getPlayer(), "bonetool.admin.bypass"))
				for(EntityType t : ToolConfig.entityBlacklist)
					eList.remove(t);
			new EntitySelectGui(getPlayer(), eList, (type) -> {
				spawner.setSpawnedType(type);
				spawner.update();
				new SpawnerGui(getPlayer(), block).display();
			}).display();
		});
		setItem(0,0,typeItem);
		
		
		//Delay
		ItemStack delayIcon = Util.createTexturedHead("eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvMDdjNzhmM2VlNzgzZmVlY2QyNjkyZWJhNTQ4NTFkYTVjNDMyMzA1NWViZDJmNjgzY2QzZTgzMDJmZWE3YyJ9fX0=");
		Util.setName(delayIcon, ChatColor.GREEN+Locale.parse(u, "currentdelay")+": "+ChatColor.WHITE+spawner.getDelay());
		Util.setLore(delayIcon, LORE);
		GuiItem delayItem = new GuiItem(delayIcon);
		IntegerRunnable delayRun = (i) -> {
			spawner.setDelay(checkAmount(spawner, SpawnerOption.DELAY, spawner.getDelay() + i));
			spawner.update();
			refresh();
		};
		delayItem.setRun(getRun(delayRun));
		setItem(0, 1, delayItem);
		
		//Max Nearby Entities
		ItemStack maxNearbyIcon = Util.createTexturedHead("eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvNWZkZTNiZmNlMmQ4Y2I3MjRkZTg1NTZlNWVjMjFiN2YxNWY1ODQ2ODRhYjc4NTIxNGFkZDE2NGJlNzYyNGIifX19");
		Util.setName(maxNearbyIcon, ChatColor.GREEN+Locale.parse(u, "maxnearbyentities")+": "+ChatColor.WHITE+spawner.getMaxNearbyEntities());
		Util.setLore(maxNearbyIcon, LORE);
		GuiItem maxNearbyItem = new GuiItem(maxNearbyIcon);
		IntegerRunnable maxNearbyRun = (i) -> {
			spawner.setMaxNearbyEntities(checkAmount(spawner, SpawnerOption.MAXNEARBY, spawner.getMaxNearbyEntities() + i));
			spawner.update();
			refresh();
		};
		maxNearbyItem.setRun(getRun(maxNearbyRun));
		setItem(0, 2, maxNearbyItem);
		
		//Min Spawn Delay
		ItemStack minSpawnDelayIcon = Util.createTexturedHead("eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvM2VmMGM1NzczZGY1NjBjYzNmYzczYjU0YjVmMDhjZDY5ODU2NDE1YWI1NjlhMzdkNmQ0NGYyZjQyM2RmMjAifX19");
		Util.setName(minSpawnDelayIcon, ChatColor.GREEN+Locale.parse(u, "minspawndelay")+": "+ChatColor.WHITE+spawner.getMinSpawnDelay());
		Util.setLore(minSpawnDelayIcon, LORE);
		GuiItem minSpawnDelayItem = new GuiItem(minSpawnDelayIcon);
		IntegerRunnable minSpawnDelayRun = (i) -> {
			spawner.setMinSpawnDelay(checkAmount(spawner, SpawnerOption.MINDELAY, spawner.getMinSpawnDelay() + i));
			spawner.update();
			refresh();
		};
		minSpawnDelayItem.setRun(getRun(minSpawnDelayRun));
		setItem(0, 3, minSpawnDelayItem);
		
		//Max Spawn Delay
		ItemStack maxSpawnDelayIcon = Util.createTexturedHead("eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvMmM0ODg2ZWYzNjJiMmM4MjNhNmFhNjUyNDFjNWM3ZGU3MWM5NGQ4ZWM1ODIyYzUxZTk2OTc2NjQxZjUzZWEzNSJ9fX0=");
		Util.setName(maxSpawnDelayIcon, ChatColor.GREEN+Locale.parse(u, "maxspawndelay")+": "+ChatColor.WHITE+spawner.getMaxSpawnDelay());
		Util.setLore(maxSpawnDelayIcon, LORE);
		GuiItem maxSpawnDelayItem = new GuiItem(maxSpawnDelayIcon);
		IntegerRunnable maxSpawnDelayRun = (i) -> {
			spawner.setMaxSpawnDelay(checkAmount(spawner, SpawnerOption.MAXDELAY, spawner.getMaxSpawnDelay() + i));
			spawner.update();
			refresh();
		};
		maxSpawnDelayItem.setRun(getRun(maxSpawnDelayRun));
		setItem(0, 4, maxSpawnDelayItem);
		
		//Required Player Range
		ItemStack reqPlayerRangeIcon = Util.createTexturedHead("eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvNjllZWIzNTE5ZGRjMTRkMGE3ZmNlNDFiNWZhYmY5NDIxOTI5NmEyMzFkZGNlZDIwMmRmMDVhZWMxNTYyMjEifX19");
		Util.setName(reqPlayerRangeIcon, ChatColor.GREEN+Locale.parse(u, "requiredplayerrange")+": "+ChatColor.WHITE+spawner.getRequiredPlayerRange());
		Util.setLore(reqPlayerRangeIcon, LORE);
		GuiItem reqPlayerRangeItem = new GuiItem(reqPlayerRangeIcon);
		IntegerRunnable reqPlayerRangeRun = (i) -> {
			spawner.setRequiredPlayerRange(checkAmount(spawner, SpawnerOption.REQRANGE, spawner.getRequiredPlayerRange() + i));
			spawner.update();
			refresh();
		};
		reqPlayerRangeItem.setRun(getRun(reqPlayerRangeRun));
		setItem(0, 5, reqPlayerRangeItem);
		
		//Spawn count
		ItemStack spawnCountIcon = Util.createTexturedHead("eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvMTQzYzc5Y2Q5YzJkMzE4N2VhMDMyNDVmZTIxMjhlMGQyYWJiZTc5NDUyMTRiYzU4MzRkZmE0MDNjMTM0ZTI3In19fQ==");
		Util.setName(spawnCountIcon, ChatColor.GREEN+Locale.parse(u, "spawncount")+": "+ChatColor.WHITE+spawner.getSpawnCount());
		Util.setLore(spawnCountIcon, LORE);
		GuiItem spawnCountItem = new GuiItem(spawnCountIcon);
		IntegerRunnable spawnCountRun = (i) -> {
			spawner.setSpawnCount(checkAmount(spawner, SpawnerOption.SPAWNCOUNT, spawner.getSpawnCount() + i));
			spawner.update();
			refresh();
		};
		spawnCountItem.setRun(getRun(spawnCountRun));
		setItem(0, 6, spawnCountItem);
		
		//spawn Range
		ItemStack spawnRangeIcon = Util.createTexturedHead("eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvNzNjY2I0ZGIyYzE5NjkyODM0M2U2YzFjNzlhMjdkNzM3ZTY1NWQwYzlmOTE2OWI3YTg4ZDE3NDQ1NzE0MTcifX19");
		Util.setName(spawnRangeIcon, ChatColor.GREEN+Locale.parse(u, "spawnrange")+": "+ChatColor.WHITE+spawner.getSpawnRange());
		Util.setLore(spawnRangeIcon, LORE);
		GuiItem spawnRangeItem = new GuiItem(spawnRangeIcon);
		IntegerRunnable spawnRangeRun = (i) -> {
			spawner.setSpawnRange(checkAmount(spawner, SpawnerOption.SPAWNRANGE, spawner.getSpawnRange() + i));
			spawner.update();
			refresh();
		};
		spawnRangeItem.setRun(getRun(spawnRangeRun));
		setItem(0, 7, spawnRangeItem);
	}
	
	private void refresh() {
		this.clear();
		this.addItems();
		this.display();
	}
	
	private InventoryRunnable getRun(IntegerRunnable run) {
		return (event) -> {
			if(event.getClick() == ClickType.LEFT)
				run.run(-1);
			else if(event.getClick() == ClickType.SHIFT_LEFT)
				run.run(-20);
			else if(event.getClick() == ClickType.RIGHT)
				run.run(1);
			else if(event.getClick() == ClickType.SHIFT_RIGHT)
				run.run(20);
		};
	}
	
	private int checkAmount(CreatureSpawner spawner, SpawnerOption option, int amount) {
		if(amount < 0)
			amount  = 0;
		switch(option) {
			case DELAY:
				if(amount > ToolConfig.spawnerDelay)
					amount = ToolConfig.spawnerDelay;
				break;
			case MAXNEARBY:
				if(amount > ToolConfig.maxNearbyEntities)
					amount = ToolConfig.maxNearbyEntities;
				break;
			case MAXDELAY:
				if(amount == 0)
					amount = 1;
				if(spawner.getMinSpawnDelay()>amount)
					amount = spawner.getMinSpawnDelay();
				if(amount > ToolConfig.maxSpawnDelay)
					amount = ToolConfig.maxSpawnDelay;
				break;
			case MINDELAY:
				if(spawner.getMaxSpawnDelay() < amount)
					amount = spawner.getMaxSpawnDelay();
				if(amount > ToolConfig.minSpawnDelay)
					amount = ToolConfig.minSpawnDelay;
				break;
			case REQRANGE:
				if(amount > ToolConfig.reqPlayerRange)
					amount = ToolConfig.reqPlayerRange;
				break;
			case SPAWNCOUNT:
				if(amount > ToolConfig.spawnCount)
					amount = ToolConfig.spawnCount;
				break;
			case SPAWNRANGE:
				if(amount > ToolConfig.spawnRange)
					amount = ToolConfig.spawnRange;
				break;
		}
		return amount;
	}
	private static enum SpawnerOption{
		DELAY,
		MAXNEARBY,
		MINDELAY,
		MAXDELAY,
		REQRANGE,
		SPAWNCOUNT,
		SPAWNRANGE
	}

}
