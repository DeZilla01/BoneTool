package net.dezilla.bonetool.gui;

import java.util.Arrays;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.bukkit.Instrument;
import org.bukkit.Material;
import org.bukkit.Note;
import org.bukkit.Sound;
import org.bukkit.Note.Tone;
import org.bukkit.block.Block;
import org.bukkit.block.data.type.NoteBlock;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import net.dezilla.bonetool.ToolMain;
import net.dezilla.bonetool.ToolUser;
import net.dezilla.bonetool.Util;
import net.dezilla.bonetool.util.Locale;

public class NoteGui extends GuiPage {
	private Block block = null;
	public static String[] heads = {"SKELETON", "WITHER_SKELETON", "CUSTOM_HEAD", "ZOMBIE", "CREEPER", "PIGLIN", "DRAGON"};

	public NoteGui(Player player, Block noteblock) {
		super(6, player);
		setName(Locale.parse(ToolUser.getUser(player), "noteblockeditor"));
		this.block = noteblock;
		if(!(noteblock.getBlockData() instanceof NoteBlock))
			return;
		addItems();
	}
	
	private void addItems() {
		NoteBlock noteblock = (NoteBlock) block.getBlockData();
		int row = 0;
		int col = 0;
		for(Note note : NOTES) {
			String name = note.getTone()+(note.isSharped() ? "♯":"")+" Octave: "+note.getOctave();
			Material mat = (note.isSharped() ? Material.BLACK_WOOL : Material.WHITE_WOOL);
			ItemStack icon = Util.setName(new ItemStack(mat), name);
			if(noteblock.getNote().isSharped() == note.isSharped() && noteblock.getNote().getTone() == note.getTone() && noteblock.getNote().getOctave() == note.getOctave()) {
				icon.addUnsafeEnchantment(Enchantment.MENDING, 0);
			}
			GuiItem item = new GuiItem(icon);
			item.setRun((event) -> {
				noteblock.setNote(note);
				block.setBlockData(noteblock);
				play();
				refresh();
			});
			setItem(row, col++, item);
			if(col>=8) {
				col=0;
				row++;
			}
		}
		if(ToolMain.getVersionNumber()>=19) {
			ItemStack icon = Util.setName(new ItemStack(Material.ZOMBIE_HEAD), Locale.parse(ToolUser.getUser(getPlayer()), "mobnoise"));
			GuiItem item = new GuiItem(icon);
			item.setRun((event) -> {
				new NoteBlockMobGui(getPlayer(), this).display();
			});
			setItem(0,8,item);
		}
		col = 0;
		row = 4;
		for(Instrument ins : Instrument.values()) {
			if(ToolMain.getVersionNumber() >=19 && Arrays.asList(heads).contains(ins.toString()))
				continue;
			ItemStack icon = getIcon(ins);
			if(noteblock.getInstrument() == ins) {
				icon.addUnsafeEnchantment(Enchantment.MENDING, 0);
			}
			GuiItem item = new GuiItem(icon);
			item.setRun((event) -> {
				noteblock.setInstrument(ins);
				block.setBlockData(noteblock);
				play();
				refresh();
			});
			setItem(row, col++, item);
			if(col>=9) {
				row++;
				col=0;
				if(row == 6)
					break;
			}
		}
	}
	
	public void refresh() {
		this.clear();
		this.addItems();
		this.display();
	}
	
	public void play() {
		NoteBlock noteblock = (NoteBlock) block.getBlockData();
		if(ToolMain.getVersionNumber() >= 19 && Arrays.asList(heads).contains(noteblock.getInstrument().toString())) {
			switch(noteblock.getInstrument()) {
				case CREEPER: getPlayer().playSound(getPlayer(), Sound.ENTITY_CREEPER_PRIMED, 1, 1);break;
				case DRAGON: getPlayer().playSound(getPlayer(), Sound.ENTITY_ENDER_DRAGON_GROWL, 1, 1);break;
				case PIGLIN: getPlayer().playSound(getPlayer(), Sound.ENTITY_PIGLIN_AMBIENT, 1, 1);break;
				case SKELETON: getPlayer().playSound(getPlayer(), Sound.ENTITY_SKELETON_AMBIENT, 1, 1);break;
				case WITHER_SKELETON: getPlayer().playSound(getPlayer(), Sound.ENTITY_WITHER_SKELETON_AMBIENT, 1, 1);break;
				case ZOMBIE: getPlayer().playSound(getPlayer(), Sound.ENTITY_ZOMBIE_AMBIENT, 1, 1);break;
				default: {}
			}
			return;
		}
		getPlayer().playNote(getPlayer().getLocation(), noteblock.getInstrument(), noteblock.getNote());
	}
	
	public Block getBlock() {
		return block;
	}
	
	private static final List<Note> NOTES = Arrays.asList(
			Note.sharp(0, Tone.F),
			Note.sharp(0, Tone.G),
			Note.sharp(0, Tone.A),
			Note.natural(0, Tone.B),
			Note.sharp(0, Tone.C),
			Note.sharp(0, Tone.D),
			Note.natural(0, Tone.E),
			Note.sharp(1, Tone.F),
			
			Note.sharp(0, Tone.F),
			Note.natural(0, Tone.G),
			Note.natural(0, Tone.A),
			Note.natural(0, Tone.B),
			Note.natural(0, Tone.C),
			Note.natural(0, Tone.D),
			Note.natural(0, Tone.E),
			Note.natural(0, Tone.F),
			
			Note.sharp(1, Tone.F),
			Note.sharp(1, Tone.G),
			Note.sharp(1, Tone.A),
			Note.natural(1, Tone.B),
			Note.sharp(1, Tone.C),
			Note.sharp(1, Tone.D),
			Note.natural(1, Tone.E),
			Note.sharp(2, Tone.F),
			
			Note.natural(0, Tone.F),
			Note.natural(1, Tone.G),
			Note.natural(1, Tone.A),
			Note.natural(1, Tone.B),
			Note.natural(1, Tone.C),
			Note.natural(1, Tone.D),
			Note.natural(1, Tone.E),
			Note.natural(1, Tone.F));
	
	public ItemStack getIcon(Instrument instrument) {
		String name = StringUtils.capitalize(instrument.toString().toLowerCase().replace("_", " "));
		switch(instrument) {
			case BANJO: return Util.setName(new ItemStack(Material.HAY_BLOCK), name);
			case BASS_DRUM: return Util.setName(new ItemStack(Material.STONE), name);
			case BASS_GUITAR: return Util.setName(new ItemStack(Material.OAK_PLANKS), name);
			case BELL: return Util.setName(new ItemStack(Material.GOLD_BLOCK), name);
			case BIT: return Util.setName(new ItemStack(Material.EMERALD_BLOCK), name);
			case CHIME: return Util.setName(new ItemStack(Material.ICE), name);
			case COW_BELL: return Util.setName(new ItemStack(Material.SOUL_SAND), name);
			case DIDGERIDOO: return Util.setName(new ItemStack(Material.PUMPKIN), name);
			case FLUTE: return Util.setName(new ItemStack(Material.CLAY), name);
			case GUITAR: return Util.setName(new ItemStack(Material.WHITE_WOOL), name);
			case IRON_XYLOPHONE: return Util.setName(new ItemStack(Material.IRON_BLOCK), name);
			case PIANO: return Util.setName(new ItemStack(Material.NOTE_BLOCK), name);
			case PLING: return Util.setName(new ItemStack(Material.GLOWSTONE), name);
			case SNARE_DRUM: return Util.setName(new ItemStack(Material.SAND), name);
			case STICKS: return Util.setName(new ItemStack(Material.GLASS), name);
			case XYLOPHONE: return Util.setName(new ItemStack(Material.BONE_BLOCK), name);
			case CREEPER: return Util.setName(new ItemStack(Material.CREEPER_HEAD), name);
			case CUSTOM_HEAD: return Util.setName(new ItemStack(Material.PLAYER_HEAD), name);
			case DRAGON: return Util.setName(new ItemStack(Material.DRAGON_HEAD), name);
			case PIGLIN: return Util.setName(new ItemStack(Material.PIGLIN_HEAD), name);
			case SKELETON: return Util.setName(new ItemStack(Material.SKELETON_SKULL), name);
			case WITHER_SKELETON: return Util.setName(new ItemStack(Material.WITHER_SKELETON_SKULL), name);
			case ZOMBIE: return Util.setName(new ItemStack(Material.ZOMBIE_HEAD), name);
		}
		return Util.setName(new ItemStack(Material.NOTE_BLOCK), name);
	}

}

