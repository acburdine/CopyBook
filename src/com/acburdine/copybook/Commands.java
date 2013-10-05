package com.acburdine.copybook;

import java.util.ArrayList;
import java.util.List;

import net.milkbowl.vault.economy.Economy;
import net.milkbowl.vault.economy.EconomyResponse;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.Chest;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.RegisteredServiceProvider;

public class Commands {
	
	private static PluginManager plugin = Bukkit.getServer().getPluginManager();
	private static Plugin name = plugin.getPlugin("CopyBook");
	private static FileConfiguration config = name.getConfig();
	private static Economy econ = null;
	
	public static boolean setUpEcon() {
		if (plugin.getPlugin("Vault") == null) {
			return false;
		}
		RegisteredServiceProvider<Economy> rsp = Bukkit.getServer().getServicesManager().getRegistration(Economy.class);
		if (rsp == null) {
			return false;
		}
		econ = rsp.getProvider();
		return econ != null;
	}
	
	public static boolean isInt(String input) {
		try {
			Integer.parseInt(input);
			return true;
		} catch (NumberFormatException e) {
			return false;
		}
	}
	
	@SuppressWarnings("deprecation")
	public static void commandCopy(Player sender, String[] args, Economy economy) {
		if (economy != null) {
			econ = economy;
		}
		if(args.length == 0) {
			sender.sendMessage(ChatColor.RED + "Type /cpb help for more information.");
			return;
		}
		if (args[0].equalsIgnoreCase("help")) {
			if (!sender.hasPermission("copybook.help")) {
				sender.sendMessage(ChatColor.RED + "You don't have permission to use this command.");
			} else {
				if(args.length == 1) {
					sender.sendMessage(ChatColor.BLUE + "------------CopyBook Commands Page 1 of 3 -------------");
					sender.sendMessage(ChatColor.WHITE + "/cpb copy [amount] - " + ChatColor.DARK_AQUA + "Copies the book in your hand into your inventory.");
					sender.sendMessage(ChatColor.WHITE + "/cpb chest [amount] - " + ChatColor.DARK_AQUA + "Saves book in your hand into the chest you are looking at.");
					sender.sendMessage(ChatColor.WHITE + "/cpb edit <bookname> - " + ChatColor.DARK_AQUA + "Loads an editable copy of the specified book.");
					sender.sendMessage(ChatColor.WHITE + "/cpb give <player> [amount] - " + ChatColor.DARK_AQUA + "Gives the specified player a copy of the book in your inventory.");
					sender.sendMessage(ChatColor.WHITE + "/cpb help - " + ChatColor.DARK_AQUA + "Displays the list of CopyBook commands");
					sender.sendMessage(ChatColor.GREEN + "Type /cpb help 2 for the next page of commands.");
				} else if (args.length >= 2) {
					if (isInt(args[1])) {
						int page = Integer.parseInt(args[1]);
						switch(page) {
							case 1:
								sender.sendMessage(ChatColor.BLUE + "------------CopyBook Commands Page 1 of 3 -------------");
								sender.sendMessage(ChatColor.WHITE + "/cpb copy [amount] - " + ChatColor.DARK_AQUA + "Copies the book in your hand into your inventory.");
								sender.sendMessage(ChatColor.WHITE + "/cpb chest [amount] - " + ChatColor.DARK_AQUA + "Saves book in your hand into the chest you are looking at.");
								sender.sendMessage(ChatColor.WHITE + "/cpb edit <bookname> - " + ChatColor.DARK_AQUA + "Loads an editable copy of the specified book.");
								sender.sendMessage(ChatColor.WHITE + "/cpb give <player> [amount] - " + ChatColor.DARK_AQUA + "Gives the specified player a copy of the book in your inventory.");
								sender.sendMessage(ChatColor.WHITE + "/cpb help - " + ChatColor.DARK_AQUA + "Displays the list of CopyBook commands");
								sender.sendMessage(ChatColor.GREEN + "Type /cpb help 2 for the next page of commands.");
								break;
							case 2:
								sender.sendMessage(ChatColor.BLUE + "------------CopyBook Commands Page 2 of 3 -------------");
								sender.sendMessage(ChatColor.WHITE + "/cpb load <bookname> - " + ChatColor.DARK_AQUA + "Loads book from file and places it in your inventory.");
								sender.sendMessage(ChatColor.WHITE + "/cpb modify - " + ChatColor.DARK_AQUA + "Replaces the written book in your hand with an editable copy.");
								sender.sendMessage(ChatColor.WHITE + "/cpb reload - " + ChatColor.DARK_AQUA + "Reloads the config and the plugin.");
								sender.sendMessage(ChatColor.WHITE + "/cpb save [price] - " + ChatColor.DARK_AQUA + "Saves book in your hand to file.");
								sender.sendMessage(ChatColor.WHITE + "/cpb vault - " + ChatColor.DARK_AQUA + "Toggles the Vault capability of this plugin");
								sender.sendMessage(ChatColor.GREEN + "Type /cpb help 3 for the next page of commands.");
								break;
							case 3:
								sender.sendMessage(ChatColor.BLUE + "------------CopyBook Commands Page 3 of 3 -------------");
								sender.sendMessage(ChatColor.WHITE + "/cpb price [command|toggle|set] [command name|price] - " + ChatColor.DARK_AQUA + "Toggles the price functionality, sets the price, or sets commands exempt from the price.");
								break;
							default:
								sender.sendMessage(ChatColor.BLUE + "------------CopyBook Commands Page 1 of 3 -------------");
								sender.sendMessage(ChatColor.WHITE + "/cpb copy [amount] - " + ChatColor.DARK_AQUA + "Copies the book in your hand into your inventory.");
								sender.sendMessage(ChatColor.WHITE + "/cpb chest [amount] - " + ChatColor.DARK_AQUA + "Saves book in your hand into the chest you are looking at.");
								sender.sendMessage(ChatColor.WHITE + "/cpb edit <bookname> - " + ChatColor.DARK_AQUA + "Loads an editable copy of the specified book.");
								sender.sendMessage(ChatColor.WHITE + "/cpb give <player> [amount] - " + ChatColor.DARK_AQUA + "Gives the specified player a copy of the book in your inventory.");
								sender.sendMessage(ChatColor.WHITE + "/cpb help - " + ChatColor.DARK_AQUA + "Displays the list of CopyBook commands");
								sender.sendMessage(ChatColor.GREEN + "Type /cpb help 2 for the next page of commands.");
						}
					} else {
						sender.sendMessage(ChatColor.BLUE + "------------CopyBook Commands-------------");
						sender.sendMessage(ChatColor.WHITE + "/cpb copy [amount] - " + ChatColor.DARK_AQUA + "Copies the book in your hand into your inventory.");
						sender.sendMessage(ChatColor.WHITE + "/cpb chest [amount] - " + ChatColor.DARK_AQUA + "Saves book in your hand into the chest you are looking at.");
						sender.sendMessage(ChatColor.WHITE + "/cpb edit <bookname> - " + ChatColor.DARK_AQUA + "Loads an editable copy of the specified book.");
						sender.sendMessage(ChatColor.WHITE + "/cpb give <player> [amount] - " + ChatColor.DARK_AQUA + "Gives the specified player a copy of the book in your inventory.");
						sender.sendMessage(ChatColor.WHITE + "/cpb help - " + ChatColor.DARK_AQUA + "Displays the list of CopyBook commands");
						sender.sendMessage(ChatColor.GREEN + "Type /cpb help 2 for the next page of commands.");
					}
				}
			}
		}
		else if(args[0].equalsIgnoreCase("give")) {
			if (!sender.hasPermission("copybook.give")) {
				sender.sendMessage(ChatColor.RED + "You don't have permission to use this command.");
			} else {
				if(args.length == 2) {
					if (sender.getItemInHand().getType() == Material.WRITTEN_BOOK) {
						Player p;
						if ((p = name.getServer().getPlayer(args[1])) != null) {
								if (!priceHandling(args[0], sender, 0)) {
									return;
								}
								ItemStack item = new Book(sender.getItemInHand()).createItem();
								p.getInventory().addItem(item);
								p.sendMessage("You recieve some book......");
								sender.sendMessage("Book successfully sent!");
						}
						else {	
							sender.sendMessage(ChatColor.RED + "Player is offline.");
						}
					} else {
						sender.sendMessage(ChatColor.RED + "This isn't a book!");
					}
				} else if(args.length == 3) {
					if (sender.getItemInHand().getType() == Material.WRITTEN_BOOK) {
						Player p;
						if ((p = name.getServer().getPlayer(args[1])) != null) {
							if (!isInt(args[2])) {
								sender.sendMessage(ChatColor.RED + "Second arguement must be an integer.");
							} else {
								if (!priceHandling(args[0], sender, 0)) {
									return;
								}
								int bookNumber = Integer.parseInt(args[2]);
								ItemStack item = new Book(sender.getItemInHand()).createItem();
								item.setAmount(bookNumber);
								p.getInventory().addItem(item);
								p.sendMessage("You recieve some book......");
								sender.sendMessage("Book successfully sent!");
							}
						} else {
							sender.sendMessage(ChatColor.RED + "Player is offline");
						}
					} else {
						sender.sendMessage(ChatColor.RED + "This isn't a book!");
					}
				}
				else if (args.length == 1) {
					sender.sendMessage(ChatColor.DARK_AQUA + "Too few arguements");
					sender.sendMessage(ChatColor.WHITE + "Usage: /cpb give <player> [amount]");
				}
				else if (args.length >= 4) {
					sender.sendMessage(ChatColor.DARK_AQUA + "Too many arguements");
					sender.sendMessage(ChatColor.WHITE + "Usage: /cpb give <player> [amount]");
				}
			}
		}
		else if (args[0].equalsIgnoreCase("save")) {
			if (!sender.hasPermission("copybook.save")) {
				sender.sendMessage(ChatColor.RED + "You don't have permission to use this command.");
			} else {
				String ext = config.getString("extension");
				String bName;
				ItemStack bookItem = sender.getItemInHand();
				if (bookItem.getType() == Material.WRITTEN_BOOK) {
					if (!priceHandling(args[0], sender, 0)) {
						return;
					}
					Book book = new Book(bookItem);
					bName = book.getTitle().replaceAll("\\s", "_").toLowerCase();
					Functions.saveBook(bName, ext, book.getAuthor(), book.getTitle(), book.getPages());
					sender.sendMessage(ChatColor.GREEN + "Book Successfully Saved!");
				} else {
					sender.sendMessage(ChatColor.RED + "This isn't a book!");
				}
			}
		}
		else if(args[0].equalsIgnoreCase("load")) {
			if (!sender.hasPermission("copybook.load")) {
				sender.sendMessage(ChatColor.RED + "You don't have permission to use this command.");
			} else {
				boolean load = config.getBoolean("book_in_hand.load", false);
				if (load) {
					if (sender.getItemInHand().getType() == Material.BOOK_AND_QUILL) {
						if (!priceHandling(args[0], sender, 0)) {
							return;
						}
						Functions.initLoad(sender, args, false, true);
					} else {
						if (sender.hasPermission("copybook.bypass")) {
							if (!config.getBoolean("price.bypass", true)) {
								if (!priceHandling(args[0], sender, 0)) {
									return;
								}
							}
							Functions.initLoad(sender, args, false, false);
						} else {
							sender.sendMessage(ChatColor.RED + "You must have a book and quill in your hand in order to use this command.");
						}
					}
				} else {
					if (!priceHandling(args[0], sender, 0)) {
						return;
					}
					Functions.initLoad(sender, args, false, false);
				}
			}
		}
		else if (args[0].equalsIgnoreCase("copy")) {
			if (!sender.hasPermission("copybook.load")) {
				sender.sendMessage(ChatColor.RED + "You don't have permission to use this command.");
			} else {
				if (args.length == 1) {
					if(sender.getItemInHand().getType() == Material.WRITTEN_BOOK) {
						if (!priceHandling(args[0], sender, 0)) {
							return;
						}
						Book book = new Book(sender.getItemInHand());
						ItemStack newItem = book.createItem();
						sender.getInventory().addItem(newItem);
						sender.sendMessage(ChatColor.GREEN + "Book has been copied!");
					} else {
							sender.sendMessage(ChatColor.RED + "This isn't a book!");
					}
				} else if (args.length == 2) {
					if(sender.getItemInHand().getType() == Material.WRITTEN_BOOK) {
						if (!isInt(args[1])) {
							sender.sendMessage(ChatColor.RED + "Second arguement must be an integer.");
						} else {
							if (!priceHandling(args[0], sender, 0)) {
								return;
							}
							int numberOfBooks = Integer.parseInt(args[1]);
							ItemStack item = new Book(sender.getItemInHand()).createItem();
							item.setAmount(numberOfBooks);
							sender.getInventory().addItem(item);
							sender.sendMessage(ChatColor.GREEN + "Books have been copied!");
						}
					} else {
						sender.sendMessage(ChatColor.RED + "This isn't a book!");
					}
				}
			}
		}
		else if (args[0].equalsIgnoreCase("chest")) {
			if (!sender.hasPermission("copybook.chest")) {
				sender.sendMessage(ChatColor.RED + "You don't have permission to use this command.");
			} else {
				Block b = sender.getTargetBlock(null, 100);
				if (b.getType() == Material.CHEST) {
					ItemStack item = sender.getItemInHand();
					if (item.getType() == Material.WRITTEN_BOOK) {
						ItemStack book = new Book(item).createItem();
						Chest c = (Chest) b.getState();
						Inventory inv = c.getBlockInventory();
						if (args.length == 1) {
							inv.addItem(book);
							sender.sendMessage(ChatColor.GREEN + "Book has been placed in chest!");
						} else if (args.length == 2) {
							if (!isInt(args[1])) {
								sender.sendMessage("Second arguement must be an integer.");
							} else {
								if (!priceHandling(args[0], sender, 0)) {
									return;
								}
								int numBook = Integer.parseInt(args[1]);
								for (int i=0;i<numBook;i++) {
									inv.addItem(book);
								}
								sender.sendMessage(ChatColor.GREEN + "Books have been placed in chest!");
							}
						} else {
							sender.sendMessage(ChatColor.RED + "Something went wrong. Try again?");
						}
					} else {
						sender.sendMessage(ChatColor.RED + "This isn't a book!");
					}
				} else {
					sender.sendMessage(ChatColor.RED + "You must be looking at a chest to use this correctly");
				}
			}
		}
		else if (args[0].equalsIgnoreCase("reload")) {
			if (!sender.hasPermission("copybook.reload")) {
				sender.sendMessage(ChatColor.RED + "You don't have permission to use this command.");
			} else {
				name.reloadConfig();
				plugin.disablePlugin(name);
				plugin.enablePlugin(name);
				sender.sendMessage(ChatColor.GREEN + "Plugin successfully reloaded!");
			}
		}
		else if (args[0].equalsIgnoreCase("vault")) {
			if (!sender.hasPermission("copybook.vault")) {
				sender.sendMessage(ChatColor.RED + "You don't have permission to use this command.");
			} else {
				boolean bool = config.getBoolean("vault", false);
				if (!bool) {
					if (!setUpEcon()) {
						sender.sendMessage(ChatColor.DARK_RED + "ERROR: Vault instance not found. Please download Vault.");
					} else {
						bool = !bool;
						config.set("vault", bool);
						name.saveConfig();
						sender.sendMessage(ChatColor.GREEN + "Vault support has been enabled!");
					}
				} else {
					bool = !bool;
					config.set("vault", bool);
					name.saveConfig();
					sender.sendMessage(ChatColor.GREEN + "Vault support has been disabled.");
				}
			}
		}
		else if (args[0].equalsIgnoreCase("modify")) {
			if (!sender.hasPermission("copybook.modify")) {
				sender.sendMessage(ChatColor.RED + "You don't have permission to use this command.");
			} else {
				if (sender.getItemInHand().getType() == Material.WRITTEN_BOOK) {
					if (!priceHandling(args[0], sender, 0)) {
						return;
					}
					ItemStack item = new Book(sender.getItemInHand()).editBook();
					sender.setItemInHand(item);
					sender.sendMessage(ChatColor.GREEN + "Book is now modifiable!");
				}
				else {
					sender.sendMessage(ChatColor.RED + "This isn't a book!");
				}
			}
		}
		else if (args[0].equalsIgnoreCase("edit")) {
			if (!sender.hasPermission("copybook.edit")) {
				sender.sendMessage(ChatColor.RED + "You don't have permission to use this command.");
			} else {	
				boolean edit = config.getBoolean("book_in_hand.edit", false);
				if (edit) {
					if (sender.getItemInHand().getType() == Material.BOOK_AND_QUILL) {
						if (!priceHandling(args[0], sender, 0)) {
							return;
						}
						Functions.initLoad(sender, args, true, true);
					} else {
						if (sender.hasPermission("copybook.bypass")) {
							if (!config.getBoolean("price.bypass", true)) {
								if (!priceHandling(args[0], sender, 0)) {
									return;
								}
							}
							Functions.initLoad(sender, args, true, false);
						} else {
							sender.sendMessage(ChatColor.RED + "You must have a book and quill in your hand in order to use this command.");
						}
					}
				} else {
					if (!priceHandling(args[0], sender, 0)) {
						return;
					}
					Functions.initLoad(sender, args, true, false);
				}
			}
		}
		else if (args[0].equalsIgnoreCase("price")) {
			if (!sender.hasPermission("copybook.price")) {
				sender.sendMessage(ChatColor.RED + "You don't have permission to use this command.");
			} else {
				if (args.length == 1) {
					boolean bool = config.getBoolean("price.enabled" , false);
					config.set("price.enabled", !bool);
					if (bool)
						sender.sendMessage(ChatColor.GREEN + "Price functionality has been disabled.");
					else
						sender.sendMessage(ChatColor.GREEN + "Price functionality has been enabled.");
				} else {
					if (args[1].equalsIgnoreCase("command")) {
						if (args.length == 2) {
							sender.sendMessage(ChatColor.RED + "Incorrect Usage");
							sender.sendMessage(ChatColor.RED + "Correct usage: /cpb price [command|toggle|set] [command name|price]");
						} else {
							List<String> cmdList = new ArrayList<String>();
							for (int i=2;i<args.length; i++) {
								cmdList.add(args[i]);
							}
							config.set("price.exempt_commands", cmdList);
							name.saveConfig();
							sender.sendMessage(ChatColor.GREEN + "Command exemptions set.");
						}
					} else if (args[1].equalsIgnoreCase("set")) {
						if (args.length == 2) {
							sender.sendMessage(ChatColor.RED + "Incorrect Usage");
							sender.sendMessage(ChatColor.RED + "Correct usage: /cpb price [command|toggle|set] [command name|price]");
						} else {
							if (isInt(args[2])) {
								int price = Integer.parseInt(args[2]);
								config.set("price.default_price", price);
								sender.sendMessage(ChatColor.GREEN + "Default price has been set to " + price + " coins.");
							} else {
								sender.sendMessage(ChatColor.RED + "Incorrect Usage");
								sender.sendMessage(ChatColor.RED + "Correct usage: /cpb price [command|toggle|set] [command name|price]");
							}
						}
					} else {
						sender.sendMessage(ChatColor.RED + "Incorrect Usage");
						sender.sendMessage(ChatColor.RED + "Correct usage: /cpb price [command|toggle|set] [command name|price]");
					}
				}
			}
		}
		else {
			sender.sendMessage(ChatColor.RED + "Command not found. Type /cpb help for more info.");
		}
	}
	public static boolean commandExempt(String cmdName) {
		List<String> exComm = config.getStringList("price.exempt_commands");
		String[] exCommArr = exComm.toArray(new String[0]);
		for (int i=0; i<exCommArr.length; i++) {
			if (cmdName.equalsIgnoreCase(exCommArr[i]))
				return true;
		}
		return false;
	}
	public static boolean priceHandling(String cmdName, Player p, double inPrice) {
		if (config.getBoolean("price.enabled", false)) {
			if (!config.getBoolean("vault", false)) {
				p.sendMessage(ChatColor.RED + "Vault is currently not enabled. Please enable Vault.");
				return false;
			}
			if (!commandExempt(cmdName)) {
				double price = 0;
				if (inPrice == 0)
					price = config.getDouble("price.default_price", 15.0);
				else
					price = inPrice;
				int priceInt = (int) price;
				if (econ.has(p.getName(), price)) {
					EconomyResponse trans = econ.withdrawPlayer(p.getName(), price);
					if (trans.transactionSuccess()) {
						p.sendMessage(ChatColor.GREEN + "" + priceInt + " coins were removed from your wallet.");
						return true;
					} else {
						p.sendMessage(ChatColor.RED + "Withdrawal Failed!");
						return false;
					}
				} else {
					p.sendMessage("You don't have enough money to execute this command. You need " + priceInt + " coins.");
					return false;
				}
			} else {
				return true;
			}
		} else {
			return true;
		}
	}
}
