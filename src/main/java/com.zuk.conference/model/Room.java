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

    public String getName() {
        return name;
    }

    public int getFirstFloorCapacity() {
        return firstFloorCapacity;
    }

    public int getSecondFloorCapacity() {
        return secondFloorCapacity;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public static Room.Builder newBuilder() {
        return new Room().new Builder();
    }

    public class Builder {

        private Builder() {
        }

        public Builder setId(int id) {
            Room.this.id = id;
            return this;
        }

        public Builder setName(String name) {
            Room.this.name = name;
            return this;
        }

        public Builder setFirstFloorCapacity(int firstFloorCapacity) {
            Room.this.firstFloorCapacity = firstFloorCapacity;
            return this;
        }

        public Builder setSecondFloorCapacity(int secondFloorCapacity) {
            Room.this.secondFloorCapacity = secondFloorCapacity;
            return this;
        }

        public Room build() {
            return Room.this;
        }
    }
}
