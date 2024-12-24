package com.khanenka.cucumbers.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.ArrayList;
import java.util.List;

public class Jar {
    private final List<Cucumber> cucumbers; // Лист огурцов в банке
    private final double maxVolume; // Максимальный объем банки

    private double currentVolume;

    public Jar(double maxVolume) {
        this.cucumbers = new ArrayList<>();
        this.maxVolume = maxVolume;
        this.currentVolume = 0;
    }

    public double getRemainingVolume() {
        return maxVolume - currentVolume;
    }

    public boolean addCucumber(Cucumber cucumber) {
        //todo: optimize getRemainingVolume
        double remainingVolume = getRemainingVolume();
        if (cucumber.getSize() <= remainingVolume) {
            cucumbers.add(cucumber);
            currentVolume += cucumber.getSize();
            return true;
        } else {
            // Если огурец не помещается целиком, отрезаем часть
            double fittingPart = remainingVolume;
            cucumbers.add(new Cucumber(fittingPart));
            currentVolume += fittingPart;
            return false;
        }
    }
    public List<Cucumber> getCucumbers() {
        return cucumbers;
    }

    @Override
    public String toString() {
        return "Jar{" +
                "cucumbers=" + cucumbers +
                ", maxVolume=" + maxVolume +
                '}';
    }
}
