package com.bilgeadam.teknikservis.model;

import java.util.Date;

public class Booking 
{
	private long id;
	private String note;
	private Date booking_date;
	private String status;
	private long service_id;
	private long user_id;
	
	
	public Booking(long id, String note, Date booking_date, String status, long service_id, long user_id) {
		
		this.id = id;
		this.note = note;
		this.booking_date = booking_date;
		this.status = status;
		this.service_id = service_id;
		this.user_id = user_id;
	}

	public Booking() 
	{
		
	}
	
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public String getNote() {
		return note;
	}
	public void setNote(String note) {
		this.note = note;
	}
	public Date getBooking_date() {
		return booking_date;
	}
	public void setBooking_date(Date booking_date) {
		this.booking_date = booking_date;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public long getService_id() {
		return service_id;
	}
	public void setService_id(long service_id) {
		this.service_id = service_id;
	}
	public long getUser_id() {
		return user_id;
	}
	public void setUser_id(long user_id) {
		this.user_id = user_id;
	}

	@Override
	public String toString() {
		return "Booking [id=" + id + ", note=" + note + ", booking_date=" + booking_date + ", status=" + status
				+ ", service_id=" + service_id + ", user_id=" + user_id + "]";
	}
	

}
