import { createAction, props } from "@ngrx/store";
import { IBackendError } from "src/app/shared/types/backendErrors.interface";
import { ICurrentUser } from "src/app/shared/types/currentUser.interface";
import { IRegisterRequest } from "../types/registerRequest.interface";
import { AuthActionTypes } from "./actionTypes";


export const registerButtonPressAction = createAction(
  AuthActionTypes.REGISTER_BUTTON_PRESS
)

export const registerAction = createAction(
  AuthActionTypes.REGISTER,
  props<{request: IRegisterRequest}>()
)


export const registerSuccessAction = createAction(
  AuthActionTypes.REGISTER_SUCCESS,
  props<{currentUser: ICurrentUser}>()
)

export const registerFailureAction = createAction(
  AuthActionTypes.REGISTER_FAILURE,
  props<{errors: IBackendError}>()
)
