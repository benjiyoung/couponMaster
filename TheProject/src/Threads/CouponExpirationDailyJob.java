package Threads;

import Beans.Coupon;
import DBDAO.CouponDBDAO;

import java.time.LocalDate;
import java.util.*;
import java.util.concurrent.TimeUnit;

public class CouponExpirationDailyJob {
    private CouponDBDAO couponDBDAO = new CouponDBDAO();
    boolean quit;
    ArrayList<Coupon> coupons;

    /**
     * CTOR of the thread, sets the time of day it will be run, what is the period between every run,
     the action itself, which is to delete all coupons with passed expiration date, and the condition for it to stop.
     */
    public CouponExpirationDailyJob(){
        //set time of job
        Calendar firstCheck =Calendar.getInstance();
        firstCheck.set(Calendar.HOUR_OF_DAY,23);
        firstCheck.set(Calendar.MINUTE,59);
        firstCheck.set(Calendar.SECOND,59);

        //every night at midnight you run your task
        Timer timer = new Timer();
        //period: 1 day
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                coupons = couponDBDAO.getAllCoupons();
                while (!quit) {
                    for (Coupon item : coupons) {
                        if (item.getEndDate().isBefore(LocalDate.now()) || item.getEndDate().isEqual(LocalDate.now())) {
                            couponDBDAO.deleteCoupon(item.getId());
                        }
                    }
                }
            }
        }, firstCheck.getTime(), TimeUnit.MILLISECONDS.convert(1, TimeUnit.DAYS));
    }

    /**
     * A method that changes the value of attribute quit to true, which will stop the thread from running.
     */
    public void stopDailyJob() {
        quit = true;
    }
}