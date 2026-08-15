# 💳 Console-Based ATM Interface Simulator

> A secure, object-oriented banking application built in Java to simulate real-world Automated Teller Machine (ATM) transactions.

---

## 🚀 Core Functionalities

* **🔐 Authentication Security:** Validates User ID and PIN combinations with a strict 3-attempt limit before locking access.
* **💸 Deposit & Withdrawal:** Real-time updates to account balances with error checks for zero or negative values and insufficient funds.
* **🔄 Account Transfers:** Seamlessly transfer funds between different registered users with automatic balancing and dual transaction logging.
* **📜 Transaction Logs:** Detailed history tracking including timestamps, transaction types, amounts, and post-transaction balances.

---

## 🛠️ System Architecture

The application follows clean object-oriented design principles, split into modular classes:
* `Main.java` — Initializes the banking system and pre-seeds test accounts.
* `ATM.java` — Handles user interface menus, session loops, and input scanning.
* `Bank.java` — Manages account directories and authenticates user credentials.
* `Account.java` — Core financial logic (deposits, withdrawals, transfers, and balance checks).
* `Transaction.java` — Formats and stores individual transaction records with timestamps.

---

## 📂 Project Structure
```text

ATM/
│
├── src/
│   └── atm/
│       ├── Main.java         # Entry point & mock account seeding
│       ├── ATM.java          # User interface and menu navigation logic
│       ├── Bank.java         # Account registry and authentication manager
│       ├── Account.java      # Core banking logic (deposit, withdraw, transfer)
│       └── Transaction.java  # Transaction details and timestamp formatter
│
└── README.md
