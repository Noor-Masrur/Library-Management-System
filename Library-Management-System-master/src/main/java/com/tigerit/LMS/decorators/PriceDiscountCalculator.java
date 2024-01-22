package com.tigerit.LMS.decorators;

import com.tigerit.LMS.entities.Price;

public abstract class PriceDiscountCalculator implements Price {
    protected Price changedPrice;

    public PriceDiscountCalculator(Price changedPrice){
        this.changedPrice = changedPrice;
    }

    @Override
    public double getPrice(){
        return changedPrice.getPrice();
    }
}
