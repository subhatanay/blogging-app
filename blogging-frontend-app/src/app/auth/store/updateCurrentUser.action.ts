import { createAction, props } from "@ngrx/store";
import { IBackendError } from "src/app/shared/types/backendErrors.interface";
import { ICurrentUser } from "src/app/shared/types/currentUser.interface";
import { ICurrentUserInput } from "src/app/shared/types/ICurrentUserInput.interface";
import { ILoginRequest } from "../types/loginRequest.interface";
import { IUpdateUserRequest } from "../types/updateUserRequest.interface";
import { AuthActionTypes } from "./actionTypes";

export const updateCurrentUserAction = createAction(
  AuthActionTypes.UPDATE_CURRENT_USER,
  props<{userId: number, data: IUpdateUserRequest}> ()
)

export const updateCurrentUserActionSuccess = createAction(
  AuthActionTypes.UPDATE_CURRENT_USER_SUCCESS,
  props<{currentUser: ICurrentUser}> ()
)
export const updateCurrentUserActionFailure = createAction(
  AuthActionTypes.UPDATE_CURRENT_USER_FAILURE,
  props<{errors: IBackendError}> ()
)
