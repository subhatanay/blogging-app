import { createAction, props } from '@ngrx/store';
import { IArticle } from 'src/app/shared/types/article.interface';
import { IArticleResponse } from 'src/app/shared/types/articleResponse.interface';
import { IGetFeedResponse } from '../../types/getFeedResponse.interface';
import { ArticleActionType } from '../articleActionTypes';

export const getArticleAction = createAction(
  ArticleActionType.GET_ARTICLE,
  props<{ articleId: number }>()
);

export const getArticleActionSuccess = createAction(
  ArticleActionType.GET_ARTICLE_SUCCESS,
  props<{ article: IArticle }>()
);

export const getArticleActionFailure = createAction(
  ArticleActionType.GET_ARTICLE_FAILURE
);

