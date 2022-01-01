package net.dezilla.bonetool.util;

import org.bukkit.block.Block;
import org.bukkit.block.Sign;
import org.bukkit.entity.Player;

import net.minecraft.core.BlockPosition;
import net.minecraft.server.level.EntityPlayer;
import net.minecraft.server.level.WorldServer;
import net.minecraft.world.level.block.entity.TileEntitySign;

public class SignEdit1_17 {
	
	public static void editSign(Player p, Block block) {
		if(!(block.getState() instanceof Sign))
			return;
        
		try {
			WorldServer wserver = (WorldServer) block.getWorld().getClass().getMethod("getHandle").invoke(block.getWorld());
			TileEntitySign sign = (TileEntitySign) wserver.getTileEntity(new BlockPosition(block.getX(), block.getY(), block.getZ()));
			//sign.getClass().getDeclaredField("isEditable").set(sign, true);
			sign.f=true;
			EntityPlayer ep = (EntityPlayer) p.getClass().getMethod("getHandle").invoke(p);
			sign.a(ep);
			sign.update();
			ep.openSign(sign);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
