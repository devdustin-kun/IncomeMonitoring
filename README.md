# 💼 Java Business Toolkit: Buy-and-Sell & Lending Business System

This Java program serves as a mini business management system that supports **Buy-and-Sell** and **Lending** transactions. It calculates profits and interests, maintains a **transaction history**, supports **searching and filtering by date or business type**, and provides an **automated summary** of profits for both business models.

---

## 📌 Features

- ✅ **Buy-and-Sell Calculator**
  - Input cost and selling price
  - Compute PHP and USD profits
  - Search by PHP or USD profit
- ✅ **Lending Calculator**
  - Input loan amount and duration (in months)
  - Fixed monthly interest rate (10%)
  - Auto-compute interest and total payable
- 📅 **Transaction History**
  - Autosaves after every transaction
  - Stored in a text file
  - Sorted in descending order by date
- 🔍 **Search & Filter Options**
  - Filter transactions by:
    - Date
    - Business type (Buy-and-Sell or Lending)
- 📊 **Summary View**
  - Displays total profits per business type
  - Automatically updates with every transaction

---

## 🛠️ How to Install and Run

### Requirements
- Java Development Kit (JDK 8 or later)
- A text/code editor (e.g., VS Code, IntelliJ, Notepad++)
- A terminal/command prompt




🧠 Usage Instructions
Upon running the program:

Choose a business type:

Buy-and-Sell

Lending

Provide required inputs:

For Buy-and-Sell: cost and selling price

For Lending: loan amount and months

View calculated results

Check transaction history (autosaved in transactions.txt)

Search/filter transactions as needed

Exit the program any time


📁 Project Structure

business-calculator/
│
├── Main.java                 # Main logic for menu and transaction handling
├── BuyAndSell.java           # Buy-and-sell class
├── Lending.java              # Lending class
├── Transaction.java          # Transaction record class
├── TransactionHistory.java   # Handles saving, sorting, and filtering history
├── transactions.txt          # Text file where all transactions are saved
├── README.md                 # This file


📌 To-Do / Future Features
 Add graphical user interface (GUI) using JavaFX or Swing

 Export summary reports to PDF/Excel

 Add user login for multi-user support

 Include currency converter API for live exchange rates

🧑‍💻 Author
Dustin B. Pastidio
Student at Cebu Technological University (CTU)

devdustin-kun||dustinskey@gmail.com

