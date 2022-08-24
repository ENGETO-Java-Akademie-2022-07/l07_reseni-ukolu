package com.engeto.assetregister;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Employee implements Assignable {
    private String firstname;
    private String surname;
    private String jobTitle;

    public Employee(String firstname, String surname, String jobTitle) {
        this.firstname = firstname;
        this.surname = surname;
        this.jobTitle = jobTitle;
    }

    public static List<Employee> loadEmployees() throws RegisterException {
        String path = Settings.EMPLOYEES_PATHNAME;
        List<Employee> result = new ArrayList<>();
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
                Employee next =
                        new Employee(items[0], items[1], items[2]);
                result.add(next);
                lineNo++;
            }
        } catch (FileNotFoundException e) {
            throw new RegisterException(
                    "Soubor "+path+" se nepodařilo najít: "+e.getLocalizedMessage());
        }
        return result;
    }

    @Override
    public String getDescription() {
        return firstname + " "+ surname+ " ("+ jobTitle +")";
    }

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getJobTitle() {
        return jobTitle;
    }

    public void setJobTitle(String jobTitle) {
        this.jobTitle = jobTitle;
    }

    @Override
    public String toString() {
        return "Employee{" + firstname + ' ' + surname + " (" + jobTitle + ')' +
                '}';
    }
}
