package net.dezilla.bonetool.util;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.bukkit.Material;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.EntityType;

import net.dezilla.bonetool.ToolMain;

public class ToolConfig {
	//Misc
	public static Material toolMaterial = Material.BONE;
	public static boolean overlayBUtilSecretBlocks = true;
	public static boolean allowColorOnSign = true;
	public static List<String> toolBlacklist = new ArrayList<String>();
	public static boolean LightAPI = false;
	
	//Illegal Blocks
	public static boolean netherPortal = true;
	public static boolean endPortal = true;
	public static boolean pistonHead = true;
	public static boolean doubleLadder = true;
	public static boolean litRedstoneLamp = true;
	public static boolean paintingByName = true;
	public static boolean commandBlocks = true;
	public static boolean commandBlockMinecart = true;
	public static boolean sculkSensor = true;
	public static boolean jigsaw = true;
	
	public static boolean debugStick = true;
	public static boolean spawner =  true;
	public static boolean barrier =  true;
	public static boolean structureVoid = true;
	public static boolean dragonEgg = true;
	public static boolean structureBlock = true;
	public static boolean lightBlock = true;
	
	//Spawner Config
	public static List<EntityType> entityBlacklist = new ArrayList<EntityType>();
	public static int spawnerDelay = 10000;
	public static int maxNearbyEntities = 1000;
	public static int minSpawnDelay = 10000;
	public static int maxSpawnDelay = 10000;
	public static int reqPlayerRange = 300;
	public static int spawnCount = 100;
	public static int spawnRange = 300;
	
	//
	
	public static void reloadConfig() {
		ToolMain.getInstance().reloadConfig();
		loadConfig();
	}
	
	public static void loadConfig() {
		//Check is the config file exists first, if not create one
		generateConfig();
		//loadConfig
		FileConfiguration config = ToolMain.getInstance().getConfig();
		//Misc
		try {
			toolMaterial = Material.valueOf(config.getString("toolMaterial").toUpperCase());
		} catch(Exception e) {}
		try{overlayBUtilSecretBlocks = config.getBoolean("overlayBUtilSecretBlocks");}catch(Exception e) {}
		try{allowColorOnSign = config.getBoolean("allowColorOnSign");}catch(Exception e) {}
		try{toolBlacklist = config.getStringList("toolBlacklist");}catch(Exception e) {}
		try{LightAPI = config.getBoolean("LightAPI");}catch(Exception e) {}
		//Illegal Blocks
		try{netherPortal = config.getBoolean("netherPortal");}catch(Exception e) {}
		try{endPortal = config.getBoolean("endPortal");}catch(Exception e) {}
		try{pistonHead = config.getBoolean("pistonHead");}catch(Exception e) {}
		try{doubleLadder = config.getBoolean("doubleLadder");}catch(Exception e) {}
		try{litRedstoneLamp = config.getBoolean("litRedstoneLamp");}catch(Exception e) {}
		try{paintingByName = config.getBoolean("paintingByName");}catch(Exception e) {}
		try{commandBlocks = config.getBoolean("commandBlocks");}catch(Exception e) {}
		try{commandBlockMinecart = config.getBoolean("commandBlockMinecart");}catch(Exception e) {}
		try{sculkSensor = config.getBoolean("sculkSensor");}catch(Exception e) {}
		try{jigsaw = config.getBoolean("jigsaw");}catch(Exception e) {}
		
		try{debugStick = config.getBoolean("debugStick");}catch(Exception e) {}
		try{spawner = config.getBoolean("spawner");}catch(Exception e) {}
		try{barrier = config.getBoolean("barrier");}catch(Exception e) {}
		try{structureVoid = config.getBoolean("structureVoid");}catch(Exception e) {}
		try{dragonEgg = config.getBoolean("dragonEgg");}catch(Exception e) {}
		try{structureBlock = config.getBoolean("structureBlock");}catch(Exception e) {}
		try{lightBlock = config.getBoolean("lightBlock");}catch(Exception e) {}
		//Spawner
		entityBlacklist.clear();
		for(String s : config.getStringList("entityBlacklist")) {
			try {
				entityBlacklist.add(EntityType.valueOf(s.toUpperCase()));
			}catch(Exception e) {}
		}
		try{spawnerDelay = config.getInt("spawnerDelay");}catch(Exception e) {}
		try{maxNearbyEntities = config.getInt("maxNearbyEntities");}catch(Exception e) {}
		try{minSpawnDelay = config.getInt("minSpawnDelay");}catch(Exception e) {}
		try{maxSpawnDelay = config.getInt("maxSpawnDelay");}catch(Exception e) {}
		try{reqPlayerRange = config.getInt("reqPlayerRange");}catch(Exception e) {}
		try{spawnCount = config.getInt("spawnCount");}catch(Exception e) {}
		try{spawnRange = config.getInt("spawnRange");}catch(Exception e) {}
		
		//version check
		if(ToolMain.getVersionNumber()<17) {
			sculkSensor = false;
			lightBlock = false;
		}
	}
	
	public static void generateConfig() {
		//Check is the config file exists first, if not create one
			File folder = ToolMain.getInstance().getDataFolder();
			if(!folder.exists() || !folder.isDirectory())
				folder.mkdir();
			File f  = new File(ToolMain.getInstance().getDataFolder().getPath(), "config.yml");
			if(!f.exists()) {
				ToolMain.getInstance().saveDefaultConfig();
				ToolMain.getInstance().reloadConfig();
			}
	}
	
	/*
	 * Permission List
	 * bonetool.tool.use - Basic permission to be able to use the bone tool.
	 * bonetool.tool.nobupdate - Permission to use the no block update feature.
	 * bonetool.tool.forceplace - Permission to use the force place feature.
	 * bonetool.tool.snipeplace - Permission to use the snipe place feature.
	 * bonetool.tool.flyspeed - Permission to use the fly speed feature.
	 * bonetool.tool.signedit - Permission to use the sign edit feature.
	 * bonetool.tool.nolitter - Permission to use the no litter feater.
	 * bonetool.blocks.gui - Permission to access the illegal blocks gui. if using Builder's Utilities use builders.util.secretblocks
	 * bonetool.blocks.use - Permission to use BoneTool illegal blocks.
	 * bonetool.blocks.paintings - Permission to access the paintings gui.
	 * bonetool.blocks.lightblock - Permission to access the light block gui.
	 * bonetool.admin.reload - Reload configuration file
	 * bonetool.admin.bypass - Bypass the blacklists
	 */

}