import { Action, createReducer, on } from "@ngrx/store";
import {  IPopularTagsState } from "../types/popular-tags-state.interface";
import { getPopularTagsListAction, getPopularTagsListActionFailure, getPopularTagsListActionSuccess } from "./actions/popular-tags-list.actions";

export const initialState: IPopularTagsState = {
  isLoading : false,
  data: null,
  error :null
}


const popularTagsReducer = createReducer(
  initialState,
  on(
    getPopularTagsListAction,
    (state): IPopularTagsState => ({
      ...state,
      isLoading: true,
    })
  ),
  on(
    getPopularTagsListActionSuccess,
    (state,action): IPopularTagsState => ({
      ...state,
      isLoading: false,
      data: action.tags,
      error: null
    })
  ),
  on(
    getPopularTagsListActionFailure,
    (state): IPopularTagsState => ({
      ...state,
      isLoading: false,
      error: null,
      data: null
    })
  ),
)


export function reducers(state: IPopularTagsState, action: Action) {
  return popularTagsReducer(state, action)
}
