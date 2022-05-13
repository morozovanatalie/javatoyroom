package com.example.toyrooms;

import java.io.Console;
import java.util.*;
import java.io.Serializable;

public class Centre implements Serializable {
    private List<Room> rooms;

    public Centre() {
        this.rooms = new ArrayList<>();
    }

    public List<Room> getRooms() {
        return rooms;
    }

    public void deleteRoom(Room room){
        rooms.remove(room);
    }

    @Override
    public String toString() {
        return "Rooms : " + rooms;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Centre centre = (Centre) o;
        return Objects.equals(rooms, centre.rooms);
    }

    @Override
    public int hashCode() {
        return Objects.hash(rooms);
    }

    public void addRoom(Room room) {
        this.rooms.add(room);
        System.out.println("Room added");
    }

    public boolean findRoomByName(String name){
        for (Room room : getRooms()){
            if (room.getTitle().toLowerCase().contains(name))
                return false;
        }
        return true;
    }
}
