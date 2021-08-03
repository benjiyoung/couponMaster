package Utilities;

import DB.ConnectionPool;
import DB.DatabaseManager;
import Run.Test;

import java.sql.Date;
import java.time.LocalDate;

public class MyUtilities {

    public static LocalDate dateToLocalDate (Date date) {
        return date.toLocalDate();
    }

    /**
     * Initializing the database by calling the creation method of all tables
     */
    public static void initialization() {
        DatabaseManager.createDataBase();
        DatabaseManager.createTableCategories();
        DatabaseManager.createTableCompanies();
        DatabaseManager.createTableCoupons();
        DatabaseManager.createTableCustomers();
        DatabaseManager.createTableCustomersVsCoupons();
    }

    /**
     * Closing the program by stopping the thread, closing all database connections and exiting the JVM
     */
    public static void closeProgram() {
        try {
            Test.couponExpirationDailyJob.stopDailyJob();
            ConnectionPool.getInstance().closeAllConnection();
            System.exit(0);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
