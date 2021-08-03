package Facades;

import Beans.Category;
import Beans.Coupon;
import Beans.Customer;
import Exceptions.CouponMasterException;

import java.time.LocalDate;
import java.util.ArrayList;

public class CustomerFacade extends ClientFacade {
    private int customerID;

    /**
     * An empty CTOR that creates an instance of a customer facade
     */
    public CustomerFacade() {
    }

    public int getCustomerID() {
        return customerID;
    }

    /**
     * A method that overrides its parent login method, and validates that the credentials given are valid via
     the isCustomerExists method and assigns its customer ID for later use.
     * @param email customer's email
     * @param password customer's password
     * @return An indication whether the action was successful or not
     */
    @Override
    public boolean login(String email, String password) {
        if (customersDBDAO.isCustomerExists(email, password) > 0) {
            customerID = customersDBDAO.customerIDLogin(email, password);
            return true;
        }
        return false;
    }

    /**
     * A method that allows the customer user to purchase a coupon under a set of conditions. throws a custom
     exception is all the codintions are not met. if they do, it deducts the amount if relevant coupons by 1.
     * @param coupon An instance of a coupon to be purchased
     * @return An indication whether the action was successful or not
     * @throws CouponMasterException
     */

    public boolean purchaseCoupon(Coupon coupon) throws CouponMasterException {
        if (coupon.getAmount() < 1) {
            throw new CouponMasterException("This coupon is out of stock!");
        }
        ArrayList<Coupon> coupons = customersDBDAO.getOneCustomer(customerID).getCoupons();
        for (Coupon item : coupons) {
            if (item.equals(coupon)) {
                throw new CouponMasterException("You have already purchased this coupon!");
            } else if (coupon.getEndDate().isBefore(LocalDate.now())) {
                throw new CouponMasterException("This coupon is expired!");
            }
        }
        coupon.setAmount(coupon.getAmount() - 1);
        couponDBDAO.updateCoupon(coupon);
        return couponDBDAO.addCouponPurchase(customerID, coupon.getId());
    }

    /**
     * A method that allows the customer user to fetch all of its coupons, or throws a custom exception if there are none
     * @return
     * @throws CouponMasterException
     */
    public ArrayList<Coupon> getAllCustomerCoupons() throws CouponMasterException {
        if(couponDBDAO.getAllCustomerCoupons(customerID).isEmpty()){
            throw new CouponMasterException("You do not have coupons on your list yet");
        }
        return couponDBDAO.getAllCustomerCoupons(customerID);
    }

    /**
     * A method that allows the customer to fetch all of his coupon under a given category, or throws a custom
     exception if there are none
     * @param category The category which all of the coupons related to will be returned
     * @return
     * @throws CouponMasterException
     */
    public ArrayList<Coupon> getAllCategoryCoupons(Category category) throws CouponMasterException {
        if(couponDBDAO.getAllCategoryCouponsCustomer(customerID, category).isEmpty()) {
            throw new CouponMasterException("You do not have any coupons under that category");
        }
        return couponDBDAO.getAllCategoryCouponsCustomer(customerID, category);
    }

    /**
     * A method that allows the customer to fetch all of his coupons under a given price, or throws a custom
     excpetion if there are none
     * @param maxPrice an int that all the coupons with lesser price value will be retruned
     * @return
     * @throws CouponMasterException
     */
    public ArrayList<Coupon> getAllPriceCoupons(double maxPrice) throws CouponMasterException {
        if(couponDBDAO.getMaxPriceCoupons(maxPrice).isEmpty()){
            throw new CouponMasterException("There are no coupons under that price");
        }
        return couponDBDAO.getMaxPriceCoupons(maxPrice);
    }

    /**
     * A method that return the detials of the logged in customer
     * @return
     */
    public Customer getCustomerDetails() {
        return customersDBDAO.getOneCustomer(customerID);
    }
}
