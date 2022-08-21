package net.dezilla.bonetool;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.List;
import java.util.Set;
import java.util.UUID;

import org.apache.commons.lang.StringUtils;
import org.bukkit.Art;
import org.bukkit.ChatColor;
import org.bukkit.DyeColor;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.block.Sign;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.PotionMeta;
import org.bukkit.inventory.meta.SkullMeta;
import org.bukkit.persistence.PersistentDataType;
import org.bukkit.potion.PotionEffectType;

import com.mojang.authlib.GameProfile;
import com.mojang.authlib.properties.Property;

import net.dezilla.bonetool.util.Locale;
import net.dezilla.bonetool.util.SignEdit1_17;
import net.dezilla.bonetool.util.SignEdit1_18;
import net.dezilla.bonetool.util.ToolConfig;
import net.dezilla.bonetool.wandtool.WandTool;
import net.md_5.bungee.api.ChatMessageType;
import net.md_5.bungee.api.chat.TextComponent;

public class Util {
	public static String MSG_START = ChatColor.DARK_GRAY+"» "+ChatColor.WHITE;
	public static NamespacedKey specialBlockKey = new NamespacedKey(ToolMain.getInstance(), "blockType");
	//I wanted a proper clockwise list of blockface.
	public static BlockFace[] BLOCKFACE_LIST = {
			BlockFace.NORTH,
			BlockFace.NORTH_NORTH_EAST,
			BlockFace.NORTH_EAST,
			BlockFace.EAST_NORTH_EAST,
			BlockFace.EAST,
			BlockFace.EAST_SOUTH_EAST,
			BlockFace.SOUTH_EAST,
			BlockFace.SOUTH_SOUTH_EAST,
			BlockFace.SOUTH,
			BlockFace.SOUTH_SOUTH_WEST,
			BlockFace.SOUTH_WEST,
			BlockFace.WEST_SOUTH_WEST,
			BlockFace.WEST,
			BlockFace.WEST_NORTH_WEST,
			BlockFace.NORTH_WEST,
			BlockFace.NORTH_NORTH_WEST,
			BlockFace.UP,
			BlockFace.DOWN,
			BlockFace.SELF};
	
	public static ItemStack setName(ItemStack item, String name) {
		ItemMeta meta = item.getItemMeta();
		meta.setDisplayName(ChatColor.RESET+name);
		item.setItemMeta(meta);
		return item;
	}
	
	public static ItemStack setLore(ItemStack item, String lore) {
		return setLore(item, Arrays.asList(lore));
	}
	
	public static String matName(Material material) {
		String s = material.toString().toLowerCase().replace("_", " ");
		return StringUtils.capitalize(s);
	}
	
	public static ItemStack setLore(ItemStack item, List<String> lore) {
		for(String s : lore)
			s = ChatColor.RESET+s;
		ItemMeta meta = item.getItemMeta();
		meta.setLore(lore);
		item.setItemMeta(meta);
		return item;
	}
	
	public static ItemStack getItemInHand(Player player) {
		return player.getInventory().getItemInMainHand();
	}
	
	public static BlockFace getBlockFacing(Player player, Set<BlockFace> faces) {
		if(player.getLocation().getPitch() > 60 && faces.contains(BlockFace.UP))
			return BlockFace.UP;
		if(player.getLocation().getPitch() < -60 && faces.contains(BlockFace.DOWN))
			return BlockFace.DOWN;
		if(faces.contains(getPlayerFacing(player)))
			return getPlayerFacing(player).getOppositeFace();
		String[] narrowed = {"NORTH", "SOUTH", "EAST", "WEST"};
		for(String s : narrowed) {
			if(player.getFacing().toString().startsWith(s)) {
				BlockFace face = BlockFace.valueOf(s);
				if(faces.contains(face))
					return face.getOppositeFace();
			}
		}
		//fuck it
		for(BlockFace f : BlockFace.values())
			if(faces.contains(f))
				return f;
		//at this point fuck you
		return null;
	}
	
	public static BlockFace getPlayerFacing(Player player) {
		float yaw = player.getLocation().getYaw();
		while(yaw<-180)
			yaw+=360;
		while(yaw>180)
			yaw-=360;
		float yawToTest = -168.75f;
		for(BlockFace face : BLOCKFACE_LIST) {
			if(yaw <  yawToTest)
				return face;
			else
				yawToTest+=22.5;
		}
		return BlockFace.NORTH;
	}
	
	public static ItemStack getPaintingIcon(Art art) {
		String name = art.toString().replace("_", " ").toLowerCase();
		name = StringUtils.capitalize(name);
		
		name +=" ("+art.getBlockWidth()+" x "+art.getBlockHeight()+")";
		
		//it took me an hour and a half to create these textures, but worth it. Feel free to copy if you need =)
		switch(art.toString()) {
			case "ALBAN": return setName(createTexturedHead("ewogICJ0aW1lc3RhbXAiIDogMTYwNjk5Njg3ODk5MSwKICAicHJvZmlsZUlkIiA6ICJhOTJkZWM4NWI2YjA0ZWRmYmZiNTRmMzBlMmUxYTAwMiIsCiAgInByb2ZpbGVOYW1lIiA6ICJBdXRvQmFuIiwKICAidGV4dHVyZXMiIDogewogICAgIlNLSU4iIDogewogICAgICAidXJsIiA6ICJodHRwOi8vdGV4dHVyZXMubWluZWNyYWZ0Lm5ldC90ZXh0dXJlLzlhNzlkOWJjNDBmNjA2OTYxODYyNDM5MGQ1NTQ4ZWMyMmQzOWZmYzM1NTYyNzcwNjJjMzFmYjg1ZDAyOGY5MGEiCiAgICB9CiAgfQp9"), name);
			case "AZTEC": return setName(createTexturedHead("ewogICJ0aW1lc3RhbXAiIDogMTYwNjk5OTczMTU3NSwKICAicHJvZmlsZUlkIiA6ICJhOTJkZWM4NWI2YjA0ZWRmYmZiNTRmMzBlMmUxYTAwMiIsCiAgInByb2ZpbGVOYW1lIiA6ICJBdXRvQmFuIiwKICAidGV4dHVyZXMiIDogewogICAgIlNLSU4iIDogewogICAgICAidXJsIiA6ICJodHRwOi8vdGV4dHVyZXMubWluZWNyYWZ0Lm5ldC90ZXh0dXJlLzVmNzI1MDQyMDE0ZjVhNGUwNDdhZDI0NDZjMmI4N2M3MDI0MjJlZmMwZjhhN2EzZTQyYWI2ZTk5MGNjMmM0Y2UiCiAgICB9CiAgfQp9"), name);
			case "AZTEC2": return setName(createTexturedHead("ewogICJ0aW1lc3RhbXAiIDogMTYwNjk5OTc3NzUzMCwKICAicHJvZmlsZUlkIiA6ICJhOTJkZWM4NWI2YjA0ZWRmYmZiNTRmMzBlMmUxYTAwMiIsCiAgInByb2ZpbGVOYW1lIiA6ICJBdXRvQmFuIiwKICAidGV4dHVyZXMiIDogewogICAgIlNLSU4iIDogewogICAgICAidXJsIiA6ICJodHRwOi8vdGV4dHVyZXMubWluZWNyYWZ0Lm5ldC90ZXh0dXJlLzgwNTQ4MGE0YzRmODE2YjkyODQ3Y2NjNzdkYzRmMmZiYTI1ZGJjMDMxYzAxMjQzYmVmZTVlZWQxNzkxMWNlNDMiCiAgICB9CiAgfQp9"), name);
			case "BOMB": return setName(createTexturedHead("ewogICJ0aW1lc3RhbXAiIDogMTYwNjk5OTgxMTg4MCwKICAicHJvZmlsZUlkIiA6ICJhOTJkZWM4NWI2YjA0ZWRmYmZiNTRmMzBlMmUxYTAwMiIsCiAgInByb2ZpbGVOYW1lIiA6ICJBdXRvQmFuIiwKICAidGV4dHVyZXMiIDogewogICAgIlNLSU4iIDogewogICAgICAidXJsIiA6ICJodHRwOi8vdGV4dHVyZXMubWluZWNyYWZ0Lm5ldC90ZXh0dXJlLzdlM2I4NDliZWVlZWI0YWYyZDhiYmQyOTRiNWEzNzM2NWI0Njg2ODQzZmFmMGQ1N2U5OTJiNWViOWY5YTA3MTIiCiAgICB9CiAgfQp9"), name);
			case "BURNING_SKULL": return setName(createTexturedHead("ewogICJ0aW1lc3RhbXAiIDogMTYwNjk5OTg1ODYwMSwKICAicHJvZmlsZUlkIiA6ICJhOTJkZWM4NWI2YjA0ZWRmYmZiNTRmMzBlMmUxYTAwMiIsCiAgInByb2ZpbGVOYW1lIiA6ICJBdXRvQmFuIiwKICAidGV4dHVyZXMiIDogewogICAgIlNLSU4iIDogewogICAgICAidXJsIiA6ICJodHRwOi8vdGV4dHVyZXMubWluZWNyYWZ0Lm5ldC90ZXh0dXJlLzE3ODFlNzQwOTBjZmRkMTk0ZDgzMjkzNDQ2ZjQxZGQwOGQ4ZTk3ZThhMGZmYjA0ZDg0OWUxMzYxMzQwOGM5YjkiCiAgICB9CiAgfQp9"), name);
			case "BUST": return setName(createTexturedHead("ewogICJ0aW1lc3RhbXAiIDogMTYwNjk5OTkzNTQ2OSwKICAicHJvZmlsZUlkIiA6ICJhOTJkZWM4NWI2YjA0ZWRmYmZiNTRmMzBlMmUxYTAwMiIsCiAgInByb2ZpbGVOYW1lIiA6ICJBdXRvQmFuIiwKICAidGV4dHVyZXMiIDogewogICAgIlNLSU4iIDogewogICAgICAidXJsIiA6ICJodHRwOi8vdGV4dHVyZXMubWluZWNyYWZ0Lm5ldC90ZXh0dXJlLzdkMDJhYzZjNmYyOTFiZjM4MzVmYjY3Yzc0OTY4ZDBkMzIxOTQxZmQyNDMwMTYwOWQwNTUzZjRjYWE1MzYxZWIiCiAgICB9CiAgfQp9"), name);
			case "COURBET": return setName(createTexturedHead("ewogICJ0aW1lc3RhbXAiIDogMTYwNzAwMDI5MDkyMiwKICAicHJvZmlsZUlkIiA6ICJhOTJkZWM4NWI2YjA0ZWRmYmZiNTRmMzBlMmUxYTAwMiIsCiAgInByb2ZpbGVOYW1lIiA6ICJBdXRvQmFuIiwKICAidGV4dHVyZXMiIDogewogICAgIlNLSU4iIDogewogICAgICAidXJsIiA6ICJodHRwOi8vdGV4dHVyZXMubWluZWNyYWZ0Lm5ldC90ZXh0dXJlLzVlMTUwZWQ2ODJjYzUyODAwYWZhYzYyYzRmMjI0YTM1MmU5YzQ4YjIzNzlhODQzZTI1OTUzYTY3YjNkMGE4YjMiCiAgICB9CiAgfQp9"), name);
			case "CREEBET": return setName(createTexturedHead("ewogICJ0aW1lc3RhbXAiIDogMTYwNzAwMDMxODA1MywKICAicHJvZmlsZUlkIiA6ICJhOTJkZWM4NWI2YjA0ZWRmYmZiNTRmMzBlMmUxYTAwMiIsCiAgInByb2ZpbGVOYW1lIiA6ICJBdXRvQmFuIiwKICAidGV4dHVyZXMiIDogewogICAgIlNLSU4iIDogewogICAgICAidXJsIiA6ICJodHRwOi8vdGV4dHVyZXMubWluZWNyYWZ0Lm5ldC90ZXh0dXJlLzZiMDU0MmFhNTU0ODBkZGEzYjI5NWI1NmNmN2UzYTk5NDcxODU3YmQyMDAxYmI3MzY2ODRkNWE1NTZhMWMzZmQiCiAgICB9CiAgfQp9"), name);
			case "DONKEY_KONG": return setName(createTexturedHead("ewogICJ0aW1lc3RhbXAiIDogMTYwNzAwMDM0NTEyOCwKICAicHJvZmlsZUlkIiA6ICJhOTJkZWM4NWI2YjA0ZWRmYmZiNTRmMzBlMmUxYTAwMiIsCiAgInByb2ZpbGVOYW1lIiA6ICJBdXRvQmFuIiwKICAidGV4dHVyZXMiIDogewogICAgIlNLSU4iIDogewogICAgICAidXJsIiA6ICJodHRwOi8vdGV4dHVyZXMubWluZWNyYWZ0Lm5ldC90ZXh0dXJlLzkzMjc2MzQ2Zjg0NWQ2NTJhOTQwMDUyM2IyNjFhYmI5OGYwNDVjZDBkYjRiOWE2ZTRjODAxNTUzOGRlNzBmYTQiCiAgICB9CiAgfQp9"), name);
			case "FIGHTERS": return setName(createTexturedHead("ewogICJ0aW1lc3RhbXAiIDogMTYwNzAwMDM3NDk4NSwKICAicHJvZmlsZUlkIiA6ICJhOTJkZWM4NWI2YjA0ZWRmYmZiNTRmMzBlMmUxYTAwMiIsCiAgInByb2ZpbGVOYW1lIiA6ICJBdXRvQmFuIiwKICAidGV4dHVyZXMiIDogewogICAgIlNLSU4iIDogewogICAgICAidXJsIiA6ICJodHRwOi8vdGV4dHVyZXMubWluZWNyYWZ0Lm5ldC90ZXh0dXJlL2YzMzhmZjRlOGNhMDU0ZmI4YjI2ODMxMGMxYjM1MjgxOGQ3YTBhMjEyMTk4ODViNTQ3NGMwMWVmYmVlNDJiMjUiCiAgICB9CiAgfQp9"), name);
			case "GRAHAM": return setName(createTexturedHead("ewogICJ0aW1lc3RhbXAiIDogMTYwNzAwMDQxNDIyOCwKICAicHJvZmlsZUlkIiA6ICJhOTJkZWM4NWI2YjA0ZWRmYmZiNTRmMzBlMmUxYTAwMiIsCiAgInByb2ZpbGVOYW1lIiA6ICJBdXRvQmFuIiwKICAidGV4dHVyZXMiIDogewogICAgIlNLSU4iIDogewogICAgICAidXJsIiA6ICJodHRwOi8vdGV4dHVyZXMubWluZWNyYWZ0Lm5ldC90ZXh0dXJlLzNhY2U5NjcyYTg3ZDZiMzliMjI1MzJhMmRkYmI0Y2JjN2FjM2QxYzY2OTFkMjdhYTI2ODMxMThhMDJiYTE1MGIiCiAgICB9CiAgfQp9"), name);
			case "KEBAB": return setName(createTexturedHead("ewogICJ0aW1lc3RhbXAiIDogMTYwNzAwMDQ3OTY0NSwKICAicHJvZmlsZUlkIiA6ICJhOTJkZWM4NWI2YjA0ZWRmYmZiNTRmMzBlMmUxYTAwMiIsCiAgInByb2ZpbGVOYW1lIiA6ICJBdXRvQmFuIiwKICAidGV4dHVyZXMiIDogewogICAgIlNLSU4iIDogewogICAgICAidXJsIiA6ICJodHRwOi8vdGV4dHVyZXMubWluZWNyYWZ0Lm5ldC90ZXh0dXJlLzZjMDhjODg5Yzg4MmNmNDM2MDE0NDZiNWM0MjEwOGMyNTVlMmU0M2RhZTYwOGFjYTk1Y2IzN2I4ZDBjNDdhMzciCiAgICB9CiAgfQp9"), name);
			case "MATCH": return setName(createTexturedHead("ewogICJ0aW1lc3RhbXAiIDogMTYwNzAwMDcyNTU3OSwKICAicHJvZmlsZUlkIiA6ICJhOTJkZWM4NWI2YjA0ZWRmYmZiNTRmMzBlMmUxYTAwMiIsCiAgInByb2ZpbGVOYW1lIiA6ICJBdXRvQmFuIiwKICAidGV4dHVyZXMiIDogewogICAgIlNLSU4iIDogewogICAgICAidXJsIiA6ICJodHRwOi8vdGV4dHVyZXMubWluZWNyYWZ0Lm5ldC90ZXh0dXJlLzc3OTBiNjllMmQ1ZjBkOWRmMTYzZjY4YmNmMzBkZTM2MWJiOWE4ZjkxOWI2MTU4NjBhMzdhMTNmNjdjMjE2ZDgiCiAgICB9CiAgfQp9"), name);
			case "PIGSCENE": return setName(createTexturedHead("ewogICJ0aW1lc3RhbXAiIDogMTYwNzAwMDc4NTM1NiwKICAicHJvZmlsZUlkIiA6ICJhOTJkZWM4NWI2YjA0ZWRmYmZiNTRmMzBlMmUxYTAwMiIsCiAgInByb2ZpbGVOYW1lIiA6ICJBdXRvQmFuIiwKICAidGV4dHVyZXMiIDogewogICAgIlNLSU4iIDogewogICAgICAidXJsIiA6ICJodHRwOi8vdGV4dHVyZXMubWluZWNyYWZ0Lm5ldC90ZXh0dXJlL2EyOTI3OTExM2M3YTY1MTQ2YzdhMTEzNGE4ZTQ5NTIxYTI0ZWQyMDJlZjQ4Y2RmMjU5Njk5ZWU4Yzg3Njc3NGUiCiAgICB9CiAgfQp9"), name);
			case "PLANT": return setName(createTexturedHead("ewogICJ0aW1lc3RhbXAiIDogMTYwNzAwMDgxNDE0MiwKICAicHJvZmlsZUlkIiA6ICJhOTJkZWM4NWI2YjA0ZWRmYmZiNTRmMzBlMmUxYTAwMiIsCiAgInByb2ZpbGVOYW1lIiA6ICJBdXRvQmFuIiwKICAidGV4dHVyZXMiIDogewogICAgIlNLSU4iIDogewogICAgICAidXJsIiA6ICJodHRwOi8vdGV4dHVyZXMubWluZWNyYWZ0Lm5ldC90ZXh0dXJlL2UyY2YzNDAyNDFkMGVhZmVkYjlhMmNhN2JjOGMzNDI0NTkyYzgzNGZmMTIxOTI0YThlMjE2NjY0ZjJjNjE2ZSIKICAgIH0KICB9Cn0="), name);
			case "POINTER": return setName(createTexturedHead("ewogICJ0aW1lc3RhbXAiIDogMTYwNzAwMDg2NDQ2MCwKICAicHJvZmlsZUlkIiA6ICJhOTJkZWM4NWI2YjA0ZWRmYmZiNTRmMzBlMmUxYTAwMiIsCiAgInByb2ZpbGVOYW1lIiA6ICJBdXRvQmFuIiwKICAidGV4dHVyZXMiIDogewogICAgIlNLSU4iIDogewogICAgICAidXJsIiA6ICJodHRwOi8vdGV4dHVyZXMubWluZWNyYWZ0Lm5ldC90ZXh0dXJlL2ZkNTFlNjg2NTc4YzExMDI3ZjVkNzg3MDRiMDQxYmJlOWM1YjhjNmYzNjRjMzY4MDlhY2U2NzUyODk1ZmUyNTMiCiAgICB9CiAgfQp9"), name);
			case "POOL": return setName(createTexturedHead("ewogICJ0aW1lc3RhbXAiIDogMTYwNzAwMDg5MDQzMywKICAicHJvZmlsZUlkIiA6ICJhOTJkZWM4NWI2YjA0ZWRmYmZiNTRmMzBlMmUxYTAwMiIsCiAgInByb2ZpbGVOYW1lIiA6ICJBdXRvQmFuIiwKICAidGV4dHVyZXMiIDogewogICAgIlNLSU4iIDogewogICAgICAidXJsIiA6ICJodHRwOi8vdGV4dHVyZXMubWluZWNyYWZ0Lm5ldC90ZXh0dXJlLzE0YTNiN2YxN2Y3ODE3MGJmZDg2MTU0NzU2Y2I5MjViOTg3YWVlYmRhYTA2MGQzZTFhOTk5Zjk4ZDFmMzU0OGQiCiAgICB9CiAgfQp9"), name);
			case "SEA": return setName(createTexturedHead("ewogICJ0aW1lc3RhbXAiIDogMTYwNzAwMDkyNTg5MCwKICAicHJvZmlsZUlkIiA6ICJhOTJkZWM4NWI2YjA0ZWRmYmZiNTRmMzBlMmUxYTAwMiIsCiAgInByb2ZpbGVOYW1lIiA6ICJBdXRvQmFuIiwKICAidGV4dHVyZXMiIDogewogICAgIlNLSU4iIDogewogICAgICAidXJsIiA6ICJodHRwOi8vdGV4dHVyZXMubWluZWNyYWZ0Lm5ldC90ZXh0dXJlL2FjY2JiZjNkZDI0Zjk3MTlmZWMwNmVlOGQ4ZGVlODUxY2JkOGFiN2JmMGUzNGJkMDUyYTE1ZmQ1MzZlZTI5MDAiCiAgICB9CiAgfQp9"), name);
			case "SKELETON": return setName(createTexturedHead("ewogICJ0aW1lc3RhbXAiIDogMTYwNzAwMDk2MzAxMCwKICAicHJvZmlsZUlkIiA6ICJhOTJkZWM4NWI2YjA0ZWRmYmZiNTRmMzBlMmUxYTAwMiIsCiAgInByb2ZpbGVOYW1lIiA6ICJBdXRvQmFuIiwKICAidGV4dHVyZXMiIDogewogICAgIlNLSU4iIDogewogICAgICAidXJsIiA6ICJodHRwOi8vdGV4dHVyZXMubWluZWNyYWZ0Lm5ldC90ZXh0dXJlL2NmMThmNmM5YzJmMThmMWU3ZmZiNGJmYTYzMmY5MmYyNzE4OTBkZGE4NDhmYzRjMDUxMjZjNGYwNTdiNjFjNzUiCiAgICB9CiAgfQp9"), name);
			case "SKULL_AND_ROSES": return setName(createTexturedHead("ewogICJ0aW1lc3RhbXAiIDogMTYwNzAwMDk4NzE0NywKICAicHJvZmlsZUlkIiA6ICJhOTJkZWM4NWI2YjA0ZWRmYmZiNTRmMzBlMmUxYTAwMiIsCiAgInByb2ZpbGVOYW1lIiA6ICJBdXRvQmFuIiwKICAidGV4dHVyZXMiIDogewogICAgIlNLSU4iIDogewogICAgICAidXJsIiA6ICJodHRwOi8vdGV4dHVyZXMubWluZWNyYWZ0Lm5ldC90ZXh0dXJlLzc1NzBkMDMxM2ZkOWJjZjg3Yzk2YWUwNWRmMDNmMTRmMmIyZWVjNWYwZTdhN2RiMDdmNWViNWQ4ZGM1ZjgwMmYiCiAgICB9CiAgfQp9"), name);
			case "STAGE": return setName(createTexturedHead("ewogICJ0aW1lc3RhbXAiIDogMTYwNzAwMTAzMjY2NywKICAicHJvZmlsZUlkIiA6ICJhOTJkZWM4NWI2YjA0ZWRmYmZiNTRmMzBlMmUxYTAwMiIsCiAgInByb2ZpbGVOYW1lIiA6ICJBdXRvQmFuIiwKICAidGV4dHVyZXMiIDogewogICAgIlNLSU4iIDogewogICAgICAidXJsIiA6ICJodHRwOi8vdGV4dHVyZXMubWluZWNyYWZ0Lm5ldC90ZXh0dXJlL2I3NjY5ZWRjYTQ3MmQ2NWZmMjQxMWQwZTZlN2I2NzY1NGIwOWQ1ZjRhZjIwYzVmNjk5ZDNjMzk0N2FiZDMxOWEiCiAgICB9CiAgfQp9"), name);
			case "SUNSET": return setName(createTexturedHead("ewogICJ0aW1lc3RhbXAiIDogMTYwNzAwMTEyMDE1OCwKICAicHJvZmlsZUlkIiA6ICJhOTJkZWM4NWI2YjA0ZWRmYmZiNTRmMzBlMmUxYTAwMiIsCiAgInByb2ZpbGVOYW1lIiA6ICJBdXRvQmFuIiwKICAidGV4dHVyZXMiIDogewogICAgIlNLSU4iIDogewogICAgICAidXJsIiA6ICJodHRwOi8vdGV4dHVyZXMubWluZWNyYWZ0Lm5ldC90ZXh0dXJlLzhkNzg5NzFmMGQ4YmMxY2IwY2U2NDIzMGE2Nzg4NGFhMGYzNzE4Yjg1YTdlMTk5NDFiNzk3NGY1MDFkZGZiNCIKICAgIH0KICB9Cn0="), name);
			case "VOID": return setName(createTexturedHead("ewogICJ0aW1lc3RhbXAiIDogMTYwNzAwMTE0ODU0NywKICAicHJvZmlsZUlkIiA6ICJhOTJkZWM4NWI2YjA0ZWRmYmZiNTRmMzBlMmUxYTAwMiIsCiAgInByb2ZpbGVOYW1lIiA6ICJBdXRvQmFuIiwKICAidGV4dHVyZXMiIDogewogICAgIlNLSU4iIDogewogICAgICAidXJsIiA6ICJodHRwOi8vdGV4dHVyZXMubWluZWNyYWZ0Lm5ldC90ZXh0dXJlL2NjNjdiZjI5ZDY5NzkzYjg5ZGU2OGJmYjllNDI2ZjgwZjkxMjg5OTM4ZTVmMzczOTAwMTdmZTRkZDk3MjllNzkiCiAgICB9CiAgfQp9"), name);
			case "WANDERER": return setName(createTexturedHead("ewogICJ0aW1lc3RhbXAiIDogMTYwNzAwMTE3Njk0MSwKICAicHJvZmlsZUlkIiA6ICJhOTJkZWM4NWI2YjA0ZWRmYmZiNTRmMzBlMmUxYTAwMiIsCiAgInByb2ZpbGVOYW1lIiA6ICJBdXRvQmFuIiwKICAidGV4dHVyZXMiIDogewogICAgIlNLSU4iIDogewogICAgICAidXJsIiA6ICJodHRwOi8vdGV4dHVyZXMubWluZWNyYWZ0Lm5ldC90ZXh0dXJlL2Q1M2I0N2YwNTI3YjZkZmZmMmI1Nzc3NzRkYWVjZjkxMzE1M2NiYmI2MWVjZDA4NzZlZmQwMGFjMzFlYTVkNDciCiAgICB9CiAgfQp9"), name);
			case "WASTELAND": return setName(createTexturedHead("ewogICJ0aW1lc3RhbXAiIDogMTYwNzAwMTIwMDAxOSwKICAicHJvZmlsZUlkIiA6ICJhOTJkZWM4NWI2YjA0ZWRmYmZiNTRmMzBlMmUxYTAwMiIsCiAgInByb2ZpbGVOYW1lIiA6ICJBdXRvQmFuIiwKICAidGV4dHVyZXMiIDogewogICAgIlNLSU4iIDogewogICAgICAidXJsIiA6ICJodHRwOi8vdGV4dHVyZXMubWluZWNyYWZ0Lm5ldC90ZXh0dXJlLzkxZjRkNjgyMWY3MDlkMDRmZmVhYmUzMzMzNjJmNTRjNzdmMWI1ODIwNjhkNGIxMTg3ZGZkNTczNTM4MjAxY2IiCiAgICB9CiAgfQp9"), name);
			case "WITHER": return setName(createTexturedHead("ewogICJ0aW1lc3RhbXAiIDogMTYwNzAwMTIyODM5MywKICAicHJvZmlsZUlkIiA6ICJhOTJkZWM4NWI2YjA0ZWRmYmZiNTRmMzBlMmUxYTAwMiIsCiAgInByb2ZpbGVOYW1lIiA6ICJBdXRvQmFuIiwKICAidGV4dHVyZXMiIDogewogICAgIlNLSU4iIDogewogICAgICAidXJsIiA6ICJodHRwOi8vdGV4dHVyZXMubWluZWNyYWZ0Lm5ldC90ZXh0dXJlLzkzMWQzYTg5YzlkZGQ4NjFjMjhhYmM1ZDdmMmVjNWQ0NTM2MzcwNDJlMjI1OTc3N2ZmNWYwZmMzYjVkZGY0Y2MiCiAgICB9CiAgfQp9"), name);
			case "EARTH": return setName(createTexturedHead("ewogICJ0aW1lc3RhbXAiIDogMTY1NTM5NDQyMzQxMiwKICAicHJvZmlsZUlkIiA6ICI2MGM2OGMwMTUwMGY0NDhiYjZlOWExOGVmZjEzMjQyYyIsCiAgInByb2ZpbGVOYW1lIiA6ICJCcmF3bEJvdCIsCiAgInRleHR1cmVzIiA6IHsKICAgICJTS0lOIiA6IHsKICAgICAgInVybCIgOiAiaHR0cDovL3RleHR1cmVzLm1pbmVjcmFmdC5uZXQvdGV4dHVyZS84MGYzYjBjOGQ0MzFkMjY2ZDg1Y2U3Y2E2NmZkZTNiOTY1ODVhMjljZGY3YzM2NjBkMjdiMDIxOWNhNTkxMjQyIgogICAgfQogIH0KfQ=="), name);
			case "WIND": return setName(createTexturedHead("ewogICJ0aW1lc3RhbXAiIDogMTY1NTM5NDYxNTAyNywKICAicHJvZmlsZUlkIiA6ICI2MGM2OGMwMTUwMGY0NDhiYjZlOWExOGVmZjEzMjQyYyIsCiAgInByb2ZpbGVOYW1lIiA6ICJCcmF3bEJvdCIsCiAgInRleHR1cmVzIiA6IHsKICAgICJTS0lOIiA6IHsKICAgICAgInVybCIgOiAiaHR0cDovL3RleHR1cmVzLm1pbmVjcmFmdC5uZXQvdGV4dHVyZS82NzhiMTZlNGQzMTVjYzNmMjcxNWQ0MjMxMjMxMmRiZWQxMWMwYjU4YTA5NjA5OTY0NzZjN2JiMTlkZjJjYzBkIgogICAgfQogIH0KfQ=="), name);
			case "WATER": return setName(createTexturedHead("ewogICJ0aW1lc3RhbXAiIDogMTY1NTM5NDU5MTMyMiwKICAicHJvZmlsZUlkIiA6ICI2MGM2OGMwMTUwMGY0NDhiYjZlOWExOGVmZjEzMjQyYyIsCiAgInByb2ZpbGVOYW1lIiA6ICJCcmF3bEJvdCIsCiAgInRleHR1cmVzIiA6IHsKICAgICJTS0lOIiA6IHsKICAgICAgInVybCIgOiAiaHR0cDovL3RleHR1cmVzLm1pbmVjcmFmdC5uZXQvdGV4dHVyZS80N2YyZDc5NzhmMWFiNjYyNTQ2ZmNhMWZmZmI2MzllMTU4MGRlMzVjNDNmMmRjYWI0ZmQxNzk3Njk2ZWI3MWRjIgogICAgfQogIH0KfQ=="), name);
			case "FIRE": return setName(createTexturedHead("ewogICJ0aW1lc3RhbXAiIDogMTY1NTM5NDU1MTM4MywKICAicHJvZmlsZUlkIiA6ICI2MGM2OGMwMTUwMGY0NDhiYjZlOWExOGVmZjEzMjQyYyIsCiAgInByb2ZpbGVOYW1lIiA6ICJCcmF3bEJvdCIsCiAgInRleHR1cmVzIiA6IHsKICAgICJTS0lOIiA6IHsKICAgICAgInVybCIgOiAiaHR0cDovL3RleHR1cmVzLm1pbmVjcmFmdC5uZXQvdGV4dHVyZS9iYjViYTliZjExOGI3OGQyYjljODMxOGExNTA2NjRjYzFhYzRjZTdkMTE5NDE4NjNkNGQ3N2FmNTk3N2Y4MWFkIgogICAgfQogIH0KfQ=="), name);
		}
		return setName(new ItemStack(Material.PAINTING), name);
	}
	
	public static ItemStack getEntityIcon(EntityType type) {
		switch(type.toString()) {
			case "AREA_EFFECT_CLOUD": return setName(new ItemStack(Material.LINGERING_POTION), type.toString());
			case "ARMOR_STAND": return setName(new ItemStack(Material.ARMOR_STAND), type.toString());
			case "ARROW": return setName(new ItemStack(Material.ARROW), type.toString());
			case "BAT": return setName(createTexturedHead("eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvZjc2NjE5NjUyZmFmZWM5MGNlOThkZjUwMTNjNjNkYzZhNzc3NzZhYjI3ODczYjczZGFmYjJiNmJkZWIxODUifX19"), type.toString());
			case "BEE": return setName(createTexturedHead("eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvNmQxNjU4ZmQ0OTRmOGUyMjkwZGI0MWRjNGQxMWQ0NjdjMjU5NjFlZGNhNjMzMTdlOGY5OTcxZWIyOGE0N2NjNSJ9fX0="), type.toString());
			case "BLAZE": return setName(createTexturedHead("eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvYjc4ZWYyZTRjZjJjNDFhMmQxNGJmZGU5Y2FmZjEwMjE5ZjViMWJmNWIzNWE0OWViNTFjNjQ2Nzg4MmNiNWYwIn19fQ=="), type.toString());
			case "BOAT": return setName(new ItemStack(Material.OAK_BOAT), type.toString());
			case "CAT": return setName(createTexturedHead("eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvOGViMmU0ZGIzYmNmYjA1MDNmMWZmZmIzMzk4ODhlNWQ0MzIzOThiMjc2OTljNWExYTFjZDgwNzljOGQ5N2QifX19"), type.toString());
			case "CAVE_SPIDER": return setName(createTexturedHead("eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvNDE2NDVkZmQ3N2QwOTkyMzEwN2IzNDk2ZTk0ZWViNWMzMDMyOWY5N2VmYzk2ZWQ3NmUyMjZlOTgyMjQifX19"), type.toString());
			case "CHICKEN": return setName(createTexturedHead("eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvMTYzODQ2OWE1OTljZWVmNzIwNzUzNzYwMzI0OGE5YWIxMWZmNTkxZmQzNzhiZWE0NzM1YjM0NmE3ZmFlODkzIn19fQ=="), type.toString());
			case "COD": return setName(new ItemStack(Material.COD), type.toString());
			case "COW": return setName(createTexturedHead("eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvNWQ2YzZlZGE5NDJmN2Y1ZjcxYzMxNjFjNzMwNmY0YWVkMzA3ZDgyODk1ZjlkMmIwN2FiNDUyNTcxOGVkYzUifX19"), type.toString());
			case "CREEPER": return setName(new ItemStack(Material.CREEPER_HEAD), type.toString());
			case "DOLPHIN": return setName(createTexturedHead("eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvOGU5Njg4Yjk1MGQ4ODBiNTViN2FhMmNmY2Q3NmU1YTBmYTk0YWFjNmQxNmY3OGU4MzNmNzQ0M2VhMjlmZWQzIn19fQ=="), type.toString());
			case "DONKEY": return setName(createTexturedHead("eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvMzk5YmI1MGQxYTIxNGMzOTQ5MTdlMjViYjNmMmUyMDY5OGJmOThjYTcwM2U0Y2MwOGI0MjQ2MmRmMzA5ZDZlNiJ9fX0="), type.toString());
			case "DRAGON_FIREBALL": return setName(createTexturedHead("eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvNDJlODcwODlmOTMyOWI1NGM5YTU5NjU2MjUzNTQxMDdjN2Y5NmIzMDU0ZjFkZWY4Y2VlYTJiOTBjZTZmOGQifX19"), type.toString());
			case "DROPPED_ITEM": return setName(new ItemStack(Material.STONE), type.toString());
			case "DROWNED": return setName(createTexturedHead("eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvYzg0ZGY3OWM0OTEwNGIxOThjZGFkNmQ5OWZkMGQwYmNmMTUzMWM5MmQ0YWI2MjY5ZTQwYjdkM2NiYmI4ZTk4YyJ9fX0="), type.toString());
			case "EGG": return setName(new ItemStack(Material.EGG), type.toString());
			case "ELDER_GUARDIAN": return setName(createTexturedHead("eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvNDM0MGEyNjhmMjVmZDVjYzI3NmNhMTQ3YTg0NDZiMjYzMGE1NTg2N2EyMzQ5ZjdjYTEwN2MyNmViNTg5OTEifX19"), type.toString());
			case "ENDER_CRYSTAL": return setName(new ItemStack(Material.END_CRYSTAL), type.toString());
			case "ENDER_DRAGON": return setName(new ItemStack(Material.DRAGON_HEAD), type.toString());
			case "ENDER_PEARL": return setName(new ItemStack(Material.ENDER_PEARL), type.toString());
			case "ENDER_SIGNAL": return setName(new ItemStack(Material.ENDER_EYE), type.toString());
			case "ENDERMAN": return setName(createTexturedHead("eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvOTZjMGIzNmQ1M2ZmZjY5YTQ5YzdkNmYzOTMyZjJiMGZlOTQ4ZTAzMjIyNmQ1ZTgwNDVlYzU4NDA4YTM2ZTk1MSJ9fX0="), type.toString());
			case "ENDERMITE": return setName(createTexturedHead("eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvNWJjN2I5ZDM2ZmI5MmI2YmYyOTJiZTczZDMyYzZjNWIwZWNjMjViNDQzMjNhNTQxZmFlMWYxZTY3ZTM5M2EzZSJ9fX0="), type.toString());
			case "EVOKER": return setName(createTexturedHead("eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvZDk1NDEzNWRjODIyMTM5NzhkYjQ3ODc3OGFlMTIxMzU5MWI5M2QyMjhkMzZkZDU0ZjFlYTFkYTQ4ZTdjYmE2In19fQ=="), type.toString());
			case "EVOKER_FANGS": return setName(createTexturedHead("eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvZDk1NDEzNWRjODIyMTM5NzhkYjQ3ODc3OGFlMTIxMzU5MWI5M2QyMjhkMzZkZDU0ZjFlYTFkYTQ4ZTdjYmE2In19fQ=="), type.toString());
			case "EXPERIENCE_ORB": return setName(createTexturedHead("eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvOTg5MDFmNzE0MzRkNTM5MjA3NDc2OTRmNjgyZjVlNTNiOGY3NDQ4M2YyNjljMzg0YzY5MzZiN2Q4NjU4MiJ9fX0="), type.toString());
			case "FALLING_BLOCK": return setName(new ItemStack(Material.SAND), type.toString());
			case "FIREBALL": return setName(new ItemStack(Material.FIRE_CHARGE), type.toString());
			case "FIREWORK": return setName(new ItemStack(Material.FIREWORK_ROCKET), type.toString());
			case "FISHING_HOOK": return setName(new ItemStack(Material.FISHING_ROD), type.toString());
			case "FOX": return setName(createTexturedHead("eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvZDg5NTRhNDJlNjllMDg4MWFlNmQyNGQ0MjgxNDU5YzE0NGEwZDVhOTY4YWVkMzVkNmQzZDczYTNjNjVkMjZhIn19fQ=="), type.toString());
			case "GHAST": return setName(createTexturedHead("eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvN2E4YjcxNGQzMmQ3ZjZjZjhiMzdlMjIxYjc1OGI5YzU5OWZmNzY2NjdjN2NkNDViYmM0OWM1ZWYxOTg1ODY0NiJ9fX0="), type.toString());
			case "GIANT": return setName(new ItemStack(Material.ZOMBIE_HEAD), type.toString());
			case "GUARDIAN": return setName(createTexturedHead("eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvYTBiZjM0YTcxZTc3MTViNmJhNTJkNWRkMWJhZTVjYjg1Zjc3M2RjOWIwZDQ1N2I0YmZjNWY5ZGQzY2M3Yzk0In19fQ=="), type.toString());
			case "HOGLIN": return setName(createTexturedHead("eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvOWJiOWJjMGYwMWRiZDc2MmEwOGQ5ZTc3YzA4MDY5ZWQ3Yzk1MzY0YWEzMGNhMTA3MjIwODU2MWI3MzBlOGQ3NSJ9fX0="), type.toString());
			case "HORSE": return setName(createTexturedHead("eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvNjc5ZDBjZjA2MTVmZjgxYjFkNWQwYjc5MWFmODU0OTRhYjZiNWFmOTcxZGUxOGE0NmE4ZjkxMWIzYjU5NzM2ZSJ9fX0="), type.toString());
			case "HUSK": return setName(createTexturedHead("eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvZDY3NGM2M2M4ZGI1ZjRjYTYyOGQ2OWEzYjFmOGEzNmUyOWQ4ZmQ3NzVlMWE2YmRiNmNhYmI0YmU0ZGIxMjEifX19"), type.toString());
			case "ILLUSIONER": return setName(createTexturedHead("eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvNTEyNTEyZTdkMDE2YTIzNDNhN2JmZjFhNGNkMTUzNTdhYjg1MTU3OWYxMzg5YmQ0ZTNhMjRjYmViODhiIn19fQ=="), type.toString());
			case "IRON_GOLEM": return setName(createTexturedHead("eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvMjJiY2FjZWViNDE2MmY0MDBkNDQ3NDMzMTU5MzJhYzgyMGQzMTE5YWM4OTg2YTAxNjFhNzI2MTYxY2NjOTNmYyJ9fX0="), type.toString());
			case "ITEM_FRAME": return setName(new ItemStack(Material.ITEM_FRAME), type.toString());
			case "LEASH_HITCH": return setName(new ItemStack(Material.LEAD), type.toString());
			case "LIGHTNING": return setName(createTexturedHead("eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvYzNkMTQ1NjFiYmQwNjNmNzA0MjRhOGFmY2MzN2JmZTljNzQ1NjJlYTM2ZjdiZmEzZjIzMjA2ODMwYzY0ZmFmMSJ9fX0="), type.toString());
			case "LLAMA": return setName(createTexturedHead("eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvMmE1ZjEwZTZlNjIzMmYxODJmZTk2NmY1MDFmMWMzNzk5ZDQ1YWUxOTAzMWExZTQ5NDFiNWRlZTBmZWZmMDU5YiJ9fX0="), type.toString());
			case "LLAMA_SPIT": return setName(createTexturedHead("eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvMmE1ZjEwZTZlNjIzMmYxODJmZTk2NmY1MDFmMWMzNzk5ZDQ1YWUxOTAzMWExZTQ5NDFiNWRlZTBmZWZmMDU5YiJ9fX0="), type.toString());
			case "MAGMA_CUBE": return setName(createTexturedHead("eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvMzg5NTdkNTAyM2M5MzdjNGM0MWFhMjQxMmQ0MzQxMGJkYTIzY2Y3OWE5ZjZhYjM2Yjc2ZmVmMmQ3YzQyOSJ9fX0="), type.toString());
			case "MINECART": return setName(new ItemStack(Material.MINECART), type.toString());
			case "MINECART_CHEST": return setName(new ItemStack(Material.CHEST_MINECART), type.toString());
			case "MINECART_COMMAND": return setName(new ItemStack(Material.COMMAND_BLOCK_MINECART), type.toString());
			case "MINECART_FURNACE": return setName(new ItemStack(Material.FURNACE_MINECART), type.toString());
			case "MINECART_HOPPER": return setName(new ItemStack(Material.HOPPER_MINECART), type.toString());
			case "MINECART_MOB_SPAWNER": return setName(new ItemStack(Material.MINECART), type.toString());
			case "MINECART_TNT": return setName(new ItemStack(Material.TNT_MINECART), type.toString());
			case "MULE": return setName(createTexturedHead("eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvYTA0ODZhNzQyZTdkZGEwYmFlNjFjZTJmNTVmYTEzNTI3ZjFjM2IzMzRjNTdjMDM0YmI0Y2YxMzJmYjVmNWYifX19"), type.toString());
			case "MUSHROOM_COW": return setName(createTexturedHead("eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvMmI1Mjg0MWYyZmQ1ODllMGJjODRjYmFiZjllMWMyN2NiNzBjYWM5OGY4ZDZiM2RkMDY1ZTU1YTRkY2I3MGQ3NyJ9fX0="), type.toString());
			case "OCELOT": return setName(createTexturedHead("eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvMWM2MGVhNzA0YTkwMTdlMjQ0YWNlMTI2ZjQxNTM5ZTQ5OTFmZjRkZjI5ZDYxMmFiYTVjZjg1ZDE4N2I1MjMifX19"), type.toString());
			case "PAINTING": return setName(new ItemStack(Material.PAINTING), type.toString());
			case "PANDA": return setName(createTexturedHead("eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvZGYwMDg1ODkyNmNkOGNkZjNmMWNmNzFlMjEwY2RlNWRhZjg3MDgzMjA1NDdiZDZkZjU3OTU4NTljNjhkOWIzZiJ9fX0="), type.toString());
			case "PARROT": return setName(createTexturedHead("eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvYTRiYThkNjZmZWNiMTk5MmU5NGI4Njg3ZDZhYjRhNTMyMGFiNzU5NGFjMTk0YTI2MTVlZDRkZjgxOGVkYmMzIn19fQ=="), type.toString());
			case "PHANTOM": return setName(createTexturedHead("eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvNzQ2ODMwZGE1ZjgzYTNhYWVkODM4YTk5MTU2YWQ3ODFhNzg5Y2ZjZjEzZTI1YmVlZjdmNTRhODZlNGZhNCJ9fX0="), type.toString());
			case "PIG": return setName(createTexturedHead("eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvNjIxNjY4ZWY3Y2I3OWRkOWMyMmNlM2QxZjNmNGNiNmUyNTU5ODkzYjZkZjRhNDY5NTE0ZTY2N2MxNmFhNCJ9fX0="), type.toString());
			case "PIGLIN": return setName(createTexturedHead("eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvNDNjZTAyMzc0N2M3ODI5YjUxYWI5Y2Q2NmU3OGEyMDM0ZDhmOTg4OTIyMzQyYTQ0ZWU1NmI3YWI1YzVmNTA1YSJ9fX0="), type.toString());
			case "PIGLIN_BRUTE": return setName(createTexturedHead("eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvM2UzMDBlOTAyNzM0OWM0OTA3NDk3NDM4YmFjMjllM2E0Yzg3YTg0OGM1MGIzNGMyMTI0MjcyN2I1N2Y0ZTFjZiJ9fX0="), type.toString());
			case "PILLAGER": return setName(createTexturedHead("eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvNGFlZTZiYjM3Y2JmYzkyYjBkODZkYjVhZGE0NzkwYzY0ZmY0NDY4ZDY4Yjg0OTQyZmRlMDQ0MDVlOGVmNTMzMyJ9fX0="), type.toString());
			case "PLAYER": return setName(new ItemStack(Material.PLAYER_HEAD), type.toString());
			case "POLAR_BEAR": return setName(createTexturedHead("eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvYzRmZTkyNjkyMmZiYjQwNmYzNDNiMzRhMTBiYjk4OTkyY2VlNDQxMDEzN2QzZjg4MDk5NDI3YjIyZGUzYWI5MCJ9fX0="), type.toString());
			case "PRIMED_TNT": return setName(new ItemStack(Material.TNT), type.toString());
			case "PUFFERFISH": return setName(new ItemStack(Material.PUFFERFISH), type.toString());
			case "RABBIT": return setName(createTexturedHead("eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvZmZlY2M2YjVlNmVhNWNlZDc0YzQ2ZTc2MjdiZTNmMDgyNjMyN2ZiYTI2Mzg2YzZjYzc4NjMzNzJlOWJjIn19fQ=="), type.toString());
			case "RAVAGER": return setName(createTexturedHead("eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvMWNiOWYxMzlmOTQ4OWQ4NmU0MTBhMDZkOGNiYzY3MGM4MDI4MTM3NTA4ZTNlNGJlZjYxMmZlMzJlZGQ2MDE5MyJ9fX0="), type.toString());
			case "SALMON": return setName(new ItemStack(Material.SALMON), type.toString());
			case "SHEEP": return setName(createTexturedHead("eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvZjMxZjljY2M2YjNlMzJlY2YxM2I4YTExYWMyOWNkMzNkMThjOTVmYzczZGI4YTY2YzVkNjU3Y2NiOGJlNzAifX19"), type.toString());
			case "SHULKER": return setName(createTexturedHead("eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvMjVjNGQyNGFmZmRkNDgxMDI2MjAzNjE1MjdkMjE1NmUxOGMyMjNiYWU1MTg5YWM0Mzk4MTU2NDNmM2NmZjlkIn19fQ=="), type.toString());
			case "SHULKER_BULLET": return setName(createTexturedHead("eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvMjE1ZmVjNjUxOGE0MWYxNjYxMzFlNjViMTBmNDZmYjg3ZTk3YzQ5MmI0NmRiYzI1ZGUyNjM3NjcyMWZhNjRlMCJ9fX0="), type.toString());
			case "SILVERFISH": return setName(createTexturedHead("eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvOTJlYzJjM2NiOTVhYjc3ZjdhNjBmYjRkMTYwYmNlZDRiODc5MzI5YjYyNjYzZDdhOTg2MDY0MmU1ODhhYjIxMCJ9fX0="), type.toString());
			case "SKELETON": return setName(new ItemStack(Material.SKELETON_SKULL), type.toString());
			case "SKELETON_HORSE": return setName(createTexturedHead("eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvNDdlZmZjZTM1MTMyYzg2ZmY3MmJjYWU3N2RmYmIxZDIyNTg3ZTk0ZGYzY2JjMjU3MGVkMTdjZjg5NzNhIn19fQ=="), type.toString());
			case "SLIME": return setName(createTexturedHead("eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvYTIwZTg0ZDMyZDFlOWM5MTlkM2ZkYmI1M2YyYjM3YmEyNzRjMTIxYzU3YjI4MTBlNWE0NzJmNDBkYWNmMDA0ZiJ9fX0="), type.toString());
			case "SMALL_FIREBALL": return setName(new ItemStack(Material.FIRE_CHARGE), type.toString());
			case "SNOWBALL": return setName(new ItemStack(Material.SNOWBALL), type.toString());
			case "SNOWMAN": return setName(new ItemStack(Material.CARVED_PUMPKIN), type.toString());
			case "SPECTRAL_ARROW": return setName(new ItemStack(Material.SPECTRAL_ARROW), type.toString());
			case "SPIDER": return setName(createTexturedHead("eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvY2Q1NDE1NDFkYWFmZjUwODk2Y2QyNThiZGJkZDRjZjgwYzNiYTgxNjczNTcyNjA3OGJmZTM5MzkyN2U1N2YxIn19fQ=="), type.toString());
			case "SPLASH_POTION": return setName(new ItemStack(Material.SPLASH_POTION), type.toString());
			case "SQUID": return setName(createTexturedHead("eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvMDE0MzNiZTI0MjM2NmFmMTI2ZGE0MzRiODczNWRmMWViNWIzY2IyY2VkZTM5MTQ1OTc0ZTljNDgzNjA3YmFjIn19fQ=="), type.toString());
			case "STRAY": return setName(createTexturedHead("eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvMmM1MDk3OTE2YmMwNTY1ZDMwNjAxYzBlZWJmZWIyODcyNzdhMzRlODY3YjRlYTQzYzYzODE5ZDUzZTg5ZWRlNyJ9fX0="), type.toString());
			case "STRIDER": return setName(createTexturedHead("eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvMThhOWFkZjc4MGVjN2RkNDYyNWM5YzA3NzkwNTJlNmExNWE0NTE4NjY2MjM1MTFlNGM4MmU5NjU1NzE0YjNjMSJ9fX0="), type.toString());
			case "THROWN_EXP_BOTTLE": return setName(new ItemStack(Material.EXPERIENCE_BOTTLE), type.toString());
			case "TRADER_LLAMA": return setName(createTexturedHead("eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvZTg5YTJlYjE3NzA1ZmU3MTU0YWIwNDFlNWM3NmEwOGQ0MTU0NmEzMWJhMjBlYTMwNjBlM2VjOGVkYzEwNDEyYyJ9fX0="), type.toString());
			case "TRIDENT": return setName(new ItemStack(Material.TRIDENT), type.toString());
			case "TROPICAL_FISH": return setName(new ItemStack(Material.TROPICAL_FISH), type.toString());
			case "TURTLE": return setName(createTexturedHead("eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvMGE0MDUwZTdhYWNjNDUzOTIwMjY1OGZkYzMzOWRkMTgyZDdlMzIyZjlmYmNjNGQ1Zjk5YjU3MThhIn19fQ=="), type.toString());
			case "VEX": return setName(createTexturedHead("eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvYzJlYzVhNTE2NjE3ZmYxNTczY2QyZjlkNWYzOTY5ZjU2ZDU1NzVjNGZmNGVmZWZhYmQyYTE4ZGM3YWI5OGNkIn19fQ=="), type.toString());
			case "VILLAGER": return setName(createTexturedHead("eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvNDFiODMwZWI0MDgyYWNlYzgzNmJjODM1ZTQwYTExMjgyYmI1MTE5MzMxNWY5MTE4NDMzN2U4ZDM1NTU1ODMifX19"), type.toString());
			case "VINDICATOR": return setName(createTexturedHead("eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvOWUxY2FiMzgyNDU4ZTg0M2FjNDM1NmUzZTAwZTFkMzVjMzZmNDQ5ZmExYTg0NDg4YWIyYzY1NTdiMzkyZCJ9fX0="), type.toString());
			case "WANDERING_TRADER": return setName(createTexturedHead("eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvNWYxMzc5YTgyMjkwZDdhYmUxZWZhYWJiYzcwNzEwZmYyZWMwMmRkMzRhZGUzODZiYzAwYzkzMGM0NjFjZjkzMiJ9fX0="), type.toString());
			case "WITCH": return setName(createTexturedHead("eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvMjBlMTNkMTg0NzRmYzk0ZWQ1NWFlYjcwNjk1NjZlNDY4N2Q3NzNkYWMxNmY0YzNmODcyMmZjOTViZjlmMmRmYSJ9fX0="), type.toString());
			case "WITHER": return setName(createTexturedHead("eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvY2RmNzRlMzIzZWQ0MTQzNjk2NWY1YzU3ZGRmMjgxNWQ1MzMyZmU5OTllNjhmYmI5ZDZjZjVjOGJkNDEzOWYifX19"), type.toString());
			case "WITHER_SKELETON": return setName(new ItemStack(Material.WITHER_SKELETON_SKULL), type.toString());
			case "WITHER_SKULL": return setName(createTexturedHead("eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvOTY0ZTFjM2UzMTVjOGQ4ZmZmYzM3OTg1YjY2ODFjNWJkMTZhNmY5N2ZmZDA3MTk5ZThhMDVlZmJlZjEwMzc5MyJ9fX0="), type.toString());
			case "WOLF": return setName(createTexturedHead("eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvZDA0OThkZTZmNWIwOWUwY2UzNWE3MjkyZmU1MGI3OWZjZTkwNjVkOWJlOGUyYTg3YzdhMTM1NjZlZmIyNmQ3MiJ9fX0="), type.toString());
			case "ZOGLIN": return setName(createTexturedHead("eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvM2M4YzdjNWQwNTU2Y2Q2NjI5NzE2ZTM5MTg4YjIxZTdjMDQ3NzQ3OWYyNDI1ODdiZjE5ZTBiYzc2YjMyMjU1MSJ9fX0="), type.toString());
			case "ZOMBIE": return setName(new ItemStack(Material.ZOMBIE_HEAD), type.toString());
			case "ZOMBIE_HORSE": return setName(createTexturedHead("eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvZjg5NmExZGJkZTFhMTk1NDBjZTczMzZjNmM5NGY1OTY1MmFhOThiYjEwNjhiMmVmOGM4ZmE2ZWY4NTgwNGY1NyJ9fX0="), type.toString());
			case "ZOMBIE_VILLAGER": return setName(createTexturedHead("eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvZTVlMDhhODc3NmMxNzY0YzNmZTZhNmRkZDQxMmRmY2I4N2Y0MTMzMWRhZDQ3OWFjOTZjMjFkZjRiZjNhYzg5YyJ9fX0="), type.toString());
			case "ZOMBIFIED_PIGLIN": return setName(createTexturedHead("eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvN2VhYmFlY2M1ZmFlNWE4YTQ5Yzg4NjNmZjQ4MzFhYWEyODQxOThmMWEyMzk4ODkwYzc2NWUwYThkZTE4ZGE4YyJ9fX0="), type.toString());
			case "AXOLOTL": return setName(createTexturedHead("eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvNWMxMzhmNDAxYzY3ZmMyZTFlMzg3ZDljOTBhOTY5MTc3MmVlNDg2ZThkZGJmMmVkMzc1ZmM4MzQ4NzQ2ZjkzNiJ9fX0="), type.toString());
			case "GLOW_ITEM_FRAME": return setName(new ItemStack(Material.GLOW_ITEM_FRAME), type.toString());
			case "GLOW_SQUID": return setName(createTexturedHead("eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvMmVjZDBiNWViNmIzODRkYjA3NmQ4NDQ2MDY1MjAyOTU5ZGRkZmYwMTYxZTBkNzIzYjNkZjBjYzU4NmQxNmJiZCJ9fX0="), type.toString());
			case "GOAT": return setName(createTexturedHead("eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvYTY2MjMzNmQ4YWUwOTI0MDdlNThmN2NjODBkMjBmMjBlNzY1MDM1N2E0NTRjZTE2ZTMzMDc2MTlhMDExMDY0OCJ9fX0="), type.toString());
			case "MARKER": return setName(new ItemStack(Material.BARRIER), type.toString());
			case "ALLAY": return setName(createTexturedHead("eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvYmVlYTg0NWNjMGI1OGZmNzYzZGVjZmZlMTFjZDFjODQ1YzVkMDljM2IwNGZlODBiMDY2M2RhNWM3YzY5OWViMyJ9fX0="), type.toString());
			case "CHEST_BOAT": return setName(new ItemStack(Material.valueOf("OAK_CHEST_BOAT")), type.toString());
			case "FROG": return setName(createTexturedHead("eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvMjNjZTZmOTk5OGVkMmRhNzU3ZDFlNjM3MmYwNGVmYTIwZTU3ZGZjMTdjM2EwNjQ3ODY1N2JiZGY1MWMyZjJhMiJ9fX0="), type.toString());
			case "TADPOLE": return setName(createTexturedHead("eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvYjIzZWJmMjZiN2E0NDFlMTBhODZmYjVjMmE1ZjNiNTE5MjU4YTVjNWRkZGQ2YTFhNzU1NDlmNTE3MzMyODE1YiJ9fX0="), type.toString());
			case "WARDEN": return setName(createTexturedHead("eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvNmNmMzY3NGIyZGRjMGVmN2MzOWUzYjljNmI1ODY3N2RlNWNmMzc3ZDJlYjA3M2YyZjNmZTUwOTE5YjFjYTRjOSJ9fX0="), type.toString());
			default:
				break;
		}
		return setName(new ItemStack(Material.STONE), type.toString());
	}
	
	public static ItemStack getLocaleIcon(String key) {
		switch(key.toLowerCase()) {
			case "en": return createTexturedHead("eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvODc5ZDk5ZDljNDY0NzRlMjcxM2E3ZTg0YTk1ZTRjZTdlOGZmOGVhNGQxNjQ0MTNhNTkyZTQ0MzVkMmM2ZjlkYyJ9fX0=");//english
			case "fr": return createTexturedHead("eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvNTEyNjlhMDY3ZWUzN2U2MzYzNWNhMWU3MjNiNjc2ZjEzOWRjMmRiZGRmZjk2YmJmZWY5OWQ4YjM1Yzk5NmJjIn19fQ==");//french
			case "de": return createTexturedHead("eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvNWU3ODk5YjQ4MDY4NTg2OTdlMjgzZjA4NGQ5MTczZmU0ODc4ODY0NTM3NzQ2MjZiMjRiZDhjZmVjYzc3YjNmIn19fQ==");//german
			case "es": return createTexturedHead("eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvMzJiZDQ1MjE5ODMzMDllMGFkNzZjMWVlMjk4NzQyODc5NTdlYzNkOTZmOGQ4ODkzMjRkYThjODg3ZTQ4NWVhOCJ9fX0=");//spanish
			case "ar": return createTexturedHead("eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvZGQ5ZjI5NDRkNDhmYjk2N2RlN2FhNWQ0YjhjODQ4MzA3ZjQ2NzMyM2FlYTkzMzY1YjdlZDJjMTZhOGYxYTkzOSJ9fX0=");//arabic
			case "bg": return createTexturedHead("eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvMTkwMzllMWZkODhjNzhkOWQ3YWRjNWFhZDVhYjE2ZTM1NmJlMTM0NjQ5MzRlZDllMmIwY2VmMjA1MWM1YjUzNCJ9fX0=");//bulgerian
			case "zh": return createTexturedHead("eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvN2Y5YmMwMzVjZGM4MGYxYWI1ZTExOThmMjlmM2FkM2ZkZDJiNDJkOWE2OWFlYjY0ZGU5OTA2ODE4MDBiOThkYyJ9fX0=");//chinese
			case "da": return createTexturedHead("eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvMTBjMjMwNTVjMzkyNjA2ZjdlNTMxZGFhMjY3NmViZTJlMzQ4OTg4ODEwYzE1ZjE1ZGM1YjM3MzM5OTgyMzIifX19");//danish
			case "nl": return createTexturedHead("eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvYzIzY2YyMTBlZGVhMzk2ZjJmNWRmYmNlZDY5ODQ4NDM0ZjkzNDA0ZWVmZWFiZjU0YjIzYzA3M2IwOTBhZGYifX19");//dutch
			case "el": return createTexturedHead("eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvMTUxNGRlNmRkMmI3NjgyYjFkM2ViY2QxMDI5MWFlMWYwMjFlMzAxMmI1YzhiZWZmZWI3NWIxODE5ZWI0MjU5ZCJ9fX0=");//greek
			case "hu": return createTexturedHead("eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvNGE5YzNjNGI2YzUwMzEzMzJkZDJiZmVjZTVlMzFlOTk5ZjhkZWZmNTU0NzQwNjVjYzg2OTkzZDdiZGNkYmQwIn19fQ==");//hungarian
			case "is": return createTexturedHead("eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvYjIwNTc3MWJlZDI1ODZjNGYwNWZkYzU5MzgzMzlhNzExZjRlYjNmNzc3OGQ5Mzc3MTc5NGMxYWQ0YmYwMDkxZSJ9fX0=");//icelandic
			case "it": return createTexturedHead("eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvODVjZTg5MjIzZmE0MmZlMDZhZDY1ZDhkNDRjYTQxMmFlODk5YzgzMTMwOWQ2ODkyNGRmZTBkMTQyZmRiZWVhNCJ9fX0=");//italian
			case "ja": case "jp": return createTexturedHead("eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvZDY0MGFlNDY2MTYyYTQ3ZDNlZTMzYzQwNzZkZjFjYWI5NmYxMTg2MGYwN2VkYjFmMDgzMmM1MjVhOWUzMzMyMyJ9fX0=");//japanese
			case "pt": return createTexturedHead("eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvZWJkNTFmNDY5M2FmMTc0ZTZmZTE5NzkyMzNkMjNhNDBiYjk4NzM5OGUzODkxNjY1ZmFmZDJiYTU2N2I1YTUzYSJ9fX0=");//portugese
			case "uk": return createTexturedHead("eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvMjhiOWY1MmUzNmFhNWM3Y2FhYTFlN2YyNmVhOTdlMjhmNjM1ZThlYWM5YWVmNzRjZWM5N2Y0NjVmNWE2YjUxIn19fQ==");//ukrainian
			case "ru": return createTexturedHead("eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvMTZlYWZlZjk4MGQ2MTE3ZGFiZTg5ODJhYzRiNDUwOTg4N2UyYzQ2MjFmNmE4ZmU1YzliNzM1YTgzZDc3NWFkIn19fQ==");//russian
			case "sv": return createTexturedHead("eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvN2Q4NjI0MmIwZDk3ZWNlOTk5NDY2MGYzOTc0ZDcyZGY3Yjg4N2Y2MzBhNDUzMGRhZGM1YjFhYjdjMjEzNGFlYyJ9fX0=");//swedish
			//At this point I decided to stop. Sorry I didn't include your langage =/
			default: return new ItemStack(Material.STONE);
		}
	}
	
	public static ItemStack getPotionIcon(PotionEffectType type) {
		ItemStack icon = new ItemStack(Material.POTION);
		PotionMeta meta = (PotionMeta) icon.getItemMeta();
		meta.setColor(type.getColor());
		icon.setItemMeta(meta);
		setName(icon, StringUtils.capitalize(type.getName()));
		return icon;
	}
	
	public static ChatColor DyetoChat(DyeColor color) {
		switch(color) {
			case WHITE: return ChatColor.WHITE;
			case ORANGE: return ChatColor.GOLD;
			case MAGENTA: return ChatColor.LIGHT_PURPLE;
			case LIGHT_BLUE: return ChatColor.BLUE;
			case YELLOW: return ChatColor.YELLOW;
			case LIME: return ChatColor.GREEN;
			case PINK: return ChatColor.LIGHT_PURPLE;
			case GRAY: return ChatColor.DARK_GRAY;
			case LIGHT_GRAY: return ChatColor.GRAY;
			case CYAN: return ChatColor.DARK_AQUA;
			case PURPLE: return ChatColor.DARK_PURPLE;
			case BLUE: return ChatColor.DARK_BLUE;
			case BROWN: return ChatColor.GOLD;
			case GREEN: return ChatColor.DARK_GREEN;
			case RED: return ChatColor.RED;
			case BLACK: return ChatColor.BLACK;
		}
		return ChatColor.WHITE;
	}
	
	public static boolean permCheck(Player player, String perm) {
		if(player.isOp() || player.hasPermission(perm))
			return true;

		String p = "";
		for(String s : perm.split("\\.")) {
			if(!p.isEmpty()) {
				if(player.hasPermission(p+".*"))
					return true;
			}else if(player.hasPermission("*")) {
				return true;
			}
			
			if(!p.isEmpty())
				p+=".";
			p+=s;
		}
		return false;
	}
	
	public static ItemStack createTexturedHead(String texture) {
		ItemStack head = new ItemStack(Material.PLAYER_HEAD);
		SkullMeta meta = (SkullMeta) head.getItemMeta();

		mutateItemMeta(meta, texture);
		head.setItemMeta(meta);

		return head;
	}
	
	public static ItemStack setSpecialBlockType(ItemStack item, String type) {
		ItemMeta meta = item.getItemMeta();
		meta.getPersistentDataContainer().set(specialBlockKey, PersistentDataType.STRING, type);
		item.setItemMeta(meta);
		return item;
	}
	
	public static ItemStack getDoubleLadderItem() {
		ItemStack item = createTexturedHead("eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvYjVmMmEzMmFmYzFkZmZmYjY3N2YxOGU3ZGQxMDhkY2Q5MWRmYTgwMzA2YWIyOTM5YmEyNmJiZmI0MTUxZjMwYyJ9fX0=");
		setSpecialBlockType(item, "doubleLadder");
		return setName(item, ChatColor.RESET+Locale.parse("doubleLadder"));
	}
	
	public static ItemStack getPistonHeadItem() {
		ItemStack item = createTexturedHead("eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvMWYyZWZiNDJjYWFlNTYxZmUyZDk3YmY3MTFjY2MwNjQ3ZGU2OTVmZDJkZTIxZDljZGNiMTI5YmEyOTMzY2VkZiJ9fX0=");
		setSpecialBlockType(item, "pistonHead");
		return setName(item, ChatColor.RESET+Locale.parse("pistonHead"));
	}
	
	public static ItemStack GetEndPortalItem() {
		ItemStack item = createTexturedHead("eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvZGFhOGZjOGRlNjQxN2I0OGQ0OGM4MGI0NDNjZjUzMjZlM2Q5ZGE0ZGJlOWIyNWZjZDQ5NTQ5ZDk2MTY4ZmMwIn19fQ==");
		setSpecialBlockType(item, "endPortal");
		return setName(item, ChatColor.RESET+Locale.parse("endPortal"));
	}
	
	public static ItemStack getNetherPortalItem() {
		ItemStack item = createTexturedHead("eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvYjBiZmMyNTc3ZjZlMjZjNmM2ZjczNjVjMmM0MDc2YmNjZWU2NTMxMjQ5ODkzODJjZTkzYmNhNGZjOWUzOWIifX19");
		setSpecialBlockType(item, "netherPortal");
		return setName(item, ChatColor.RESET+Locale.parse("netherPortal"));
	}
	
	public static ItemStack getLitLampItem() {
		ItemStack item = createTexturedHead("eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvYTkxOWRkNzJlMzhjZWMzNjljNjUwODY4Njg5NmNjYjg0MTAwZmQwMjdjNGY2MGE2ODFkMTZhNzY0MDMyOWNjIn19fQ==");
		setSpecialBlockType(item, "litRedstoneLamp");
		return setName(item, ChatColor.RESET+Locale.parse("litRedstoneLamp"));
	}
	
	public static ItemStack getInvisibleFrame() {
		ItemStack item = ToolConfig.invisFrameItem.clone();
		setSpecialBlockType(item, "invisFrame");
		setName(item, Locale.parse(null, "invisFrame"));
		return item;
	}
	
	public static void sendNotification(Player player, String msg) {
		ToolUser user = ToolUser.getUser(player);
		sendNotification(user, msg);
	}
	
	public static boolean isToolBlacklisted(WandTool tool) {
		String name = tool.getClass().getSimpleName().replace("Tool", "");
		for(String s : ToolConfig.toolBlacklist) {
			if(s.equalsIgnoreCase(name))
				return true;
		}
		return false;
	}
	
	public static void sendNotification(ToolUser user, String msg) {
		switch(user.getNotificationLoc()) {
			case ACTIONBAR:
				user.getPlayer().spigot().sendMessage(ChatMessageType.ACTION_BAR, TextComponent.fromLegacyText(msg));
				break;
			case SUBTITLE:
				user.getPlayer().sendTitle(ChatColor.RESET+"", msg, 0, 20, 10);
				break;
			case CHAT:
				user.getPlayer().sendMessage(MSG_START+msg);
				break;
		}
	}
	
	private static Method metaSetProfileMethod;
	private static Field metaProfileField;
	
	private static void mutateItemMeta(SkullMeta meta, String b64) {
		//This is not my code I stole it from another plugin. I'm a terrible person
		try {
			if (metaSetProfileMethod == null) {
				metaSetProfileMethod = meta.getClass().getDeclaredMethod("setProfile", GameProfile.class);
				metaSetProfileMethod.setAccessible(true);
			}
			metaSetProfileMethod.invoke(meta, makeProfile(b64));
		} catch (Exception ex) {
			// if in an older API where there is no setProfile method,
			// we set the profile field directly.
			try {
				if (metaProfileField == null) {
					metaProfileField = meta.getClass().getDeclaredField("profile");
					metaProfileField.setAccessible(true);
				}
				metaProfileField.set(meta, makeProfile(b64));

			} catch (NoSuchFieldException | IllegalAccessException ex2) {
				ex2.printStackTrace();
			}
		}
	}
	
	private static GameProfile makeProfile(String b64) {
		//This is not my code I stole it from another plugin. I'm a terrible person
		// random uuid based on the b64 string
		UUID id = new UUID(
				b64.substring(b64.length() - 20).hashCode(),
				b64.substring(b64.length() - 10).hashCode()
		);
		GameProfile profile = new GameProfile(id, "aaaaa");
		profile.getProperties().put("textures", new Property("textures", b64));
		return profile;
	}
	
	public static void editSign(Player p, Block block) {
		if(!(block.getState() instanceof Sign))
			return;
		if(ToolMain.getVersionNumber() == 17) {
			SignEdit1_17.editSign(p, block);
			return;
		} else if(ToolMain.getVersionNumber() >= 18) {
			SignEdit1_18.editSign(p, block);
			return;
		}
		try {
			Object world = block.getWorld().getClass().getMethod("getHandle").invoke(block.getWorld());
		    Object blockPos = getNMSClass("BlockPosition").getConstructor(int.class, int.class, int.class).newInstance(block.getX(), block.getY(), block.getZ());
		    Object sign = world.getClass().getMethod("getTileEntity", getNMSClass("BlockPosition")).invoke(world, blockPos);
		    sign.getClass().getDeclaredField("isEditable").set(sign, true);
		    Object eh = p.getClass().getMethod("getHandle").invoke(p);
		    sign.getClass().getMethod("a", eh.getClass()).invoke(sign, eh);
		    sign.getClass().getMethod("update").invoke(sign);
		    Object player = p.getClass().getMethod("getHandle").invoke(p);
		    player.getClass().getMethod("openSign", getNMSClass("TileEntitySign")).invoke(player, sign);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	private static Class<?> getNMSClass(String nmsClassString) throws ClassNotFoundException {
        return Class.forName("net.minecraft.server." + ToolMain.getVersion() + "." + nmsClassString);
    }
	
	private static Class<?> getCraftBukkitClass(String nmsClassString) throws ClassNotFoundException {
        return Class.forName("org.bukkit.craftbukkit." + ToolMain.getVersion() + "." + nmsClassString);
    }

}
