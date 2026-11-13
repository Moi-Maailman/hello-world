# Quizzer

Quizzer is a web application where teachers can create and manage quizzes, and students can take them to learn about course topics.

This project is developed by team **Hello World**.

## Team members

- Mai Vu ([phuongmai19067-web](https://github.com/phuongmai19067-web))
- Mai Phuong Luu ([@phuongmaix2](https://github.com/phuongmaix2))
- Thy Nguyen ([nnat-bot](https://github.com/nnat-bot))
- Minh Doan ([ngocminh1110](https://github.com/ngocminh1110))

## Backlog

[Quizzer backlog](https://github.com/orgs/Moi-Maailman/projects/2)

## Deployment
The application is deployed to Rahti: https://hello-world-quizzer-hello-world.2.rahtiapp.fi

## Developer guide

### Requirements

- Java 17
- Node.js
- Git

### Start the backend

Open a terminal in the project folder.

On Windows, run:

```powershell
.\mvnw.cmd spring-boot:run
```

On macOS, Linux, or Git Bash, run:

```bash
./mvnw spring-boot:run
```

Open the teacher dashboard at http://localhost:8080.

The published quizzes API is available at http://localhost:8080/api/quizzes.

Press `Ctrl+C` to stop the backend.

### Start the frontend

Keep the backend running and open another terminal in the project folder.

Run:

```bash
cd frontend
npm install
npm run dev
```

Open the student dashboard at http://localhost:5173.

Press `Ctrl+C` to stop the frontend.

### Run the tests

Stop the backend before running the tests.

On Windows:

```powershell
.\mvnw.cmd test
```

On macOS, Linux, or Git Bash:

```bash
./mvnw test
```

### Build the applications

Build the backend on Windows:

```powershell
.\mvnw.cmd clean package
```

Build the frontend:

```bash
cd frontend
npm install
npm run build
```
