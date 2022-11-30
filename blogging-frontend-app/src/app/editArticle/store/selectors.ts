import { createFeatureSelector, createSelector } from '@ngrx/store';
import { IAppState } from 'src/app/shared/types/app.state.interface';
import {  EditArticleStateInterface } from '../types/editArticle.interface';

export const editArticleFeatureSelector = createFeatureSelector<
  IAppState,
  EditArticleStateInterface
>('editArticle');

export const isSubmittingSelector = createSelector(
  editArticleFeatureSelector,
  (editArticleState: EditArticleStateInterface) =>
  editArticleState.isSubmitting
);

export const isLoadingEditArticleSelector = createSelector(
  editArticleFeatureSelector,
  (editArticleState: EditArticleStateInterface) =>
  editArticleState.isLoading
);

export const validationErrorsSelector = createSelector(
  editArticleFeatureSelector,
  (editArticleState: EditArticleStateInterface) =>
  editArticleState.validationErrors
);

export const articleEditArticleSelector = createSelector(
  editArticleFeatureSelector,
  (editArticleState: EditArticleStateInterface) =>
  editArticleState.article
);
