package com.bilgeadam.teknikservis.model;

public class Service 
{
	private long id;
	private String description;
	private int duration;
	private int laptop;
	private int desktop;
	private int mac;
	
	public Service () 
	{
		
	}
	
	public Service(long id, String description, int duration, int laptop, int desktop, int mac) 
	{
		
		this.id = id;
		this.description = description;
		this.duration = duration;
		this.laptop = laptop;
		this.desktop = desktop;
		this.mac = mac;
	}


	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public int getDuration() {
		return duration;
	}
	public void setDuration(int duration) {
		this.duration = duration;
	}
	public int getLaptop() {
		return laptop;
	}
	public void setLaptop(int laptop) {
		this.laptop = laptop;
	}
	public int getDesktop() {
		return desktop;
	}
	public void setDesktop(int desktop) {
		this.desktop = desktop;
	}
	public int getMac() {
		return mac;
	}
	public void setMac(int mac) {
		this.mac = mac;
	}

	@Override
	public String toString() {
		return "Service [id=" + id + ", description=" + description + ", duration=" + duration + ", laptop=" + laptop
				+ ", desktop=" + desktop + ", mac=" + mac + "]";
	}
	
	

}
