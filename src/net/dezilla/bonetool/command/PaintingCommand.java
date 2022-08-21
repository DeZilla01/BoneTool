package net.dezilla.bonetool.command;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import net.dezilla.bonetool.ToolUser;
import net.dezilla.bonetool.Util;
import net.dezilla.bonetool.gui.PaintingGui;
import net.dezilla.bonetool.util.Locale;
import net.dezilla.bonetool.util.ToolConfig;

public class PaintingCommand extends Command{

	public PaintingCommand() {
		super("paintings");
	}

	@Override
	public boolean execute(CommandSender sender, String commandLabel, String[] args) {
		if(!(sender instanceof Player)) {
			sender.sendMessage(Locale.parse("senderNotPlayer"));
			return true;
		}
		if(!ToolConfig.paintingByName) {
			sender.sendMessage(Util.MSG_START+ChatColor.RED+Locale.parse("accessDisabled"));
			return true;
		}
		Player p = (Player) sender;
		if(!Util.permCheck(p, "bonetool.blocks.paintings")) {
			p.sendMessage(Util.MSG_START+ChatColor.RED+Locale.parse(ToolUser.getUser(p),"accessDenied"));
			return true;
		}
		new PaintingGui(p).display();
		return true;
	}

}
