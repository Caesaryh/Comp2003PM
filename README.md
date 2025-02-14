### Password Management Application: README

---

## **Overview**

This repository contains the source code and associated files for the **Password Management Application**, a user-friendly and secure password manager tailored specifically for elderly individuals and non-tech-savvy users. The project prioritizes simplicity, accessibility, and security to provide an intuitive platform for managing passwords and account information.

---

## **Features**

- **Ease of Use**: Simplified single-page application (SPA) design with minimal navigation.
- **Security**: Strong encryption for password storage and secure user authentication.
- **Accessibility**: Features include adjustable font sizes, high-contrast themes, and an intuitive interface.
- **Core Functionalities**:
  - Secure storage of passwords and account details.
  - Easy retrieval and editing of stored information.
  - Categorization and labeling for organization.
- **Compatibility**: Works seamlessly across desktop, tablet, and mobile devices.
- **Guided Help**: Step-by-step instructions for common tasks.

---

## **Project Structure**

```
├── /src
│   ├── /components     # UI components (buttons, forms, modals, etc.)
│   ├── /services       # Authentication and data storage services
│   ├── /utils          # Utility functions for encryption and validation
│   └── /styles         # Application styling (CSS/SCSS files)
├── /public
│   ├── index.html      # Main HTML file for the SPA
│   └── assets          # Static assets (images, icons, etc.)
├── README.md           # This file
├── package.json        # Node.js dependencies and scripts
├── .gitignore          # Ignored files for Git
└── LICENSE             # Project license
```

---

## **Setup Instructions**

### Prerequisites:
1. **Node.js**: Ensure you have Node.js (v16 or higher) installed on your machine.
2. **Git**: Required for cloning the repository.

### Steps:
1. **Clone the Repository**:
   ```bash
   git clone https://github.com/Caesaryh/Comp2003PM
   cd password-management-app
   ```
2. **Install Dependencies**:
   ```bash
   npm install
   ```
3. **Run the Application**:
   ```bash
   npm start
   ```

---

## **Technologies Used**

- **Frontend**:
  - React.js (or Vue.js depending on team preference)
  - HTML5, CSS3, JavaScript (ES6+)
- **Backend**:
  - Node.js with Express.js
  - Local storage for MVP (future scalability for database integration)
- **Security**:
  - AES encryption for data storage
  - JWT for user authentication
- **Version Control**:
  - Git and GitHub for source code management

---

## **License**

This project is licensed under the MIT License. See the file for details.

---

## **Acknowledgments**

This project was developed by the team:
- **Fin Cutland** (Cybersecurity Expert)
- **Tyler Bennett** (Technical Lead)
- **Shenglong Xie** (Front-End Developer)
- **Yong Guo** (Back-End Developer)

---

## **Contact**

For questions or issues, please contact us via the repository's [issues](https://github.com/username/password-management-app/issues) tab.

