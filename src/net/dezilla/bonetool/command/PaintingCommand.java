package net.dezilla.bonetool.command;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import net.dezilla.bonetool.Util;
import net.dezilla.bonetool.gui.PaintingGui;
import net.dezilla.bonetool.util.ToolConfig;

public class PaintingCommand extends Command{

	public PaintingCommand() {
		super("paintings");
	}

	@Override
	public boolean execute(CommandSender sender, String commandLabel, String[] args) {
		if(!(sender instanceof Player)) {
			sender.sendMessage("You must be a player to use this command.");
			return true;
		}
		if(!ToolConfig.paintingByName) {
			sender.sendMessage(Util.MSG_START+ChatColor.RED+"This feature is disabled.");
			return true;
		}
		Player p = (Player) sender;
		if(!Util.permCheck(p, "bonetool.blocks.paintings")) {
			p.sendMessage(Util.MSG_START+ChatColor.RED+"You do not have access to this feature.");
			return true;
		}
		new PaintingGui(p).display();
		return true;
	}

}
