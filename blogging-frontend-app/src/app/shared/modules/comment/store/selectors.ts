import { createFeatureSelector, createSelector } from "@ngrx/store";
import { IAppState } from "src/app/shared/types/app.state.interface";
import { ICommentState } from "../types/comment.state";

export const settingsFeatureSelector = createFeatureSelector<IAppState, ICommentState>('comments')

export const isSubmittingSelector = createSelector(settingsFeatureSelector, (settingsState: ICommentState) => settingsState.isSubmitting)

export const validationErrorSelector = createSelector(settingsFeatureSelector, (settingsState: ICommentState) => settingsState.validationErrors)

export const commentDataSelector =  createSelector(settingsFeatureSelector, (settingsState: ICommentState) => settingsState.comments)
