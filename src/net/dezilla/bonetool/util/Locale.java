package net.dezilla.bonetool.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;

import org.bukkit.configuration.file.YamlConfiguration;

import net.dezilla.bonetool.ToolMain;
import net.dezilla.bonetool.ToolUser;

public class Locale {
	
	public static String parse(String key) {
		return parse(null, key);
	}
	
	public static String parse(ToolUser user, String key) {
		String locale = ToolConfig.defaultLocale;
		if(user != null && !user.getLocale().equalsIgnoreCase("default"))
			locale = user.getLocale();
		File file = new File(getLocaleFolder().getPath(), locale+".yml");
		if(file.exists()) {
			try {
				YamlConfiguration yaml = YamlConfiguration.loadConfiguration(new InputStreamReader(new FileInputStream(file)));
				String value = yaml.getString(key);
				if(value != null)
					return value;
			} catch (Exception e) {}
		}
		//fallback to english
		switch(key) {
			case "oldVersionWarning1": return "WARNING - You're running on an older version of Minecraft than BoneTool currently support.";
			case "oldVersionWarning2": return "No support will be provided if the plugin/server crashes.";
			case "newVersionWarning1": return "WARNING - You're running on a newer version of Minecraft than officially supported by BoneTool.";
			case "newVersionWarning2": return "Please update the plugin if a newer version is available.";
			case "senderNotPlayer": return "You must be a player to use this command.";
			case "accessDenied": return "You do not have access to this feature.";
			case "accessDisabled": return "This feature is disabled.";
			case "configReload": return "BoneTool configuration has been reloaded.";
			case "editSigns": return "Edit Signs";
			case "enabled": return "Enabled";
			case "disabled": return "Disabled";
			case "onsneak": return "On Sneak";
			case "sneaktoggle": return "Sneak Toggle";
			case "manyArguments": return "Too many arguments";
			case "invalidArgument": return "Invalid argument";
			case "flyondoublecrouch": return "Fly speed on double crouch";
			case "noLitter": return "No litter";
			case "noblockupdate": return "No Block Update";
			case "forceplace": return "Force Placing";
			case "snipeplace": return "Snipe Placing";
			case "flyspeed": return "Fly Speed";
			case "notoolforblock": return "No tool for this block";
			//beacon
			case "beaconpotioneffects": return "Beacon Potion Effects";
			case "setfirsteffect": return "Set Primary Effect";
			case "setsecondeffect": return "Set Secondary Effect";
			case "currenteffect": return "Current Effect";
			case "selectpotion": return "Select Potion Type";
			//gui
			case "selectrotation": return "Select Rotation";
			case "blockoption": return "Block Option";
			case "selectcolor": return "Select a color";
			case "selectedcolor": return "Color selected";
			case "selectentity": return "Select Entity";
			case "page": return "Page";
			case "next": return "Next";
			case "previous": return "Previous";
			case "brewstandpotions": return "Brewing Stand Potions";
			case "potion": return "Potion";
			case "btSecretBlocks": return "BoneTool Secret Blocks";
			case "paintings": return "Paintings";
			case "lightblock": return "Light Block";
			case "selectlanguage": return "Select a language";
			case "multifaceseditor": return "Multiple Facing Editor";
			case "noteblockeditor": return "Note Block Editor";
			case "none": return "None";
			case "selectflower": return "Select Flower Pot Content";
			case "redstoneconnection": return "Redstone Connection";
			case "rightclickaction": return "Right Click Action";
			case "wallconnection": return "Wall Connection";
			case "default": return "Default";
			//spawner
			case "spawnereditor": return "Spawner Editor";
			case "changeentitytype": return "Change Entity Type";
			case "currentdelay": return "Current Delay";
			case "maxnearbyentities": return "Maximum Nearby Entities";
			case "minspawndelay": return "Minimum Spawn Delay";
			case "maxspawndelay": return "Maximum Spawn Delay";
			case "requiredplayerrange": return "Required Player Range";
			case "spawncount": return "Spawn Count";
			case "spawnrange": return "Spawn Range";
			//toolgui
			case "editflyspeed": return "Edit Fly Speed";
			case "flySpeedInstructions1": return "Allow the user to edit fly speed";
			case "flySpeedInstructions2": return "by double sneaking and scrolling.";
			case "editSignsInstructions1": return "Allow the user to edit signs by sneaking";
			case "editSignsInstructions2": return "and right clicking with bare hands.";
			case "noLitterInstructions": return "Automatically delete item when dropped.";
			case "notification": return "Notification";
			case "wandmaterial": return "Wand Material";
			case "wandMaterialInstructions1": return "Drag an item here to set as your wand.";
			case "wandMaterialInstructions2": return "Shift + Right Click to spawn a wand in your inventory.";
			case "bonetoolDescription": return "A tool to edit block properties and more.";
			//special blocks
			case "netherPortal": return "Nether Portal";
			case "pistonHead": return "Piston Head";
			case "endPortal": return "End Portal";
			case "doubleLadder": return "Double Ladder";
			case "litRedstoneLamp": return "Lit Redstone Lamp";
			case "invisFrame": return "Invisible Item Frame";
			//wandtools
			case "true": return "True";
			case "false": return "False";
			case "leftclick": return "Left Click";
			case "rightclick": return "Right Click";
			case "shift": return "Shift";
			case "comparatormode": return "Comparator Mode";
			case "daylightdetectorinverted": return "Daylight Detector Inverted";
			case "doorhinge": return "Door Hinge";
			case "dispensertriggered": return "Dispenser Triggered";
			case "direction": return "Direction";
			case "debugtool": return "Debug Tool";
			case "debugtooldescription": return "A similar tool to the vanilla's debug stick";
			case "debugtoolinstructions1": return "Right Click to toggle property";
			case "debugtoolinstructions2": return "Shift + Right Click to switch property";
			case "blockcannotbetoggled": return "Block cannot be toggled";
			case "commandconditional": return "Command Conditional";
			case "color": return "Color";
			case "choosecolor": return "Choose Color";
			case "togglecolor": return "Toggle Color";
			case "colorinstructions1": return "Change the color of the block.";
			case "colorinstructions2": return "Sneak and right click on ground to select the color.";
			case "chesttype": return "Chest Type";
			case "candleamount": return "Candle Amount";
			case "signalfire": return "Signal Fire";
			case "bubblecolumndrag": return "Bubble Column Drag";
			case "cakebites": return "Cake Bites";
			case "bisected": return "Bisected";
			case "dripleadtilt": return "Big Dripleaf Tilt";
			case "bellattachment": return "Bell Attachment";
			case "bedpart": return "Bed Part";
			case "bambooleaves": return "Bamboo Leaves";
			case "attached": return "Attached";
			case "power": return "Power";//redstone power
			case "amthystsize": return "Amthyst Size";
			case "small": return "Small";
			case "medium": return "Medium";
			case "large": return "Large";
			case "cluster": return "Cluster";
			case "age": return "Age";
			case "addlight": return "Add Light";
			case "lightinstructions1": return "Make the block become a light source";
			case "lightinstructions2": return "This will set the light level of the block at 15 (max)";
			case "lightlevel": return "Light Level";
			case "orientation": return "Orientation";
			case "opened": return "Opened";
			case "editnote": return "Edit Note";
			case "instrument": return "Instrument";
			case "selectrightclicktool": return "Select a tool to bind to right-click.";
			case "multiplefacing": return "Multiple Facing";
			case "multifacesinstructions": return "Change the facing of walls, fences and more.";
			case "lock": return "Lock";
			case "unlocked": return "Unlocked";
			case "lockInstructions1": return "Set a password on the container.";
			case "lockInstructions2": return "An item with the password as it's name";
			case "lockInstructions3": return "will be required to open the container.";
			case "lockInstructions4": return "To set a password, place an item with";
			case "lockInstructions5": return "the desired password here.";
			case "removepass": return "Remove password.";
			case "lit": return "Lit";
			case "lid": return "Lid";
			case "openlid": return "Open the lid.";
			case "closelid": return "Close the lid.";
			case "visualBugWarning": return "This tool can cause visual bugs.";
			case "level": return "Level";
			case "leavespersistant": return "Leaves Persistant";
			case "leavesdistance": return "Leaves Distance";
			case "lanternhanging": return "Lantern Hanging";
			case "jukeboxitem": return "Jukebox Item";
			case "jukeboxinstructions1": return "Place an item here to set";
			case "jukeboxinstructions2": return "On the record slot.";
			case "stopmusic": return "Stop the music.";
			case "jigsaworientation": return "Jigsaw Orientation";
			case "hopperenabled": return "Hopper Enabled";
			case "honeystorage": return "Honey Storage";
			case "hangable": return "Hangable";
			case "hasberry": return "Has Berry";
			case "inwall": return "In Wall";
			case "attachable": return "Attachable";
			case "flowerpot": return "Flower Pot";
			case "empty": return "Empty";
			case "notapot": return "Not a pot";
			case "playerfacing": return "Player's Facing";
			case "playerfacingdesc": return "Make the block face the player";
			case "playerfacingex": return "ex. signs, skulls, banners";
			case "pistontype": return "Piston Type";
			case "pistonextended": return "Piston Extended";
			case "pistonheadshort": return "Piston Head Short";
			case "moistlevel": return "Moist Level";
			case "endportaleye": return "End Portal Frame Eye";
			case "dripstonethickness": return "Dripstone Thickness";
			case "dripstonedirection": return "Dripstone Direction";
			case "waterlogged": return "Waterlogged";
			case "repeaterlock": return "Repeater lock";
			case "repeaterdelay": return "Repeater delay";
			case "rail": return "Rail";
			case "powered": return "Powered";
			case "seapickles": return "Sea Pickles";
			case "tntunstable": return "TNT Unstable";
			case "snowy": return "Snowy";
			case "snowlayers": return "Snow layers";
			case "stairshape": return "Stair Shape";
			case "structuremode": return "Structure Mode";
			case "switchface": return "Switch Face";
			case "slabtype": return "Slab Type";
			case "scaffoldingdistance": return "Scaffolding Distance";
			case "scaffoldingbottom": return "Scaffolding Bottom";
			case "bloom": return "Bloom";//sculk
			case "sculksensorphase": return "Sculk Sensor Phase";
			case "sculksensorvibration": return "Sculk Sensor Vibration Frequency";
			case "shrieking": return "Shrieking";
			case "shriekersummon": return "Can Summon";
			case "shriekerwarning": return "Warning Level";
			case "respawnanchorcharge": return "Respawn Anchor Charge";
			case "rotation": return "Rotation";
			case "chooserotation": return "Choose Rotation";
			case "togglerotation": return "Toggle Rotation";
			case "wallfacing": return "Wall Facing";
			case "tripwiredisarmed": return "Tripwire Disarmed";
			case "turtleeggamount": return "Turtle Egg Amount";
			case "turtleegghatch": return "Turtle Egg Hatch";
			//case "": return "";
			
			default: return "{"+key+"}";
		}
	}
	
	public static File getLocaleFolder() {
		File folder = new File(ToolMain.getInstance().getDataFolder(), "locale");
		if(!folder.exists() || !folder.isDirectory())
			folder.mkdir();
		return folder;
	}

}
