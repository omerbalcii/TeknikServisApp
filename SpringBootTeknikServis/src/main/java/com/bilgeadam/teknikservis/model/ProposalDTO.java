package com.bilgeadam.teknikservis.model;

public class ProposalDTO {
    private String productName;
    private String note;
    private float price;

    public ProposalDTO() {
    }

    public ProposalDTO(String productName, String note, float price) {
        this.productName = productName;
        this.note = note;
        this.price = price;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public float getPrice() {
        return price;
    }

    public void setPrice(float price) {
        this.price = price;
    }
}
