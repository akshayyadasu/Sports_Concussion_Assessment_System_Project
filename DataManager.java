import java.util.Scanner;
import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class DataManager {
    private List<Athlete> allAthletes = new ArrayList<>();
    private List<Doctor> allDoctors = new ArrayList<>();

    public void addAthlete(Athlete athlete) {
        allAthletes.add(athlete);
    }

    public void addDoctor(Doctor doctor) {
        allDoctors.add(doctor);
    }

    public List<Athlete> getAllAthletes() {
        return allAthletes;
    }

    public List<Doctor> getAllDoctors() {
        return allDoctors;
    }

    public void linkAthleteToDoctor(Athlete athlete, Doctor doctor) {
        doctor.addAthlete(athlete);
        athlete.setDoctor(doctor);
    }

}