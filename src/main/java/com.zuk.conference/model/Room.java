package com.zuk.conference.model;

public class Room {
    private int id;
    private String name;
    private int firstFloorCapacity;
    private int secondFloorCapacity;

    public Room(int id, String name, int firstFloorCapacity, int secondFloorCapacity) {
        this.id = id;
        this.name = name;
        this.firstFloorCapacity = firstFloorCapacity;
        this.secondFloorCapacity = secondFloorCapacity;
    }

    public Room() {

    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getFirstFloorCapacity() {
        return firstFloorCapacity;
    }

    public void setFirstFloorCapacity(int firstFloorCapacity) {
        this.firstFloorCapacity = firstFloorCapacity;
    }

    public int getSecondFloorCapacity() {
        return secondFloorCapacity;
    }

    public void setSecondFloorCapacity(int secondFloorCapacity) {
        this.secondFloorCapacity = secondFloorCapacity;
    }
}
