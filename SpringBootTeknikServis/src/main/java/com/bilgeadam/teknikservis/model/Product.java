package com.bilgeadam.teknikservis.model;

public class Product 
{
	
	private long id;
	private String name;

	
	public Product(long id, String name) {
		
		this.id = id;
		this.name = name;
	}


	public Product() 
	{
		
	}
	
	
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}


	@Override
	public String toString() {
		return "Product [id=" + id + ", name=" + name + "]";
	}

	
}
