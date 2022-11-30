import { createFeatureSelector, createSelector } from '@ngrx/store';
import { IAppState } from 'src/app/shared/types/app.state.interface';
import { CreateArticleStateInterface } from '../types/createArticleState.interface';

export const createArticleFeatureSelector = createFeatureSelector<
  IAppState,
  CreateArticleStateInterface
>('createArticle');

export const isSubmittingSelector = createSelector(
  createArticleFeatureSelector,
  (createArticleState: CreateArticleStateInterface) =>
    createArticleState.isSubmitting
);

export const validationErrorsSelector = createSelector(
  createArticleFeatureSelector,
  (createArticleState: CreateArticleStateInterface) =>
    createArticleState.validationErrors
);
