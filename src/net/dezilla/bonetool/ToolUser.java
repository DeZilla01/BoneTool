package net.dezilla.bonetool;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.bukkit.ChatColor;
import org.bukkit.DyeColor;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import net.dezilla.bonetool.util.ToolConfig;
import net.dezilla.bonetool.wandtool.NoneTool;
import net.dezilla.bonetool.wandtool.WandTool;

public class ToolUser {
	//static stuff
	private static List<ToolUser> USERS = new ArrayList<ToolUser>();
	
	public static ToolUser getUser(Player player) {
		for(ToolUser u : USERS) {
			if(u.getPlayer().equals(player))
				return u;
		}
		return new ToolUser(player);
	}
	//ToolUser Object
	private Player player;
	private Timestamp lastUse;
	private Map<PlacingOption, PlacingState> placingOptions = new HashMap<PlacingOption, PlacingState>();
	private Map<PlacingOption, Boolean> placingToggle = new HashMap<PlacingOption, Boolean>();
	private boolean editFlySpeed = false;
	private boolean editSign = false;
	private boolean noLitter = false;
	private NotificationLocation notification = NotificationLocation.ACTIONBAR;
	private Material wandMaterial = ToolConfig.toolMaterial;
	private WandTool rightClickTool = new NoneTool();
	private DyeColor toolColor = DyeColor.WHITE;
	private Map<Material, WandTool> debugTool = new HashMap<Material, WandTool>();
	
	private ToolUser(Player player) {
		this.player = player;
		USERS.add(this);
		lastUse = new Timestamp(0);
		loadUserData();
	}
	
	public WandTool getRightClickTool() {
		return rightClickTool;
	}
	
	public ToolUser setRightClickTool(WandTool tool) {
		rightClickTool = tool;
		saveUserData();
		return this;
	}
	
	public Material getWandMaterial() {
		return wandMaterial;
	}
	
	public ToolUser setWandMaterial(Material material) {
		wandMaterial=material;
		saveUserData();
		return this;
	}
	
	public Timestamp getLastUse() {
		return lastUse;
	}
	
	public ToolUser setLastUseNow() {
		lastUse = new Timestamp(new Date().getTime()); 
		return this;
	}
	
	public Player getPlayer() {
		return player;
	}
	
	// Set State
	
	public ToolUser setNoBlockUpdate(PlacingState state) {
		placingOptions.put(PlacingOption.NOBLOCKUPDATE, state);
		saveUserData();
		return this;
	}
	
	public ToolUser setForcePlace(PlacingState state) {
		placingOptions.put(PlacingOption.FORCEPLACE, state);
		saveUserData();
		return this;
	}
	
	public ToolUser setSnipePlace(PlacingState state) {
		placingOptions.put(PlacingOption.SNIPEPLACE, state);
		saveUserData();
		return this;
	}
	
	// Get State
	
	public PlacingState getBlockUpdateState() {
		return getState(PlacingOption.NOBLOCKUPDATE);
	}
	
	public PlacingState getForcePlacingState() {
		return getState(PlacingOption.FORCEPLACE);
	}
	
	public PlacingState getSnipePlaceState() {
		return getState(PlacingOption.SNIPEPLACE);
	}
	
	private PlacingState getState(PlacingOption option) {
		if(!placingOptions.containsKey(option))
			placingOptions.put(option, PlacingState.DISABLED);
		return placingOptions.get(option);
	}
	
	// Is State
	
	public boolean isBlockUpdateProtected() {
		if(!Util.permCheck(getPlayer(), "bonetool.tool.nobupdate"))
			return false;
		return stateCheck(PlacingOption.NOBLOCKUPDATE);
	}
	
	public boolean isForcePlacing() {
		if(!Util.permCheck(getPlayer(), "bonetool.tool.forceplace"))
			return false;
		return stateCheck(PlacingOption.FORCEPLACE);
	}
	
	public boolean isSnipePlacing() {
		if(!Util.permCheck(getPlayer(), "bonetool.tool.snipeplace"))
			return false;
		return stateCheck(PlacingOption.SNIPEPLACE);
	}
	
	private boolean stateCheck(PlacingOption option) {
		if(!placingOptions.containsKey(option))
			placingOptions.put(option, PlacingState.DISABLED);
		if(!placingToggle.containsKey(option))
			placingToggle.put(option, false);
		if(placingOptions.get(option) == PlacingState.ENABLED)
			return true;
		else if(placingOptions.get(option) == PlacingState.ONSNEAK && player.isSneaking())
			return true;
		else if(placingOptions.get(option) == PlacingState.SNEAKTOGGLE && placingToggle.get(option))
			return true;
		return false;
	}
	
	// Sneak Toggle
	
	public void sneakToggle() {
		String msg = "";
		
		for(PlacingOption option : PlacingOption.values()) {
			if(getState(option) == PlacingState.SNEAKTOGGLE) {
				if(!placingToggle.containsKey(option))
					placingToggle.put(option, false);
				placingToggle.put(option, !placingToggle.get(option));
				msg += ChatColor.GRAY+option.getName()+":"+(placingToggle.get(option) ? ChatColor.GREEN+" True ": ChatColor.RED+" False ");
			}
		}
		if(!msg.isEmpty())
			Util.sendNotification(this, msg);
	}
	
	public void ToggleOption(PlacingOption option) {
		List<PlacingState> states = Arrays.asList(PlacingState.values());
		if(states.indexOf(getState(option))+1 >= states.size())
			placingOptions.put(option, states.get(0));
		else
			placingOptions.put(option, states.get(states.indexOf(getState(option))+1));
		saveUserData();
	}
	
	public ToolUser setEditFlySpeed(boolean value) {
		editFlySpeed = value;
		saveUserData();
		return this;
	}
	
	public boolean getEditFlySpeed() {
		if(!Util.permCheck(player, "bonetool.tool.flyspeed"))
			return false;
		return editFlySpeed;
	}
	
	public ToolUser setEditSign(boolean value) {
		editSign = value;
		saveUserData();
		return this;
	}
	
	public boolean getEditSign() {
		if(!Util.permCheck(player, "bonetool.tool.signedit"))
			return false;
		return editSign;
	}
	
	public boolean getNoLitter() {
		if(!Util.permCheck(player, "bonetool.tool.nolitter"))
			return false;
		return noLitter;
	}
	
	public ToolUser setNoLitter(boolean value) {
		noLitter = value;
		saveUserData();
		return this;
	}
	
	public void setNotificationLoc(NotificationLocation location) {
		notification = location;
		saveUserData();
	}
	
	public void toggleNotificationLoc() {
		List<NotificationLocation> list = Arrays.asList(NotificationLocation.values());
		if(list.indexOf(notification)+1 >= list.size())
			notification = list.get(0);
		else
			notification = list.get(list.indexOf(notification)+1);
		saveUserData();
	}
	
	public NotificationLocation getNotificationLoc() {
		return notification;
	}
	
	public ToolUser setToolColor(DyeColor color) {
		toolColor = color;
		saveUserData();
		return this;
	}
	
	public DyeColor getToolColor() {
		return toolColor;
	}
	
	public WandTool getRightClickToolFor(Block block) {
		if(debugTool.containsKey(block.getType()))
			return debugTool.get(block.getType());
		List<WandTool> compatible = getCompatibleTools(block);
		if(compatible.isEmpty()) {
			return null;
		}
		debugTool.put(block.getType(), compatible.get(0));
		return compatible.get(0);
	}
	
	public WandTool toggleRightClickToolFor(Block block) {
		WandTool current = getRightClickToolFor(block);
		if(current == null)
			return null;
		List<WandTool> compatible = getCompatibleTools(block);
		if(compatible.indexOf(current)+1 >= compatible.size()) {
			debugTool.put(block.getType(), compatible.get(0));
			return compatible.get(0);
		}
		debugTool.put(block.getType(), compatible.get(compatible.indexOf(current)+1));
		return compatible.get(compatible.indexOf(current)+1);
	}
	
	private List<WandTool> getCompatibleTools(Block block){
		List<WandTool> compatible = new ArrayList<WandTool>();
		for(WandTool t : ToolMain.getTools()) {
			if(t.isBlockCompatible(block) && t.canToggle()) {
				if(Util.isToolBlacklisted(t) && !Util.permCheck(getPlayer(), "bonetool.admin.bypass"))
					continue;
				compatible.add(t);
			}
		}
		return compatible;
	}
	
	@SuppressWarnings("unchecked")
	private void saveUserData() {
		File folder = getUserDataFolder();
		//
		for(PlacingOption option : PlacingOption.values())
			stateCheck(option);
		//
		JSONObject json = new JSONObject();
		json.put("noBlockUpdate", placingOptions.get(PlacingOption.NOBLOCKUPDATE).toString());
		json.put("forcePlace", placingOptions.get(PlacingOption.FORCEPLACE).toString());
		json.put("snipePlace", placingOptions.get(PlacingOption.SNIPEPLACE).toString());
		json.put("editFlySpeed", editFlySpeed);
		json.put("editSign", editSign);
		json.put("noLitter", noLitter);
		json.put("notification", notification.toString());
		json.put("wandMaterial", wandMaterial.toString());
		json.put("rightClickTool", rightClickTool.getClass().getSimpleName());
		json.put("toolColor", toolColor.toString());
		//
		File file = new File(folder, player.getUniqueId().toString()+".json");
		try(FileWriter f = new FileWriter(file)){
			f.write(json.toJSONString());
			f.flush();
		}catch(IOException e) {
			e.printStackTrace();
		}
	}
	
	private void loadUserData() {
		File folder = getUserDataFolder();
		File file = new File(folder, player.getUniqueId().toString()+".json");
		if(!file.exists())
			return;
		JSONParser jsonParser = new JSONParser();
		try(FileReader reader = new FileReader(file)){
			Object obj = jsonParser.parse(reader);
			JSONObject json = (JSONObject) obj;
			try{placingOptions.put(PlacingOption.NOBLOCKUPDATE, PlacingState.valueOf((String) json.get("noBlockUpdate")));}catch(Exception e) {}
			try{placingOptions.put(PlacingOption.FORCEPLACE, PlacingState.valueOf((String) json.get("forcePlace")));}catch(Exception e) {}
			try{placingOptions.put(PlacingOption.SNIPEPLACE, PlacingState.valueOf((String) json.get("snipePlace")));}catch(Exception e) {}
			try{editFlySpeed = (boolean) json.get("editFlySpeed");}catch(Exception e) {}
			try{editSign = (boolean) json.get("editSign");}catch(Exception e) {}
			try{noLitter = (boolean) json.get("noLitter");}catch(Exception e) {}
			try{notification = NotificationLocation.valueOf((String) json.get("notification"));}catch(Exception e) {}
			try{wandMaterial = Material.valueOf((String) json.get("wandMaterial"));}catch(Exception e) {}
			try{for(WandTool t : ToolMain.getTools()) {
				if(t.getClass().getSimpleName().equalsIgnoreCase((String) json.get("rightClickTool"))) {
					rightClickTool = t;
					break;
				}
			}}catch(Exception e) {}
			try{toolColor = DyeColor.valueOf((String) json.get("toolColor"));}catch(Exception e) {}
		}catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	private File getUserDataFolder() {
		File folder = new File(ToolMain.getInstance().getDataFolder(), "userdata");
		if(!folder.exists() || !folder.isDirectory())
			folder.mkdir();
		return folder;
	}
	
	public enum PlacingState{
		DISABLED(ChatColor.RED, "Disabled"),
		ENABLED(ChatColor.GREEN, "Enabled"),
		ONSNEAK(ChatColor.AQUA, "On Sneak"),
		SNEAKTOGGLE(ChatColor.YELLOW, "Sneak Toggle");
		ChatColor color;
		String name;
		PlacingState(ChatColor color, String name){
			this.color = color;
			this.name = name;
		}
		public ChatColor getColor() {
			return color;
		}
		public String getName() {
			return name;
		}
		public String getColoredName() {
			return color+name;
		}
	}
	
	public enum PlacingOption{
		NOBLOCKUPDATE("No Block Update"),
		FORCEPLACE("Force Placing"),
		SNIPEPLACE("Snipe Placing");
		String name;
		PlacingOption(String name) {
			this.name = name;
		}
		public String getName() {
			return name;
		}
	}
	
	public enum NotificationLocation{
		SUBTITLE,
		ACTIONBAR,
		CHAT;
	}
}
