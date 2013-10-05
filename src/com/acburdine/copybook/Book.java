package com.acburdine.copybook;

import java.util.List;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.BookMeta;

public class Book {
	
	public Book(ItemStack item) {
		ItemStack book = item;
		BookMeta bData = (BookMeta) book.getItemMeta();
		if(bData.hasAuthor()) 
			this.author = bData.getAuthor();
		if(bData.hasTitle())
			this.title = bData.getTitle();
		if(bData.hasPages())
			this.pages = bData.getPages();
	}
	
	public Book(String title, String author, List<String> pages) {
		this.author = author;
		this.title = title;
		this.pages = pages;
		
	}
	
	public String getAuthor() {
        return author;
    }
	
	public void setAuthor(String sAuthor) {
		author = sAuthor;
	}
	
	public String getTitle() {
		return title;
	}
	
	public List<String> getPages() {
		return pages;
	}
	
	public ItemStack createItem() {
	    ItemStack is = new ItemStack(Material.WRITTEN_BOOK);
	    BookMeta bm = (BookMeta) is.getItemMeta();
	    if(!title.isEmpty()) {
	        bm.setTitle(title);
	    }
	    if(!author.isEmpty()) {
	        bm.setAuthor(author);
	    }
	    if(!(pages.size() == 0)) {
	        bm.setPages(pages);
	    }
	    is.setItemMeta(bm);
	    return is;
	}
	
	public ItemStack editBook() {
		ItemStack is = new ItemStack(Material.BOOK_AND_QUILL);
		BookMeta bm = (BookMeta) is.getItemMeta();
		if (!(pages.size() == 0)) {
			bm.setPages(pages);
		}
		is.setItemMeta(bm);
		return is;
	}
	private String author;
	private String title;
	private List<String> pages;
}
