package DAO;

import Beans.Company;

import java.util.ArrayList;

public interface CompaniesDAO {
    /**
     * Counting how many companies there are in the companies table with both given email and password
     * Used by the LoginManager class for login details validation
     * @param email Company user's email
     * @param password Company user's password
     * @return The number of companies with both given email and password
     */
    int isCompanyExists(String email, String password);
    /**
     * Adding a given company as a row in the companies table in the database
     * @param company a company object to insert into the companies table
     * @return Indication whether the company was added to the table or not
     */
    boolean addCompany(Company company);
    /**
     * Updating a company in the companies table by it's ID, and setting it as the given company object
     * @param company The updated object of the company to save in the companies table
     * @return Indication whether the company was updated in the table or not
     */
    boolean updateCompany(Company company);
    /**
     * Deleting the row of the given company in the companies table
     * @param companyID The ID of the company for deletion
     * @return Indication whether the company was deleted from the table or not
     */
    boolean deleteCompany(int companyID);
    /**
     * Getting a company from the companies table in the database
     * @param companyID The ID of the company to select from the table
     * @return Object of the company with the given ID
     */
    Company getOneCompany(int companyID);
    /**
     * Getting all of the companies from the companies table in the database
     * @return ArrayList of company objects representing all of the companies in the table
     */
    ArrayList<Company> getAllCompanies();
}