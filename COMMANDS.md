# Development Commands

## Gradle Build & Run (Recommended)

### Build Project
```bash
./gradlew build
```

### Run Application
```bash
# Clean output (no progress bar)
./gradlew run --console=plain

# OR build and run JAR directly (cleanest)
./gradlew shadowJar && java -jar build/libs/kirkstein.jar
```

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

# Run the JAR
java -jar build/libs/kirkstein.jar
```

### Clean Build
```bash
./gradlew clean build
```

---

## Legacy Manual Compilation (Pre-Gradle)

### Compile
```bash
javac -d bin src/main/java/*.java src/main/java/kirkstein/*/*.java
```

### Run
```bash
java -cp bin KirkStein
```

---

## Testing

### JUnit Tests (Unit Tests)
```bash
# Run all tests
./gradlew test

# Run tests with detailed output
./gradlew test --info

# Clean and test
./gradlew clean test
```

### Integration Tests (text-ui-test)
**The script automatically cleans data files!**
```bash
cd text-ui-test
./runtest.sh  # or runtest.bat on Windows
cd ..
```

### Update Expected Output (When Behavior Intentionally Changes)
```bash
# 1. Clean data files
rm -f data/tasks.txt text-ui-test/data/tasks.txt

# 2. Generate new EXPECTED.TXT
./gradlew shadowJar
java -jar build/libs/kirkstein.jar < text-ui-test/input.txt > text-ui-test/EXPECTED.TXT

# 3. Verify it looks correct
cat text-ui-test/EXPECTED.TXT

# 4. Run test to confirm
cd text-ui-test && ./runtest.sh && cd ..
```

### Full Test Suite
```bash
# Run both JUnit and integration tests
./gradlew test && cd text-ui-test && ./runtest.sh && cd ..
```

---

## Git Workflow

### Standard Increment Workflow
```bash
# Create branch
git checkout master
git checkout -b branch-Level-9

# Make changes and commit
git add .
git commit -m "Implement find command to search tasks by keyword"

# Test everything
./gradlew test
cd text-ui-test && ./runtest.sh && cd ..

# If tests pass, push and merge
git push origin branch-Level-9
git checkout master
git merge --no-ff branch-Level-9
git tag Level-9
git push origin master
git push origin branch-Level-9
git push origin Level-9
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
./gradlew test && cd text-ui-test && ./runtest.sh && cd ..
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
rm -rf data text-ui-test/data

# Full clean
./gradlew clean && rm -rf data text-ui-test/data
```

### Quick Manual Test
```bash
# Clean data and run
rm -f data/tasks.txt
java -jar build/libs/kirkstein.jar
```

### Full Build and Test Pipeline
```bash
# Everything in one command
./gradlew clean build test && cd text-ui-test && ./runtest.sh && cd ..
```

---

## Example Test Session

### Testing Find Command
```bash
# Build and run
./gradlew shadowJar
java -jar build/libs/kirkstein.jar

# Commands to test:
> todo read book
> todo write essay  
> deadline return book /by 2025/12/31
> event book club /from 2025/12/01 /to 2025/12/02
> list
> find book          # Should show: todo, deadline, event
> find essay         # Should show: todo only
> find club          # Should show: event only
> find homework      # Should show: No matching tasks
> find               # Should show: Error message
> bye
```

### Testing All Features
```bash
# Build JAR
./gradlew shadowJar

# Run comprehensive test
java -jar build/libs/kirkstein.jar

# Test sequence:
> todo read book
> deadline homework /by 2025/12/31
> event meeting /from 2025/12/01 /to 2025/12/02
> list
> mark 1
> unmark 1
> find book
> delete 1
> list
> bye
```

---

## Troubleshooting

### Gradle Progress Bar Interfering with Output
```bash
# Use --console=plain
./gradlew run --console=plain

# OR run JAR directly (better)
./gradlew shadowJar && java -jar build/libs/kirkstein.jar
```

### Tests Failing Due to Stale Data
```bash
# Clean data files before testing
rm -f data/tasks.txt text-ui-test/data/tasks.txt
cd text-ui-test && ./runtest.sh && cd ..
```

### JAR Not Found
```bash
# Rebuild JAR
./gradlew clean shadowJar
ls -la build/libs/  # Verify it exists
```

### Compilation Errors
```bash
# Clean build from scratch
./gradlew clean build --info
```

---

## Quick Reference

| Task | Command |
|------|---------|
| Build project | `./gradlew build` |
| Run with Gradle | `./gradlew run --console=plain` |
| Run JAR | `java -jar build/libs/kirkstein.jar` |
| Run JUnit tests | `./gradlew test` |
| Run integration tests | `cd text-ui-test && ./runtest.sh && cd ..` |
| Full test suite | `./gradlew test && cd text-ui-test && ./runtest.sh && cd ..` |
| Build JAR | `./gradlew shadowJar` |
| Clean build | `./gradlew clean build` |
| View test report | `start build/reports/tests/test/index.html` |