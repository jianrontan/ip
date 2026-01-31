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

### Update Test Cases
1. Edit `text-ui-test/input.txt` with test commands
2. Generate expected output:
```bash
java -cp bin KirkStein < text-ui-test/input.txt > text-ui-test/EXPECTED.TXT
```
3. Run test:
```bash
cd text-ui-test
./runtest.sh
```

## Git Workflow

### For new level (e.g., Level-9)
```bash
git checkout master
git checkout -b branch-Level-9
# Make changes...
git add .
git commit -m "Implement Level-9"
git push origin branch-Level-9

git checkout master
git merge --no-ff branch-Level-9
git tag Level-9
git push origin master
git push origin Level-9
```