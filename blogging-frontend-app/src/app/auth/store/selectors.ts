import { createFeatureSelector, createSelector } from "@ngrx/store";
import { IAppState } from "src/app/shared/types/app.state.interface";
import { IAuthState } from "../types/authState.interface";

export const authFeatureSelector = createFeatureSelector<IAppState, IAuthState>('auth')

export const isSubmittingSelector = createSelector(authFeatureSelector, (authState: IAuthState) => authState.isSubmitting)

export const validationErrorsSelector = createSelector(authFeatureSelector, (authState: IAuthState) => authState.validationErrors)

export const isLoggedInSelector = createSelector(authFeatureSelector, (authState: IAuthState) => authState.isLoggedIn === true)

export const isAnnonymousSelector = createSelector(authFeatureSelector, (authState: IAuthState) => authState.isLoggedIn == false || authState.isLoggedIn == null)

export const currentUserSelector = createSelector(authFeatureSelector, (authState: IAuthState) => authState.currentUser )
