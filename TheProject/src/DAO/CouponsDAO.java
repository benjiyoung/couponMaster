package DAO;

import Beans.Coupon;

import java.util.ArrayList;

public interface CouponsDAO {
    /**
     * Adding a given coupon as a row in the coupons table in the database
     * @param coupon a coupon object to insert into the coupons table
     * @return Indication whether the coupon was added to the table or not
     */
    boolean addCoupon(Coupon coupon);
    /**
     * Updating a coupon in the coupons table by it's ID, and setting it as the given coupon object
     * @param coupon The updated object of the coupon to save in the coupons table
     * @return Indication whether the coupon was updated in the table or not
     */
    boolean updateCoupon(Coupon coupon);
    /**
     * Deleting the row of the given coupon in the coupons table
     * @param couponID The ID of the coupon for deletion
     * @return Indication whether the coupon was deleted from the table or not
     */
    boolean deleteCoupon(int couponID);
    /**
     * Getting a coupon from the coupons table in the database
     * @param couponID The ID of the coupon to select from the table
     * @return Object of the coupon with the given ID
     */
    Coupon getOneCoupon(int couponID);
    /**
     * Getting all of the coupons from the coupons table in the database
     * @return ArrayList of coupon objects representing all of the coupons in the table
     */
    ArrayList<Coupon> getAllCoupons();
    /**
     * Creating a new row in the table CUSTOMERS_VS_COUPONS to represent a coupon purchase by a customer
     * @param customerID The purchasing customer's ID in the database
     * @param couponID The purchased coupon's ID in the database
     * @return Indication whether the purchase was inserted into the CUSTOMERS_VS_COUPONS table ot not
     */
    boolean addCouponPurchase(int customerID, int couponID);
    /**
     * Deleting the purchase's row in the table CUSTOMERS_VS_COUPONS
     * @param customerID The purchasing customer's ID in the database
     * @param couponID The purchased coupon's ID in the database
     * @return Indication whether the purchase was deleted from the CUSTOMERS_VS_COUPONS table or not
     */
    boolean deleteCouponPurchase(int customerID, int couponID);
}