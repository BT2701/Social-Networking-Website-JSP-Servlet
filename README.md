# Social Networking Website

## Overview

This is a social networking website project that allows users to create profiles, connect with friends, and share updates. The project uses modern web technologies including Bootstrap, CSS, Fetch API, JPA, JSP, Servlet, and MySQL to provide a robust and interactive user experience.

## Features

- **User Registration and Login:** Users can register, log in, and manage their profiles.
- **Friendship Management:** Users can send, accept, and remove friend requests.
- **Posts and Updates:** Users can create, view, and interact with posts and updates.
- **Real-time Notifications:** Users receive notifications for friend requests and posts.

## Technology Stack

- **Frontend:**
  - **Bootstrap:** For responsive design and modern UI components.
  - **CSS:** Custom styling to enhance the appearance.
  - **Fetch API:** For making asynchronous HTTP requests.

- **Backend:**
  - **JSP (JavaServer Pages):** For generating dynamic web content.
  - **Servlets:** For handling HTTP requests and responses.
  - **JPA (Java Persistence API):** For ORM and database interactions.
  - **MySQL:** For storing user data and application state.

## Installation

### Prerequisites

- Java Development Kit (JDK) 8 or higher
- Apache Tomcat or any compatible servlet container
- MySQL Database
- Maven (for dependency management)

### Setup

1. **Clone the Repository:**

   ```bash
   git clone https://github.com/yourusername/social-networking-website-jsp-servlet.git

2. **Configure MySQL Database:**
   ```bash
    jdbc.url=jdbc:mysql://localhost:3306/social_network
    jdbc.username=root
    jdbc.password=yourpassword
3. **Build and Deploy:**
   ```bash
    cd social-networking-website-jsp-servlet
    mvn clean install
    ```
    Deploy the generated WAR file (target/social-networking-website-jsp-servlet.war) to your servlet container (e.g., Apache Tomcat).
4. **Run the Application:**
   Start the servlet container and navigate to ``http://localhost:8080/social-networking-website-jsp-servlet`` in your browser.

## Usage
- __Register:__ Create a new account by navigating to the registration page and providing your details.
- **Login:** Access your account by logging in with your credentials.
- **Manage Profile:** Update your profile information and settings.
- **Connect with Friends:** Search for users and send/accept friend requests.
- **Post Updates:** Share updates and interact with posts from other users.
## API Endpoints
- **POST /api/register:** Register a new user
- **POST /api/login:** Authenticate and log in a user
- **GET /api/friends:** Retrieve a list of friends
- **POST /api/friends/request:** Send a friend request
- **POST /api/posts:** Create a new post
- **GET /api/posts:** Retrieve posts
## Contributing

We welcome contributions to improve the project! To contribute, please follow these guidelines:

1. **Fork the Repository:**
   - Click the "Fork" button at the top-right of this repository to create your own copy.

2. **Clone Your Fork:**
   - Clone the repository to your local machine using the following command:

     ```bash
     git clone https://github.com/yourusername/social-networking-website-jsp-servlet.git
     ```

3. **Create a New Branch:**
   - Create a new branch for your feature or fix:

     ```bash
     git checkout -b feature-or-fix-name
     ```

4. **Make Changes:**
   - Implement your changes or new features.

5. **Commit Your Changes:**
   - Stage your changes and commit them with a descriptive message:

     ```bash
     git add .
     git commit -m "Add a descriptive message about your changes"
     ```

6. **Push Your Changes:**
   - Push your changes to your fork:

     ```bash
     git push origin feature-or-fix-name
     ```

7. **Create a Pull Request:**
   - Go to the original repository and click on the "New Pull Request" button.
   - Select your branch from the dropdown menu and describe the changes you made.
   - Submit the pull request for review.

## Code of Conduct

Please adhere to the following code of conduct while contributing:

- Be respectful and considerate to other contributors.
- Focus on constructive feedback and collaborative problem-solving.
- Follow the project's coding standards and guidelines.

## License

This project is licensed under the [Apache v2.0 License](LICENSE). See the [LICENSE](LICENSE) file for details.



