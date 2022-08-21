package net.dezilla.bonetool.wandtool;

import java.util.Arrays;
import java.util.List;

import org.bukkit.ChatColor;
import org.bukkit.DyeColor;
import org.bukkit.Material;
import org.bukkit.block.Banner;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.block.ShulkerBox;
import org.bukkit.block.banner.Pattern;
import org.bukkit.block.data.Directional;
import org.bukkit.block.data.Rotatable;
import org.bukkit.block.data.type.Bed;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;

import net.dezilla.bonetool.ToolUser;
import net.dezilla.bonetool.Util;
import net.dezilla.bonetool.gui.ColorSelectGui;
import net.dezilla.bonetool.util.Locale;

public class ColorTool extends WandTool{

	@Override
	public boolean isServerCompatible() {
		return true;
	}

	@Override
	public boolean isBlockCompatible(Block block) {
		return isColored(block);
	}
	
	@Override
	public ItemStack getIcon(Block block, ToolUser user) {
		String name = Locale.parse(user, "color");
		Material material = Material.ORANGE_DYE;
		if(block != null) {
			name = Locale.parse(user, "color")+": "+getDyeColor(block);
			material = getColorMaterial(getDyeColor(block));
		}
		ItemStack icon = Util.setName(new ItemStack(material), name);
		if(block != null) {
			List<String> lore = Arrays.asList(
					ChatColor.AQUA+Locale.parse(user, "leftclick")+": "+ChatColor.WHITE+Locale.parse(user, "choosecolor"),
					ChatColor.AQUA+Locale.parse(user, "rightclick")+": "+ChatColor.WHITE+Locale.parse(user, "togglecolor"));
			icon = Util.setLore(icon, lore);
		} else {
			List<String> lore = Arrays.asList(
					ChatColor.GRAY+Locale.parse(user, "colorinstructions1"),
					ChatColor.GRAY+Locale.parse(user, "colorinstructions2")
					);
			icon = Util.setLore(icon, lore);
		}
		return icon;
	}
	
	@Override
	public void onLeftClick(InventoryClickEvent event, Block block) {
		if(event.getClick() == ClickType.LEFT) {
			new ColorSelectGui((Player) event.getWhoClicked(), block).display();
		} else if (event.getClick() == ClickType.RIGHT) {
			toggleColor(block);
		}
	}
	
	@Override
	public void onRightClick(PlayerInteractEvent event, Block block) {
		ToolUser user = ToolUser.getUser(event.getPlayer());
		if(event.getPlayer().isSneaking() && event.getPlayer().getLocation().getBlock().getRelative(BlockFace.DOWN).getType().isSolid()) {
			new ColorSelectGui(user.getPlayer()).display();
		} else {
			setColor(block, user.getToolColor());
		}
	}
	
	@Override
	public boolean isRightClickTool() {
		return true;
	}
	
	@Override
	public boolean canToggle() {
		return true;
	}
	
	@Override
	public void toggle(Block block) {
		toggleColor(block);
	}
	
	@Override
	public boolean hasGui() {
		return true;
	}
	
	@Override
	public void openGui(Player player, Block block) {
		new ColorSelectGui(player, block).display();
	}
	
	private final static String[] WHITELIST = {
			"_WALL_BANNER",
			"_BANNER",
			"_BED",
			"_CARPET",
			"_CONCRETE",
			"_CONCRETE_POWDER",
			"_GLAZED_TERRACOTTA",
			"_SHULKER_BOX",
			"_STAINED_GLASS",
			"_STAINED_GLASS_PANE",
			"_TERRACOTTA",
			"_WOOL"
	};
	
	public static boolean isColored(Block block) {
		for(String s : WHITELIST) {
			if(block.getType().toString().endsWith(s))
				return true;
		}
		return false;
	}
	
	public static DyeColor getDyeColor(Block block) {
		for(String s : WHITELIST) {
			if(block.getType().toString().endsWith(s)) {
				try {
					return DyeColor.valueOf(block.getType().toString().replace(s, ""));
				}catch(Exception e) {e.printStackTrace();}
			}
		}
		return DyeColor.BLACK;
	}
	
	public static Material getNewColorMaterial(Block block, DyeColor color) {
		for(String s : WHITELIST) {
			if(block.getType().toString().endsWith(s)) {
				return Material.valueOf(color.toString()+s);
			}
		}
		return null;
	}
	
	public static Block setColor(Block block, DyeColor color) {
		for(String s : WHITELIST) {
			if(block.getType().toString().endsWith(s)) {
				try {
					BlockFace direction = BlockFace.NORTH;
					BlockFace rotation = BlockFace.NORTH;
					Bed.Part bed = Bed.Part.FOOT;
					if(block.getBlockData() instanceof Directional)
						direction = DirectionalTool.getFacing(block);
					if(block.getBlockData() instanceof Rotatable)
						rotation = RotatableTool.getRotation(block);
					if(BedTool.isBed(block))
						bed = BedTool.getBedPart(block);
					if(block.getState() instanceof ShulkerBox) {
						ItemStack[] contents = ((ShulkerBox) block.getState()).getInventory().getContents();
						block.setType(Material.valueOf(color.toString()+s));
						((ShulkerBox) block.getState()).getInventory().setContents(contents);
						block.getState().update();
					}
					else if(block.getState() instanceof Banner) {
						Banner banner = (Banner) block.getState();
						List<Pattern> patterns = banner.getPatterns();
						for(Pattern p : patterns)
							if(p.getColor().equals(getDyeColor(block)))
								patterns.set(patterns.indexOf(p), new Pattern(color, p.getPattern()));
						block.setType(Material.valueOf(color.toString()+s));
						Banner newBanner = (Banner) block.getState();
						newBanner.setPatterns(patterns);
						newBanner.update();
					}
					else
						block.setType(Material.valueOf(color.toString()+s));
					if(block.getBlockData() instanceof Directional)
						DirectionalTool.setFacing(block, direction);
					if(block.getBlockData() instanceof Rotatable)
						RotatableTool.setRotation(block, rotation);
					if(BedTool.isBed(block))
						BedTool.setBedPart(block, bed);
					return block;
				}catch(Exception e) {e.printStackTrace();}
			}
		}
		return block;
	}
	
	public static Block toggleColor(Block block) {
		for(String s : WHITELIST) {
			if(block.getType().toString().endsWith(s)) {
				try {
					List<DyeColor> colors = Arrays.asList(DyeColor.values());
					DyeColor next = DyeColor.BLACK;
					if(colors.indexOf(getDyeColor(block)) >= colors.size()-1)
						next = colors.get(0);
					else
						next = colors.get(colors.indexOf(getDyeColor(block))+1);
					return setColor(block, next);
				}catch(Exception e) {e.printStackTrace();}
			}
		}
		return block;
	}
	
	public static Material getColorMaterial(DyeColor color) {
		switch(color) {
			case WHITE:
				return Material.WHITE_DYE;//0
			case ORANGE:
				return Material.ORANGE_DYE;//1
			case MAGENTA:
				return Material.MAGENTA_DYE;//2
			case LIGHT_BLUE:
				return Material.LIGHT_BLUE_DYE;//3
			case YELLOW:
				return Material.YELLOW_DYE;//4
			case LIME:
				return Material.LIME_DYE;//5
			case PINK:
				return Material.PINK_DYE;//6
			case LIGHT_GRAY:
				return Material.LIGHT_GRAY_DYE;//7
			case GRAY:
				return Material.GRAY_DYE;//8
			case CYAN:
				return Material.CYAN_DYE;//9
			case PURPLE:
				return Material.PURPLE_DYE;//10
			case BLUE:
				return Material.BLUE_DYE;//11
			case BROWN:
				return Material.BROWN_DYE;//12
			case GREEN:
				return Material.GREEN_DYE;//13
			case RED:
				return Material.RED_DYE;//14
			case BLACK:
				return Material.BLACK_DYE;//15
		}
		return Material.WHITE_DYE;
	}

}
