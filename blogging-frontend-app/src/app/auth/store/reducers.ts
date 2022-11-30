import { state } from '@angular/animations';
import { Action, createReducer, on } from '@ngrx/store';
import { IAuthState } from '../types/authState.interface';
import { getCurrentUserAction, getCurrentUserActionFailure, getCurrentUserActionSuccess } from './getCurrentUser.actions';
import { loginAction, loginActionFailure, loginActionSuccess } from './login.actions';
import {
  registerAction,
  registerButtonPressAction,
  registerFailureAction,
  registerSuccessAction,
} from './register.actions';
import { logoutAction } from './sync.actions';
import { updateCurrentUserActionSuccess } from './updateCurrentUser.action';

const initialState: IAuthState = {
  isSubmitting: false,
  currentUser: null,
  isLoggedIn: null,
  validationErrors: null,
  isLoading: false
};

const authReducer = createReducer(
  initialState,
  on(
    registerButtonPressAction,
    (state): IAuthState => ({
      ...state,
      isSubmitting: true,
      validationErrors: null,
    })
  ),
  on(
    registerSuccessAction,
    (state, action): IAuthState => ({
      ...state,
      isSubmitting: false,
      isLoggedIn: true,
      currentUser: action.currentUser,
    })
  ),
  on(
    registerFailureAction,
    (state, action): IAuthState => ({
      ...state,
      isSubmitting: false,
      isLoggedIn: false,
      validationErrors: action.errors
    }),

  ),
  on(
    loginAction,
    (state): IAuthState => ({
      ...state,
      isSubmitting: true,
      validationErrors: null,
    })
  ),
  on(
    loginActionSuccess,
    (state, action): IAuthState => ({
      ...state,
      isSubmitting: false,
      isLoggedIn: true,
      currentUser: action.currentUser,
    })
  ),
  on(
    loginActionFailure,
    (state, action): IAuthState => ({
      ...state,
      isSubmitting: false,
      isLoggedIn: false,
      validationErrors: action.errors
    })
  ),
  on(
    getCurrentUserAction,
    (state): IAuthState => ({
      ...state,
      isLoading: true
    })
  ),
  on(
    getCurrentUserActionSuccess,
    (state, action): IAuthState => ({
      ...state,
      isLoading: false,
      currentUser: action.currentUser,
      isLoggedIn: true
    })
  ),
  on(
    getCurrentUserActionFailure,
    (state): IAuthState => ({
      ...state,
      isLoading: false,
      currentUser: null,
      isLoggedIn: false
    })
  ),
  on(
    updateCurrentUserActionSuccess,
    (state,action): IAuthState => ({
      ...state,
      currentUser: action.currentUser
    })
  ),
  on(
    logoutAction,
    (state): IAuthState => ({
      ...state,
      ...initialState,
      isLoggedIn: false
    })
  )
);


export function reducers(state: IAuthState, actions: Action) {
  return authReducer(state, actions);
}
