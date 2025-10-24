# Notes Manager Application - Java File I/O

A comprehensive CLI-based notes management system demonstrating Java File I/O operations.

##  Features

- **Create Notes**: Add new notes with title, content, and category
- **View Notes**: Display all notes or view specific notes by ID
- **Update**: Modify existing notes (title, content, or category)
- **Delete**: Remove notes with confirmation

##  Installation & Setup

### Prerequisites
- Java Development Kit (JDK) 8 or higher
- VS Code / IntelliJ IDEA / Terminal
- Write permissions in the directory

### Compilation & Execution

**Using Terminal:**

```bash
# Compile the program
javac NotesApp.java

# Run the application
java NotesApp
```

**Using VS Code:**
1. Install Java Extension Pack
2. Open project folder
3. Right-click `NotesApp.java`
4. Select "Run Java"

**Using IntelliJ IDEA:**
1. Create new Java project
2. Add `NotesApp.java` to src folder
3. Right-click and select "Run 'NotesApp.main()'"

### First Run
On first run, the application will:
1. Look for `notes.txt` file
2. If not found, create a new empty database
3. Display: "ℹ No existing notes file found. Starting fresh."

## How to Use

### Main Menu

```
MAIN MENU
Press 1 for Create New Note
Press 2 for View All Notes
Press 3 for View Note by ID
Press 4 for Update Note
Press 5 for Delete Note
Press 0 for Exit
```

### 1. Create New Note

```
--- CREATE NEW NOTE ---
Enter note title: Shopping List
Enter category (e.g., Personal, Work, Study): Personal
Enter note content (type 'END' on a new line to finish):
- Milk
- Bread
- Eggs
- Coffee
END
Note added successfully with ID: 1
```

### 2. View All Notes

Displays a summary of all notes:
```
ALL NOTES
ID: 1   | Shopping List                 | Category: Personal      | 24-Oct-2025 14:30
ID: 2   | Meeting Notes                 | Category: Work          | 24-Oct-2025 15:45
Total Notes: 2
```

### 3. View Note by ID

Shows complete note details:
```
Enter note ID: 1

[ID: 1] Shopping List
Category: Personal
Created: 24-Oct-2025 14:30
------------------------------------------------------------
- Milk
- Bread
- Eggs
- Coffee
```

### 5. Update Note

Update title, content, category, or all:
```
--- UPDATE NOTE ---
Enter note ID to update: 1

What do you want to update?
Press 1 for Title
Press 2 for Content
Press 3 for Category
Press 4 for All

Enter choice: 1
Enter new title: Weekly Shopping List
Note updated successfully!
```

### 6. Delete Note

Remove a note with confirmation:
```
--- DELETE NOTE ---
Enter note ID to delete: 2

[Shows note details]

Are you sure you want to delete? (yes/no): yes
Note deleted successfully!
```

## File Format

### Internal Storage Format (notes.txt)

Notes are stored in a custom delimited format:
```
1|||Shopping List|||Milk<br>Bread<br>Eggs|||24-Oct-2025 14:30|||Personal
2|||Meeting Notes|||Discuss Q4 goals|||24-Oct-2025 15:45|||Work
```

**Format Structure:**
```
ID|||Title|||Content|||Timestamp|||Category
```

- **Delimiter**: `|||` (triple pipe)
- **Line breaks**: Stored as `<br>` tags
- **Encoding**: UTF-8 text

### Why This Format?

1. **Simple**: Easy to parse and write
2. **Human-readable**: Can be viewed in text editor
3. **Handles multi-line**: Content with newlines supported
4. **No external dependencies**: Pure Java implementation

