import { Action, createReducer, on } from "@ngrx/store";
import { updateCurrentUserAction, updateCurrentUserActionFailure, updateCurrentUserActionSuccess } from "src/app/auth/store/updateCurrentUser.action";
import { ISettingsState } from "../components/types/setingsState.inerface";

const intialState : ISettingsState = {
  isSubmitting :false,
  validationErrors : null
}

const settingsReducers = createReducer(
  intialState,
  on(
    updateCurrentUserAction,
    (state): ISettingsState => ({
      ...state,
      isSubmitting: true
    })
  ),
  on(
    updateCurrentUserActionSuccess,
    (state): ISettingsState => ({
      ...state,
      isSubmitting: false,
      validationErrors: null
    })
  ),
  on(
    updateCurrentUserActionFailure,
    (state,action): ISettingsState => ({
      ...state,
      isSubmitting: false,
      validationErrors:action.errors
    })
  )
)

export function reducers(state: ISettingsState, action : Action) {
  return settingsReducers(state, action)
}
