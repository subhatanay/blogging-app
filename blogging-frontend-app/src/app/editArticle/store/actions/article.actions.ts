import { createAction, props } from "@ngrx/store";
import { IArticleInput, ICreateArticleRequest } from "src/app/shared/modules/articleForm/types/articleInput.interface";
import { IArticle } from "src/app/shared/types/article.interface";
import { IBackendError } from "src/app/shared/types/backendErrors.interface";
import {  EditArticleActionTypes,  } from "../editArticleActionTypes";

export const editArticleAction = createAction(
  EditArticleActionTypes.UPDATE_ARTICLE,
  props<{articleId:number, article: IArticleInput}>()
)

export const editArticleActionSuccess = createAction(
  EditArticleActionTypes.UPDATE_ARTICLE_SUCCESS,
  props<{ article: IArticle }>()
)

export const editArticleActionFailure = createAction(
  EditArticleActionTypes.UPDATE_ARTICLE_FAILURE,
  props<{error: IBackendError}>()
)

export const getArticleAction = createAction(
  EditArticleActionTypes.GET_ARTICLE,
  props<{ articleId: number }>()
);

export const getArticleActionSuccess = createAction(
  EditArticleActionTypes.GET_ARTICLE_SUCCESS,
  props<{ article: IArticle }>()
);

export const getArticleActionFailure = createAction(
  EditArticleActionTypes.GET_ARTICLE_FAILURE
);

