import { Action, createReducer, on } from '@ngrx/store';
import { ICommentState } from '../types/comment.state';
import { createCommentAction, createCommentActionFailure, createCommentActionSuccess, getCommentsActionSuccess } from './actions/comment.action';

const initialState: ICommentState = {
  isSubmitting: false,
  validationErrors: null,
  comments:null,
  isLoading: false
};

const commentReducer = createReducer(
  initialState,
  on(
    createCommentAction,
    (state): ICommentState => ({
      ...state,
      isSubmitting: true,
    })
  ),
  on(
    createCommentActionSuccess,
    (state): ICommentState => ({
      ...state,
      isSubmitting: false,
      validationErrors: null,
      comments: state.comments

    })
  ),
  on(
    createCommentActionFailure,
    (state,action): ICommentState => ({
      ...state,
      isSubmitting: false,
      validationErrors: action.error
    })
  ),
  on(
    getCommentsActionSuccess,
    (state,action): ICommentState => ({
      ...state,
      isSubmitting: false,
      validationErrors: null,
      comments: action.comments
    })
  )
);


export function reducers(state: ICommentState, action : Action) {
  return commentReducer(state, action)
}
