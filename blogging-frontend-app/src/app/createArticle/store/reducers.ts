import { Action, createReducer, on } from '@ngrx/store';
import { CreateArticleStateInterface } from '../types/createArticleState.interface';
import { createArticleAction, createArticleActionFaulure, createArticleActionSuccess } from './actions/article.actions';

const initialState: CreateArticleStateInterface = {
  isSubmitting: false,
  validationErrors: null,
};

const createArticleReducer = createReducer(
  initialState,
  on(
    createArticleAction,
    (state): CreateArticleStateInterface => ({
      ...state,
      isSubmitting: true,
    })
  ),
  on(
    createArticleActionSuccess,
    (state): CreateArticleStateInterface => ({
      ...state,
      isSubmitting: false,
      validationErrors: null
    })
  ),
  on(
    createArticleActionFaulure,
    (state,action): CreateArticleStateInterface => ({
      ...state,
      isSubmitting: false,
      validationErrors: action.error
    })
  )
);


export function reducers(state: CreateArticleStateInterface, action : Action) {
  return createArticleReducer(state, action)
}
