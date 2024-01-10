package com.bilgeadam.teknikservis.model;

import org.springframework.data.annotation.Id;

public class SaleDTO {
@Id
	private Long id;
    private Long price;
    private String note;
	private String product;
	private Long product_id;

	public SaleDTO() {
	}

	public SaleDTO(Long id, Long price, String note, String product, Long product_id) {
		this.id = id;
		this.price = price;
		this.note = note;
		this.product = product;
		this.product_id = product_id;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getPrice() {
		return price;
	}

	public void setPrice(Long price) {
		this.price = price;
	}

	public String getNote() {
		return note;
	}

	public void setNote(String note) {
		this.note = note;
	}

	public String getProduct() {
		return product;
	}

	public void setProduct(String product) {
		this.product = product;
	}

	public Long getProduct_id() {
		return product_id;
	}

	public void setProduct_id(Long product_id) {
		this.product_id = product_id;
	}

	@Override
	public String toString() {
		return "SaleDTO{" +
				"id=" + id +
				", price='" + price + '\'' +
				", note='" + note + '\'' +
				", product='" + product + '\'' +
				'}';
	}
}
