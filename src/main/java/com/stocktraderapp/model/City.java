package com.stocktraderapp.model;

public enum City {
    BDP("Budapest"),
    MSC("Miskolc"),
    KRK("Krakow"),
    WRS("Warsaw");

    private final String cityName;

    public String getCityName() {
        return cityName;
    }

    City(String cityName) {
        this.cityName = cityName;
    }

}
