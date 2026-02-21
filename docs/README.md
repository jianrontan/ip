# KirkStein User Guide

KirkStein is a task management chatbot with a JavaFX GUI that helps you track todos, deadlines, and events. Type commands into the chat window and KirkStein will manage your task list — saving everything automatically so your tasks persist across sessions.

---

## Quick Start

1. Run the app with `./gradlew run`
2. Type a command into the text box at the bottom
3. Press **Send** or hit **Enter**

> [!TIP]
> You can also press Enter in the text field instead of clicking Send — both trigger `#handleUserInput`.

---

## Commands

### Add a Todo

Adds a simple task with no date.

**Format:** `todo <description>`

**Example:**
```
todo read epstein files
```

**Output:**
```
Got it. I've added this task:
  [T][ ] read epstein files
Now you have 1 tasks in the list.
```

---

### Add a Deadline

Adds a task with a due date.

**Format:** `deadline <description> /by <date>`

> [!NOTE]
> Accepted date formats are `yyyy/MM/dd` (e.g. `2025/12/31`) or `dd/MM/yyyy` (e.g. `31/12/2025`).

**Example:**
```
deadline submit assignment /by 2025/12/31
```

**Output:**
```
Got it. I've added this task:
  [D][ ] submit assignment (by: Dec 31 2025)
Now you have 2 tasks in the list.
```

---

### Add an Event

Adds a task with a start and end date.

**Format:** `event <description> /from <start date> /to <end date>`

**Example:**
```
event book club /from 2025/12/01 /to 2025/12/02
```

**Output:**
```
Got it. I've added this task:
  [E][ ] book club (from: Dec 01 2025 to: Dec 02 2025)
Now you have 3 tasks in the list.
```

---

### List All Tasks

Displays all tasks currently in your list.

**Format:** `list`

**Output:**
```
Here are your Epstein files:
1.[T][ ] read epstein files
2.[D][ ] submit assignment (by: Dec 31 2025)
3.[E][ ] book club (from: Dec 01 2025 to: Dec 02 2025)
```

---

### Mark a Task as Done

Marks a task as completed using its number from `list`.

**Format:** `mark <task number>`

**Example:**
```
mark 1
```

**Output:**
```
Nice! I've marked this as redacted:
[T][X] read epstein files
```

---

### Unmark a Task

Marks a previously completed task as not done.

**Format:** `unmark <task number>`

**Example:**
```
unmark 1
```

**Output:**
```
OK! I've unredacted this:
[T][ ] read epstein files
```

---

### Find Tasks

Searches your task list for tasks whose descriptions contain the keyword. Case-insensitive.

**Format:** `find <keyword>`

**Example:**
```
find book
```

**Output:**
```
Here are your searched Epstein files:
1.[T][ ] read epstein files
2.[E][ ] book club (from: Dec 01 2025 to: Dec 02 2025)
```

---

### Delete a Task

Removes a task permanently using its number from `list`.

**Format:** `delete <task number>`

> [!WARNING]
> Deletion is permanent. There is no undo — the task is removed from `data/tasks.txt` immediately.

**Example:**
```
delete 2
```

**Output:**
```
Noted. I've removed this file:
  [D][ ] submit assignment (by: Dec 31 2025)
Now you have 2 files in the list.
```

---

### Exit

Closes the application.

**Format:** `bye`

---

## Clash Detection

KirkStein automatically warns you when a newly added task clashes with an existing one. The task is **still added** even when a clash is detected.

| Scenario | Clash Condition |
|---|---|
| Event vs Event | Date ranges overlap |
| Deadline vs Deadline | Same due date |
| Deadline within Event | Deadline date falls within event's range |
| Todo | Never clashes |

**Example:**
```
event holiday /from 2025/12/01 /to 2025/12/05
deadline assignment /by 2025/12/03
```

**Output:**
```
Warning! This task clashes with:
  [E][ ] holiday (from: Dec 01 2025 to: Dec 05 2025)
Task has been added anyway.
Got it. I've added this task:
  [D][ ] assignment (by: Dec 03 2025)
Now you have 2 tasks in the list.
```

---

## Task Persistence

All tasks are automatically saved to `data/tasks.txt` after every change (add, mark, unmark, delete). Your tasks will be reloaded the next time you start KirkStein.

> [!IMPORTANT]
> If you delete `data/tasks.txt` manually, all tasks will be lost permanently on the next run.

---

## Testing Checklist

Use this to verify all features are working correctly after a fresh start (`rm -f data/tasks.txt && ./gradlew run`):

- [ ] `todo <description>` — adds a todo task
- [ ] `deadline <desc> /by <date>` — adds a deadline
- [ ] `event <desc> /from <date> /to <date>` — adds an event
- [ ] `list` — shows all tasks
- [ ] `mark <number>` — marks task as done
- [ ] `unmark <number>` — unmarks task
- [ ] `find <keyword>` — finds matching tasks
- [ ] `delete <number>` — deletes task
- [ ] `bye` — closes the application
- [ ] Tasks persist after restart
- [ ] Clash warning appears for overlapping dates
- [ ] Error messages display for invalid commands

---

## Reference

### Task Format

| Symbol | Meaning |
|---|---|
| `[T]` | Todo |
| `[D]` | Deadline |
| `[E]` | Event |
| `[X]` | Done |
| `[ ]` | Not done |

### Date Formats

| Format | Example |
|---|---|
| `yyyy/MM/dd` | `2025/12/31` |
| `dd/MM/yyyy` | `31/12/2025` |

### Command Summary

| Command | Format |
|---|---|
| Add todo | `todo <description>` |
| Add deadline | `deadline <desc> /by <date>` |
| Add event | `event <desc> /from <date> /to <date>` |
| List tasks | `list` |
| Mark done | `mark <number>` |
| Unmark | `unmark <number>` |
| Find | `find <keyword>` |
| Delete | `delete <number>` |
| Exit | `bye` |