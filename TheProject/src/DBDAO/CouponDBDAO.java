package DBDAO;

import Beans.Coupon;
import DAO.CouponsDAO;
import Beans.Category;
import DB.DBUtilities;
import Utilities.MyUtilities;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class CouponDBDAO implements CouponsDAO {
    private static final String ADD_COUPON = "INSERT INTO `coupon_master`.`coupons`(`COMPANY_ID`, `CATEGORY_ID`, `TITLE`, `DESCRIPTION`, `START_DATE`, `END_DATE`, `AMOUNT`, `PRICE`, `IMAGE`) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";
    private static final String UPDATE_COUPON = "UPDATE `coupon_master`.`coupons` SET `COMPANY_ID`=?, `CATEGORY_ID`=?, `TITLE`=?, `DESCRIPTION`=?, `START_DATE`=?, `END_DATE`=?, `AMOUNT`=?, `PRICE`=?, `IMAGE`=? WHERE `ID`=?";
    private static final String DELETE_COUPON = "DELETE FROM `coupon_master`.`coupons` WHERE `ID`=?";
    private static final String GET_ONE_COUPON = "SELECT * FROM `coupon_master`.`coupons` WHERE `ID`=?";
    private static final String GET_ALL_COUPONS = "SELECT * FROM `coupon_master`.`coupons`";
    private static final String ADD_COUPON_PURCHASE = "INSERT INTO `coupon_master`.`customers_vs_coupons`(`CUSTOMER_ID`, `COUPON_ID`) VALUES (?, ?)";
    private static final String DELETE_COUPON_PURCHASE = "DELETE FROM `coupon_master`.`customers_vs_coupons` WHERE `CUSTOMER_ID`=? AND `COUPON_ID`=?";
    private static final String COUNT_COMPANY_COUPONS = "SELECT COUNT(TITLE) AS `company coupons` FROM `coupon_master`.`coupons` WHERE `TITLE`=? AND `COMPANY_ID`=?";
    private static final String COUNT_COUPON_ID = "SELECT COUNT(ID) AS `coupon ID` FROM `coupon_master`.`coupons` WHERE `ID`=?";
    private static final String GET_ALL_COMPANY_COUPONS = "SELECT * FROM `coupon_master`.`coupons` WHERE `company_ID`=?";
    private static final String GET_ALL_CATEGORY_COUPONS_COMPANY = "SELECT * FROM `coupon_master`.`coupons` WHERE `company_ID`=? AND`category_ID`=?";
    private static final String GET_ALL_PRICE_COUPONS = "SELECT * FROM `coupon_master`.`coupons` WHERE `price`<=?";
    private static final String GET_ALL_CUSTOMER_COUPONS = "SELECT `coupon_ID` FROM `coupon_master`.`customers_vs_coupons` WHERE `customer_ID`=?";


    //Methods
    /**
     * Adding a given coupon as a row in the coupons table in the database
     * @param coupon a coupon object to insert into the coupons table
     * @return Indication whether the coupon was added to the table or not
     */
    @Override
    public boolean addCoupon(Coupon coupon) {
        Map<Integer, Object> parameters = new HashMap<>();
        parameters.put(1, coupon.getCompanyID());
        parameters.put(2, coupon.getCategory().getID());
        parameters.put(3, coupon.getTitle());
        parameters.put(4, coupon.getDescription());
        parameters.put(5, coupon.getStartDate());
        parameters.put(6, coupon.getEndDate());
        parameters.put(7, coupon.getAmount());
        parameters.put(8, coupon.getPrice());
        parameters.put(9, coupon.getImage());
        return (DBUtilities.runQueryWithMap(ADD_COUPON, parameters));
    }

    /**
     * Updating a coupon in the coupons table by it's ID, and setting it as the given coupon object
     * @param coupon The updated object of the coupon to save in the coupons table
     * @return Indication whether the coupon was updated in the table or not
     */
    @Override
    public boolean updateCoupon(Coupon coupon) {
        Map<Integer, Object> parameters = new HashMap<>();
        parameters.put(1, coupon.getCompanyID());
        parameters.put(2, coupon.getCategory().getID());
        parameters.put(3, coupon.getTitle());
        parameters.put(4, coupon.getDescription());
        parameters.put(5, coupon.getStartDate());
        parameters.put(6, coupon.getEndDate());
        parameters.put(7, coupon.getAmount());
        parameters.put(8, coupon.getPrice());
        parameters.put(9, coupon.getImage());
        parameters.put(10, coupon.getId());
        return (DBUtilities.runQueryWithMap(UPDATE_COUPON, parameters));
    }

    /**
     * Deleting the row of the given coupon in the coupons table
     * @param couponID The ID of the coupon for deletion
     * @return Indication whether the coupon was deleted from the table or not
     */
    @Override
    public boolean deleteCoupon(int couponID) {
        Map<Integer, Object> parameters = new HashMap<>();
        parameters.put(1, couponID);
        System.out.println("Coupon #" + couponID + " was deleted!");
        return (DBUtilities.runQueryWithMap(DELETE_COUPON, parameters));
    }

    /**
     * Getting a coupon from the coupons table in the database
     * @param couponID The ID of the coupon to select from the table
     * @return Object of the coupon with the given ID
     */
    @Override
    public Coupon getOneCoupon(int couponID) {
        Coupon result = null;
        Map<Integer, Object> parameters = new HashMap<>();
        parameters.put(1, couponID);
        ResultSet resultSet = DBUtilities.runQueryWithMapAndResult(GET_ONE_COUPON, parameters);
        try {
            while (resultSet.next()) {
                result = new Coupon(
                        resultSet.getInt(1),
                        resultSet.getInt(2),
                        Category.indexToCategory(resultSet.getInt(3)),
                        resultSet.getString(4),
                        resultSet.getString(5),
                        MyUtilities.dateToLocalDate(resultSet.getDate(6)),
                        MyUtilities.dateToLocalDate(resultSet.getDate(7)),
                        resultSet.getInt(8),
                        resultSet.getInt(9),
                        resultSet.getString(10));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return result;
    }

    /**
     * Getting all of the coupons from the coupons table in the database
     * @return ArrayList of coupon objects representing all of the coupons in the table
     */
    @Override
    public ArrayList<Coupon> getAllCoupons() {
        ArrayList<Coupon> coupons = new ArrayList<>();
        ResultSet resultSet = DBUtilities.runQueryWithResult(GET_ALL_COUPONS);
        try {
            while (resultSet.next()) {
                Coupon coupon = new Coupon(
                        resultSet.getInt(1),
                        resultSet.getInt(2),
                        Category.indexToCategory(resultSet.getInt(3)),
                        resultSet.getString(4),
                        resultSet.getString(5),
                        MyUtilities.dateToLocalDate(resultSet.getDate(6)),
                        MyUtilities.dateToLocalDate(resultSet.getDate(7)),
                        resultSet.getInt(8),
                        resultSet.getInt(9),
                        resultSet.getString(10));
                coupons.add(coupon);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return coupons;
    }

    /**
     * Creating a new row in the table CUSTOMERS_VS_COUPONS to represent a coupon purchase by a customer
     * @param customerID The purchasing customer's ID in the database
     * @param couponID The purchased coupon's ID in the database
     * @return Indication whether the purchase was inserted into the CUSTOMERS_VS_COUPONS table ot not
     */
    @Override
    public boolean addCouponPurchase(int customerID, int couponID) {
        Map<Integer, Object> parameters = new HashMap<>();
        parameters.put(1, customerID);
        parameters.put(2, couponID);
        return (DBUtilities.runQueryWithMap(ADD_COUPON_PURCHASE, parameters));
    }

    /**
     * Deleting the purchase's row in the table CUSTOMERS_VS_COUPONS
     * @param customerID The purchasing customer's ID in the database
     * @param couponID The purchased coupon's ID in the database
     * @return Indication whether the purchase was deleted from the CUSTOMERS_VS_COUPONS table or not
     */
    @Override
    public boolean deleteCouponPurchase(int customerID, int couponID) {
        Map<Integer, Object> parameters = new HashMap<>();
        parameters.put(1, customerID);
        parameters.put(2, couponID);
        return (DBUtilities.runQueryWithMap(DELETE_COUPON_PURCHASE, parameters));
    }

    /**
     * Counting how many coupons with a certain name a company has saved in the database
     * @param couponName The coupon name for counting
     * @param companyID The company we want to count it's coupons
     * @return The number of coupons with the given name
     */
    public int countCompanyCoupons(String couponName, int companyID) {
        Map<Integer, Object> parameters = new HashMap<>();
        parameters.put(1, couponName);
        parameters.put(2, companyID);
        return DBUtilities.runQueryInt(COUNT_COMPANY_COUPONS, parameters);
    }

    /**
     * Counting how many coupons there are in the coupons table with the given ID
     * @param couponID Coupon's ID for counting
     * @return The number of coupons with the given ID
     */
    public int countCouponID(int couponID) {
        Map<Integer, Object> parameters = new HashMap<>();
        parameters.put(1, couponID);
        return DBUtilities.runQueryInt(COUNT_COUPON_ID, parameters);
    }

    /**
     * Getting all of the coupons of a certain company from the coupons table
     * @param companyID The ID of the company to count it's coupons
     * @return ArrayList of coupon objects representing all coupons of a certain company
     */
    public ArrayList<Coupon> getAllCompanyCoupons(int companyID) {
        ArrayList<Coupon> coupons = new ArrayList<>();
        Map<Integer, Object> parameters = new HashMap<>();
        parameters.put(1, companyID);
        ResultSet resultSet = DBUtilities.runQueryWithMapAndResult(GET_ALL_COMPANY_COUPONS, parameters);
        try {
            while (resultSet.next()) {
                Coupon coupon = new Coupon(
                        resultSet.getInt(1),
                        resultSet.getInt(2),
                        Category.indexToCategory(resultSet.getInt(3)),
                        resultSet.getString(4),
                        resultSet.getString(5),
                        MyUtilities.dateToLocalDate(resultSet.getDate(6)),
                        MyUtilities.dateToLocalDate(resultSet.getDate(7)),
                        resultSet.getInt(8),
                        resultSet.getInt(9),
                        resultSet.getString(10));
                coupons.add(coupon);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return coupons;
    }

    /**
     * Getting all of coupons from a certain category that belongs to a certain company
     * @param companyID The ID of the company to count it's coupons of the category
     * @param category The category to count from the company's coupons
     * @return ArrayList of coupon objects of the given category and company
     */
    public ArrayList<Coupon> getAllCategoryCouponsCompany(int companyID, Category category) {
        ArrayList<Coupon> coupons = new ArrayList<>();
        int categoryID = category.getID();
        Map<Integer, Object> parameters = new HashMap<>();
        parameters.put(1, companyID);
        parameters.put(2, categoryID);
        ResultSet resultSet = DBUtilities.runQueryWithMapAndResult(GET_ALL_CATEGORY_COUPONS_COMPANY, parameters);
        try {
            while (resultSet.next()) {
                Coupon coupon = new Coupon(
                        resultSet.getInt(1),
                        resultSet.getInt(2),
                        Category.indexToCategory(resultSet.getInt(3)),
                        resultSet.getString(4),
                        resultSet.getString(5),
                        MyUtilities.dateToLocalDate(resultSet.getDate(6)),
                        MyUtilities.dateToLocalDate(resultSet.getDate(7)),
                        resultSet.getInt(8),
                        resultSet.getInt(9),
                        resultSet.getString(10));
                coupons.add(coupon);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return coupons;
    }

    /**
     * Getting all of coupons from a certain category that a certain customer purchased
     * @param customerID The ID of the customer to count it's coupons of the category
     * @param category The category to count from the customer's coupons
     * @return ArrayList of coupon objects from the given category that the customer purchased
     */
    public ArrayList<Coupon> getAllCategoryCouponsCustomer(int customerID, Category category) {
        ArrayList<Coupon> coupons = getAllCustomerCoupons(customerID);
        coupons.removeIf(coupon -> coupon.getCategory() != category);
        return coupons;
    }

    /**
     * Getting all of the coupons with or under a given price from the coupons table
     * @param maxPrice The maximum price for the coupons selection
     * @return ArrayList of coupon objects representing all coupons under the given price
     */
    public ArrayList<Coupon> getMaxPriceCoupons(double maxPrice) {
        ArrayList<Coupon> coupons = new ArrayList<>();
        Map<Integer, Object> parameters = new HashMap<>();
        parameters.put(1, maxPrice);
        ResultSet resultSet = DBUtilities.runQueryWithMapAndResult(GET_ALL_PRICE_COUPONS, parameters);
        try {
            while (resultSet.next()) {
                Coupon coupon = new Coupon(
                        resultSet.getInt(1),
                        resultSet.getInt(2),
                        Category.indexToCategory(resultSet.getInt(3)),
                        resultSet.getString(4),
                        resultSet.getString(5),
                        MyUtilities.dateToLocalDate(resultSet.getDate(6)),
                        MyUtilities.dateToLocalDate(resultSet.getDate(7)),
                        resultSet.getInt(8),
                        resultSet.getInt(9),
                        resultSet.getString(10));
                coupons.add(coupon);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return coupons;
    }

    /**
     * Getting all of the coupons that belongs to a certain customer from the table CUSTOMERS_VS_COUPONS
     * @param customerID The ID of the customer to count it's coupons
     * @return ArrayList of coupon objects representing all coupons of a certain customer
     */
    public ArrayList<Coupon> getAllCustomerCoupons(int customerID) {
        ArrayList<Coupon> coupons = new ArrayList<>();
        Map<Integer, Object> parameters = new HashMap<>();
        parameters.put(1, customerID);
        ResultSet resultSet = DBUtilities.runQueryWithMapAndResult(GET_ALL_CUSTOMER_COUPONS, parameters);
        try {
            while (resultSet.next()) {
                int couponID = resultSet.getInt(1);
                Coupon coupon = getOneCoupon(couponID);
                coupons.add(coupon);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return coupons;
    }
}