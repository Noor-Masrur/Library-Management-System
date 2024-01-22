package com.tigerit.LMS.entities;

public class BuyingPrice implements Price{
    private double priceInTaka;

    public BuyingPrice(double priceInTaka){
        this.priceInTaka = priceInTaka;
    }

    @Override
    public double getPrice(){
        return priceInTaka;
    }
}
