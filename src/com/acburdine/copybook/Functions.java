package com.acburdine.copybook;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.Plugin;

public class Functions {

	private static Plugin name = Bukkit.getServer().getPluginManager().getPlugin("CopyBook");
	private static FileConfiguration config = name.getConfig();
	
	public static void saveBook(String bookName, String ext, String author, String title, List<String> pages) {
		if(!bookName.endsWith(ext)) {
			bookName += ext;
		}
		String path = name.getDataFolder() + File.separator + bookName;
		FileHandling file = new FileHandling(path, FileHandling.WRITE);
		file.Open();
		file.WriteLine("title: " + title);
		file.WriteLine("author: " + author);
		for (int i=0;i<pages.size();i++) {
			String currentPage = pages.get(i).replaceAll("\\n", "&ENTER&");
			file.WriteLine("page" + i + ": " + currentPage);
		}
		file.Close();
	}
	
	public static ItemStack loadBook(String bookName, String ext, boolean edit) {
		if(!bookName.endsWith(ext)) {
			bookName += ext;
		}
		String path = name.getDataFolder() + File.separator + bookName;
		FileHandling file = new FileHandling(path, FileHandling.READ);
		file.Open();
		String title = "default";
		String author = "default";
		String line;
		String[] splittedLine;
		List<String> pages = new ArrayList<String>();
		String page = "default";
		while((line = file.ReadLine()) != null) {
			splittedLine = line.split(": ");
			if (splittedLine[0].matches("title"))
				title = line.split(": ")[1];
			else if (splittedLine[0].matches("author"))
				author = line.split(": ")[1];
			for (int i=0; i<50; i++) {
				String pageName = "page" + i;
				if (splittedLine[0].matches(pageName)) {
					page = line.split(": ")[1];
					pages.add(page.replaceAll("&ENTER&", "\n"));
				} else {
					continue;
				}
			}
		}
		file.Close();
		if (edit) {
			return new Book(title, author, pages).editBook();
		}
		else {
		return new Book(title, author, pages).createItem();
		}
	}
	
	public static void initLoad(Player sender, String[] args, boolean edit, boolean bookInHand) {
		String bName = null;
		if(args.length == 1) {
			sender.sendMessage(ChatColor.RED + "You must supply a book name.");
			return;
		} else if (args.length == 2) { 
			bName = args[1].toLowerCase();
		} else if (args.length >= 3) {
			bName = args[1].toLowerCase() + "_";
			for (int i=2;i<args.length;i++) {
				String addon;
				if (i == args.length-1)
					addon = args[i];
				else {
					addon = args[i] + "_";
				}
				bName += addon.toLowerCase();
			}
		}
		String ext = config.getString("extension");
		ItemStack item = loadBook(bName, ext, edit);
		if (bookInHand) {
			sender.setItemInHand(item);
		} else {
			sender.getInventory().addItem(item);
		}
		sender.sendMessage(ChatColor.GREEN + "Book Successfully Loaded!");
	}
	public static boolean checkBookInHand(Player p) {
		boolean value = config.getBoolean("book_in_hand.sign", false);
		return value;
	}
	public static void signLoad(Player p, String[] args) {
		boolean bookInHand = checkBookInHand(p);
		String bName = null;
		if(args.length == 0) {
			p.sendMessage(ChatColor.RED + "You must supply a book name.");
		} else if (args.length == 1) { 
			bName = args[0].toLowerCase();
		} else if (args.length >= 2) {
			bName = args[0].toLowerCase() + "_";
			for (int i=1;i<args.length;i++) {
				String addon;
				if (i == args.length-1)
					addon = args[i];
				else {
					addon = args[i] + "_";
				}
				bName += addon.toLowerCase();
			}
		}
		String ext = config.getString("extension");
		ItemStack item = loadBook(bName, ext, false);
		if (bookInHand) {
			p.setItemInHand(item);
		} else {
			p.getInventory().addItem(item);
		}
		p.sendMessage(ChatColor.GREEN + "Book Successfully Loaded!");
		
	}
}
