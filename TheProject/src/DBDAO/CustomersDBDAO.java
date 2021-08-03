package DBDAO;

import Beans.Customer;
import DAO.CustomersDAO;
import DB.DBUtilities;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class CustomersDBDAO implements CustomersDAO {
    //Fields
    private Connection connection;
    private static final String ADD_CUSTOMER = "INSERT INTO `coupon_master`.`customers`(`FIRST_NAME`, `LAST_NAME`, `EMAIL`, `PASSWORD`) VALUES (?, ?, ?, ?)";
    private static final String UPDATE_CUSTOMER = "UPDATE `coupon_master`.`customers` SET `FIRST_NAME`=?, `LAST_NAME`=?, `EMAIL`=? ,`PASSWORD`=? WHERE `ID`=?";
    private static final String DELETE_CUSTOMER = "DELETE FROM `coupon_master`.`customers` WHERE `ID`=?";
    private static final String GET_ONE_CUSTOMER = "SELECT * FROM `coupon_master`.`customers` WHERE `ID`=?";
    private static final String GET_ALL_CUSTOMERS = "SELECT * FROM `coupon_master`.`customers`";
    private static final String IS_CUSTOMER_EXISTS = "SELECT COUNT(EMAIL) AS `customers` FROM `coupon_master`.`customers` WHERE `EMAIL`=? AND `PASSWORD`=?";
    private static final String COUNT_CUSTOMER_EMAIL = "SELECT COUNT(EMAIL) AS `customer email` FROM `coupon_master`.`customers` WHERE `EMAIL`=?";
    private static final String COUNT_CUSTOMER_ID = "SELECT COUNT(ID) AS `customer ID` FROM `coupon_master`.`customers` WHERE `ID`=?";
    private static final String CUSTOMER_ID_LOGIN = "SELECT `ID` FROM `coupon_master`.`customers` WHERE `EMAIL`=? AND `PASSWORD`=?";

    //Methods
    /**
     * Counting how many customers there are with both given email and password
     * Used by the LoginManager class for login details validation
     * @param email Customer user's email
     * @param password Customer user's password
     * @return The number of customers with both given email and password
     */
    @Override
    public int isCustomerExists(String email, String password) {
        Map<Integer, Object> parameters = new HashMap<>();
        parameters.put(1, email);
        parameters.put(2, password);
        return (DBUtilities.runQueryInt(IS_CUSTOMER_EXISTS, parameters));
    }

    /**
     * Adding a given customer as a row in the customers table in the database
     * @param customer a customer object to insert into the customers table
     * @return Indication whether the customer was added to the table or not
     */
    @Override
    public boolean addCustomer(Customer customer) {
        Map<Integer, Object> parameters = new HashMap<>();
        parameters.put(1, customer.getFirstName());
        parameters.put(2, customer.getLastName());
        parameters.put(3, customer.getEmail());
        parameters.put(4, customer.getPassword());
        return (DBUtilities.runQueryWithMap(ADD_CUSTOMER, parameters));
    }

    /**
     * Updating a customer in the customers table by it's ID, and setting it as the given customer object
     * @param customer The updated object of the customer to save in the customers table
     * @return Indication whether the customer was updated in the table or not
     */
    @Override
    public boolean updateCustomer(Customer customer) {
        Map<Integer, Object> parameters = new HashMap<>();
        parameters.put(1, customer.getFirstName());
        parameters.put(2, customer.getLastName());
        parameters.put(3, customer.getEmail());
        parameters.put(4, customer.getPassword());
        parameters.put(5, customer.getId());
        return (DBUtilities.runQueryWithMap(UPDATE_CUSTOMER, parameters));
    }

    /**
     * Deleting the row of the given customer in the customers table
     * @param customerID The ID of the customer for deletion
     * @return Indication whether the customer was deleted from the table or not
     */
    @Override
    public boolean deleteCustomer(int customerID) {
        Map<Integer, Object> parameters = new HashMap<>();
        parameters.put(1, customerID);
        return (DBUtilities.runQueryWithMap(DELETE_CUSTOMER, parameters));
    }

    /**
     * Getting a customer from the customers table in the database
     * @param customerID The ID of the customer to select from the table
     * @return Object of the customer with the given ID
     */
    @Override
    public Customer getOneCustomer(int customerID) {
        Customer result = null;
        CouponDBDAO couponDBDAO = new CouponDBDAO();
        Map<Integer, Object> parameters = new HashMap<>();
        parameters.put(1, customerID);
        ResultSet resultSet = (DBUtilities.runQueryWithMapAndResult(GET_ONE_CUSTOMER, parameters));
        try {
            while (resultSet.next()) {
                result = new Customer(
                        resultSet.getInt(1),
                        resultSet.getString(2),
                        resultSet.getString(3),
                        resultSet.getString(4),
                        resultSet.getString(5),
                        couponDBDAO.getAllCustomerCoupons(customerID)
                );
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return result;
    }

    /**
     * Getting all of the customers from the customers table in the database
     * @return ArrayList of customer objects representing all of the customers in the table
     */
    @Override
    public ArrayList<Customer> getAllCustomers() {
        ArrayList<Customer> customers = new ArrayList<>();
        CouponDBDAO couponDBDAO = new CouponDBDAO();
        ResultSet resultSet = DBUtilities.runQueryWithResult(GET_ALL_CUSTOMERS);
        try {
            while (resultSet.next()) {
                Customer customer = new Customer(
                        resultSet.getInt(1),
                        resultSet.getString(2),
                        resultSet.getString(3),
                        resultSet.getString(4),
                        resultSet.getString(5),
                        couponDBDAO.getAllCustomerCoupons(resultSet.getInt(1))
                );
                customers.add(customer);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return customers;
    }

    /**
     * Counting how many customers there are in the customers table with the given email
     * @param email Customer's email for counting
     * @return the number of customers with the given email
     */
    public int countCustomerEmail(String email) {
        Map<Integer, Object> parameters = new HashMap<>();
        parameters.put(1, email);
        return DBUtilities.runQueryInt(COUNT_CUSTOMER_EMAIL, parameters);
    }

    /**
     * Counting how many customers there are in the customers table with the given ID
     * @param customerID Customer's ID for counting
     * @return the number of customers with the given ID
     */
    public int countCustomerId(int customerID) {
        Map<Integer, Object> parameters = new HashMap<>();
        parameters.put(1, customerID);
        return DBUtilities.runQueryInt(COUNT_CUSTOMER_ID, parameters);
    }

    /**
     * Getting a customer's ID from the table given both email and password
     * @param email Customer's email
     * @param password Customer's password
     * @return The ID of a customer with both given email and password
     */
    public int customerIDLogin(String email, String password) {
        Map<Integer, Object> parameters = new HashMap<>();
        parameters.put(1, email);
        parameters.put(2, password);
        return DBUtilities.runQueryInt(CUSTOMER_ID_LOGIN, parameters);
    }
}