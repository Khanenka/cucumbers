package com.khanenka.cucumbers.entity;




public class Cucumber {
    private final double size; // Объем огурца

    public Cucumber(double size) {
        this.size = size;
    }
    
    public double getSize() {
        return size;
    }

    @Override
    public String toString() {
        return "Cucumber{" +
                "size=" + size +
                '}';
    }
}