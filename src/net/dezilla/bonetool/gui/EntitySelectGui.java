package net.dezilla.bonetool.gui;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import net.dezilla.bonetool.ToolUser;
import net.dezilla.bonetool.Util;
import net.dezilla.bonetool.util.EntityTypeRunnable;
import net.dezilla.bonetool.util.Locale;

public class EntitySelectGui extends GuiPage{
	List<EntitySelectGui> pages = new ArrayList<EntitySelectGui>();
	List<EntityType> options;
	EntityTypeRunnable run;

	public EntitySelectGui(Player player, List<EntityType> options, EntityTypeRunnable run) {
		super(6, player);
		this.run = run;
		this.options = options;
		setName(Locale.parse(ToolUser.getUser(player), "selectentity"));
		addItems();
	}
	
	private EntitySelectGui(Player player, int page) {
		super(6, player);
		setName(Locale.parse(ToolUser.getUser(player), "selectentity")+" - "+Locale.parse(ToolUser.getUser(player), "page")+" "+page);
	}
	
	private void addItems() {
		EntitySelectGui currentPage = this;
		pages.add(currentPage);
		int row = 1;
		int col = 0;
		for(EntityType type : options) {
			ItemStack icon = Util.getEntityIcon(type);
			GuiItem item = new GuiItem(icon);
			item.setRun((event) -> run.run(type));
			currentPage.setItem(row, col++, item);
			if(col>8) {
				row++;
				col=0;
			}
			if(row>5) {
				pages.get(0).setName(Locale.parse(ToolUser.getUser(getPlayer()), "selectentity")+" - "+Locale.parse(ToolUser.getUser(getPlayer()), "page")+" 1");
				currentPage = new EntitySelectGui(getPlayer(), pages.size()+1);
				pages.add(currentPage);
				row = 1;
				col = 0;
			}
		}
		for(EntitySelectGui page : pages) {
			if(pages.indexOf(page) != 0) {
				GuiItem previous = new GuiItem(getPrevious(ToolUser.getUser(getPlayer())));
				previous.setRun((event) -> {
					pages.get(pages.indexOf(page)-1).multiPageFix();
					pages.get(pages.indexOf(page)-1).display();
				});
				page.setItem(0,0,previous);
			}
			if(pages.indexOf(page)+1 != pages.size()) {
				GuiItem next = new GuiItem(getNext(ToolUser.getUser(getPlayer())));
				next.setRun((event) -> {
					pages.get(pages.indexOf(page)+1).multiPageFix();
					pages.get(pages.indexOf(page)+1).display();
				});
				page.setItem(0,8,next);
			}
		}
	}
	
	private static ItemStack getNext(ToolUser user) {
		ItemStack icon = Util.createTexturedHead("eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvZDllY2NjNWMxYzc5YWE3ODI2YTE1YTdmNWYxMmZiNDAzMjgxNTdjNTI0MjE2NGJhMmFlZjQ3ZTVkZTlhNWNmYyJ9fX0=");
		return Util.setName(icon, Locale.parse(user, "next"));
	}
	
	private static ItemStack getPrevious(ToolUser user) {
		ItemStack icon = Util.createTexturedHead("eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvODY0Zjc3OWE4ZTNmZmEyMzExNDNmYTY5Yjk2YjE0ZWUzNWMxNmQ2NjllMTljNzVmZDFhN2RhNGJmMzA2YyJ9fX0=");
		return Util.setName(icon, Locale.parse(user, "previous"));
	}

}
