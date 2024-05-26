import java.time.LocalDate;
import java.util.List;
import java.util.Scanner;

public class EventSchedulerMain {
    private static EventScheduler eventScheduler;
    private static Scanner scanner;
    private static String currentUser;

    public static void main(String[] args) {
        eventScheduler = new EventScheduler();
        scanner = new Scanner(System.in);
        boolean loggedIn = false;

        while (!loggedIn) {
            System.out.println("1. Register");
            System.out.println("2. Login");
            System.out.print("Choose an option: ");
            int choice = Integer.parseInt(scanner.nextLine());

            switch (choice) {
                case 1:
                    loggedIn = register();
                    break;
                case 2:
                    loggedIn = login();
                    break;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }

        boolean running = true;
        while (running) {
            System.out.println("1. Add Event");
            System.out.println("2. Edit Event");
            System.out.println("3. Delete Event");
            System.out.println("4. Search Events");
            System.out.println("5. List All Events");
            System.out.println("6. Exit");
            System.out.print("Choose an option: ");
            int choice = Integer.parseInt(scanner.nextLine());

            switch (choice) {
                case 1:
                    addEvent();
                    break;
                case 2:
                    editEvent();
                    break;
                case 3:
                    deleteEvent();
                    break;
                case 4:
                    searchEvents();
                    break;
                case 5:
                    listEvents();
                    break;
                case 6:
                    running = false;
                    break;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }
    }

    private static boolean register() {
        System.out.print("Enter username: ");
        String username = scanner.nextLine();
        System.out.print("Enter password: ");
        String password = scanner.nextLine();

        if (eventScheduler.registerUser(username, password)) {
            currentUser = username;
            return true;
        } else {
            System.out.println("Username already exists. Please try again.");
            return false;
        }
    }

    private static boolean login() {
        System.out.print("Enter username: ");
        String username = scanner.nextLine();
        System.out.print("Enter password: ");
        String password = scanner.nextLine();

        if (eventScheduler.loginUser(username, password)) {
            currentUser = username;
            return true;
        } else {
            System.out.println("Invalid credentials. Please try again.");
            return false;
        }
    }

    private static void addEvent() {
        System.out.print("Enter event title: ");
        String title = scanner.nextLine();
        System.out.print("Enter event date (YYYY-MM-DD): ");
        LocalDate date = LocalDate.parse(scanner.nextLine());
        System.out.print("Enter event description: ");
        String description = scanner.nextLine();

        Event event = new Event(title, date, description);
        eventScheduler.addEvent(currentUser, event);
        System.out.println("Event added successfully.");
    }

    private static void editEvent() {
        System.out.print("Enter title of event to edit: ");
        String oldTitle = scanner.nextLine();
        System.out.print("Enter new event title: ");
        String newTitle = scanner.nextLine();
        System
    }
}

