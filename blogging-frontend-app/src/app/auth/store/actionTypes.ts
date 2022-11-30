export enum AuthActionTypes {
  REGISTER = '[Auth] Register',
  REGISTER_SUCCESS = '[Auth] Register success',
  REGISTER_FAILURE = '[Auth] Register failure',
  REGISTER_BUTTON_PRESS = '[Auth] Register Button press',

  LOGIN = '[Auth] Login',
  LOGIN_SUCCESS = '[Auth] Login success',
  LOGIN_FAILURE = '[Auth] Login failure',

  GET_CURRENT_USER = '[Auth] Get Current User',
  GET_CURRENT_USER_SUCCESS = '[Auth] Get Current User success',
  GET_CURRENT_USER_FAILURE = '[Auth] Get Current User failure',

  UPDATE_CURRENT_USER = '[Auth] Update Current User',
  UPDATE_CURRENT_USER_SUCCESS = '[Auth] Update Current User success',
  UPDATE_CURRENT_USER_FAILURE = '[Auth] Update Current User failure',

  LOGOUT = "[Auth] Logout"
}
