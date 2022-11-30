import { createFeatureSelector, createSelector } from "@ngrx/store";
import { IAppState } from "src/app/shared/types/app.state.interface";
import { IUserProfileState } from "../types/userProfileState";

export const getUserProfileFeatureSelector = createFeatureSelector<IAppState, IUserProfileState>('userProfile')

export const isLoadingSelector = createSelector(getUserProfileFeatureSelector, (userProfileState: IUserProfileState) => userProfileState.isLoading)

export const errorUserProfileSelector = createSelector(getUserProfileFeatureSelector, (userProfileState: IUserProfileState) => userProfileState.error)

export const dataUserProfileSelector = createSelector(getUserProfileFeatureSelector, (userProfileState: IUserProfileState) => userProfileState.data)
