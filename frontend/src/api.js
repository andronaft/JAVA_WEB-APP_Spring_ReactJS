// Talks to the Spring Boot backend. URLs are relative: in production the UI is served
// by the backend itself, in development `npm start` proxies them to localhost:8080
// (see "proxy" in package.json).
//
// Actions answer with a one element array: ["message for the user"],
// reads answer with the object itself. On network/server errors we return
// a message array as well, so components can handle both the same way.

export const REQUEST_FAILED = 'Request failed, please try again later';

async function request(url, options) {
  try {
    const response = await fetch(url, { credentials: 'same-origin', ...options });
    const data = await response.json();
    if (!response.ok && !Array.isArray(data)) {
      return [REQUEST_FAILED];
    }
    return data;
  } catch (error) {
    return [REQUEST_FAILED];
  }
}

export function getJson(url) {
  return request(url);
}

// Sends the params as a form body (not in the URL), so passwords don't end up in logs.
export function postForm(url, params = {}) {
  return request(url, { method: 'POST', body: new URLSearchParams(params) });
}
