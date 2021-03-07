package com.epam.task.six.objects;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class Dock {
    private final Logger LOGGER = LogManager.getLogger(Dock.class);
    private final Port PORT = Port.getInstance();

    public void process(Ship ship) {
        LOGGER.info("Started Ship №" + ship.getId() + " processing");
        if (ship.isLoaded()) {
            PORT.addContainers(ship.unloadShip());
        } else {
            int capacity = ship.getCapacity();
            int currentContainersNumber = ship.getContainersNumber();
            int containersToAdd = PORT.takeContainers(capacity - currentContainersNumber);
            ship.loadShip(containersToAdd);
        }
        LOGGER.info("Finished Ship №" + ship.getId() + " processing");
    }
}
