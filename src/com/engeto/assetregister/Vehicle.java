package com.engeto.assetregister;

import java.io.File;
import java.io.FileNotFoundException;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Vehicle extends Asset {
    private int mileage;
    private LocalDate lastRoadworthinessTest;

    public Vehicle(
            int id, String description,
            int mileage, LocalDate lastRoadworthinessTest) {
        super(id, description);
        this.mileage = mileage;
        this.lastRoadworthinessTest = lastRoadworthinessTest;
    }

    public static List<Vehicle> loadVehicles() throws RegisterException {
        String path = Settings.VEHICLES_PATHNAME;
        List<Vehicle> result = new ArrayList<>();
        String parseItem = "";
        int lineNo = 1;
        try (Scanner scanner = new Scanner(new File(path))) {
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                String[] items = line.split(Settings.getDelimiter());
                if (items.length != 4) {
                    throw new RegisterException(
                            "Nesprávný počet položek na řádku č. "
                                    +lineNo+":\n"+line);
                }
                parseItem = items[0];
                int id = Integer.parseInt(parseItem);
                parseItem = items[2];
                int mileage = Integer.parseInt(parseItem);
                parseItem = items[3];
                LocalDate lastRoadWorthiness = LocalDate.parse(parseItem);
                Vehicle vehicle =
                        new Vehicle(id, items[1], mileage, lastRoadWorthiness);
                result.add(vehicle);
                lineNo++;
            }
        } catch (NumberFormatException e) {
            throw new RegisterException(
                    "Nesprávný formát čísla "+parseItem+" na řádku č. "+lineNo
                            +" v souboru "+path+" : "+e.getLocalizedMessage());
        } catch (DateTimeParseException e) {
            throw new RegisterException(
                    "Nesprávný formát data "+parseItem+" na řádku č. "+lineNo
                            +" v souboru "+path+" : "+e.getLocalizedMessage());
        } catch (FileNotFoundException e) {
            throw new RegisterException(
                    "Soubor "+path+" se nepodařilo najít: "+e.getLocalizedMessage());
        }
        return result;
    }

    public int getMileage() {
        return mileage;
    }

    public void setMileage(int mileage) {
        this.mileage = mileage;
    }

    public LocalDate getLastRoadworthinessTest() {
        return lastRoadworthinessTest;
    }

    public void setLastRoadworthinessTest(LocalDate lastRoadworthinessTest) {
        this.lastRoadworthinessTest = lastRoadworthinessTest;
    }

    @Override
    public String toString() {
        return "Vehicle{" +
                getId() + ": " + getDescription() + ", " + mileage + " km, lastRoadworthinessTest=" + lastRoadworthinessTest +
                '}';
    }
}
