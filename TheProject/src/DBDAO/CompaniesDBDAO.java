package DBDAO;

import Beans.Company;
import DAO.CompaniesDAO;
import DB.DBUtilities;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class CompaniesDBDAO implements CompaniesDAO {
    //Fields
    private static final String ADD_COMPANY = "INSERT INTO `coupon_master`.`companies`(`NAME`, `EMAIL`, `PASSWORD`) VALUES (?, ?, ?)";
    private static final String UPDATE_COMPANY = "UPDATE `coupon_master`.`companies` SET `NAME`=? ,`EMAIL`=? ,`PASSWORD`=? WHERE `ID`=?";
    private static final String DELETE_COMPANY = "DELETE FROM `coupon_master`.`companies` WHERE `ID`=?";
    private static final String GET_ONE_COMPANY = "SELECT * FROM `coupon_master`.`companies` WHERE `ID`=?";
    private static final String GET_ALL_COMPANIES = "SELECT * FROM `coupon_master`.`companies`";
    private static final String IS_COMPANY_EXISTS = "SELECT COUNT(EMAIL) AS `companies` FROM `coupon_master`.`companies` WHERE `EMAIL`=? AND `PASSWORD`=?";
    private static final String COUNT_COMPANY_NAME = "SELECT COUNT(NAME) AS `company name` FROM `coupon_master`.`companies` WHERE `NAME`=?";
    private static final String COUNT_COMPANY_EMAIL = "SELECT COUNT(EMAIL) AS `company email` FROM `coupon_master`.`companies` WHERE `EMAIL`=?";
    private static final String COUNT_COMPANY_ID = "SELECT COUNT(ID) AS `company ID` FROM `coupon_master`.`companies` WHERE `ID`=?";
    private static final String COMPANY_ID_LOGIN = "SELECT `ID` FROM `coupon_master`.`companies` WHERE `EMAIL`=? AND `PASSWORD`=?";


    //Methods
    /**
     * Counting how many companies there are in the companies table with both given email and password
     * Used by the LoginManager class for login details validation
     * @param email Company user's email
     * @param password Company user's password
     * @return The number of companies with both given email and password
     */
    @Override
    public int isCompanyExists(String email, String password) {
        Map<Integer, Object> parameters = new HashMap<>();
        parameters.put(1, email);
        parameters.put(2, password);
        return (DBUtilities.runQueryInt(IS_COMPANY_EXISTS, parameters));
    }

    /**
     * Adding a given company as a row in the companies table in the database
     * @param company a company object to insert into the companies table
     * @return Indication whether the company was added to the table or not
     */
    @Override
    public boolean addCompany(Company company) {
        Map<Integer, Object> parameters = new HashMap<>();
        parameters.put(1, company.getName());
        parameters.put(2, company.getEmail());
        parameters.put(3, company.getPassword());
        return (DBUtilities.runQueryWithMap(ADD_COMPANY, parameters));
    }

    /**
     * Updating a company in the companies table by it's ID, and setting it as the given company object
     * @param company The updated object of the company to save in the companies table
     * @return Indication whether the company was updated in the table or not
     */
    @Override
    public boolean updateCompany(Company company) {
        Map<Integer, Object> parameters = new HashMap<>();
        parameters.put(1, company.getName());
        parameters.put(2, company.getEmail());
        parameters.put(3, company.getPassword());
        parameters.put(4, company.getId());
        return (DBUtilities.runQueryWithMap(UPDATE_COMPANY, parameters));
    }

    /**
     * Deleting the row of the given company in the companies table
     * @param companyID The ID of the company for deletion
     * @return Indication whether the company was deleted from the table or not
     */
    @Override
    public boolean deleteCompany(int companyID) {
        Map<Integer, Object> parameters = new HashMap<>();
        parameters.put(1, companyID);
        return (DBUtilities.runQueryWithMap(DELETE_COMPANY, parameters));
    }

    /**
     * Getting a company from the companies table in the database
     * @param companyID The ID of the company to select from the table
     * @return Object of the company with the given ID
     */
    @Override
    public Company getOneCompany(int companyID) {
        Company result = null;
        CouponDBDAO couponDBDAO = new CouponDBDAO();
        Map<Integer, Object> parameters = new HashMap<>();
        parameters.put(1, companyID);
        ResultSet resultSet = (DBUtilities.runQueryWithMapAndResult(GET_ONE_COMPANY, parameters));
        try {
            while (resultSet.next()) {
                result = new Company(
                        resultSet.getInt(1),
                        resultSet.getString(2),
                        resultSet.getString(3),
                        resultSet.getString(4),
                        couponDBDAO.getAllCompanyCoupons(companyID)
                );
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return result;
    }

    /**
     * Getting all of the companies from the companies table in the database
     * @return ArrayList of company objects representing all of the companies in the table
     */
    public ArrayList<Company> getAllCompanies() {
        ArrayList<Company> companies = new ArrayList<>();
        CouponDBDAO couponDBDAO = new CouponDBDAO();
        ResultSet resultSet = DBUtilities.runQueryWithResult(GET_ALL_COMPANIES);
        try {
            while (resultSet.next()) {
                Company company = new Company(
                        resultSet.getInt(1),
                        resultSet.getString(2),
                        resultSet.getString(3),
                        resultSet.getString(4),
                        couponDBDAO.getAllCompanyCoupons(resultSet.getInt(1))
                );
                companies.add(company);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return companies;
    }

    /**
     * Counting how many companies there are in the companies table with the given name
     * @param name Company's name for counting
     * @return the number of companies with the given name
     */
    public int countCompanyName(String name) {
        Map<Integer, Object> parameters = new HashMap<>();
        parameters.put(1, name);
        return DBUtilities.runQueryInt(COUNT_COMPANY_NAME, parameters);
    }

    /**
     * Counting how many companies there are in the companies table with the given email
     * @param email Company's email for counting
     * @return the number of companies with the given email
     */
    public int countCompanyEmail(String email) {
        Map<Integer, Object> parameters = new HashMap<>();
        parameters.put(1, email);
        return DBUtilities.runQueryInt(COUNT_COMPANY_EMAIL, parameters);
    }

    /**
     * Counting how many companies there are in the companies table with the given ID
     * @param companyID Company's ID for counting
     * @return the number of companies with the given ID
     */
    public int countCompanyId(int companyID) {
        Map<Integer, Object> parameters = new HashMap<>();
        parameters.put(1, companyID);
        return DBUtilities.runQueryInt(COUNT_COMPANY_ID, parameters);
    }

    /**
     * Getting a company's ID from the table given both email and password
     * @param email Company's email
     * @param password Company's password
     * @return The ID of a company with both given email and password
     */
    public int companyIDLogin(String email, String password) {
        Map<Integer, Object> parameters = new HashMap<>();
        parameters.put(1, email);
        parameters.put(2, password);
        return DBUtilities.runQueryInt(COMPANY_ID_LOGIN, parameters);
    }

}