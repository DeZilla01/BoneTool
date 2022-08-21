package net.dezilla.bonetool.gui;

import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.bukkit.Material;
import org.bukkit.block.BlockFace;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import net.dezilla.bonetool.ToolUser;
import net.dezilla.bonetool.Util;
import net.dezilla.bonetool.util.BlockFaceRunnable;
import net.dezilla.bonetool.util.Locale;

public class BlockFaceSelectGui extends GuiPage{

	public BlockFaceSelectGui(Player player, List<BlockFace> values, BlockFaceRunnable run) {
		super(5, player);
		setName(Locale.parse(ToolUser.getUser(player), "selectrotation"));
		addItems(values, run);
	}
	
	private void addItems(List<BlockFace> values, BlockFaceRunnable run) {
		for(BlockFace face : values) {
			Material mat = Material.WHITE_WOOL;
			if(face == BlockFace.SOUTH || face == BlockFace.WEST || face == BlockFace.EAST)
				mat = Material.BLACK_WOOL;
			else if(face == BlockFace.NORTH)
				mat = Material.RED_WOOL;
			else if(face == BlockFace.UP || face == BlockFace.DOWN || face == BlockFace.SELF)
				mat = Material.SLIME_BALL;
			ItemStack icon = Util.setName(new ItemStack(mat), StringUtils.capitalize(face.toString().toLowerCase().replace("_", " ")));
			GuiItem item = new GuiItem(icon);
			item.setRun((event) -> run.run(face));
			setItem(item, face);
		}
	}
	
	private void setItem(GuiItem item, BlockFace face) {
		switch(face) {
		case DOWN: setItem(3, 4, item); break;
		case EAST: setItem(2, 6, item);break;
		case EAST_NORTH_EAST: setItem(1, 6, item);break;
		case EAST_SOUTH_EAST: setItem(3, 6, item);break;
		case NORTH: setItem(0, 4, item);break;
		case NORTH_EAST: setItem(0, 6, item);break;
		case NORTH_NORTH_EAST: setItem(0, 5, item);break;
		case NORTH_NORTH_WEST: setItem(0, 3, item);break;
		case NORTH_WEST: setItem(0, 2, item);break;
		case SELF: setItem(2, 4, item);break;
		case SOUTH: setItem(4, 4, item);break;
		case SOUTH_EAST: setItem(4, 6, item);break;
		case SOUTH_SOUTH_EAST: setItem(4, 5, item);break;
		case SOUTH_SOUTH_WEST: setItem(4, 3, item);break;
		case SOUTH_WEST: setItem(4, 2, item);break;
		case UP: setItem(1, 4, item);break;
		case WEST: setItem(2, 2, item);break;
		case WEST_NORTH_WEST: setItem(1, 2, item);break;
		case WEST_SOUTH_WEST: setItem(3, 2, item);break;
		default:
			break;
			
		}
	}

}
