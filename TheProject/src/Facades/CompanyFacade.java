package Facades;

import Beans.Category;
import Beans.Company;
import Beans.Coupon;
import Exceptions.CouponMasterException;

import java.util.ArrayList;

public class CompanyFacade extends ClientFacade{
    private int companyID;

    /**
     * An empty CTOR that creates an instance of the company facade
     */
    public CompanyFacade() {}


    public int getCompanyID() {
        return this.companyID;
    }

    /**
     * A method that overrides the login method of its parent, checks weather the credentials taken in exist
     via the isCompanyExists method and assigns its companyID for later use.
     * @param email Company's email
     * @param password Company's password
     * @return An indication whether the action was successful or not
     */
    @Override
    public boolean login(String email, String password) {
        if (companiesDBDAO.isCompanyExists(email, password) > 0) {
            this.companyID = companiesDBDAO.companyIDLogin(email, password);
            return true;
        }
        return false;
    }

    /**
     * A method that allows the company user to add a new coupon to the database, or throws a custom exception
     if a coupon with that title already exists
     * @param coupon A coupon instance to be added to the database
     * @return An indication whether the action was successful or not
     * @throws CouponMasterException
     */

    public boolean addCoupon(Coupon coupon) throws CouponMasterException {
        if (couponDBDAO.countCompanyCoupons(coupon.getTitle(), companyID) > 0) {
            throw new CouponMasterException("A coupon with this name already exists in this company!");
        }
        Coupon result = new Coupon(companyID, coupon.getCategory(), coupon.getTitle(), coupon.getDescription(),
                coupon.getStartDate(), coupon.getEndDate(), coupon.getAmount(), coupon.getPrice(), coupon.getImage());

        return couponDBDAO.addCoupon(result);
    }

    /**
     * A method that allows the company user ti update an existing coupon and log it back in to the database
     * @param coupon A coupon instance to be updated and sent back to the database
     * @return An indication whether the action was successful or not
     */
    public boolean updateCoupon(Coupon coupon) {
        return couponDBDAO.updateCoupon(coupon);
    }

    /**
     * A method that allows the company user to delete a coupon from the database identifying it via its id
      or throws a custom exception is there is no such coupon
     * @param couponID A couponId that will be the indicator for which coupon to delete from the database
     * @return An indication whether the action was successful or not
     * @throws CouponMasterException
     */

    public boolean deleteCoupon(int couponID) throws CouponMasterException {
        if (couponDBDAO.countCouponID(couponID) < 1) {
            throw new CouponMasterException("There is no coupon with this ID!");
        }
        return couponDBDAO.deleteCoupon(couponID);
    }

    /**
     * A method that allows the company user to fetch a single coupon identifying it via its id
     * @param couponID A couponId that will be the indicator of which coupon to fetch
     * @return An instance of a coupon
     */

    public Coupon getOneCoupon(int couponID) throws CouponMasterException {
        if (couponDBDAO.countCouponID(couponID) < 1) {
            throw new CouponMasterException("There is no coupon with this ID!");
        }

        return couponDBDAO.getOneCoupon(couponID);
    }

    /**
     * A method that fetches all of the company's coupons, or throws a custom exception if there are none
     * @return A list of coupons
     * @throws CouponMasterException
     */

    public ArrayList<Coupon> getAllCompanyCoupons() throws CouponMasterException {
        if (couponDBDAO.getAllCoupons().isEmpty()){
            throw new CouponMasterException("There are no coupons yet!");
    }
        return couponDBDAO.getAllCompanyCoupons(companyID);
    }

    /**
     * A method that fetches all the coupons with the given category, or throws a custom exception if there are none
     * @param category A category that all the coupons under it will be returned
     * @return A list of coupons
     * @throws CouponMasterException
     */

    public ArrayList<Coupon> getAllCategoryCoupons(Category category) throws CouponMasterException {

        if(couponDBDAO.getAllCategoryCouponsCompany(companyID, category).isEmpty()){
            throw new CouponMasterException("T  here are no coupons under that category");
        }
        return couponDBDAO.getAllCategoryCouponsCompany(companyID, category);
    }

    /**
     * A method that fetches all the coupons with the price under the given value, or throws a custom exception
     if there are none
     * @param maxPrice an int that all the coupons with lesser price value will be retruned
     * @return A list of coupons
     * @throws CouponMasterException
     */

    public ArrayList<Coupon> getAllPriceCoupons(double maxPrice) throws CouponMasterException {
        if(couponDBDAO.getMaxPriceCoupons(maxPrice).isEmpty()) {
            throw new CouponMasterException("There are not coupons under that price");

        }
        return couponDBDAO.getMaxPriceCoupons(maxPrice);
    }

    /**
     * A method that fetches the details of the company currently logged in.
     * @return A company instance
     */
    public Company getCompanyDetails() {
        return companiesDBDAO.getOneCompany(companyID);
    }

}
