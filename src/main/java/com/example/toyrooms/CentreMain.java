package com.example.toyrooms;

import java.util.*;
import java.io.*;

import static java.lang.System.exit;

public class CentreMain {
    static Centre centre;

    public static void printMenu(String[] options){
        System.out.println("Options available : ");
        for (String option : options){
            System.out.println(option);
        }
        System.out.print("Your choice : ");
    }
    /*public static void main(String[] args){
        centre = new Centre();
        createDefault();
        String[] options = {" 1 - Add a room",
                " 2 - Add a toy",
                " 3 - Print all toys",
                " 4 - Sort toys by price",
                " 5 - Print all rooms",
                " 6 - Find a toy",
                " 7 - Write to file",
                " 8 - Read from file",
                " 9 - Exit",
        };

        Scanner scanner = new Scanner(System.in);
        int option = 1;

        while (option != 9) {
            printMenu(options);
            try {
                option = Integer.parseInt(scanner.nextLine());
                if (option < 1 || option  > 9){
                    throw new CustomException("Please enter an integer value between 1 and 9");
                }
                switch (option) {
                    case 1: addRoom(scanner); break;
                    case 2: addToy(scanner); break;
                    case 3: printToys(); break;
                    case 4: sortToys(); break;
                    case 5: printAllRooms(); break;
                    case 6: findToy(scanner); break;
                    case 7: writeToFile(); break;
                    case 8: readFromFile(); break;
                    case 9:
                        System.out.println(" Bye :) ");
                        exit(0);
                }
            }
            catch (Exception e){
                System.out.println("Please enter an integer value between 1 and 9");
            }
        }
    }*/

    public static void createDefault(){
        Room room1 = new Room("Sunny", 7, 56890);
        centre.addRoom(room1);
        room1.addToy(new Toy("car", "small", 506));
        room1.addToy(new Toy("doll", "big", 873));
        room1.addToy(new Toy("cube", "medium", 1250));
        room1.addToy(new Toy("ball", "small", 2669));
        Room room2 = new Room("Moony", 11, 7809);
        centre.addRoom(room2);
        room2.addToy(new Toy("cube", "medium", 1508));
        room2.addToy(new Toy("doll", "small", 2687));
        room2.addToy(new Toy("cube", "medium", 1956));
        Room room3 = new Room("Lollipop", 6, 11265);
        centre.addRoom(room3);
        room3.addToy(new Toy("ball", "big", 326));
        room3.addToy(new Toy("car", "medium", 1836));
        room3.addToy(new Toy("car", "big", 982));
    }


    private static void addRoom(Scanner scanner) {
        try {
            System.out.print("Enter room title : ");
            String title = scanner.nextLine();
            System.out.print("Enter kids age (2 - 12) : ");
            int age = Integer.parseInt(scanner.nextLine());
            if (age > 12 || age < 2) {
                //age = 8;
                throw new CustomException("Wrong age!");
            }
            System.out.print("Enter room price (in cents) : ");
            int price = Integer.parseInt(scanner.nextLine());
            centre.addRoom(new Room(title, age, price));
        }
        catch (CustomException ex){
            System.out.println("Wrong age!");
        }
        catch (Exception e){
            System.out.println("Incorrect data, please try again");
        }
    }

    private static void printAllRooms() {
        for (Room room: centre.getRooms()) {
            System.out.println(room);
        }
    }

    private static void addToy(Scanner scanner) {
        try {
            System.out.println(" Chose a room : ");
            for (int i = 0; i < centre.getRooms().size(); i++) {
                System.out.println((i + 1) + " - " + centre.getRooms().get(i));
            }
            int option = Integer.parseInt(scanner.nextLine()) - 1;
            if (option < 0 || option >= centre.getRooms().size()) {
                System.out.println("Room doesn't exist!");
                return;
            }
            Room room = centre.getRooms().get(option);
            String name = "name";
            String[] nameOptions = {" 1 - car",
                    " 2 - doll",
                    " 3 - ball",
                    " 4 - cube",
            };
            System.out.println("Toy name : ");
            printMenu(nameOptions);
            try {
                int nameOption = Integer.parseInt(scanner.nextLine());
                if (nameOption <1 || nameOption > 4){
                    throw new CustomException("Option doesn't exist");
                }
                switch (nameOption) {
                    case 1: name = "car"; break;
                    case 2: name = "doll"; break;
                    case 3: name = "ball"; break;
                    case 4: name = "cube"; break;
                }
            }
            catch (CustomException ex){
                System.out.println("Option doesn't exist, please try again");
                return;
            }
            catch (Exception e){
                System.out.println("Incorrect data, please try again");
                return;
            }
        /*System.out.println("Enter toy name : car, doll, ball or cube : ");
        String name = scanner.nextLine();
        System.out.println("Enter toy size : big, medium or small : ");
        String size = scanner.nextLine();*/
            String size = "size";
            String[] sizeOptions = {" 1 - small",
                    " 2 - medium",
                    " 3 - big",
            };
            System.out.println("Toy size : ");
            printMenu(sizeOptions);
            try {
                int sizeOption = Integer.parseInt(scanner.nextLine());
                if (sizeOption <1 || sizeOption > 3){
                    throw new CustomException("Option doesn't exist, please try again");
                }
                switch (sizeOption) {
                    case 1: size = "small"; break;
                    case 2: size = "medium"; break;
                    case 3: size = "big"; break;
                }
            }
            catch (CustomException ex){
                System.out.println("Option doesn't exist, please try again");
                return;
            }
            catch (Exception e){
                System.out.println("Incorrect data, please try again");
                return;
            }
            System.out.println("Enter toy price (in cents) : ");
            int price = Integer.parseInt(scanner.nextLine());
            Toy toy = new Toy(name, size, price);
            room.addToy(toy);
        }
        catch (Exception e){
            System.out.println("Incorrect data, please try again");
        }
    }

    private static void printToys() {
        for (Room room: centre.getRooms()) {
            System.out.println("Room '" + room.getTitle() + "' : ");
            room.printToys();
        }
    }

    private static void sortToys(){
        for (Room room: centre.getRooms()) {
            room.sortToys(room.getToys());
        }
        printToys();
    }

    private static void findToy(Scanner scanner){
        try {
            String[] searchOptions = {" 1 - Find by name",
                    " 2 - Find by size",
                    " 3 - Find by price",
            };
            printMenu(searchOptions);
            int searchOption = Integer.parseInt(scanner.nextLine());
            if (searchOption < 1 || searchOption > 3){
                throw new CustomException("Option doesn't exist, please try again");
            }
            switch (searchOption){
                case 1: findByName(scanner); break;
                case 2: findBySize(scanner); break;
                case 3: findByPrice(scanner); break;
            }
        }
        catch (CustomException ex){
            System.out.println("Option doesn't exist, please try again");
        }
        catch (Exception e){
            System.out.println("Incorrect data, please try again");
        }
    }

    private static void findByName(Scanner scanner){
        try {
            String name = "name";
            String[] nameOptions = {" 1 - car",
                    " 2 - doll",
                    " 3 - ball",
                    " 4 - cube",
            };
            System.out.println("Toy name : ");
            printMenu(nameOptions);
            int nameOption = Integer.parseInt(scanner.nextLine());
            if (nameOption <1 || nameOption > 4){
                throw new CustomException("Option doesn't exist");
            }
            switch (nameOption) {
                case 1: name = "car"; break;
                case 2: name = "doll"; break;
                case 3: name = "ball"; break;
                case 4: name = "cube"; break;
            }
            for (Room room: centre.getRooms()) {
                System.out.println("Room '" + room.getTitle() + "' found toys : ");
//                room.findToysByName(name);
            }
        }
        catch (CustomException ex){
            System.out.println("Option doesn't exist, please try again");
        }
        catch (Exception e){
            System.out.println("Incorrect data, please try again");
        }
    }

    private static void findBySize(Scanner scanner){
        try {
            String size = "size";
            String[] sizeOptions = {" 1 - small",
                    " 2 - medium",
                    " 3 - big",
            };
            System.out.println("Toy size : ");
            printMenu(sizeOptions);
            int sizeOption = Integer.parseInt(scanner.nextLine());
            if (sizeOption <1 || sizeOption > 3){
                throw new CustomException("Option doesn't exist, please try again");
            }
            switch (sizeOption) {
                case 1: size = "small"; break;
                case 2: size = "medium"; break;
                case 3: size = "big"; break;
            }
            for (Room room: centre.getRooms()) {
                System.out.println("Room '" + room.getTitle() + "' found toys : ");
                room.findToysBySize(size);
            }
        }
        catch (CustomException ex){
            System.out.println("Option doesn't exist, please try again");
        }
        catch (Exception e){
            System.out.println("Incorrect data, please try again");
        }
    }

    private static void findByPrice(Scanner scanner){
        try {
            System.out.println("Enter minimal price (in cents) : ");
            int minPrice = Integer.parseInt(scanner.nextLine());
            System.out.println("Enter maximum price (in cents) : ");
            int maxPrice = Integer.parseInt(scanner.nextLine());
            if (maxPrice <= minPrice){
                throw new CustomException("Incorrect data, please try again");
            }
            for (Room room: centre.getRooms()) {
                System.out.println("Room '" + room.getTitle() + "' found toys : ");
                room.findToysByPrice(minPrice, maxPrice);
            }
        } catch (Exception ex){
            System.out.println("Incorrect data, please try again");
        }
    }

    public static void writeToFile() throws IOException {
        File outFile = new File("output.txt");
        OutputStream output = new FileOutputStream(outFile);
        ObjectOutputStream ooutput = new ObjectOutputStream(output);
        ooutput.writeObject(centre);
        ooutput.close();
    }

    public static void readFromFile() throws IOException, ClassNotFoundException, FileNotFoundException {
        try {
            File inFile = new File("output.txt");
            InputStream input = new FileInputStream(inFile);
            ObjectInputStream iinput = new ObjectInputStream(input);
            Centre readCentre = (Centre) iinput.readObject();
            for (Room room : readCentre.getRooms()){
                centre.addRoom(room);
            }
            iinput.close();
        }
        catch (FileNotFoundException e){
            System.out.println("File not found!");
        }
    }

}
