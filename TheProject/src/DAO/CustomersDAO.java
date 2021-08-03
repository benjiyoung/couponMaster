package DAO;

import Beans.Customer;

import java.sql.SQLException;
import java.util.ArrayList;

public interface CustomersDAO {
    /**
     * Counting how many customers there are with both given email and password
     * Used by the LoginManager class for login details validation
     * @param email Customer user's email
     * @param password Customer user's password
     * @return The number of customers with both given email and password
     */
    int isCustomerExists(String email, String password);
    /**
     * Adding a given customer as a row in the customers table in the database
     * @param customer a customer object to insert into the customers table
     * @return Indication whether the customer was added to the table or not
     */
    boolean addCustomer(Customer customer);
    /**
     * Updating a customer in the customers table by it's ID, and setting it as the given customer object
     * @param customer The updated object of the customer to save in the customers table
     * @return Indication whether the customer was updated in the table or not
     */
    boolean updateCustomer(Customer customer);
    /**
     * Deleting the row of the given customer in the customers table
     * @param customerID The ID of the customer for deletion
     * @return Indication whether the customer was deleted from the table or not
     */
    boolean deleteCustomer (int customerID);
    /**
     * Getting a customer from the customers table in the database
     * @param customerID The ID of the customer to select from the table
     * @return Object of the customer with the given ID
     */
    Customer getOneCustomer(int customerID);
    /**
     * Getting all of the customers from the customers table in the database
     * @return ArrayList of customer objects representing all of the customers in the table
     */
    ArrayList<Customer> getAllCustomers();
}