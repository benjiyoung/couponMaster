package Run;

import Beans.Category;
import Beans.Company;
import Beans.Coupon;
import Beans.Customer;
import DB.DatabaseManager;
import Exceptions.CouponMasterException;
import Facades.AdminFacade;
import Facades.CompanyFacade;
import Facades.CustomerFacade;
import Login.ClientType;
import Login.LoginManager;
import Threads.CouponExpirationDailyJob;
import Utilities.MyUtilities;

import java.time.LocalDate;

public class Test {
    public static CouponExpirationDailyJob couponExpirationDailyJob;

    public static void testAll() {
        DatabaseManager.dropDataBase();
        MyUtilities.initialization();
//        OldCouponExpirationDailyJob dailyJob = new OldCouponExpirationDailyJob();
//        Thread dailyJobThread = new Thread(dailyJob);
//        dailyJobThread.start();
        couponExpirationDailyJob = new CouponExpirationDailyJob();
        try {
            AdminFacade admin = (AdminFacade) LoginManager.getInstance().login("admin@admin.com", "admin", ClientType.ADMINISTRATOR);
            Company company1 = new Company("Company 1", "comp1@gmail.com", "123456comp1");
            Company company2 = new Company("Company 2", "comp2@gmail.com", "123456comp2");
            admin.addCompany(company1);
            admin.addCompany(company2);
            company2 = admin.getOneCompany(2);
            company2.setPassword("654321c2");
            admin.updateCompany(company2);
            System.out.println("All companies: " + admin.getAllCompanies());
            admin.deleteCompany(2);


            Customer customer1 = new Customer("FirstName 1", "LastName 1", "cust1@gmail.com", "123456cust1");
            Customer customer2 = new Customer("FirstName 2", "LastName 2", "cust2@gmail.com", "123456cust2");
            admin.addCustomer(customer1);
            admin.addCustomer(customer2);
            customer2 = admin.getOneCustomer(2);
            customer2.setPassword("654321cust1");
            admin.updateCustomer(customer2);
            System.out.println("All customers: " + admin.getAllCustomers());
            admin.deleteCustomer(2);


            CompanyFacade companyUser = (CompanyFacade) LoginManager.getInstance().login("comp1@gmail.com", "123456comp1", ClientType.COMPANY);
            Coupon coupon1 = new Coupon(companyUser.getCompanyID(), Category.VACATION, "Title 1", "Description 1", LocalDate.of(2021, 5, 1), LocalDate.of (2021, 11, 1), 1000, 99.90, "Image 1");
            Coupon coupon2 = new Coupon(companyUser.getCompanyID(), Category.FASHION, "Title 2", "Description 2", LocalDate.of(2021, 6, 1), LocalDate.of (2021, 12, 1), 2500, 120.20, "Image 2");
            Coupon coupon3 = new Coupon(companyUser.getCompanyID(), Category.ELECTRICITY, "Title 3", "Description 3", LocalDate.of(2021, 8, 1), LocalDate.of (2021, 10, 1), 1500, 175.50, "Image 3");
            Coupon coupon4 = new Coupon(companyUser.getCompanyID(), Category.FASHION, "Title 4", "Description 4", LocalDate.of(2021, 7, 1), LocalDate.of (2022, 1, 1), 500, 50.00, "Image 4");
            Coupon coupon5 = new Coupon(companyUser.getCompanyID(), Category.PETS, "Title 5", "Description 5", LocalDate.of(2021, 1, 1), LocalDate.of (2021, 4, 12), 800, 125.00, "Image 5");
            Coupon coupon6 = new Coupon(companyUser.getCompanyID(), Category.RESTAURANT, "Title 6", "Description 6", LocalDate.of(2021,7,1), LocalDate.of(2021, 11, 1),  750, 149.90, "Image 6");

            companyUser.addCoupon(coupon1);
            companyUser.addCoupon(coupon2);
            companyUser.addCoupon(coupon3);
            companyUser.addCoupon(coupon4);
            companyUser.addCoupon(coupon5);
            companyUser.addCoupon(coupon6);
            //Saving the auto incremented ID from the DB in the java Coupon objects
            coupon1 = companyUser.getOneCoupon(1);
            coupon2 = companyUser.getOneCoupon(2);
            coupon3 = companyUser.getOneCoupon(3);
            coupon4 = companyUser.getOneCoupon(4);
            coupon5 = companyUser.getOneCoupon(5);
            coupon5 = companyUser.getOneCoupon(6);
            coupon2.setAmount(2000);
            companyUser.updateCoupon(coupon2);
            System.out.println("All company #" + companyUser.getCompanyID() + " coupons: " + companyUser.getAllCompanyCoupons());
            System.out.println("All company #" + companyUser.getCompanyID() + " coupons from category " + Category.FASHION.toString() + ": " + companyUser.getAllCategoryCoupons(Category.FASHION));

            System.out.println("All company #" + companyUser.getCompanyID() + " coupons under price 125₪: " + companyUser.getAllPriceCoupons(125));
            companyUser.deleteCoupon(6);
            System.out.println("Company #" + companyUser.getCompanyID() + " details: " + companyUser.getCompanyDetails());


            CustomerFacade customerUser = (CustomerFacade) LoginManager.getInstance().login("cust1@gmail.com", "123456cust1", ClientType.CUSTOMER);
            customerUser.purchaseCoupon(coupon1);
            customerUser.purchaseCoupon(coupon2);
            customerUser.purchaseCoupon(coupon4);
            System.out.println("All customer #" + customerUser.getCustomerID() + " coupons: " + customerUser.getAllCustomerCoupons());
            System.out.println("All customer #" + customerUser.getCustomerID() + " coupons from category " + Category.FASHION.toString() + ": " + customerUser.getAllCategoryCoupons(Category.FASHION));

            System.out.println("All customer #" + customerUser.getCustomerID() + " coupons under price 100₪: " + customerUser.getAllPriceCoupons(100));
            System.out.println("All customer #" + customerUser.getCustomerID() + " details: " + customerUser.getCustomerDetails());




        } catch (CouponMasterException e) {
            System.out.println(e.getMessage());
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
//            finally {
//            MyUtilities.closeProgram();
//        }
    }
}
