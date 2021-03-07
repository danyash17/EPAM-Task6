package com.epam.task.six.objects;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.ArrayDeque;
import java.util.concurrent.Semaphore;
import java.util.concurrent.atomic.AtomicReference;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class Port {

    private static final Port instance = new Port();
    private final int DOCK_AMOUNT = 5;
    private final int CONTAINERS_NUMBER_DEFAULT = 2000;
    private final Logger LOGGER = LogManager.getLogger(Port.class);
    private final ArrayDeque<Dock> docks = new ArrayDeque<Dock>();
    private static final Lock LOCK = new ReentrantLock();
    private static final AtomicReference<Port> reference = new AtomicReference<Port>();
    private final Semaphore SEMAPHORE = new Semaphore(DOCK_AMOUNT, true);
    private int containersNumber;

    private Port() {
    }

    public static Port getInstance() {
        if (reference.compareAndSet(null, instance)) {
            LOCK.lock();
            try {
                instance.init();
                reference.set(instance);
            } finally {
                LOCK.unlock();
            }
        }
        return reference.get();
    }

    public int getContainersNumber() {
        return containersNumber;
    }

    private void init() {
        containersNumber = CONTAINERS_NUMBER_DEFAULT;
        for (int i = 0; i < DOCK_AMOUNT; i++) {
            Dock dock = new Dock();
            docks.add(dock);
        }
    }

    public void process(Ship ship) throws InterruptedException {
        LOGGER.info("Ship №" + ship.getId() + " is trying to get a Dock");
        SEMAPHORE.acquire();
        LOCK.lock();
        try {
            Dock dock = docks.pollFirst();
            LOGGER.info("Ship №" + ship.getId() + " got Dock");
            dock.process(ship);
            docks.addLast(dock);
        } finally {
            SEMAPHORE.release();
            LOCK.unlock();
        }
    }

    public void addContainers(int amount) {
        LOGGER.info(containersNumber);
        containersNumber += amount;
        LOGGER.info("+" + amount);
    }

    public int takeContainers(int amount) {
        LOGGER.info(containersNumber);
        if (amount <= containersNumber) {
            containersNumber -= amount;
            LOGGER.info("-" + amount);
            return amount;
        } else {
            int remaining = containersNumber;
            containersNumber = 0;
            LOGGER.info("-" + remaining);
            return remaining;
        }
    }
}
