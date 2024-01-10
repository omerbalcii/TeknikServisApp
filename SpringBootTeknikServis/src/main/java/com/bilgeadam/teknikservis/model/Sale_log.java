package com.bilgeadam.teknikservis.model;

import java.util.Date;

public class Sale_log 
{
	private long id;
	private Date sale_log_date;
	private String credit_card;
	private long sale_id;
	private long user_id;
	
	
	public Sale_log(long id, Date sale_log_date, String credit_card, long sale_id, long user_id) 
	{
		
		this.id = id;
		this.sale_log_date = sale_log_date;
		this.credit_card = credit_card;
		this.sale_id = sale_id;
		this.user_id = user_id;
	}

	public Sale_log() 
	{
		
	}
	
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public Date getSale_log_date() {
		return sale_log_date;
	}
	public void setSale_log_date(Date sale_log_date) {
		this.sale_log_date = sale_log_date;
	}
	public String getCredit_card() {
		return credit_card;
	}
	public void setCredit_card(String credit_card) {
		this.credit_card = credit_card;
	}
	public long getSale_id() {
		return sale_id;
	}
	public void setSale_id(long sale_id) {
		this.sale_id = sale_id;
	}
	public long getUser_id() {
		return user_id;
	}
	public void setUser_id(long user_id) {
		this.user_id = user_id;
	}

	@Override
	public String toString() {
		return "Sale_log [id=" + id + ", sale_log_date=" + sale_log_date + ", credit_card=" + credit_card + ", sale_id="
				+ sale_id + ", user_id=" + user_id + "]";
	}
	
	
	
}
