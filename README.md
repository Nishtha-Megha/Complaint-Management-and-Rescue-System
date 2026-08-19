# Complaint Management System

A Java console application for registering citizen complaints and handling emergency-service requests. The system provides separate flows for citizens and administrators, with MySQL used for persistence.

## Features

- User registration, login, profile updates, and logout
- Complaint registration and complaint lookup by email
- Emergency assistance guidance for police, fire, medical, and natural-disaster situations
- Admin dashboard for worker management
- Complaint viewing and filtering tools
- Export complaint reports by department or date range

## Tech stack

- Java
- MySQL
- JDBC
- MySQL Connector/J
- IntelliJ IDEA project structure

## Project structure

```text
src/
|-- db/          Database connection and table operations
|-- ds/          Complaint data-structure service
|-- main/        Console application entry point
|-- models/      User, complaint, and worker models
`-- services/    Authentication, complaints, emergency, worker, and export services
```

## Prerequisites

- JDK 8 or later
- MySQL Server
- MySQL Connector/J 9.3.0, or another compatible Connector/J version

## Database setup

Create the database before starting the application:

```sql
CREATE DATABASE `a.i.r`;
```

The application currently connects using:

```text
Host: localhost
Port: 3306
Database: a.i.r
User: root
Password: empty
```

These values are defined in `src/db/UserTable.java` and `src/db/ComplainTable.java`. Update both files if your MySQL configuration is different.

The application creates the `user1` and `Complaint` tables when the relevant features are first used. The admin worker and complaint-management menus expect `workers` and `complaints` tables with the columns used in their SQL queries; verify or create those tables before using those admin features.

## Running in IntelliJ IDEA

1. Open the project in IntelliJ IDEA.
2. Configure the project SDK to a supported JDK.
3. Add the MySQL Connector/J JAR as a project dependency. The existing module file points to `mysql-connector-j-9.3.0.jar` in the user's Downloads directory; update that path if necessary.
4. Start MySQL Server.
5. Run `src/main/Main.java`.

## Running from the command line

From the project root, compile the source files with the Connector/J JAR on the classpath:

```powershell
New-Item -ItemType Directory -Force out\classes
javac -cp "C:\path\to\mysql-connector-j-9.3.0.jar" -d out\classes (Get-ChildItem -Recurse src -Filter *.java)
java -cp "out\classes;C:\path\to\mysql-connector-j-9.3.0.jar" main.Main
```

On macOS or Linux, replace the Windows classpath separator `;` with `:` and use the appropriate path to the Connector/J JAR.

## Using the application

The main menu supports:

1. Registering a new user
2. Logging in as a user
3. Opening the admin dashboard
4. Registering or looking up a complaint
5. Exiting the application

The current admin credentials are:

```text
Username: admin
Password: admin123
```

Change these credentials in `src/main/Main.java` before deploying the application. This project is a console prototype, so credentials and database settings are currently stored directly in the source code.

## Generated reports

Admin exports are written as text files in the current working directory. An example report is included in `ComplaintReport_Water.txt`.

## Notes

- The application requires an active MySQL connection at startup.
- Database credentials should be moved to environment variables or a configuration file for production use.
- Passwords are currently stored as plain text; a production system should hash and securely manage them.
- Emergency menu messages are informational and should not replace contacting local emergency services.
