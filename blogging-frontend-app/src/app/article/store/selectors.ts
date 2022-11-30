import { createFeatureSelector, createSelector } from "@ngrx/store";
import { IAppState } from "src/app/shared/types/app.state.interface";
import { IArticleState } from "../types/articleState.interface";

export const articleFeatureSelector = createFeatureSelector<IAppState, IArticleState>('article')

export const isLoadingArticleSelector = createSelector(articleFeatureSelector, (articleState: IArticleState) => articleState.isLoading)

export const errorArticleSelector = createSelector(articleFeatureSelector, (articleState: IArticleState) => articleState.error)

export const dataArticleSelector = createSelector(articleFeatureSelector, (articleState: IArticleState) => articleState.data)

export const isFavouriteArticleSelector = createSelector(articleFeatureSelector, (articleState: IArticleState) => articleState.data?.liked)

export const favoriteAritcleCountSelector = createSelector(articleFeatureSelector, (articleState: IArticleState) => articleState.data?.likesCount)
