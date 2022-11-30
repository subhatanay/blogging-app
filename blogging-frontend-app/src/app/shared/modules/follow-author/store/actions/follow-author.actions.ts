import { createAction, props } from "@ngrx/store";
import { IArticle } from "src/app/shared/types/article.interface";
import { IUserProfile } from "src/app/userProfile/types/userProfile.interface";
import { FollowAuthActionTypes } from "./actionTypes";

export const followAuthorAction = createAction(
  FollowAuthActionTypes.FOLLOW_AUTHOR_ACTION,
  props<{authorId: number}>()
)
export const followAuthorActionSuccess = createAction(
  FollowAuthActionTypes.FOLLOW_AUTHOR_ACTION_SUCCESS,
  props<{profile: IUserProfile}>()
)
export const followAuthorActionFailure = createAction(
  FollowAuthActionTypes.FOLLOW_AUTHOR_ACTION_FAILURE
)

export const unfollowAuthorAction = createAction(
  FollowAuthActionTypes.UNFOLLOW_AUTHOR_ACTION,
  props<{authorId: number}>()
)
export const unfollowAuthorActionSuccess = createAction(
  FollowAuthActionTypes.UNFOLLOW_AUTHOR_ACTION_SUCCESS,
  props<{profile: IUserProfile}>()
)
export const unfollowAuthorActionFailure = createAction(
  FollowAuthActionTypes.FOLLOW_AUTHOR_ACTION_FAILURE
)
