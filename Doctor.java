import java.util.Scanner;
import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Doctor {
    private String name;
    private String username;
    private String password;
    private List<Athlete> athletes = new ArrayList<>();

    public Doctor(String name, String username, String password) {
        this.name = name;
        this.username = username;
        this.password = password;
    }

    public void addAthlete(Athlete athlete) {
        athletes.add(athlete);
    }

    public List<Athlete> getAthletes() {
        return athletes;
    }

    public void printAthleteNames() {
        if (athletes.size() == 0) {
            System.out.println("You have no athletes assigned to you");
        } else {
            System.out.println("These are the athletes registered to you:");
            for (int i = 0; i < athletes.size(); i++) {
                //int num = i + 1;
                System.out.println(i+1 + ". " + athletes.get(i).getName());
            }
            System.out.println("");
        }
    }

    public Athlete checkAndReturnAthlete(String name) {
        for (Athlete athlete : athletes) {
            if (athlete.getName().equals(name)) {
                return athlete;
            }
        }
        //System.out.println("Athlete with that name doesn't exist, make sure you don't enter any extra whitespaces");
        return null;
    }

    public boolean authenticate(String username, String password) {
        return this.username.equals(username) && this.password.equals(password);
    }
}