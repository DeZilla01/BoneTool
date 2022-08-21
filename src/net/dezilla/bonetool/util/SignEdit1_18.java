package net.dezilla.bonetool.util;

import org.bukkit.block.Block;
import org.bukkit.block.Sign;
import org.bukkit.entity.Player;

public class SignEdit1_18 {
	
	public static void editSign(Player p, Block block) {
		if(!(block.getState() instanceof Sign))
			return;
        
		Sign sign = (Sign) block.getState();
		sign.setEditable(true);
		try {
			Player.class.getMethod("openSign", Sign.class).invoke(p, sign);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
