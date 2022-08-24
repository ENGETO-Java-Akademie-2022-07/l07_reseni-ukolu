package com.engeto.assetregister;

import java.util.ArrayList;
import java.util.List;

public class Main {

    public static void main(String[] args) {
        AssetsRegister register = loadFromFiles();
        System.out.println(register);
    }

    private static AssetsRegister loadFromFiles() {
        List<Asset> assets = new ArrayList<>();
        List<Assignable> assignables = new ArrayList<>();
        AssetsRegister register = new AssetsRegister();

        try {
            assets.addAll(Vehicle.loadVehicles());
            assets.addAll(Computer.loadComputers());
            assignables.addAll(Room.loadRooms());
            assignables.addAll(Employee.loadEmployees());

            register.loadAllocation(assets, assignables);
        } catch (RegisterException e) {
            System.err.println(e.getLocalizedMessage());
        }
        return register;
    }
}
