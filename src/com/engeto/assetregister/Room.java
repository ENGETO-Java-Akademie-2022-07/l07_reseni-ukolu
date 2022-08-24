package com.engeto.assetregister;

import java.io.File;
import java.io.FileNotFoundException;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Room implements Assignable {
    int roomNo;
    String building;

    @Override
    public String getDescription() {
        String result = "Room "+ roomNo;
        if (! building.isEmpty()) result +=" ("+building+")";
        return result;
    }

    public Room(int roomNo, String building) {
        this.roomNo = roomNo;
        this.building = building;
    }

    public static List<Room> loadRooms() throws RegisterException {
        String path = Settings.ROOMS_PATHNAME;
        List<Room> result = new ArrayList<>();
        String parseItem = "";
        int lineNo = 1;
        try (Scanner scanner = new Scanner(new File(path))) {
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                String[] items = line.split(Settings.getDelimiter());
                if (items.length != 2) {
                    throw new RegisterException(
                            "Nesprávný počet položek na řádku č. "
                                    +lineNo+":\n"+line);
                }
                parseItem = items[0];
                int id = Integer.parseInt(parseItem);
                Room room = new Room(id, items[1]);
                result.add(room);
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

    public int getRoomNo() {
        return roomNo;
    }

    public void setRoomNo(int roomNo) {
        this.roomNo = roomNo;
    }

    public String getBuilding() {
        return building;
    }

    public void setBuilding(String building) {
        this.building = building;
    }

    @Override
    public String toString() {
        return "Room{" + roomNo + " " + building + '}';
    }
}
