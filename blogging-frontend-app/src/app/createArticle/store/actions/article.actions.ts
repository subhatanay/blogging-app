import { createAction, props } from "@ngrx/store";
import { IArticleInput, ICreateArticleRequest } from "src/app/shared/modules/articleForm/types/articleInput.interface";
import { IArticle } from "src/app/shared/types/article.interface";
import { IBackendError } from "src/app/shared/types/backendErrors.interface";
import { CreateArticleActionTypes } from "../createArticleActionTypes";

export const createArticleAction = createAction(
  CreateArticleActionTypes.CREATE_ARTICLE,
  props<{article: IArticleInput}>()
)

export const createArticleActionSuccess = createAction(
  CreateArticleActionTypes.CREATE_ARTICLE_SUCCESS,
  props<{article: IArticle}>()
)

export const createArticleActionFaulure = createAction(
  CreateArticleActionTypes.CREATE_ARTICLE_FAILURE,
  props<{error: IBackendError}>()
)
