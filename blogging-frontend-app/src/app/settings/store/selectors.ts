import { createFeatureSelector, createSelector } from "@ngrx/store";
import { IAppState } from "src/app/shared/types/app.state.interface";
import { ISettingsState } from "../components/types/setingsState.inerface";

export const settingsFeatureSelector = createFeatureSelector<IAppState, ISettingsState>('settings')

export const isSubmittingSelector = createSelector(settingsFeatureSelector, (settingsState: ISettingsState) => settingsState.isSubmitting)

export const validationErrorSelector = createSelector(settingsFeatureSelector, (settingsState: ISettingsState) => settingsState.validationErrors)


