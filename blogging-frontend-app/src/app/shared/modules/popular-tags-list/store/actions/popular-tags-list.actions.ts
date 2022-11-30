import { createAction, props } from '@ngrx/store';
import { PopularActionTypes } from '../actionTypes';
import { IPopularTagsResponse } from '../../types/popular-tag-interface';

export const getPopularTagsListAction = createAction(
  PopularActionTypes.GET_TAG_ACTION
);

export const getPopularTagsListActionSuccess = createAction(
  PopularActionTypes.GET_TAG_ACTION_SUCCESS,
  props<{ tags: IPopularTagsResponse }>()
);

export const getPopularTagsListActionFailure = createAction(
  PopularActionTypes.GET_TAG_ACTION_FAILURE
)
