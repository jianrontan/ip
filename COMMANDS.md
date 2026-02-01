# Development Commands

## Compile
```bash
javac -d bin src/main/java/*.java
```

## Run
```bash
java -cp bin KirkStein
```

## Testing

### Run Test (Regression Test)
**The script automatically cleans data files!**
```bash
cd text-ui-test
./runtest.sh  # or runtest.bat on Windows
cd ..
```

### Update Expected Output (When Behavior Intentionally Changes)
```bash
# 1. Clean and generate new EXPECTED.TXT
rm -f data/tasks.txt text-ui-test/data/tasks.txt
javac -d bin src/main/java/*.java
java -cp bin KirkStein < text-ui-test/input.txt > text-ui-test/EXPECTED.TXT

# 2. Verify it looks correct
cat text-ui-test/EXPECTED.TXT

# 3. Run test to confirm
cd text-ui-test
./runtest.sh
cd ..
```

### Quick Manual Test
```bash
rm -f data/tasks.txt
java -cp bin KirkStein
```

## Git Workflow

### For new increment (e.g., A-MoreOOP)
```bash
# Create branch
git checkout master
git checkout -b branch-A-MoreOOP

# Make changes and commit frequently
git add .
git commit -m "Add kirkstein.ui.Ui class for better separation of concerns"

# More changes...
git add .
git commit -m "Add kirkstein.parser.Parser class to handle input parsing"

# Test before merging
cd text-ui-test && ./runtest.sh && cd ..

# If test passes, merge and tag
git push origin branch-A-MoreOOP
git checkout master
git merge --no-ff branch-A-MoreOOP
git tag A-MoreOOP
git push origin master
git push origin branch-A-MoreOOP
git push origin A-MoreOOP
```

### Quick Workflow
```bash
# Make changes, test, commit
git add .
git commit -m "Your message"
cd text-ui-test && ./runtest.sh && cd ..
```

## Common Tasks

### Clean everything and start fresh
```bash
rm -rf bin data text-ui-test/data
javac -d bin src/main/java/*.java
```

### Just compile and run
```bash
javac -d bin src/main/java/*.java && java -cp bin KirkStein
```

### Full regression test (compile + test)
```bash
javac -d bin src/main/java/*.java && cd text-ui-test && ./runtest.sh && cd ..
```