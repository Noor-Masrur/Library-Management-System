package com.tigerit.LMS.decorators;

import com.tigerit.LMS.entities.Price;

public class CouponBasedDiscount extends PriceDiscountCalculator {

    private String coupon;

    public CouponBasedDiscount(Price changedPrice, String coupon){
        super(changedPrice);
        this.coupon = coupon;
    }

    @Override
    public double getPrice() {
        double revisedPrice = super.getPrice();
        if (coupon.equalsIgnoreCase("NEWYEAR2024")) {
            revisedPrice -= revisedPrice*0.25;
        } else if (coupon.equalsIgnoreCase("BLACKFRIDAY")) {
            revisedPrice -= revisedPrice*0.35;
        }
        return revisedPrice;

    }
}
