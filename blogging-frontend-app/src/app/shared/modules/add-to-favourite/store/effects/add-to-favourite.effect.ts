import { Injectable } from "@angular/core";
import { Router } from "@angular/router";
import { Actions, createEffect, ofType } from "@ngrx/effects";
import { catchError, map, of, switchMap, tap } from "rxjs";
import { IArticle } from "src/app/shared/types/article.interface";
import { getFeedActionSuccess } from "../../../feed/store/actions/getFeed.actions";
import { AddToFavouriteService } from "../../services/add-to-favourite.service";
import { favouriteAction, favouriteActionFailure, favouriteActionSuccess, unFavouriteAction, unFavouriteActionFailure, unFavouriteActionSuccess } from "../actions/add-to-favorite.actions";

@Injectable()
export class AddToFavoriteEffect {

  constructor(private actions$ : Actions, private addToFavoriteService : AddToFavouriteService, private router: Router) {}

  favouriteArticleEffect$ = createEffect(() =>
    this.actions$.pipe(
      ofType(favouriteAction),
      switchMap(({articleId}) => {
        return this.addToFavoriteService.favouriteArticle(articleId).pipe(
          map((article:IArticle) => {
            return favouriteActionSuccess({article})
          }),
          catchError(()=> {
            return of(favouriteActionFailure())
          })
        )
      })
    )
  )

  unFavouriteArticleEffect$ = createEffect(() =>
    this.actions$.pipe(
      ofType(unFavouriteAction),
      switchMap(({articleId}) => {
        return this.addToFavoriteService.unFavouriteArticle(articleId).pipe(
          map((article: IArticle) => {
            return unFavouriteActionSuccess({article})
          }),
          catchError(()=> {
            return of(unFavouriteActionFailure())
          })
        )
      })
    )
  )



}
