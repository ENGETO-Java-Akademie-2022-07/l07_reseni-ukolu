package com.engeto.assetregister;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Computer extends Asset {
    private ComputerType type;

    public Computer(int id, String description, ComputerType type) {
        super(id, description);
        this.type = type;
    }

    public static List<Computer> loadComputers() throws RegisterException {
        String path = Settings.COMPUTERS_PATHNAME;
        List<Computer> result = new ArrayList<>();
        String parseItem = "";
        int lineNo = 1;
        try (Scanner scanner = new Scanner(new File(path))) {
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                String[] items = line.split(Settings.getDelimiter());
                if (items.length != 3) {
                    throw new RegisterException(
                            "Nesprávný počet položek na řádku č. "
                                    +lineNo+":\n"+line);
                }
                parseItem = items[0];
                int id = Integer.parseInt(parseItem);
                parseItem = items[2];
                ComputerType type = ComputerType.valueOf(parseItem);
                Computer computer =
                        new Computer(id, items[1], type);
                result.add(computer);
                lineNo++;
            }
        } catch (NumberFormatException e) {
            throw new RegisterException(
                    "Nesprávný formát čísla "+parseItem+" na řádku č. "+lineNo
                            +" v souboru "+path+" : "+e.getLocalizedMessage());
        } catch (IllegalArgumentException e) {
            throw new RegisterException(
                    "Nesprávný typ počítače "+parseItem+" na řádku č. "+lineNo
                            +" v souboru "+path+" : "+e.getLocalizedMessage());
        } catch (FileNotFoundException e) {
            throw new RegisterException(
                    "Soubor "+path+" se nepodařilo najít: "+e.getLocalizedMessage());
        }
        return result;
    }

    public ComputerType getType() {
        return type;
    }

    public void setType(ComputerType type) {
        this.type = type;
    }

    @Override
    public String toString() {
        return "Computer{" +
                getId() +
                ": " + getDescription() +
                " (" + type +
                ")}";
    }
}
