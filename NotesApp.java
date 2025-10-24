import java.util.Scanner;

// Main application class
public class NotesApp {
    private static NotesManager manager;
    private static Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        System.out.println("          NOTES MANAGER APPLICATION            ");

        manager = new NotesManager();

        int choice = 0;
        do {
            displayMainMenu();
            choice = getIntInput("\nEnter your choice: ");

            switch (choice) {
                case 1:
                    createNote();
                    break;
                case 2:
                    viewAllNotes();
                    break;
                case 3:
                    viewNoteById();
                    break;
                case 4:
                    updateNote();
                    break;
                case 5:
                    deleteNote();
                    break;
                case 0:
                    System.out.println("\nThank you for using Notes Manager!");
                    break;
                default:
                    System.out.println("\nInvalid choice! Please try again.");
            }
        } while (choice != 0);

        scanner.close();
    }

    private static void displayMainMenu() {
        System.out.println("\n" + "=".repeat(60));
        System.out.println("                    MAIN MENU");
        System.out.println("=".repeat(60));
        System.out.println("Press 1 for Create New Note");
        System.out.println("Press 2 for View All Notes");
        System.out.println("Press 3 for View Note by ID");
        System.out.println("Press 4 for Update Note");
        System.out.println("Press 5 for Delete Note");
        System.out.println("Press 0 for Exit");
    }

    // Create new note
    private static void createNote() {
        System.out.println("\n--- CREATE NEW NOTE ---");

        String title = getStringInput("Enter note title: ");
        if (title.isEmpty()) {
            System.out.println("Title cannot be empty!");
            return;
        }

        String category = getStringInput("Enter category (e.g., Personal, Work, Study): ");
        if (category.isEmpty()) {
            category = "General";
        }

        System.out.println("Enter note content (type 'END' on a new line to finish):");
        StringBuilder content = new StringBuilder();
        String line;

        while (!(line = scanner.nextLine()).equals("END")) {
            if (content.length() > 0) {
                content.append("\n");
            }
            content.append(line);
        }

        if (content.toString().trim().isEmpty()) {
            System.out.println("Content cannot be empty!");
            return;
        }

        manager.addNote(title, content.toString(), category);
    }

    // View all notes
    private static void viewAllNotes() {
        manager.viewAllNotes();
    }

    // View note by ID
    private static void viewNoteById() {
        int id = getIntInput("\nEnter note ID: ");
        manager.viewNoteById(id);
    }

    // Update note
    private static void updateNote() {
        System.out.println("\n--- UPDATE NOTE ---");

        int id = getIntInput("Enter note ID to update: ");
        manager.viewNoteById(id);

        System.out.println("\nWhat do you want to update?");
        System.out.println("Press 1 for Title");
        System.out.println("Press 2 for Content");
        System.out.println("Press 3 for Category");
        System.out.println("Press 4  for All");

        int choice = getIntInput("\nEnter choice: ");

        String newTitle = null;
        String newContent = null;
        String newCategory = null;

        switch (choice) {
            case 1:
                newTitle = getStringInput("Enter new title: ");
                break;
            case 2:
                System.out.println("Enter new content (type 'END' on a new line to finish):");
                StringBuilder content = new StringBuilder();
                String line;
                while (!(line = scanner.nextLine()).equals("END")) {
                    if (content.length() > 0) {
                        content.append("\n");
                    }
                    content.append(line);
                }
                newContent = content.toString();
                break;
            case 3:
                newCategory = getStringInput("Enter new category: ");
                break;
            case 4:
                newTitle = getStringInput("Enter new title: ");
                newCategory = getStringInput("Enter new category: ");
                System.out.println("Enter new content (type 'END' on a new line to finish):");
                StringBuilder allContent = new StringBuilder();
                String contentLine;
                while (!(contentLine = scanner.nextLine()).equals("END")) {
                    if (allContent.length() > 0) {
                        allContent.append("\n");
                    }
                    allContent.append(contentLine);
                }
                newContent = allContent.toString();
                break;
            default:
                System.out.println("Invalid choice!");
                return;
        }

        manager.updateNote(id, newTitle, newContent, newCategory);
    }

    // Delete note
    private static void deleteNote() {
        System.out.println("\n--- DELETE NOTE ---");

        int id = getIntInput("Enter note ID to delete: ");
        manager.viewNoteById(id);

        String confirm = getStringInput("\nAre you sure you want to delete? (yes/no): ");

        if (confirm.equalsIgnoreCase("yes")) {
            manager.deleteNote(id);
        } else {
            System.out.println("Deletion cancelled.");
        }
    }

    // Helper methods
    private static int getIntInput(String prompt) {
        while (true) {
            try {
                System.out.print(prompt);
                return Integer.parseInt(scanner.nextLine().trim());
            } catch (NumberFormatException e) {
                System.out.println("Invalid input! Please enter a number.");
            }
        }
    }

    private static String getStringInput(String prompt) {
        System.out.print(prompt);
        return scanner.nextLine().trim();
    }
}