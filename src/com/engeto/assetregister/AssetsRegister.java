package com.engeto.assetregister;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

public class AssetsRegister {

    private final Map<Asset, Assignable> register = new HashMap<>();

    public void allocate(Asset asset, Assignable where) {
        register.put(asset, where);
    }

    public void remove(Asset asset) {
        register.remove(asset);
    }

    public void loadAllocation(
            List<Asset> assets, List<Assignable> assignables)
            throws RegisterException {
        String path = Settings.ASSIGN_PATHNAME;
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
                Asset foundAsset = null;
                for (Asset asset : assets) {
                    if (asset.getId() == id) {
                        foundAsset = asset;
                        break;
                    }
                }
                Assignable foundAssignable = null;
                for (Assignable assignable : assignables) {
                    if (assignable.getDescription().equals(items[1])) {
                        foundAssignable = assignable;
                        break;
                    }
                }
                if (foundAsset == null || foundAssignable == null) {
                    throw new RegisterException("Nesprávné přiřazení: "+foundAsset+" -> "+foundAssignable);
                }
                allocate(foundAsset, foundAssignable);
                lineNo++;
            }
        } catch (NumberFormatException e) {
            throw new RegisterException(
                    "Nesprávný formát čísla "+parseItem+" na řádku č. "+lineNo
                            +" v souboru "+path+" : "+e.getLocalizedMessage());
        } catch (FileNotFoundException e) {
            throw new RegisterException(
                    "Soubor "+path+" se nepodařilo najít: "+e.getLocalizedMessage());
        }
    }

    @Override
    public String toString() {
        StringBuilder result = new StringBuilder("AssetsRegister:\n");
        register.forEach((asset, assignable) -> result.append("* ").append(asset).append(" -> ").append(assignable).append("\n"));
        // Nebo také:
        //        for (Map.Entry<Asset, Assignable> entry : register.entrySet()) {
        //            result.append("* ").append(entry.getKey()).append(" -> ").append(entry.getValue()).append("\n");
        //        }
        return result.toString();
    }
}
