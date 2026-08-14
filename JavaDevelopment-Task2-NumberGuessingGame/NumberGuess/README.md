# 🎯 Number Guessing Challenge

An interactive, command-line number guessing game written in **Java**. Featuring customizable profiles, flexible difficulty levels, and built-in performance tracking, this application offers an engaging gameplay experience straight from your terminal.

---

## 🌟 Key Highlights

* **Personalized Profiles**: Set your own unique gamer name right when the application launches.
* **Tiered Difficulty Settings**: 
  * **Easy**: Numbers 1 to 50 (10 chances)
  * **Medium**: Numbers 1 to 100 (7 chances)
  * **Hard**: Numbers 1 to 200 (5 chances)
* **Comprehensive Scoreboards**: Keep track of cumulative wins and losses alongside round-by-round historical summaries.
* **Foolproof Error Handling**: Built-in exception management to handle text formatting errors or out-of-bounds attempts gracefully without crashing.

---

## ⚙️ Built With

* **Programming Language:** Java
* **Core APIs:** `java.util.Scanner`, `java.util.Random`, `java.util.ArrayList`, `java.util.List`

---

## 🗂️ Code Architecture

```text
NUMBER GAME/
│
├── src/
│   └── main/
│       └── java/
│           └── NoGame.java    # Core controller handling gameplay loops, menus, and input validation
│
└── README.md
