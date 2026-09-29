# Conference – React client

Single page app for the Spring Boot backend in the parent directory.

```bash
npm install
npm start               # dev server on http://localhost:3000, API calls are proxied to http://localhost:8080
npm test                # Jest + React Testing Library
npm run build:spring    # production build copied to ../src/main/resources/static (served by Spring Boot)
```

Run the backend (`mvn spring-boot:run` in the parent directory) before `npm start`.
After changing the UI run `npm run build:spring` and commit the updated `src/main/resources/static`.
