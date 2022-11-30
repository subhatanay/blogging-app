import { createAction, props } from '@ngrx/store';
import { IArticle } from 'src/app/shared/types/article.interface';
import { ArticleActionType } from '../articleActionTypes';

export const deleteArticleAction = createAction(
  ArticleActionType.DELETE_ARTICLE,
  props<{ articleId: number }>()
);

export const deleteArticleActionSuccess = createAction(
  ArticleActionType.DELETE_ARTICLE_SUCCESS
);

export const deleteArticleActionFailure = createAction(
  ArticleActionType.DELETE_ARTICLE_FAILURE
);

