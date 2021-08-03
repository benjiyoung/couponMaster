package Beans;

import java.time.LocalDate;

public class Coupon {
    private int id;
    private int companyID;
    private Category category;
    private String title;
    private String description;
    private LocalDate startDate;
    private LocalDate endDate;
    private int amount;
    private double price;
    private String image;


    //Constructors

    /**
     * full coupon constructor for creating a coupon
     * @param id The coupon's id
     * @param companyID The companyId that the coupons belong to
     * @param category The category that the coupon under
     * @param title  Title id the coupon
     * @param description coupon's description
     * @param startDate  The coupon's start date
     * @param endDate The coupon end date
     * @param amount  coupon's amount
     * @param price The price of the coupon
     * @param image  coupons image
     */
    public Coupon(int id, int companyID, Category category, String title, String description, LocalDate startDate, LocalDate endDate, int amount, double price, String image) {
        this.id = id;
        this.companyID = companyID;
        this.category = category;
        this.title = title;
        this.description = description;
        this.startDate = startDate;
        this.endDate = endDate;
        this.amount = amount;
        this.price = price;
        this.image = image;
    }

    /**
     *  coupon constructor for creating a coupon without id (id is auto increment)
     * @param companyID -The companyId that the coupons belong to
     * @param category - The category that the coupon under
     * @param title -  Title id the coupon
     * @param description - coupon's description
     * @param startDate  The coupon's start date
     * @param endDate The coupon end date
     * @param amount  coupon's amount
     * @param price The price of the coupon
     * @param image coupons image
     */
    public Coupon(int companyID, Category category, String title, String description, LocalDate startDate, LocalDate endDate, int amount, double price, String image) {
        this.companyID = companyID;
        this.category = category;
        this.title = title;
        this.description = description;
        this.startDate = startDate;
        this.endDate = endDate;
        this.amount = amount;
        this.price = price;
        this.image = image;
    }

    /**
     * Default Coupon constructor(Beans)
     */
    public Coupon() {}

    //Getters & Setters
    public int getId() {
        return id;
    }

    private void setId(int id) {
        this.id = id;
    }

    public int getCompanyID() {
        return companyID;
    }

    private void setCompanyID(int companyID) {
        this.companyID = companyID;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }

    public LocalDate getEndDate() {
        return endDate;
    }

    public void setEndDate(LocalDate endDate) {
        this.endDate = endDate;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    @Override
    public String toString() {
        return "Coupon{" +
                "id=" + id +
                ", companyID=" + companyID +
                ", category=" + category +
                ", title='" + title + '\'' +
                ", description='" + description + '\'' +
                ", startDate=" + startDate +
                ", endDate=" + endDate +
                ", amount=" + amount +
                ", price=" + price +
                ", image='" + image + '\'' +
                '}';
    }
}
