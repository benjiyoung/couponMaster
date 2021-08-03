package Beans;

import java.util.ArrayList;
import java.util.List;

public class Company {
    private int id;
    private String name;
    private String email;
    private String password;
    private ArrayList<Coupon> coupons;


    /**
     * full Constructor Company for creating a company
     *
     * @param id   The Company's id
     * @param name  The company's name
     * @param email  The Company's email
     * @param password  The company's password
     * @param coupons The company's list of coupons
     */

    public Company(int id, String name, String email, String password, ArrayList<Coupon> coupons) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.password = password;
        this.coupons = coupons;
    }

    /**
     * company Constructor for creating a company object
     *
     * @param name   The company's name
     * @param email The Company's email
     * @param password The company's password
     */
    public Company(String name, String email, String password) {
        this.name = name;
        this.email = email;
        this.password = password;
        this.coupons = new ArrayList<>();
    }

    /**
     * Default Company constructor (Beans)
     */
    public Company() {
    }

    //Getters & Setters
    public int getId() {
        return id;
    }

    private void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    private void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public List<Coupon> getCoupons() {
        return coupons;
    }

    public void setCoupons(ArrayList<Coupon> coupons) {
        this.coupons = coupons;
    }

    //Methods
    @Override
    public String toString() {
        return "Company{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", email='" + email + '\'' +
                ", password='" + password + '\'' +
                ", coupons=" + coupons +
                '}';
    }
}
