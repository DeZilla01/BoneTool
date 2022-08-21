package net.dezilla.bonetool.wandtool;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;

import org.bukkit.Axis;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;

import net.dezilla.bonetool.ToolUser;
import net.dezilla.bonetool.Util;
import net.dezilla.bonetool.listener.BlockUpdateListener;
import net.dezilla.bonetool.util.Locale;

public class PlayerFaceTool extends WandTool{
	List<WandTool> tools = new ArrayList<WandTool>();
	
	public PlayerFaceTool() {
		for(WandTool tool : Arrays.asList(new RotatableTool(), new DirectionalTool(), new OrientableTool())) {
			if(tool.isServerCompatible())
				tools.add(tool);
		}
	}

	@Override
	public boolean isServerCompatible() {
		return true;
	}

	@Override
	public boolean isBlockCompatible(Block block) {
		return false;
	}

	@Override
	public ItemStack getIcon(Block block, ToolUser user) {
		ItemStack icon =  Util.setName(new ItemStack(Material.PLAYER_HEAD), Locale.parse(user, "playerfacing"));
		return Util.setLore(icon, Arrays.asList(ChatColor.GRAY+Locale.parse(user, "playerfacingdesc"), ChatColor.GRAY+Locale.parse(user, "playerfacingex")));
	}
	
	@Override
	public boolean isRightClickTool() {
		return true;
	}
	
	@Override
	public void onRightClick(PlayerInteractEvent event, Block block) {
		Player player = event.getPlayer();
		for(WandTool t : tools) {
			if(!t.isBlockCompatible(block))
				continue;
			BlockUpdateListener.protectBlock(block, 2);
			if(t instanceof RotatableTool) {
				BlockFace face = Util.getBlockFacing(player, new HashSet<BlockFace>(RotatableTool.getCompatibleFaces(block)));
				String s = block.getType().toString();
				if(s.contains("SKULL") || s.contains("HEAD"))
					face = face.getOppositeFace();
				RotatableTool.setRotation(block, face);
			}
			else if(t instanceof DirectionalTool) {
				BlockFace face = Util.getBlockFacing(player,DirectionalTool.getFaces(block));
				String s = block.getType().toString();
				if(s.contains("STAIR")  || s.contains("COCOA") || s.contains("BELL"))
					face = face.getOppositeFace();
				DirectionalTool.setFacing(block, face);
			}
			else if(t instanceof OrientableTool) {
				List<BlockFace> compatible = new ArrayList<BlockFace>();
				if(OrientableTool.getAxes(block).contains(Axis.X)) {
					compatible.add(BlockFace.EAST);
					compatible.add(BlockFace.WEST);
				}
				if(OrientableTool.getAxes(block).contains(Axis.Z)) {
					compatible.add(BlockFace.NORTH);
					compatible.add(BlockFace.SOUTH);
				}
				if(OrientableTool.getAxes(block).contains(Axis.Y)) {
					compatible.add(BlockFace.UP);
					compatible.add(BlockFace.DOWN);
				}
				BlockFace face = Util.getBlockFacing(player, new HashSet<BlockFace>(compatible));
				String s = block.getType().toString();
				boolean rotate = s.contains("PORTAL");
				switch(face) {
					case WEST: case EAST:
						OrientableTool.setAxis(block, (rotate ? Axis.Z : Axis.X));break;
					case NORTH: case SOUTH:
						OrientableTool.setAxis(block, (rotate ? Axis.X : Axis.Z));break;
					case UP: case DOWN:
						OrientableTool.setAxis(block, Axis.Y);break;
					default:{}
				}
			}
		}
	}

}
