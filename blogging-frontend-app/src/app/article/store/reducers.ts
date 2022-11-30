import { routerNavigatedAction, routerNavigationAction } from '@ngrx/router-store';
import { Action, createReducer, on } from '@ngrx/store';
import { favouriteActionSuccess, unFavouriteActionSuccess } from 'src/app/shared/modules/add-to-favourite/store/actions/add-to-favorite.actions';
import { IArticleState } from '../types/articleState.interface';
import {
  getArticleAction,
  getArticleActionFailure,
  getArticleActionSuccess,
} from './actions/article.actions';

const initalState: IArticleState = {
  isLoading: false,
  data: null,
  error: null,
  isFavourited: null
};

const articleReducer = createReducer(
  initalState,
  on(
    getArticleAction,
    (state): IArticleState => ({
      ...state,
      isLoading: true,
    })
  ),
  on(
    getArticleActionSuccess,
    (state, action): IArticleState => ({
      ...state,
      isLoading: false,
      data: action.article,
      error: null,
      isFavourited: action.article.favorited
    })
  ),
  on(
    getArticleActionFailure,
    (state): IArticleState => ({
      ...state,
      isLoading: false,
      data: null,
      error: null,
    })
  ),
  on(
    routerNavigationAction,
    ():IArticleState => initalState
  ),
  on(
    favouriteActionSuccess,
    (state,action): IArticleState => ({
      ...state,
      data: action.article
    })
  ),
  on(
    unFavouriteActionSuccess,
    (state,action): IArticleState => ({
      ...state,
      data: action.article
    })
  ),

);


export function reducers(state: IArticleState, actions: Action) {
  return articleReducer(state, actions);
}
