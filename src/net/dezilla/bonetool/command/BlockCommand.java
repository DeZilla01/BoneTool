package net.dezilla.bonetool.command;

import java.util.Arrays;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import net.dezilla.bonetool.Util;
import net.dezilla.bonetool.gui.IllegalBlocksGui;

public class BlockCommand extends Command{

	public BlockCommand() {
		super("blocks", "Access to secret blocks", "/blocks", Arrays.asList("secretblocks", "illegalblocks"));
	}

	@Override
	public boolean execute(CommandSender sender, String commandLabel, String[] args) {
		if(!(sender instanceof Player)) {
			sender.sendMessage("You must be a player to use this command.");
			return true;
		}
		Player player = (Player) sender;
		if(!Util.permCheck(player, "bonetool.blocks.gui")) {
			player.sendMessage(Util.MSG_START+ChatColor.RED+"You do not have access to this feature.");
			return true;
		}
		new IllegalBlocksGui(player).display();
		return true;
	}

}
