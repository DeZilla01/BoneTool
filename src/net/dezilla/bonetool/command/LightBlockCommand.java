package net.dezilla.bonetool.command;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import net.dezilla.bonetool.Util;
import net.dezilla.bonetool.gui.LightBlockGui;

public class LightBlockCommand extends Command{

	public LightBlockCommand() {
		super("lightblock");
	}

	@Override
	public boolean execute(CommandSender sender, String commandLabel, String[] args) {
		if(!(sender instanceof Player)) {
			sender.sendMessage("You must be a player to use this command.");
			return true;
		}
		Player p = (Player) sender;
		if(!Util.permCheck(p, "bonetool.blocks.lightblock")) {
			p.sendMessage(Util.MSG_START+ChatColor.RED+"You do not have access to this feature.");
			return true;
		}
		new LightBlockGui(p).display();
		return true;
	}

}
