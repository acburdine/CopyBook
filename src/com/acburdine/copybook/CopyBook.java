package com.acburdine.copybook;

import java.util.logging.Logger;

import net.milkbowl.vault.economy.Economy;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.RegisteredServiceProvider;
import org.bukkit.plugin.java.JavaPlugin;

public class CopyBook extends JavaPlugin {
	
	FileConfiguration config;
	public static Economy econ;
	protected Logger log;
	protected UpdateNotify update;
	
	public void onEnable() {
		saveDefaultConfig();
		try {
			config = getConfig();
		} catch(Exception e1) {
			e1.printStackTrace();
		}
		this.log = this.getLogger();
		this.update = new UpdateNotify(this, "http://dev.bukkit.org/bukkit-plugins/copy_book/files.rss");
		if (this.update.updateNeeded()) {
			this.log.info("A new version of CopyBook is available: Version " + this.update.getVersion());
			this.log.info("Get it from: " + this.update.getLink());
		}
		boolean vault = config.getBoolean("vault", false);
		if (vault) {
			if (!setUpEcon()) {
				getLogger().severe("No Vault instance found! Please download Vault in order for this to work.");
				getServer().getPluginManager().disablePlugin(this);
			}
		} else {
			econ = null;
		}
		getServer().getPluginManager().registerEvents(new PlayerListener(), this);
		getServer().getPluginManager().registerEvents(new SignListener(), this);
		getLogger().info("CopyBook has been enabled!");
	}
	
	public void onDisable() {
		saveConfig();
		getLogger().info("CopyBook has been disabled.");
	}
	
	private boolean setUpEcon() {
		if (getServer().getPluginManager().getPlugin("Vault") == null) {
			return false;
		}
		RegisteredServiceProvider<Economy> rsp = getServer().getServicesManager().getRegistration(Economy.class);
		if (rsp == null) {
			return false;
		}
		econ = rsp.getProvider();
		return econ != null;
	}
	
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		
		if (!(sender instanceof Player)) {
			if(cmd.getName().equalsIgnoreCase("cpb")) {	
				if (args[0].equalsIgnoreCase("help")) {
					getLogger().info(ChatColor.BLUE + "/cpb help - Lists help commands.");
					getLogger().info(ChatColor.BLUE + "/cpb give <playername> <bookname> - Gives book to player");
				} else if (args[0].equalsIgnoreCase("give")) {
					Player p;
					Plugin plugin;
					plugin = (Plugin)getServer().getPluginManager().getPlugin("CopyBook");
					if ((p = plugin.getServer().getPlayer(args[1])) != null) {
						String bName = null;
						 if(args.length == 2) {
							 getLogger().info(ChatColor.RED + "You must supply a book name.");
						 } else if (args.length == 3) {
							 bName = args[2].toLowerCase();
						 } else if (args.length >= 4) {
							 bName = args[2].toLowerCase() + "_";
							for (int i=3;i<args.length;i++) {
								String addon;
								if (i == args.length-1)
									addon = args[i];
								else {
									addon = args[i] + "_";
								}
								bName += addon.toLowerCase();
							}
						 }
						 config = getConfig();
						 String ext = config.getString("extension");
						 ItemStack item = Functions.loadBook(bName, ext, false);
						 p.getInventory().addItem(item);
						 p.sendMessage("You recieve a book.");
					} else {
						getLogger().info(ChatColor.RED + "Player is offline.");
					}
				} else {
					getLogger().info(ChatColor.RED + "Too few arguements.");
					getLogger().info(ChatColor.BLUE + "Type cpb help for more information.");
				}
				return true;
			} else {
				return false;
			}
		} 
		if (cmd.getName().equalsIgnoreCase("cpb")) {
			Commands.commandCopy((Player) sender, args, econ);
			return true;
		} else {
			return false;
		}
	}
	
}
