package Facades;

import Beans.Company;
import Beans.Customer;
import Exceptions.CouponMasterException;

import java.util.ArrayList;

public class AdminFacade extends ClientFacade {
    private final String ADMIN_EMAIL = "admin@admin.com";
    private final String ADMIN_PASSWORD = "admin";

    /**
     * An empty CTOR that creates the instance of the admin
     */

    public AdminFacade() {
    }

    /**
     * A method that verifies that the credentials taken in are valid.
     * @param email admin's email
     * @param password admin's password
     * @return An indication whether the action was successful or not
     */
    @Override
    public boolean login(String email, String password) {
        return (email.equals(ADMIN_EMAIL) && password.equals(ADMIN_PASSWORD));
    }

    /**
     * A method that allows the admin user to log in a new company to the database, under checked conditions
     and thrwos a custom exception if the conditions arent met
     * @param company Instance of a company to be added to the database
     * @return An indication whether the action was successful or not
     * @throws CouponMasterException
     */
    public boolean addCompany(Company company) throws CouponMasterException {
        if (companiesDBDAO.countCompanyName(company.getName()) > 0) {
            throw new CouponMasterException("A company with this name already exists!");
        } else if (companiesDBDAO.countCompanyEmail(company.getEmail()) > 0) {
            throw new CouponMasterException("A company with this email address already exists!");
        }
        return companiesDBDAO.addCompany(company);
    }

    /**
     * A method that allows the admin user to update the details of an existing company
     and return in to the data base.
     * @param company Instance of a company to be updated and sent back to the databse
     * @return An indication whether the action was successful or not
     */
    public boolean updateCompany(Company company){
        return companiesDBDAO.updateCompany(company);
    }

    /**
     * A method that allows the admin user to delete an existing company from the data base, identifying it via its id
     and throws a custom exception if not
     * @param companyID CompanyId that will be the indicator for which company to delete
     * @return An indication whether the action was successful or not
     * @throws CouponMasterException
     */
    public boolean deleteCompany(int companyID) throws CouponMasterException {
        if (companiesDBDAO.countCompanyId(companyID) < 1) {
            throw new CouponMasterException("There is no company with this ID!");
        }
        //couponDBDAO.deleteCompanyCoupons(companyID);
        return companiesDBDAO.deleteCompany(companyID);
    }

    /**
     * A method that fetches a single company from the database identifying it via its id if it exists
     and throws a custom exception if not
     * @param companyID CompanyId that will be the indicator for which company to return
     * @return the instance
     * @throws CouponMasterException
     */

    public Company getOneCompany(int companyID) throws CouponMasterException {
        if (companiesDBDAO.countCompanyId(companyID) < 1) {
            throw new CouponMasterException("There is no company with this ID!");
        }
        return companiesDBDAO.getOneCompany(companyID);
    }

    /**
     * a method that fetches all of the companies from the data base if there are any, and throws a custom exception
     if there are no companies in the databse
     * @return A list of companies
     * @throws CouponMasterException
     */

    public ArrayList<Company> getAllCompanies() throws CouponMasterException {
        if (companiesDBDAO.getAllCompanies().isEmpty())
            throw new CouponMasterException("There are no companies yet!");
        return companiesDBDAO.getAllCompanies();
    }

    /**
     * A method that allows the admin user to add a new customer to the database and throws a custom exception
     if a customer with that email address already exists
     * @param customer Instance of a customer to be added to the database
     * @return An indication weather the action was successful or not
     * @throws CouponMasterException
     */

    public boolean addCustomer(Customer customer) throws CouponMasterException{
        if (customersDBDAO.countCustomerEmail(customer.getEmail()) > 0) {
            throw new CouponMasterException("A customer with this email address already exists!");
        }
        return customersDBDAO.addCustomer(customer);
    }

    /**
     * A method that allows the admin user to update an existing customer and log it back it in to the database
     * @param customer Instance of a customer to be updated and sent back to the database
     * @return An indication weather the action was successful or not
     */

    public boolean updateCustomer(Customer customer) {
        return customersDBDAO.updateCustomer(customer);
    }

    /**
     * A method that allows the admin user to delete an existing customer from the database identifying it via his id
     or throws a custom exception if there is no such customer
     * @param customerID CustomerId that will be the indicator for which customer to delete
     * @return An indication whether the action was successful or not
     * @throws CouponMasterException
     */

    public boolean deleteCustomer(int customerID) throws CouponMasterException {
        if (customersDBDAO.countCustomerId(customerID) < 1) {
            throw new CouponMasterException("There is no customer with this ID!");
        }
        return customersDBDAO.deleteCustomer(customerID);
    }

    /**
     * A method that fetches a single customer from the database identifying it via his id
     or throws a custom exception if there is no such customer
     * @param customerID CustomerId that will be the indicator for which customer to return
     * @return an instance of the requested customer
     * @throws CouponMasterException
     */
    public Customer getOneCustomer(int customerID) throws CouponMasterException {
        if (customersDBDAO.countCustomerId(customerID) < 1) {
            throw new CouponMasterException("There is no customer with this ID!");
        }
        return customersDBDAO.getOneCustomer(customerID);
    }

    /**
     * A method that fetches all the customer from the database, or throws a custom exception if there are not customers
     * @return A list of customers
     * @throws CouponMasterException
     */

    public ArrayList<Customer> getAllCustomers() throws CouponMasterException {
        if (customersDBDAO.getAllCustomers().isEmpty())
            throw new CouponMasterException("There are no customers yet!");
        return customersDBDAO.getAllCustomers();
    }
}
