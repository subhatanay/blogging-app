import { HttpErrorResponse } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Router } from '@angular/router';
import { Actions, createEffect, ofType } from '@ngrx/effects';
import { catchError, map, of, switchMap, tap } from 'rxjs';
import { CreateArticleService } from 'src/app/createArticle/services/createArticle.service';
import { ArticleService } from 'src/app/shared/services/articles.service';
import { IArticle } from 'src/app/shared/types/article.interface';
import { ISaveArticleResponse } from 'src/app/shared/types/articleResponse.interface';
import { EditArticleService } from '../../services/editArticle.service';
import { editArticleAction, editArticleActionFailure, editArticleActionSuccess, getArticleAction, getArticleActionFailure, getArticleActionSuccess } from '../actions/article.actions';


@Injectable()
export class EditArticleEffect {
  constructor(
    private actions$: Actions,
    private editArticleService: EditArticleService,
    private sharedAricleService: ArticleService,
    private router: Router
  ) {}

  getArticleAction$ = createEffect(() =>
    this.actions$.pipe(
      ofType(getArticleAction),
      switchMap(({ articleId }) => {
        return this.sharedAricleService.getArticle(articleId).pipe(
          map((article: IArticle) => {

            return getArticleActionSuccess({ article });
          }),
          catchError((errorResponse: HttpErrorResponse) => {
            return of(
              getArticleActionFailure()
            );
          })
        );
      })
    )
  );

  editArticleAction$ = createEffect(() =>
    this.actions$.pipe(
      ofType(editArticleAction),
      switchMap(({ articleId, article }) => {
        return this.editArticleService.editArticle(articleId,article).pipe(
          map((articleRes: IArticle) => {

            return editArticleActionSuccess({ article:  articleRes });
          }),
          catchError((errorResponse: HttpErrorResponse) => {
            return of(
              editArticleActionFailure({error: errorResponse.error})
            );
          })
        );
      })
    )
  );

  redirectAfterSubmit$ = createEffect(
    () =>
      this.actions$.pipe(
        ofType(editArticleActionSuccess),
        tap(() => {
          this.router.navigateByUrl('/');
        })
      ),
    { dispatch: false }
  );
}
