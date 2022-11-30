import { HttpErrorResponse } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Router } from '@angular/router';
import { Actions, createEffect, ofType } from '@ngrx/effects';
import { catchError, map, of, switchMap, tap } from 'rxjs';
import { CreateArticleService } from 'src/app/createArticle/services/createArticle.service';
import { IArticleInput } from 'src/app/shared/modules/articleForm/types/articleInput.interface';
import { IArticle } from 'src/app/shared/types/article.interface';
import { ISaveArticleResponse } from 'src/app/shared/types/articleResponse.interface';
import { createArticleAction, createArticleActionFaulure, createArticleActionSuccess } from '../actions/article.actions';


@Injectable()
export class CreateArticleEffect {
  constructor(
    private actions$: Actions,
    private createArticleService: CreateArticleService,
    private router: Router
  ) {}

  createEffect$ = createEffect(() =>
    this.actions$.pipe(
      ofType(createArticleAction),
      switchMap(({ article }) => {
        return this.createArticleService.createArticle(article).pipe(
          map((article: IArticle) => {

            return createArticleActionSuccess({article});
          }),
          catchError((errorResponse: HttpErrorResponse) => {
            return of(
              createArticleActionFaulure({ error: errorResponse.error })
            );
          })
        );
      })
    )
  );

  redirectAfterSubmit$ = createEffect(
    () =>
      this.actions$.pipe(
        ofType(createArticleActionSuccess),
        tap(() => {
          this.router.navigateByUrl('/');
        })
      ),
    { dispatch: false }
  );
}
