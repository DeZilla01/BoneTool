package net.dezilla.bonetool.util;

import org.bukkit.block.Block;
import org.bukkit.block.Sign;
import org.bukkit.entity.Player;

//import net.minecraft.core.BlockPosition;
//import net.minecraft.server.level.EntityPlayer;
//import net.minecraft.server.level.WorldServer;
//import net.minecraft.world.level.block.entity.TileEntitySign;

public class SignEdit1_17 {
	
	public static void editSign(Player p, Block block) {
		if(!(block.getState() instanceof Sign))
			return;
        
		try {
			Object wserver = block.getWorld().getClass().getMethod("getHandle").invoke(block.getWorld());//WorldServer
			Object sign = wserver.getClass().getMethod("getTileEntity", Class.forName("net.minecraft.core.BlockPosition")).invoke(wserver, Class.forName("net.minecraft.core.BlockPosition").getConstructor(Integer.TYPE, Integer.TYPE, Integer.TYPE).newInstance(block.getX(), block.getY(), block.getZ()));//TileEntitySign
			sign.getClass().getDeclaredField("f").set(sign, true);
			Object ep = p.getClass().getMethod("getHandle").invoke(p);//EntityPlayer
			sign.getClass().getMethod("a", ep.getClass()).invoke(sign, ep);
			sign.getClass().getMethod("update").invoke(sign);
			ep.getClass().getMethod("openSign", sign.getClass()).invoke(ep, sign);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
