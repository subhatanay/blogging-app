import { Action, createReducer, on } from '@ngrx/store';
import { EditArticleStateInterface } from '../types/editArticle.interface';
import {  editArticleAction, editArticleActionFailure, editArticleActionSuccess, getArticleAction, getArticleActionFailure, getArticleActionSuccess } from './actions/article.actions';

const initialState: EditArticleStateInterface = {
  isSubmitting: false,
  validationErrors: null,
  isLoading: false,
  article: null
};

const editArticleReducer = createReducer(
  initialState,
  on(
    editArticleAction,
    (state): EditArticleStateInterface => ({
      ...state,
      isSubmitting: true,
      isLoading: true
    })
  ),
  on(
    editArticleActionSuccess,
    (state): EditArticleStateInterface => ({
      ...state,
      isSubmitting: false,
      isLoading: false,
      validationErrors: null
    })
  ),
  on(
    editArticleActionFailure,
    (state,action): EditArticleStateInterface => ({
      ...state,
      isSubmitting: false,
      isLoading: false,
      validationErrors: action.error
    })
  ),
  on(
    getArticleAction,
    (state): EditArticleStateInterface => ({
      ...state,
      isSubmitting: true,
      isLoading: true
    })
  ),
  on(
    getArticleActionSuccess,
    (state,action): EditArticleStateInterface => ({
      ...state,
      isSubmitting: false,
      isLoading: false,
      validationErrors: null,
      article: action.article
    })
  ),
  on(
    getArticleActionFailure,
    (state): EditArticleStateInterface => ({
      ...state,
      isSubmitting: false,
      isLoading: false,
      validationErrors: null
    })

));


export function reducers(state: EditArticleStateInterface, action : Action) {
  return editArticleReducer(state, action)
}
