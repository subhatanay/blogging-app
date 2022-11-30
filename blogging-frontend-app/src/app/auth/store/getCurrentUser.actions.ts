import { createAction, props } from "@ngrx/store"
import { ICurrentUser } from "src/app/shared/types/currentUser.interface"
import { AuthActionTypes } from "./actionTypes"

export const getCurrentUserAction = createAction(
  AuthActionTypes.GET_CURRENT_USER
)

export const getCurrentUserActionSuccess = createAction(
  AuthActionTypes.GET_CURRENT_USER_SUCCESS,
  props<{currentUser: ICurrentUser}> ()
)
export const getCurrentUserActionFailure = createAction(
  AuthActionTypes.GET_CURRENT_USER_FAILURE
)
