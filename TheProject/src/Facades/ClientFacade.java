package Facades;

import DBDAO.CompaniesDBDAO;
import DBDAO.CouponDBDAO;
import DBDAO.CustomersDBDAO;

public abstract class ClientFacade {
    protected CompaniesDBDAO companiesDBDAO = new CompaniesDBDAO();
    protected CustomersDBDAO customersDBDAO = new CustomersDBDAO();
    protected CouponDBDAO couponDBDAO = new CouponDBDAO();

    /**
     * A parent login method to force all of the
     * @param email
     * @param password
     * @return
     */
    public boolean login(String email, String password) {
        //Every login function looks different so there's no one parent to all of them
        return false;
    }
}