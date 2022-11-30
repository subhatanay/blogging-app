import { createAction, props } from '@ngrx/store';
import { IGetFeedResponse } from '../../types/getFeedResponse.interface';
import { FeedActionType } from '../feedActionTypes';

export const getFeedAction = createAction(
  FeedActionType.GET_FEED,
  props<{ url: string }>()
);

export const getFeedActionSuccess = createAction(
  FeedActionType.GET_FEED_SUCCESS,
  props<{ feed: IGetFeedResponse }>()
);

export const getFeedActionFailure = createAction(
  FeedActionType.GET_FEED_FAILURE
);

