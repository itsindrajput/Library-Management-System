# 📚 Library Management System

Welcome to the Library Management System repository! Manage your library's resources with ease using this Java-based application. From adding new books to tracking borrowed items, this system simplifies library operations.

---

## 📌 Requirements

* Java 8 or later
* MySQL Server
* IntelliJ IDEA or any Java IDE (optional)

---

## 💡 Features

- **Database Design**: Designed with efficiency in mind, the database schema organizes information about books, users, and transactions.
- **User-Friendly Interface**: A simple and intuitive user interface allows seamless interaction with the system.
- **JDBC Connectivity**: JDBC integration facilitates smooth communication between the Java application and the database.
- **Comprehensive Functionality**: Perform a range of tasks including adding, searching, borrowing, and returning books, with ease.
- **Robust Error Handling**: Implemented error handling ensures the system gracefully manages exceptions during database operations.

---

## 📋 Table of Contents

- [Database Design](#database-design)
- [User Interface](#user-interface)
- [JDBC Connectivity](#jdbc-connectivity)
- [Functionality](#functionality)
- [Error Handling](#error-handling)

---

## 🛢 Database Design

Efficiently store and manage information about books, users, and transactions with a carefully crafted database schema.

---

## 👤 User Interface

Interact seamlessly with the Library Management System through a user-friendly interface. Perform tasks such as adding, searching, borrowing, and returning books effortlessly.

---

## 🔗 JDBC Connectivity

Utilize JDBC connectivity to establish a robust connection between the Java application and the database. This ensures efficient data retrieval and manipulation.

---

## ⚡ Functionality

Experience comprehensive functionality with the ability to perform various tasks including adding books, searching for books by keyword, borrowing books, returning books, and displaying the list of borrowed books.

---

## ⚠️ Error Handling

Benefit from robust error handling mechanisms that gracefully manage exceptions during database operations. Ensure smooth system operation under all circumstances.

---

## 🚀 Getting Started
- Follow these steps to set up and run the project on your local machine.
---

### 🔗 1. Download MySQL JDBC Driver

Download the MySQL Connector/J from the official website:
👉 [https://dev.mysql.com/downloads/connector/j/](https://dev.mysql.com/downloads/connector/j/)

* Extract the ZIP file.
* Copy the `mysql-connector-j-8.3.0.jar` file into a `lib/` folder inside the project root (create `lib/` if it doesn't exist).

---

### ⚙️ 2. Add the JAR to Your Project

#### If you're using **IntelliJ IDEA**:

* Right-click on your project → `Open Module Settings` → `Libraries` → `+` → `Java`
* Select the JAR file from the `lib/` directory
* Click **Apply** and then **OK**

#### If you're using the **command line**:

Use this to compile:

```bash
javac -cp ".;lib/mysql-connector-j-8.3.0.jar" src/com/example/Main.java
```

---

### 🛠️ 3. Configure the Database Connection

Update the database connection details in your Java class (e.g., `DBConnection.java`):

```java
String url = "jdbc:mysql://localhost:3306/library_db";
String username = "root";
String password = "your_mysql_password";
```

---

### 🗃️ 4. Set Up the MySQL Database

1. Run the application — it will automatically create the following tables if they don’t already exist:

* **Books**
* **Users**
* **Transactions**

Alternatively, you can manually create the `library_db` database:

```sql
CREATE DATABASE library_db;
```

The table creation logic is already built into the `createTables()` method.

---

### ▶️ 5. Run the Application

Once everything is set up, run the application:

```bash
java -cp ".;lib/mysql-connector-j-8.3.0.jar" Main
```

You’ll see an interactive CLI menu like this:

```
Library Management System
1. Add Book
2. Search Book
3. Borrow Book
4. Return Book
5. Show Available Books
6. Exit
```
---

## ✅ You're all set!

The Library Management System is now ready and connected to your MySQL database. Start managing your library resources efficiently!

---

## 🤝 Contributing
- Feel free to fork this repo and submit pull requests for improvements, bug fixes, or feature additions.
---