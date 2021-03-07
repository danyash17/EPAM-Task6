package com.epam.task.six.objects;


import java.util.List;
import java.util.ArrayList;

public class Ships {
    private List<Ship> ships = new ArrayList<>();

    public Ships() {
    }

    public Ships(List<Ship> ships) {
        this.ships = ships;
    }

    public List<Ship> getShips() {
        return ships;
    }
}
