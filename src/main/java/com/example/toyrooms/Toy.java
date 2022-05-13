package com.example.toyrooms;

import java.util.*;
import java.io.Serializable;

public class Toy implements Comparable <Toy>, Serializable  {
    private String name;
    private String size;
    private int price;

    public Toy(String name, String size, int price) {
        this.name = name;
        this.size = size;
        this.price = price;
    }

    public String getName(){
        return name;
    }

    public String getSize(){
        return size;
    }

    public int getPrice(){
        return price;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setSize(String size) {
        this.size = size;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    @Override
    public String toString() {
        return " " + size + " " + name + " price : " + (price / 100 + "." + price / 10 % 10 + price % 10);
    }

    public String strPrice(){
        return price / 100 + "." + price / 10 % 10 + price % 10;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Toy toy = (Toy) o;
        return Objects.equals(name, toy.name) && Objects.equals(size, toy.size) && price == toy.price;
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, size, price);
    }

    @Override
    public int compareTo(Toy o) {
        return price - o.price;
    }
}
