package com.example.toyrooms;

import java.util.*;
import java.io.Serializable;

public class Room implements Serializable {
    private String title;
    private int age;
    private int price;
    private List<Toy> toys;

    public Room(String title, int age, int price) {
        this.title = title;
        this.age = age;
        this.price = price;
        this.toys = new ArrayList<>();
    }

    public String getTitle(){
        return title;
    }

    public int getAge(){
        return age;
    }

    public int getPrice(){
        return price;
    }

    public List<Toy> getToys() {
        return toys;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public void deleteToy(Toy toy){
        toys.remove(toy);
    }

    @Override
    public String toString() {
        return "Room : " +
                "title : '" + title + '\'' +
                ", kids age : " + age +
                ", price : " + (price / 100 + "." + price / 10 % 10 + price % 10) +
                ", " + toys.size() + " toys";
    }

    public String strPrice(){
        return price / 100 + "." + price / 10 % 10 + price % 10;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Room room = (Room) o;
        return age == room.age && price == room.price && Objects.equals(title, room.title) && Objects.equals(toys, room.toys);
    }

    @Override
    public int hashCode() {
        return Objects.hash(title, age, price, toys);
    }

    public int totalPrice(){
        int total = 0;
        for (Toy toy : toys){
            total += toy.getPrice();
        }
        return total;
    }

    public void addToy(Toy toy) {
        if (toy.getPrice() + totalPrice() > price)
            System.out.println("Error : no more free money! ");
        else {
            this.toys.add(toy);
            System.out.println("Toy added");
        }
    }

    public void printToys() {
        for (Toy toy : getToys())
            System.out.println(toy);
    }

    public void sortToys(List<Toy> toys){
        Collections.sort(toys);
    }

    public List<Toy> findToys(String name, String size){
        ArrayList<Toy> foundToys = new ArrayList<>();
        for (Toy toy : getToys()){
            if (toy.getName().toLowerCase().contains(name) && toy.getSize().toLowerCase().contains(size))
                foundToys.add(toy);
        }
        return foundToys;
    }

    public void findToysBySize(String size){
        for (Toy toy : getToys()){
            if (toy.getSize().toLowerCase().contains(size))
                System.out.println(toy);
        }
    }
    public void findToysByPrice(int minPrice, int maxPrice){
        for (Toy toy : getToys()){
            if (toy.getPrice() >= minPrice && toy.getPrice() <= maxPrice)
                System.out.println(toy);
        }
    }
}
