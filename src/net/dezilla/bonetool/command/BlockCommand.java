package net.dezilla.bonetool.command;

import java.util.Arrays;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import net.dezilla.bonetool.ToolUser;
import net.dezilla.bonetool.Util;
import net.dezilla.bonetool.gui.IllegalBlocksGui;
import net.dezilla.bonetool.util.Locale;

public class BlockCommand extends Command implements CommandExecutor{

	public BlockCommand() {
		super("blocks", "Access to secret blocks", "/blocks", Arrays.asList("secretblocks", "illegalblocks"));
	}

	@Override
	public boolean execute(CommandSender sender, String commandLabel, String[] args) {
		if(!(sender instanceof Player)) {
			sender.sendMessage(Locale.parse("senderNotPlayer"));
			return true;
		}
		Player player = (Player) sender;
		if(!Util.permCheck(player, "bonetool.blocks.gui")) {
			player.sendMessage(Util.MSG_START+ChatColor.RED+Locale.parse(ToolUser.getUser(player),"accessDenied"));
			return true;
		}
		new IllegalBlocksGui(player).display();
		return true;
	}

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String alias, String[] args) {
		return execute(sender, alias, args);
	}

}
