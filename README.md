FinanceTracker – Java Swing Personal Finance Manager

FinanceTracker is a desktop application built with Java Swing and MongoDB that allows users to track income, expenses, categories, and overall financial balance.
The application includes features for adding transactions, calculating totals, exporting financial summaries, and storing all records in MongoDB.

--------------------------------------------------------

Core Features:
- Add income and expense transactions

Six predefined categories:
- Salary
- Food
- Bills
- Entertainment
- Transport
- Other

Automatic calculation of:
- Total income
- Total expense
- Balance (income – expense)
- Expense totals by category
- Display all transactions in a table (GUI)
- Persistent storage using MongoDB

Export Feature:
-Export full financial summary to a TXT or CSV file

--------------------------------------------------------

Project Structure:

FinanceTracker/
│
├── src/
│   └── financeapp/
│       ├── Main.java
│       ├── FinanceTrackerForm.java
│       ├── Transaction.java
│       ├── TransactionManager.java
│       ├── MongoDBConnection.java
│       └── resources/ (optional, for background images)
│
├── README.md
└── .gitignore

--------------------------------------------------------

Setup Instructions:

Requirements:
- Java JDK 17 or newer
- MongoDB installed and running locally or remotely
- IntelliJ IDEA or any Java IDE
- Maven

How to Run:
Clone the repository:

git clone https://github.com/YOUR_USERNAME/FinanceTracker.git

- Open the project in IntelliJ IDEA (or any IDE).
- Configure MongoDB credentials inside:
- MongoDBConnection.java

Run the application:
- Main.java
- The FinanceTracker window will open automatically.

MongoDB Configuration:
- The application uses a simple connection flow handled by MongoDBConnection.java.

Default collection format:

Database: finance
Collection: transactions

Each stored transaction includes:
- Type (category)
- Amount
- Description
- Income/expense classification
