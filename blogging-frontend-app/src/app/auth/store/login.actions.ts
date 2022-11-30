import { createAction, props } from "@ngrx/store";
import { IBackendError } from "src/app/shared/types/backendErrors.interface";
import { ICurrentUser } from "src/app/shared/types/currentUser.interface";
import { ILoginRequest } from "../types/loginRequest.interface";
import { AuthActionTypes } from "./actionTypes";

export const loginAction = createAction(
  AuthActionTypes.LOGIN,
  props<{request: ILoginRequest}> ()
)

export const loginActionSuccess = createAction(
  AuthActionTypes.LOGIN_SUCCESS,
  props<{currentUser: ICurrentUser}> ()
)
export const loginActionFailure = createAction(
  AuthActionTypes.LOGIN_FAILURE,
  props<{errors: IBackendError}> ()
)
