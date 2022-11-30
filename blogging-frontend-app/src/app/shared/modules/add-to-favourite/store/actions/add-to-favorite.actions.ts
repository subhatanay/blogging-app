import { createAction, props } from "@ngrx/store";
import { IArticle } from "src/app/shared/types/article.interface";
import { AddToFavouriteActionTypes } from "../actionTypes";

export const favouriteAction = createAction(
  AddToFavouriteActionTypes.FAVORITE_ACTION,
  props<{articleId: number}>()
)
export const favouriteActionSuccess = createAction(
  AddToFavouriteActionTypes.FAVORITE_ACTION_SUCCESS,
  props<{article: IArticle}>()
)
export const favouriteActionFailure = createAction(
  AddToFavouriteActionTypes.FAVORITE_ACTION_FAILURE
)

export const unFavouriteAction = createAction(
  AddToFavouriteActionTypes.UN_FAVORITE_ACTION,
  props<{articleId: number}>()
)
export const unFavouriteActionSuccess = createAction(
  AddToFavouriteActionTypes.UN_FAVORITE_ACTION_SUCCESS,
  props<{article: IArticle}>()
)
export const unFavouriteActionFailure = createAction(
  AddToFavouriteActionTypes.UN_FAVORITE_ACTION_FAILURE,
)
