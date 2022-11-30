import { createAction } from "@ngrx/store";
import { AuthActionTypes } from "./actionTypes";

export const logoutAction = createAction(
  AuthActionTypes.LOGOUT
)
