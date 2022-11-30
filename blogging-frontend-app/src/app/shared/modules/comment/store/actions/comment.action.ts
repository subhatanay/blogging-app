import { createAction, props } from "@ngrx/store";
import { IBackendError } from "src/app/shared/types/backendErrors.interface";
import { IComment, ICommentsResponse } from "../../types/IComment.interface";
import { CommentActionTypes } from "../actionsTypes";

export const createCommentAction = createAction(
  CommentActionTypes.CREATE_COMMENT_ACTION,
  props<{articleId: number, comment : IComment}>()
)

export const createCommentActionSuccess = createAction(
  CommentActionTypes.CREATE_COMMENT_ACTION_SUCCESS
)

export const createCommentActionFailure = createAction(
  CommentActionTypes.CREATE_COMMENT_ACTION_FAILURE,
  props<{error: IBackendError}>()
)

export const getCommentsAction = createAction(
  CommentActionTypes.GET_COMMENTS_ACTION,
  props<{articleId: number}>()
)

export const getCommentsActionSuccess = createAction(
  CommentActionTypes.GET_COMMENTS_ACTION_SUCCESS,
  props<{comments: ICommentsResponse}>()
)

export const getCommentsActionFailure = createAction(
  CommentActionTypes.CREATE_COMMENT_ACTION_FAILURE
)


