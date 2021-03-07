package com.epam.task.six.objects;

import java.util.Objects;

public class Ship implements Runnable {
    private int capacity;
    private int containersNumber;
    private boolean loaded;
    private int id;

    public Ship(int id, int capacity, int containersNumber) {
        this.id = id;
        this.capacity = capacity;
        this.containersNumber = containersNumber;
        if (containersNumber >= capacity) {
            this.containersNumber = capacity;
            loaded = true;
        } else {
            this.containersNumber = containersNumber;
            loaded = false;
        }
    }

    public Ship() {
    }

    public int getContainersNumber() {
        return containersNumber;
    }

    public int getCapacity() {
        return capacity;
    }

    public boolean isLoaded() {
        return loaded;
    }

    public int getId() {
        return id;
    }

    public int unloadShip() {
        containersNumber = 0;
        loaded = false;
        return capacity;
    }

    public int loadShip(int containersNumber) {
        if (containersNumber > capacity) {
            this.containersNumber = capacity;
            loaded = true;
            return capacity - containersNumber;
        } else {
            this.containersNumber += containersNumber;
            if (this.containersNumber == capacity) {
                loaded = true;
            }
            return 0;
        }
    }

    public void run() {
        Port port = Port.getInstance();
        try {
            port.process(this);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}