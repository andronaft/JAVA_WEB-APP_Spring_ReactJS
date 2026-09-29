// Copies the production build into the Spring Boot static resources,
// so the backend serves the latest UI. Usage: npm run build:spring
const fs = require('fs');
const path = require('path');

const from = path.join(__dirname, '..', 'build');
const to = path.join(__dirname, '..', '..', 'src', 'main', 'resources', 'static');

fs.rmSync(to, { recursive: true, force: true });
fs.cpSync(from, to, { recursive: true });
console.log(`Copied ${from} -> ${to}`);
