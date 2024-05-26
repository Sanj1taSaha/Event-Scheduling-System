import java.io.*;
import java.time.LocalDate;
import java.util.*;

public class EventScheduler {
    private static final String USERS_FILE = "users.ser";
    private static final String EVENTS_FILE = "events.ser";

    private Map<String, User> users;
    private Map<String, List<Event>> userEvents;

    public EventScheduler() {
        users = new HashMap<>();
        userEvents = new HashMap<>();
        loadUsers();
        loadEvents();
    }

    // User registration
    public boolean registerUser(String username, String password) {
        if (users.containsKey(username)) {
            return false;
        }
        User user = new User(username, password);
        users.put(username, user);
        saveUsers();
        return true;
    }

    // User login
    public boolean loginUser(String username, String password) {
        User user = users.get(username);
        return user != null && user.checkPassword(password);
    }

    // Add event
    public void addEvent(String username, Event event) {
        userEvents.putIfAbsent(username, new ArrayList<>());
        userEvents.get(username).add(event);
        saveEvents();
    }

    // Edit event
    public boolean editEvent(String username, String oldTitle, Event newEvent) {
        List<Event> events = userEvents.get(username);
        if (events != null) {
            for (int i = 0; i < events.size(); i++) {
                if (events.get(i).getTitle().equals(oldTitle)) {
                    events.set(i, newEvent);
                    saveEvents();
                    return true;
                }
            }
        }
        return false;
    }

    // Delete event
    public boolean deleteEvent(String username, String title) {
        List<Event> events = userEvents.get(username);
        if (events != null) {
            for (int i = 0; i < events.size(); i++) {
                if (events.get(i).getTitle().equals(title)) {
                    events.remove(i);
                    saveEvents();
                    return true;
                }
            }
        }
        return false;
    }

    // Search events
    public List<Event> searchEvents(String username, String keyword) {
        List<Event> events = userEvents.get(username);
        if (events == null) return Collections.emptyList();

        List<Event> result = new ArrayList<>();
        for (Event event : events) {
            if (event.getTitle().contains(keyword) || event.getDescription().contains(keyword)) {
                result.add(event);
            }
        }
        return result;
    }

    // Sort events by date
    public List<Event> getSortedEvents(String username) {
        List<Event> events = userEvents.get(username);
        if (events == null) return Collections.emptyList();

        events.sort(Comparator.comparing(Event::getDate));
        return events;
    }

    // Load users from file
    @SuppressWarnings("unchecked")
    private void loadUsers() {
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(USERS_FILE))) {
            users = (Map<String, User>) ois.readObject();
        } catch (FileNotFoundException e) {
            // File not found, initialize empty users map
            users = new HashMap<>();
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    // Save users to file
    private void saveUsers() {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(USERS_FILE))) {
            oos.writeObject(users);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Load events from file
    @SuppressWarnings("unchecked")
    private void loadEvents() {
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(EVENTS_FILE))) {
            userEvents = (Map<String, List<Event>>) ois.readObject();
        } catch (FileNotFoundException e) {
            // File not found, initialize empty user events map
            userEvents = new HashMap<>();
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    // Save events to file
    private void saveEvents() {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(EVENTS_FILE))) {
            oos.writeObject(userEvents);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
