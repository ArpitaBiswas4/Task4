import java.io.*;
import java.util.ArrayList;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

// Note class to represent individual notes
class Note {
    private int id;
    private String title;
    private String content;
    private String timestamp;
    private String category;

    public Note(int id, String title, String content, String timestamp, String category) {
        this.id = id;
        this.title = title;
        this.content = content;
        this.timestamp = timestamp;
        this.category = category;
    }

    // Getters
    public int getId() { return id; }
    public String getTitle() { return title; }
    public String getContent() { return content; }
    public String getTimestamp() { return timestamp; }
    public String getCategory() { return category; }

    // Setters
    public void setTitle(String title) { this.title = title; }
    public void setContent(String content) { this.content = content; }
    public void setCategory(String category) { this.category = category; }

    // Convert note to file format (CSV-like)
    public String toFileFormat() {
        // Using delimiter: |||
        return id + "|||" + title + "|||" + content.replace("\n", "<br>") +
                "|||" + timestamp + "|||" + category;
    }

    // Create note from file format
    public static Note fromFileFormat(String line) {
        String[] parts = line.split("\\|\\|\\|");
        if (parts.length == 5) {
            int id = Integer.parseInt(parts[0]);
            String title = parts[1];
            String content = parts[2].replace("<br>", "\n");
            String timestamp = parts[3];
            String category = parts[4];
            return new Note(id, title, content, timestamp, category);
        }
        return null;
    }

    @Override
    public String toString() {
        return String.format("\n[ID: %d] %s\nCategory: %s\nCreated: %s\n%s\n%s",
                id, title, category, timestamp, "-".repeat(60), content);
    }

    public String getShortInfo() {
        String shortContent = content.length() > 50 ?
                content.substring(0, 47) + "..." : content;
        return String.format("ID: %-3d | %-30s | Category: %-15s | %s",
                id, title, category, timestamp);
    }
}

// NotesManager class - handles file operations and note management
class NotesManager {
    private static final String NOTES_FILE = "notes.txt";
    private ArrayList<Note> notes;
    private int nextId;

    public NotesManager() {
        notes = new ArrayList<>();
        nextId = 1;
        loadNotesFromFile();
    }

    // Load notes from file
    public void loadNotesFromFile() {
        File file = new File(NOTES_FILE);

        if (!file.exists()) {
            System.out.println("No existing notes file found. Starting fresh.");
            return;
        }

        try (BufferedReader reader = new BufferedReader(new FileReader(NOTES_FILE))) {
            String line;
            int count = 0;

            while ((line = reader.readLine()) != null) {
                if (!line.trim().isEmpty()) {
                    Note note = Note.fromFileFormat(line);
                    if (note != null) {
                        notes.add(note);
                        if (note.getId() >= nextId) {
                            nextId = note.getId() + 1;
                        }
                        count++;
                    }
                }
            }
            System.out.println("Loaded " + count + " notes from file.");

        } catch (IOException e) {
            System.out.println("Error reading notes file: " + e.getMessage());
        }
    }

    // Save all notes to file
    public void saveNotesToFile() {
        try (FileWriter writer = new FileWriter(NOTES_FILE);
             BufferedWriter bufferedWriter = new BufferedWriter(writer)) {

            for (Note note : notes) {
                bufferedWriter.write(note.toFileFormat());
                bufferedWriter.newLine();
            }

            System.out.println("Notes saved successfully!");

        } catch (IOException e) {
            System.out.println("Error saving notes: " + e.getMessage());
        }
    }

    // Add new note
    public void addNote(String title, String content, String category) {
        String timestamp = LocalDateTime.now()
                .format(DateTimeFormatter.ofPattern("dd-MMM-yyyy HH:mm"));

        Note note = new Note(nextId++, title, content, timestamp, category);
        notes.add(note);
        saveNotesToFile();
        System.out.println("Note added successfully with ID: " + note.getId());
    }

    // View all notes
    public void viewAllNotes() {
        if (notes.isEmpty()) {
            System.out.println("No notes available.");
            return;
        }

        System.out.println("\n" + "-".repeat(80));
        System.out.println("ALL NOTES\n");

        for (Note note : notes) {
            System.out.println(note.getShortInfo());
        }

        System.out.println("-".repeat(80));
        System.out.println("Total Notes: " + notes.size());
    }

    // View note by ID
    public void viewNoteById(int id) {
        Note note = findNoteById(id);
        if (note != null) {
            System.out.println(note);
        } else {
            System.out.println("Note with ID " + id + " not found!");
        }
    }

    // Search notes by category
    public void searchNotesByCategory(String category) {
        ArrayList<Note> results = new ArrayList<>();

        for (Note note : notes) {
            if (note.getCategory().equalsIgnoreCase(category)) {
                results.add(note);
            }
        }

        if (results.isEmpty()) {
            System.out.println("No notes found in category: " + category);
        } else {
            System.out.println("\n--- NOTES IN CATEGORY: " + category.toUpperCase() + " ---");
            for (Note note : results) {
                System.out.println(note.getShortInfo());
            }
            System.out.println("\nResults: " + results.size());
        }
    }

    // Update note
    public void updateNote(int id, String newTitle, String newContent, String newCategory) {
        Note note = findNoteById(id);

        if (note == null) {
            System.out.println("Note with ID " + id + " not found!");
            return;
        }

        if (newTitle != null && !newTitle.isEmpty()) {
            note.setTitle(newTitle);
        }
        if (newContent != null && !newContent.isEmpty()) {
            note.setContent(newContent);
        }
        if (newCategory != null && !newCategory.isEmpty()) {
            note.setCategory(newCategory);
        }

        saveNotesToFile();
        System.out.println("Note updated successfully!");
    }

    // Delete note
    public boolean deleteNote(int id) {
        Note note = findNoteById(id);

        if (note == null) {
            System.out.println("Note with ID " + id + " not found!");
            return false;
        }

        notes.remove(note);
        saveNotesToFile();
        System.out.println("Note deleted successfully!");
        return true;
    }

    // Helper method to find note by ID
    private Note findNoteById(int id) {
        for (Note note : notes) {
            if (note.getId() == id) {
                return note;
            }
        }
        return null;
    }

    // Get all notes
    public ArrayList<Note> getAllNotes() {
        return notes;
    }
}
