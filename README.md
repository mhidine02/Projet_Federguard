# FederGuard Project

## Table of Contents
1. [Software Architecture](#software-architecture)
2. [Frontend](#frontend)
3. [Backend](#backend)
4. [Getting Started](#getting-started)
5. [Login Information](#login-information)
6. [Video Demonstration](#video-demonstration)
7. [Contributing](#contributing)

---

## Software Architecture


![image](https://github.com/user-attachments/assets/79d415e5-810b-455d-b6cf-d42203489765)

FederGuard is built on a layered architecture that separates the backend and frontend. Below is a simple representation of the architecture
The frontend uses Thymeleaf templates with HTML and CSS, while the backend leverages Spring Boot with a role-based security system, database integration, and anomaly detection logic.

---

## Frontend

The frontend is built using Thymeleaf templates. It includes:

- **Login Page**: A simple and secure login interface.
- **Admin Dashboard**: Features for managing users and viewing client data.
- **Client Dashboard**: Displays uploaded files and their anomaly statuses.

Technologies used:
- Thymeleaf
- HTML/CSS

---

## Backend

The backend is implemented using **Spring Boot** and is structured as follows:

- **Config Package**:
  - Security configuration for role-based access.

- **Controllers**:
  - AdminController: Handles admin functionalities (e.g., blocking/unblocking clients, creating clients).
  - ClientController: Manages client-specific features, such as file uploads and displaying updates.
  - ClientUpdateController: REST controller for managing client update data.
  - DashboardController: Redirects users to role-based dashboards.
  - LoginController: Manages the login view.

- **Entities**:
  - `User`: Represents users and their roles.
  - `ClientUpdate`: Represents client updates with anomaly detection metadata.

- **Services**:
  - `AnomalyDetectionService`: Detects anomalies in uploaded files based on statistical metrics.

- **Repositories**:
  - `UserRepository`: Manages user data.
  - `ClientUpdateRepository`: Manages client updates.

---

## Getting Started

Follow these steps to run the FederGuard project locally:

### Prerequisites
- Java 17 or higher
- Maven

### Installation
1. Clone the repository:
   ```bash
   git clone https://github.com/Ayajaid/Federguard.git
   ```
2. Navigate to the project directory:
   ```bash
   cd federguard
   ```
3. Build the project:
   ```bash
   mvn clean install
   ```
4. Run the application:
   ```bash
   mvn spring-boot:run
   ```
5. Access the application:
   - Open [http://localhost:8080/login](http://localhost:8080/login) in your browser.

---

## Login Information

### Admin Login
- **Username**: `admin`
- **Password**: `adminpass`

### Client Logins
The system initializes five clients with the following credentials:

| Username    | Password     |
|-------------|--------------|
| client1     | clientpass1  |
| client2     | clientpass2  |
| client3     | clientpass3  |
| client4     | clientpass4  |
| client5     | clientpass5  |

---

## Video Demonstration

A video demonstration showcasing the features of FederGuard is available [here](

https://github.com/user-attachments/assets/a2d3119d-bd39-48b2-b014-15ed5425d26a

).

---

## Contributing

Contributors to this project:
- **Aya Jaid**
- **Younes Mohidine**

If you would like to contribute, please follow these steps:
1. Fork the repository.
2. Create a new branch for your feature:
   ```bash
   git checkout -b feature-name
   ```
3. Commit your changes:
   ```bash
   git commit -m "Description of changes"
   ```
4. Push your branch:
   ```bash
   git push origin feature-name
   ```
5. Submit a pull request.

---

Thank you for using FederGuard!
