package net.dezilla.bonetool;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandMap;
import org.bukkit.plugin.java.JavaPlugin;

import net.dezilla.bonetool.command.*;
import net.dezilla.bonetool.listener.*;
import net.dezilla.bonetool.util.Locale;
import net.dezilla.bonetool.util.Metrics;
import net.dezilla.bonetool.util.ToolConfig;
import net.dezilla.bonetool.wandtool.*;
import net.md_5.bungee.api.ChatColor;

public class ToolMain extends JavaPlugin{
	
	static ToolMain instance;
	static String version;
	static List<WandTool> tools = new ArrayList<WandTool>();
	static boolean plotEnabled = false;
	
	@Override
	public void onEnable() {
		instance = this;
		String a = getServer().getClass().getPackage().getName();
		version = a.substring(a.lastIndexOf('.') + 1);
		
		System.out.println("[BoneTool] Loading Configs.");
		/*-----[Configs]-----*/
		ToolConfig.loadConfig();
		
		/*-----[Version Warning]-----*/
		if(getVersionNumber() < 16) {
			System.out.println("[BoneTool] "+ChatColor.RED+Locale.parse("oldVersionWarning1"));
			System.out.println("[BoneTool] "+ChatColor.RED+Locale.parse("oldVersionWarning2"));
		}
		
		if(getVersionNumber() > 19) {
			System.out.println("[BoneTool] "+ChatColor.RED+Locale.parse("newVersionWarning1"));
			System.out.println("[BoneTool] "+ChatColor.RED+Locale.parse("newVersionWarning2"));
		}
		
		System.out.println("[BoneTool] Enabling Listeners.");
		/*-----[Listeners]-----*/
		getServer().getPluginManager().registerEvents(new GuiListener(), this);
		getServer().getPluginManager().registerEvents(new ToolListener(), this);
		getServer().getPluginManager().registerEvents(new BlockUpdateListener(), this);
		getServer().getPluginManager().registerEvents(new ForcePlaceListener(), this);
		getServer().getPluginManager().registerEvents(new SpecialBlockListener(), this);
		getServer().getPluginManager().registerEvents(new SnipePlaceListener(), this);
		getServer().getPluginManager().registerEvents(new SneakToggleListener(), this);
		
		System.out.println("[BoneTool] Enabling Commands.");
		/*-----[Commands]-----*/
		try {
			final Field bukkitCommandMap = Bukkit.getServer().getClass().getDeclaredField("commandMap");

			bukkitCommandMap.setAccessible(true);
			CommandMap commandMap = (CommandMap) bukkitCommandMap.get(Bukkit.getServer());
			
			//BoneTool Commands
			List<Command> commands = Arrays.asList(
					new BoneToolCommand(), 
					new PaintingCommand(),
					new NoBlockUpdateCommand(),
					new ForcePlaceCommand(),
					new SnipePlaceCommand(),
					new FlySpeedCommand(),
					new EditSignCommand(),
					new NoLitterCommand());
			commandMap.registerAll("bonetool", commands);
			if(getVersionNumber()>=17)
				commandMap.register("bonetool", new LightBlockCommand());
			//Builder's Utilities Commands
			if(getServer().getPluginManager().isPluginEnabled("Builders-Utilities")) {
				if(ToolConfig.disableBUtilSecretBlocks) {
					BlockCommand secretBlockCommand = new BlockCommand();
					getServer().getPluginCommand("blocks").setExecutor(secretBlockCommand);
					commandMap.register("bonetool", secretBlockCommand);
				} else if(ToolConfig.overlayBUtilSecretBlocks) {
					BUtilSecretBlocksCommand secretBlockCommand = new BUtilSecretBlocksCommand();
					getServer().getPluginCommand("blocks").setExecutor(secretBlockCommand);
					commandMap.register("builders-utilities", secretBlockCommand);
				}
			}
			else
				commandMap.register("bonetool", new BlockCommand());
		} catch(Exception e) {
			e.printStackTrace();
		}
		
		System.out.println("[BoneTool] Enabling Tools.");
		/*-----[Tools]-----*/
		WandTool[] wandTools = {
			new NoneTool(),
			//Left Click
			new AgeableTool(),
			new AmethystTool(),
			new AnaloguePowerableTool(),
			new AttachableTool(),
			new BambooTool(),
			new BeaconTool(),
			new BedTool(),
			new BellTool(),
			new BigDripLeafTool(),
			new BisectedTool(),
			new BrewingStandTool(),
			new BubbleTool(),
			new CakeTool(),
			new CampfireTool(),
			new CandleTool(),
			new ChestTool(),
			new ColorTool(),
			new CommandConditionalTool(),
			new ComparatorTool(),
			new DayDetectorTool(),
			new DirectionalTool(),
			new DispenserTool(),
			new DoorHingeTool(),
			new DripstoneDirectionTool(),
			new DripstoneTool(),
			new EndPortalFrameTool(),
			new FaceAttachableTool(),
			new FarmlandTool(),
			new GateTool(),
			new GlowBerryTool(),
			new HoneyTool(),
			new HopperTool(),
			new JigsawTool(),
			new JukeboxTool(),
			new LeavesDistanceTool(),
			new LeavesPersistanceTool(),
			new LevelledTool(),
			new LanternTool(),
			new LidTool(),
			new LightableTool(),
			new LightAPILegacyTool(),
			new LightAPITool(),
			new LockTool(),
			new MultipleFacingTool(),
			new NoteBlockTool(),
			new OpenableTool(),
			new OrientableTool(),
			new PistonHeadTool(),
			new PistonTool(),
			new PistonTypeTool(),
			new PotTool(),
			new PowerableTool(),
			new RailTool(),
			new RedstoneWireTool(),
			new RepeaterDelayTool(),
			new RepeaterLockTool(),
			new RespawnAnchorTool(),
			new RotatableTool(),
			new ScaffoldingBottomTool(),
			new ScaffoldingDistanceTool(),
			new SculkSensorTool(),
			new SculkSensorPhaseTool(),
			new SeaPickleTool(),
			new SignTool(),
			new SlabTool(),
			new SnowableTool(),
			new SnowTool(),
			new SpawnerTool(),
			new StairTool(),
			new StructureTool(),
			new SwitchTool(),
			new TntTool(),
			new TripwireTool(),
			new TurtleEggAmountTool(),
			new TurtleEggHatchTool(),
			new WallTool(),
			new WaterloggedTool(),
			//19
			new SculkShriekerTool(),
			new ShriekerWarningTool(),
			new ShriekerSummonTool(),
			new SculkBloomTool(),
			new HangingTool(),
			//Right Click
			new DebugTool(),
			new PlayerFaceTool()
		};
		for(WandTool t : wandTools) {
			if(t.isServerCompatible())
				tools.add(t);
		}
	    
		System.out.println("[BoneTool] Misc.");
		/*-----[Misc]-----*/
		if(this.getServer().getPluginManager().isPluginEnabled("PlotSquared"))
			plotEnabled = true;
		ToolConfig.loadItems();
		Metrics metrics = new Metrics(this, 9762);
		metrics.addCustomChart(new Metrics.SimplePie("butils", () -> (getServer().getPluginManager().isPluginEnabled("Builders-Utilities") ? "true" : "false")));
		metrics.addCustomChart(new Metrics.SimplePie("plots", () -> (plotEnabled ? "true" : "false")));
		metrics.addCustomChart(new Metrics.SimplePie("locale", () -> ToolConfig.defaultLocale));
	}
	
	@Override
	public void onDisable() {
		
	}
	
	public static boolean isPlotEnabled() {
		return plotEnabled;
	}
	
	public static ToolMain getInstance() {
		return instance;
	}
	
	public static String getVersion() {
		return version;
	}
	
	public static int getVersionNumber() {
		try {
			return Integer.parseInt(getVersion().split("v1_")[1].split("_")[0]);
		}catch(Exception e) {
			e.printStackTrace();
			return 0;
		}
	}
	
	public static List<WandTool> getTools(){
		return new ArrayList<WandTool>(tools);
	}
	
	/*This function can be called to register a WandTool object by another plugin*/
	public static void registerTool(WandTool tool) {
		if(tool.isServerCompatible()) 
			tools.add(tool);
	}

}
