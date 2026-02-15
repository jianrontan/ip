# Development Commands

## Gradle Build & Run (Recommended)

### Build Project
```bash
./gradlew build
```

### Run Application (GUI)
```bash
# Run with Gradle
./gradlew run

# OR build and run JAR directly
./gradlew shadowJar && java -jar build/libs/kirkstein.jar
```

**Note:** The application now runs as a **JavaFX GUI** - a chat window will open instead of console output.

### Run Tests
```bash
# Run JUnit tests
./gradlew test

# View test report
start build/reports/tests/test/index.html  # Windows
open build/reports/tests/test/index.html   # Mac/Linux
```

### Build JAR
```bash
./gradlew shadowJar
# Output: build/libs/kirkstein.jar

# Run the JAR (opens GUI window)
java -jar build/libs/kirkstein.jar
```

### Clean Build
```bash
./gradlew clean build
```

---

## Testing

### JUnit Tests (Backend Unit Tests)
```bash
# Run all tests
./gradlew test

# Run tests with detailed output
./gradlew test --info

# Clean and test
./gradlew clean test
```

**Test Files:**
- `src/test/java/TodoTest.java` - Tests for Todo tasks
- `src/test/java/DeadlineTest.java` - Tests for Deadline tasks
- `src/test/java/EventTest.java` - Tests for Event tasks
- `src/test/java/TaskListTest.java` - Tests for TaskList operations
- `src/test/java/ParserTest.java` - Tests for input parsing

---

## Git Workflow

### Standard Increment Workflow
```bash
# Create branch
git checkout master
git checkout -b branch-feature

# Make changes and commit
git add .
git commit -m "Implement feature"

# Test
./gradlew test

# If tests pass, push and merge
git push origin branch-feature
git checkout master
git merge --no-ff branch-feature
git tag feature
git push origin master
git push origin branch-feature
git push origin feature
```

### Parallel Branches Workflow
```bash
# Create all three branches from same starting point (e.g., after A-Jar)
git checkout master
git branch branch-A-JavaDoc
git branch branch-Level-9
git branch branch-A-CodingStandard

# Work on first branch
git checkout branch-Level-9
# ... make changes ...
git add .
git commit -m "Implement find command"
./gradlew test
git push origin branch-Level-9
git checkout master
git merge --no-ff branch-Level-9
git tag Level-9
git push origin master Level-9

# Work on second branch (still based on old master)
git checkout branch-A-JavaDoc
# ... make changes ...
git add .
git commit -m "Add JavaDoc comments"
./gradlew test
git push origin branch-A-JavaDoc
git checkout master
git merge --no-ff branch-A-JavaDoc
git tag A-JavaDoc
git push origin master A-JavaDoc

# Work on third branch
git checkout branch-A-CodingStandard
# ... make changes ...
git add .
git commit -m "Fix coding standard violations"
./gradlew test
git push origin branch-A-CodingStandard
git checkout master
git merge --no-ff branch-A-CodingStandard
git tag A-CodingStandard
git push origin master A-CodingStandard
```

---

## Common Tasks

### Quick Development Cycle
```bash
# Make changes, test, commit
git add .
git commit -m "Your message"
./gradlew test
```

### Clean Everything
```bash
# Clean Gradle build
./gradlew clean

# Clean data files
rm -rf data

# Full clean
./gradlew clean && rm -rf data
```

### Quick Manual Test (GUI)
```bash
# Clean data and run GUI
rm -f data/tasks.txt
./gradlew run
```

### Full Build and Test Pipeline
```bash
# Build, test, and create JAR
./gradlew clean build test shadowJar
```

---

## Testing the GUI Application

### Manual Testing Workflow

1. **Clean start:**
```bash
rm -f data/tasks.txt
./gradlew run
```

2. **Test basic commands in the GUI chat window:**
```
todo read book
todo write essay
deadline return book /by 2025/12/31
event book club /from 2025/12/01 /to 2025/12/02
list
```

3. **Test mark/unmark:**
```
mark 1
mark 3
list
unmark 1
list
```

4. **Test find:**
```
find book          # Should show: todo, deadline, event
find essay         # Should show: todo only
find club          # Should show: event only
find homework      # Should show: No matching tasks
```

5. **Test delete:**
```
delete 2
list
```

6. **Test error handling:**
```
mark 100           # Invalid task number
todo               # Empty description
deadline           # Invalid format
event              # Invalid format
```

7. **Test bye command:**
```
bye                # Should close the window
```

### Testing All Features Checklist

- [ ] `todo <description>` - Adds todo task
- [ ] `deadline <desc> /by <date>` - Adds deadline (yyyy/MM/dd or dd/MM/yyyy)
- [ ] `event <desc> /from <date> /to <date>` - Adds event
- [ ] `list` - Shows all tasks
- [ ] `mark <number>` - Marks task as done
- [ ] `unmark <number>` - Unmarks task
- [ ] `find <keyword>` - Finds matching tasks
- [ ] `delete <number>` - Deletes task
- [ ] `bye` - Closes application
- [ ] Tasks persist after restart
- [ ] Error messages display correctly
- [ ] Chat scrolls properly
- [ ] User and bot avatars display

---

## Troubleshooting

### JavaFX Warning on Startup
```
WARNING: Unsupported JavaFX configuration: classes were loaded from 'unnamed module'
```
**Solution:** This is expected and can be ignored. It doesn't affect functionality.

### FXML Not Found Error
```
IllegalStateException: Location is not set
```
**Solution:** Ensure FXML files are in `src/main/resources/view/`
```bash
ls src/main/resources/view/
# Should show: DialogBox.fxml, MainWindow.fxml
```

### Images Not Loading
**Solution:** Ensure images are in `src/main/resources/images/`
```bash
ls src/main/resources/images/
# Should show: Kirk.png, Netanyahu.png
```

### Tests Failing Due to Stale Data
```bash
# Clean data files before testing
rm -f data/tasks.txt
./gradlew clean test
```

### JAR Not Found
```bash
# Rebuild JAR
./gradlew clean shadowJar
ls -la build/libs/  # Verify kirkstein.jar exists
```

### Compilation Errors
```bash
# Clean build from scratch
./gradlew clean build --info
```

### GUI Not Appearing
```bash
# Check Java version (needs JDK 11+)
java -version

# Try running JAR directly
./gradlew shadowJar
java -jar build/libs/kirkstein.jar
```

---

## Project Structure

```
ip/
├── src/
│   ├── main/
│   │   ├── java/
│   │   │   ├── DialogBox.java
│   │   │   ├── KirkStein.java
│   │   │   ├── Launcher.java
│   │   │   ├── Main.java
│   │   │   ├── MainWindow.java
│   │   │   └── kirkstein/
│   │   │       ├── exception/
│   │   │       ├── parser/
│   │   │       ├── storage/
│   │   │       ├── task/
│   │   │       ├── tasklist/
│   │   │       └── ui/
│   │   └── resources/
│   │       ├── images/
│   │       │   ├── Kirk.png
│   │       │   └── Netanyahu.png
│   │       └── view/
│   │           ├── DialogBox.fxml
│   │           └── MainWindow.fxml
│   └── test/
│       └── java/
│           ├── DeadlineTest.java
│           ├── EventTest.java
│           ├── ParserTest.java
│           ├── TaskListTest.java
│           └── TodoTest.java
├── data/
│   └── tasks.txt (generated at runtime)
├── build.gradle
└── COMMANDS.md
```

---

## Quick Reference

| Task | Command |
|------|---------|
| Build project | `./gradlew build` |
| Run GUI | `./gradlew run` |
| Run JAR | `java -jar build/libs/kirkstein.jar` |
| Run tests | `./gradlew test` |
| View test report | `start build/reports/tests/test/index.html` |
| Build JAR | `./gradlew shadowJar` |
| Clean build | `./gradlew clean build` |
| Full pipeline | `./gradlew clean build test shadowJar` |
| Clean data | `rm -f data/tasks.txt` |
| Checkstyle | View → Tool Windows → CheckStyle |

---

## Development Notes

### Application Type
- **GUI Application** using JavaFX 17.0.7
- Entry point: `Launcher.java` → `Main.java`
- Main window: `MainWindow.fxml` controlled by `MainWindow.java`
- Chat bubbles: `DialogBox.fxml` controlled by `DialogBox.java`

### Testing Strategy
- **Unit tests** for backend logic (Parser, TaskList, Task classes)
- **Manual testing** for GUI functionality
- No automated UI tests (JavaFX testing is complex)

### Build System
- **Gradle** for dependency management and builds
- **shadowJar** plugin creates fat JAR with all dependencies
- **Checkstyle** for code quality (config in `config/checkstyle/`)

### Data Persistence
- Tasks saved to `data/tasks.txt` automatically
- File created on first run
- Loaded on application startup
