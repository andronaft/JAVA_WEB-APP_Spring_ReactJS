// The logged in user as known by the UI. The server keeps the real session
// (cookie); this copy only decides what to show.
const myInitObject = {
  user_role: undefined,
  user_id: undefined,
  user_login: undefined,
};

const ROLES = ['admin', 'manager'];

export function loadUser() {
  try {
    const role = localStorage.getItem('user_rolee');
    myInitObject.user_id = localStorage.getItem('user_idd') || undefined;
    myInitObject.user_login = localStorage.getItem('user_loginn') || undefined;
    myInitObject.user_role = ROLES.includes(role) ? role : undefined;
  } catch (e) {
    // localStorage can be unavailable (private mode), the UI then acts as logged out
  }
  return myInitObject;
}

export function saveUser(user) {
  try {
    localStorage.setItem('user_idd', user.id);
    localStorage.setItem('user_loginn', user.login);
    if (user.role) {
      localStorage.setItem('user_rolee', user.role);
    } else {
      localStorage.removeItem('user_rolee');
    }
  } catch (e) {
    // ignore, see loadUser
  }
  return loadUser();
}

export function clearUser() {
  try {
    localStorage.removeItem('user_idd');
    localStorage.removeItem('user_loginn');
    localStorage.removeItem('user_rolee');
  } catch (e) {
    // ignore, see loadUser
  }
  return loadUser();
}

export default myInitObject;
