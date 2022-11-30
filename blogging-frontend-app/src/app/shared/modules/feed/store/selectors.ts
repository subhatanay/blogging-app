import { createFeatureSelector, createSelector } from "@ngrx/store";
import { IAppState } from "src/app/shared/types/app.state.interface";
import { IFeedState } from "../types/feedState.interface";

export const feedFeatureSelector = createFeatureSelector<IAppState, IFeedState>('feedData')

export const isLoadingFeedSelector = createSelector(feedFeatureSelector, (feedState: IFeedState) => feedState.isLoading)

export const errorFeedSelector = createSelector(feedFeatureSelector, (feedState: IFeedState) => feedState.error)

export const dataFeedSelector = createSelector(feedFeatureSelector, (feedState: IFeedState) => feedState.data)

