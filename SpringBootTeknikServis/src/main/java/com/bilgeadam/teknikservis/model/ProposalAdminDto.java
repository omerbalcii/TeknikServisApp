package com.bilgeadam.teknikservis.model;


public class ProposalAdminDto {
    private long id;
    private String note;
    private long price;
    private String status;
    private long user_id;
    private long product_id;
    private String username;
    private String name;

    public ProposalAdminDto() {
    }

    public ProposalAdminDto(long id, String note, long price, String status, long user_id, long product_id, String username, String name) {
        this.id = id;
        this.note = note;
        this.price = price;
        this.status = status;
        this.user_id = user_id;
        this.product_id = product_id;
        this.username = username;
        this.name = name;
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

    public long getPrice() {
        return price;
    }

    public void setPrice(long price) {
        this.price = price;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public long getUser_id() {
        return user_id;
    }

    public void setUser_id(long user_id) {
        this.user_id = user_id;
    }

    public long getProduct_id() {
        return product_id;
    }

    public void setProduct_id(long product_id) {
        this.product_id = product_id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "ProposalDto{" +
                "id=" + id +
                ", note='" + note + '\'' +
                ", price=" + price +
                ", status=" + status +
                ", user_id=" + user_id +
                ", product_id=" + product_id +
                ", username='" + username + '\'' +
                ", productName='" + name + '\'' +
                '}';
    }
}
