import { createFeatureSelector, createSelector } from "@ngrx/store"
import { IAppState } from "src/app/shared/types/app.state.interface"
import { IPopularTagsState } from "../types/popular-tags-state.interface"

export const popularFeatureSelector = createFeatureSelector<IAppState, IPopularTagsState>('popularTags')

export const isLoadingPoplularTagsSelector = createSelector(popularFeatureSelector, (feedState: IPopularTagsState) => feedState.isLoading)

export const errorPoplularTagsSelector = createSelector(popularFeatureSelector, (feedState: IPopularTagsState) => feedState.error)

export const dataPopularTagsSelector = createSelector(popularFeatureSelector, (feedState: IPopularTagsState) => feedState.data)
