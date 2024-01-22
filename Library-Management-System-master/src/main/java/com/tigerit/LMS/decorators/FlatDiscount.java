package com.tigerit.LMS.decorators;

import com.tigerit.LMS.entities.Price;

public class FlatDiscount extends PriceDiscountCalculator{
    public FlatDiscount(Price changedPrice){
        super(changedPrice);
    }

    @Override
    public double getPrice(){
        double revisedPrice = super.getPrice();
        revisedPrice -= revisedPrice * 0.10;
        return revisedPrice;
    }
}
