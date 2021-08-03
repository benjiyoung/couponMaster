package Exceptions;

public class CouponMasterException extends Exception{
    public CouponMasterException() {
        super("A general Coupon Master error occurred!");
    }

    public CouponMasterException(String message) {
        super(message);
    }
}
