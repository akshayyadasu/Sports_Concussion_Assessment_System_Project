import java.util.Scanner;
import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Athlete {
    private String name;
    private Doctor doctor;
    private String username;
    private String password;
    private List<int[]> symptomRecordings = new ArrayList<>();

    public Athlete(String name, String username, String password) {
        this.name = name;
        this.username = username;
        this.password = password;
    }

    public String getName() {
        return name;
    }

    public void setDoctor(Doctor doctor) {
        this.doctor = doctor;
    }

    private void calculateSummary(int[] prevGame, int[] currGame, int game) {
        int prevNumSymptoms = 0;
        int currNumSymptoms = 0;
        int prevSum = 0;
        int currSum = 0;
        for (int i = 0; i < 22; i++) {
            if (prevGame[i] > 0) {
                prevNumSymptoms++;
            }
            if (currGame[i] > 0) {
                currNumSymptoms++;
            }
            prevSum += prevGame[i];
            currSum += currGame[i];
        }
        // Now Calculate Severity Rating
        String severity = "";
        if ((prevNumSymptoms - currNumSymptoms < 3) && currSum < 10) {
            severity = "No Difference";
        } else if ((prevNumSymptoms - currNumSymptoms < 3) && currSum >= 10) {
            severity = "Unsure";
        } else if ((prevNumSymptoms - currNumSymptoms >= 3) || currSum >= 15) {
            severity = "Very Different";
        }
        
        System.out.println("\nGame " + game + " Symptom Summary:");
        System.out.println("\tTotal Number of Symptoms: " + currNumSymptoms);
        System.out.println("\tSymptom Severity Score: " + currSum);
        System.out.println("\tOverall Severity Rating: " + severity);
    }

    public void getSymptomRecordings() {
        if (symptomRecordings.size() == 0) {
            System.out.println("You don't have any data recorded, please record some data before you can see a summary");
        }
        /*Scanner input = new Scanner(System.in);
        System.out.println("You have " + symptomRecordings.size() + " games of data available");
        System.out.print("Enter which game would you like to see a Symptom Summary of: ");
        int option = Integer.parseInt(input.nextLine());
        while (option <= 0 || option > symptomRecordings.size()) {
            System.out.println("\nNot a valid option, try again");
            System.out.print("Enter which game would you like to see a Symptom Summary of: ");
            option = Integer.parseInt(input.nextLine());
        }*/
        // Now output the Summaries for all games
        // Start with the first game
        int[] prevData = symptomRecordings.get(0);
        int prevNumSymptoms = 0;
        int prevSum = 0;
        for (int num : prevData) {
            if (num > 0) {
                prevNumSymptoms++;
            }
            prevSum += num;
        }
        System.out.println("\nGame 1 Symptom Summary:");
        System.out.println("\tTotal Number of Symptoms: " + prevNumSymptoms);
        System.out.println("\tSymptom Severity Score: " + prevSum);
        System.out.println("\tOverall Rating: None");
        // Give summaries for the rest of the games
        for (int i = 1; i < symptomRecordings.size(); i++) {
            calculateSummary(symptomRecordings.get(i-1), symptomRecordings.get(i), i+1);
        }
        System.out.println();
    }

    public void getRisk() {
        if (symptomRecordings.size() <= 1) {
            System.out.println("Not enough games recorded to calculate risk!");
        }
        int[] secondLast = symptomRecordings.get(symptomRecordings.size()-2);
        int[] last = symptomRecordings.get(symptomRecordings.size()-1);
        int prevNumSymptoms = 0;
        int currNumSymptoms = 0;
        int prevSum = 0;
        int currSum = 0;
        for (int i = 0; i < 22; i++) {
            if (secondLast[i] > 0) {
                prevNumSymptoms++;
            }
            if (last[i] > 0) {
                currNumSymptoms++;
            }
            prevSum += secondLast[i];
            currSum += last[i];
        }
        // Now Calculate Severity Rating
        String severity = "";
        if ((prevNumSymptoms - currNumSymptoms < 3) && currSum < 10) {
            severity = "No Difference";
        } else if ((prevNumSymptoms - currNumSymptoms < 3) && currSum >= 10) {
            severity = "Unsure";
        } else if ((prevNumSymptoms - currNumSymptoms >= 3) || currSum >= 15) {
            severity = "Very Different";
        }
        System.out.println("\n\nSeverity Rating: " + severity + "\n");
    }

    public void recordSymptoms(int[] symptoms) {
        if (symptomRecordings.size() >= 5) { // Keep only the last 5 games
            symptomRecordings.remove(0);
        }
        symptomRecordings.add(symptoms);
    }

    public boolean authenticate(String username, String password) {
        return this.username.equals(username) && this.password.equals(password);
    }
}