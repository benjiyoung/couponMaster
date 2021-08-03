package Login;

import Exceptions.CouponMasterException;
import Facades.AdminFacade;
import Facades.ClientFacade;
import Facades.CompanyFacade;
import Facades.CustomerFacade;

public class LoginManager {
    private static LoginManager instance = null;
    protected AdminFacade adminFacade = new AdminFacade();
    protected CompanyFacade companyFacade = new CompanyFacade();
    protected CustomerFacade customerFacade = new CustomerFacade();

    /**
     * An empty CTOR that creates the instance  LoginManager
     */
    private LoginManager() {
    }

    /**
     * static method to login, it's singleton and  synchronized
     * @return if its null return the old instance else return a new instance
     */
    public static LoginManager getInstance() {
        if (instance == null) {
            synchronized (LoginManager.class) {
                if (instance == null) {
                    instance = new LoginManager();
                }
            }
        }
        return instance;
    }

    /**
     * a login method , check if the details correct if not return an exception massage
     * @param email chek if the email is correct
     * @param password chek if the password is correct
     * @param clientType chek if the clientType is correct
     * @return the last result
     * @throws CouponMasterException throws an adjusted exception
     */
    public ClientFacade login(String email, String password, ClientType clientType) throws CouponMasterException {
        ClientFacade result = null;
        switch (clientType) {
            case ADMINISTRATOR -> result = (adminFacade.login(email, password)) ? adminFacade : null;
            case COMPANY -> result = (companyFacade.login(email, password)) ? companyFacade : null;
            case CUSTOMER -> result = (customerFacade.login(email, password)) ? customerFacade : null;
        }
        if (result == null)
            throw new CouponMasterException("Login failed! Please try again.");
        return result;
    }
}