package DB;

import Beans.Category;

import java.util.HashMap;
import java.util.Map;

public class DatabaseManager {
    public static String url = "jdbc:mysql://localhost:3306?createDatabaseIfNotExist=FALSE&useTimezone=TRUE&serverTimezone=GMT"; //Asia/Jerusalem
    public static String username = "root";
    public static String password = "12345678";

    private static final String CREATE_DB = "CREATE DATABASE coupon_master";
    private static final String DROP_DB = "DROP DATABASE coupon_master";

    public static final String CREATE_TABLE_CATEGORIES = "CREATE TABLE IF NOT EXISTS `coupon_master`.`categories` (`ID` int(11) NOT NULL AUTO_INCREMENT,`NAME` varchar(50) NOT NULL, PRIMARY KEY (`ID`))";
    private static final String ADD_CATEGORY = "INSERT INTO `coupon_master`.`categories`(`NAME`) VALUES (?)";
    public static final String DROP_TABLE_CATEGORIES = "DROP TABLE IF EXISTS `coupon_master`.`categories`";

    public static final String CREATE_TABLE_COMPANIES = "CREATE TABLE IF NOT EXISTS `coupon_master`.`companies` (`ID` int(11) NOT NULL AUTO_INCREMENT, `NAME` varchar(50) NOT NULL, `EMAIL` varchar(50) NOT NULL, " +
            "`PASSWORD` varchar(50) NOT NULL, PRIMARY KEY (`ID`))";
    public static final String DROP_TABLE_COMPANIES = "DROP TABLE IF EXISTS `coupon_master`.`companies`";

    public static final String CREATE_TABLE_COUPONS = "CREATE TABLE IF NOT EXISTS `coupon_master`.`coupons` (`ID` int(11) NOT NULL AUTO_INCREMENT, `COMPANY_ID` int(11) DEFAULT NULL, `CATEGORY_ID` int(11) NOT NULL, " +
            "`TITLE` varchar(50) NOT NULL, `DESCRIPTION` varchar(200) NOT NULL, `START_DATE` date NOT NULL, `END_DATE` date NOT NULL, " +
            "`AMOUNT` int(11) NOT NULL,`PRICE` double NOT NULL, `IMAGE` varchar(200) NOT NULL, PRIMARY KEY (`ID`), " +
            " FOREIGN KEY (`COMPANY_ID`) REFERENCES `companies` (`ID`) ON DELETE CASCADE, FOREIGN KEY (`CATEGORY_ID`) REFERENCES `categories` (`ID`))";
    public static final String DROP_TABLE_COUPONS = "DROP TABLE IF EXISTS `coupon_master`.`coupons`";

    public static final String CREATE_TABLE_CUSTOMERS = "CREATE TABLE IF NOT EXISTS  `coupon_master`.`customers` (`ID` int(11) NOT NULL AUTO_INCREMENT, `FIRST_NAME` varchar(50) NOT NULL,`LAST_NAME` varchar(50) NOT NULL, " +
            "`EMAIL` varchar(50) NOT NULL, `PASSWORD` varchar(50) NOT NULL, PRIMARY KEY (`ID`))";
    public static final String DROP_TABLE_CUSTOMERS = "DROP TABLE IF EXISTS `coupon_master`.`customers` IF EXISTS";

    public static final String CREATE_TABLE_CUSTOMERS_VS_COUPONS = "CREATE TABLE IF NOT EXISTS `coupon_master`.`customers_vs_coupons` (`CUSTOMER_ID` int(11) NOT NULL, `COUPON_ID` int(11) NOT NULL, " +
            "PRIMARY KEY (`CUSTOMER_ID`,`COUPON_ID`), FOREIGN KEY (`CUSTOMER_ID`) REFERENCES `customers` (`ID`) ON DELETE CASCADE, FOREIGN KEY (`COUPON_ID`) REFERENCES `coupons` (`ID`) ON DELETE CASCADE)";
    public static final String DROP_TABLE_CUSTOMERS_VS_COUPONS = "DROP TABLE IF EXISTS `coupon_master`.`customers_vs_coupons`";


    public static boolean createDataBase() {
        return DBUtilities.runQuery(CREATE_DB);
    }

    public static boolean dropDataBase() {
        return DBUtilities.runQuery(DROP_DB);
    }

    /**
     * Creating the table CATEGORIES and inserting all of the category enum options into the table
     * @return indication whether the table was created or not
     */
    public static boolean createTableCategories() {
        boolean check = DBUtilities.runQuery(CREATE_TABLE_CATEGORIES);
        for (int counter = 0; counter < Category.categories.length; counter += 1) {
            Map<Integer, Object> parameters = new HashMap<>();
            parameters.put(1, Category.categories[counter].toString());
            DBUtilities.runQueryWithMap(ADD_CATEGORY, parameters);
        }
        return check;
    }

    public static boolean dropTableCategories() {
        return DBUtilities.runQuery(DROP_TABLE_CATEGORIES);
    }


    public static boolean createTableCompanies() {
        return DBUtilities.runQuery(CREATE_TABLE_COMPANIES);
    }

    public static boolean dropTableCompanies() {
        return DBUtilities.runQuery(DROP_TABLE_COMPANIES);
    }


    public static boolean createTableCoupons() {
        return DBUtilities.runQuery(CREATE_TABLE_COUPONS);
    }

    public static boolean dropTableCoupons() {
        return DBUtilities.runQuery(DROP_TABLE_COUPONS);
    }


    public static boolean createTableCustomers() {
        return DBUtilities.runQuery(CREATE_TABLE_CUSTOMERS);
    }

    public static boolean dropTableCustomers() {
        return DBUtilities.runQuery(DROP_TABLE_CUSTOMERS);
    }


    public static boolean createTableCustomersVsCoupons() {
        return DBUtilities.runQuery(CREATE_TABLE_CUSTOMERS_VS_COUPONS);
    }

    public static boolean dropTableCustomersVsCoupons() {
        return DBUtilities.runQuery(DROP_TABLE_CUSTOMERS_VS_COUPONS);
    }
}