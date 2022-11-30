import { act } from '@ngrx/effects';
import { Action, createReducer, on } from '@ngrx/store';
import { IUserProfileState } from '../types/userProfileState';
import { getUserProfileAction, getUserProfileActionFailre, getUserProfileActionSuccess } from './actions/getUserProfile.action';

const initailState: IUserProfileState = {
  data: null,
  isLoading: false,
  error: null,
};

const userProfileReducer = createReducer(
   initailState,
  on(
    getUserProfileAction,
    (state): IUserProfileState => ({
      ...state,
      isLoading: true
     })
  ),
  on(
    getUserProfileActionSuccess,
    (state,action): IUserProfileState => ({
      ...state,
      isLoading: false,
      data: action.userProfile,
      error: null
     })
  ),
  on(
    getUserProfileActionFailre,
    (state,action): IUserProfileState => ({
      ...state,
      isLoading: false,
      data: null,
      error: null
     })
  )
);


export function reducers(state: IUserProfileState, action :Action) {
  return userProfileReducer(state,action)
}
