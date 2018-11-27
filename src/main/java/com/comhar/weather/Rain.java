package com.comhar.weather;

import com.fasterxml.jackson.annotation.JsonSetter;

public class Rain {
    private double threeHourRain;

    public double getThreeHourRain() {
        return threeHourRain;
    }

    @JsonSetter("1h")
    public void setThreeHourRain(double threeHourRain) {
        this.threeHourRain = threeHourRain;
    }
}
