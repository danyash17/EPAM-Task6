package com.epam.task.six.main;

import com.epam.task.six.objects.Port;
import com.epam.task.six.objects.Ship;
import com.epam.task.six.objects.Ships;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.log4j.BasicConfigurator;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class Main {
    private static final String PATH = "src/main/resources/input.json";

    public static void main(String[] args) throws JsonProcessingException {
        System.out.println("START\nContainers in port " + Port.getInstance().getContainersNumber());
        Port.getInstance().getContainersNumber();
        BasicConfigurator.configure();
        ObjectMapper objectMapper = new ObjectMapper();
        Ships shipsMap = new Ships();
        try {
            shipsMap = objectMapper.readValue(new File(PATH), Ships.class);
        } catch (IOException e) {
            e.printStackTrace();
        }
        List<Ship> ships = shipsMap.getShips();
        final ExecutorService executorService = Executors.newFixedThreadPool(ships.size());
        ships.forEach(ship -> executorService.submit(ship));
        executorService.shutdown();
        try {
            TimeUnit.SECONDS.sleep(2);
            System.out.println("END\nContainers in port " + Port.getInstance().getContainersNumber());
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
