import java.util.Scanner;
import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Application {
    private static DataManager dataManager = new DataManager();

    public static void doctorFrontPage(Doctor doctor) {
        Scanner input = new Scanner(System.in);
        System.out.println("\n\nHere are your options: ");
        System.out.println("Enter 1 to view Symptom Summaries from your Athletes");
        System.out.println("Enter 2 to View Risky Condition Indicator for your Athletes");
        System.out.println("Enter 3 to Log Out");
        System.out.print("Your Choice: ");
        int option = Integer.parseInt(input.nextLine());
        System.out.println();
        if (option == 1) {
            doctor.printAthleteNames();
            System.out.print("Enter Name of Athlete you are Interested in: ");
            String athleteName = input.nextLine();
            Athlete currentAthlete = doctor.checkAndReturnAthlete(athleteName);
            if (currentAthlete == null) {
                System.out.println("\nAthlete with that name doesn't exist, input is case sensitive and affected by whitespace");
                doctorFrontPage(doctor);
            } else {
                // Now we print out the Symptom Summary of that current Athlete
                currentAthlete.getSymptomRecordings();
                doctorFrontPage(doctor);
            }
        } else if (option == 2) {
            doctor.printAthleteNames();
            System.out.print("Enter Name of Athlete you are Interested in: ");
            String athleteName = input.nextLine();
            Athlete currentAthlete = doctor.checkAndReturnAthlete(athleteName);
            if (currentAthlete == null) {
                System.out.println("\nAthlete with that name doesn't exist, input is case sensitive and affected by whitespace");
                doctorFrontPage(doctor);
            } else {
                // Now we output Risky Condition Indicator for that Athlete
                currentAthlete.getRisk();
                doctorFrontPage(doctor);
            }
        } else if (option == 3) {
            applicationFrontPage();
        } else {
            System.out.println("Invalid option, please try again");
            doctorFrontPage(doctor);
        }
    }

    public static void doctorLogin() {
        Scanner input = new Scanner(System.in);
        System.out.println();
        System.out.println("Please Enter Login Information (For demo purposes, username is elizabeth and password is 123)");
        System.out.print("Username: ");
        String username = input.nextLine();
        System.out.print("Password: ");
        String password = input.nextLine();

        for (Doctor doctor : dataManager.getAllDoctors()) {
            if (doctor.authenticate(username, password)) {
                doctorFrontPage(doctor);
                return;
            }
        }
        System.out.println("Invalid login");
        applicationFrontPage();
    }

    public static void recordSymptoms(Athlete athlete) {
        System.out.println("\n\nPlease record the severity of each symptom you are facing on a scale of 0 to 6");
        System.out.println("None (0), mild (1-2), moderate (3-4), & severe(5-6)\n");
        Scanner input = new Scanner(System.in);
        String[] symptoms = {
            "Headache", "Pressure in Head", "Neck Pain", "Nausea or Vomiting",
            "Dizziniess", "Blurred Vision", "Balance Problems", "Sensitivity to Light",
            "Sensitivity to Noise", "Feeling Slowed Down", "Feeling like \"in a fog\"",
            "Don't Feel Right", "Difficulty Concentrating", "Difficulty Remembering",
            "Fatigue or Low Energy", "Confusion", "Drowsiness", "Trouble Falling Asleep",
            "More Emotional", "Irritability", "Sadness", "Nervous or Anxious"
        };
        int symptomScores[] = new int[22];
        for (int i = 0; i < 22; i++) {
            int score = -1;
            boolean validInput = false;

            while (!validInput) {
                System.out.print(symptoms[i] + " (0-6): ");
                if (input.hasNextInt()) { // Check if input is an integer
                    score = input.nextInt();
                    if (score >= 0 && score <= 6) { // Check if the score is within range
                        symptomScores[i] = score;
                        validInput = true; // Exit loop if input is valid
                    } else {
                        System.out.println("Please enter a valid score between 0 and 6.");
                    }
                } else {
                    System.out.println("Invalid input. Please enter a number between 0 and 6.");
                    input.next(); // Clear the invalid input
                }
            }
        }
        athlete.recordSymptoms(symptomScores);
        System.out.println("\n\nThank you for recording your symptoms!\n\n");
    }

    public static void viewSymptomSummary(Athlete athlete) {
        athlete.getSymptomRecordings();
    }

    public static void AmIAtRisk(Athlete athlete) {
        athlete.getRisk();
    }
    
    public static void athleteFrontPage(Athlete athlete) {
        //System.out.println("Name: " + athlete.getName());
        Scanner input = new Scanner(System.in);
        System.out.println("Here are your options: ");
        System.out.println("Enter 1 to Record Symptoms for Recent Game");
        System.out.println("Enter 2 to View Symptom Summary for Recent Games");
        System.out.println("Enter 3 for \"Am I at Risk?\" Feature");
        System.out.println("Enter 4 to Log Out");
        System.out.print("Your Choice: ");
        int option = Integer.parseInt(input.nextLine());
        //System.out.println("Option: " + option);
        if (option == 1) {
            recordSymptoms(athlete);
            athleteFrontPage(athlete);
        } else if (option == 2) {
            viewSymptomSummary(athlete);
            athleteFrontPage(athlete);
        } else if (option == 3) {
            AmIAtRisk(athlete);
            athleteFrontPage(athlete);
        } else if (option == 4) {
            applicationFrontPage();
        }
        else {
            System.out.println("\nInvalid input, please try again");
            athleteFrontPage(athlete);
        }
    }

    public static void athleteLogin() {
        Scanner input = new Scanner(System.in);
        System.out.println("Please Enter Login Information (For demo purposes, username is john and password is 123)");
        System.out.print("Username: ");
        String username = input.nextLine();
        System.out.print("Password: ");
        String password = input.nextLine();

        for (Athlete athlete : dataManager.getAllAthletes()) {
            if (athlete.authenticate(username, password)) {
                String athleteName = athlete.getName();
                System.out.println("\nWelcome, " + athleteName);
                athleteFrontPage(athlete);
                return;
            }
        }
        System.out.println("Invalid login. Try again.");

    }

    public static void applicationFrontPage() {
        Scanner input = new Scanner(System.in);
        System.out.println();
        System.out.print("Are you an athlete or doctor? Enter 1 for athlete, 2 for doctor. Or enter 3 to exit application: ");
        
        if (input.hasNextInt()) {
            int response = input.nextInt();
            if (response <= 0 || response > 3) {
                System.out.println("\nInvalid input, please input one of the options that were provided");
                applicationFrontPage();
            } else if (response == 1) {
                athleteLogin();
            } else if (response == 2) {
                doctorLogin();
            } else if (response == 3) {
                System.out.println("Thank you for using our application!");
            }
        } else {
            System.out.println("\nInvalid input, please try again");
            applicationFrontPage();
        }
        
    }

    public static void initalizeStartingObjects() {
        // Create sample doctors and athletes with demo symptoms
        Doctor doctor1 = new Doctor("Dr. Smith", "elizabeth", "123");
        dataManager.addDoctor(doctor1);

        Athlete athlete1 = new Athlete("John Doe", "john", "123");
        dataManager.addAthlete(athlete1);
        dataManager.linkAthleteToDoctor(athlete1, doctor1);

        // Randomly initialize some symptoms
        Random rand = new Random();
        for (int i = 0; i < 5; i++) {
            int[] symptoms = new int[22];
            for (int j = 0; j < 22; j++) {
                symptoms[j] = rand.nextInt(5); // Random symptom severity
            }
            athlete1.recordSymptoms(symptoms);
        }
    }

    public static void main(String[] args) {
        initalizeStartingObjects();
        applicationFrontPage();
    }
}