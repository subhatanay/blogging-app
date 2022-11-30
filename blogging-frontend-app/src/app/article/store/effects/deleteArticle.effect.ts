import { Injectable } from '@angular/core';
import { Router } from '@angular/router';
import { Actions, createEffect, ofType } from '@ngrx/effects';
import { catchError, map, of, switchMap, tap } from 'rxjs';
import { ArticleService } from '../../services/article.service';
import { deleteArticleAction, deleteArticleActionFailure, deleteArticleActionSuccess } from '../actions/delete-article.actions';

@Injectable()
export class DeleteArticleEffect {
  constructor(
    private actions$: Actions,
    private articleService: ArticleService,
    private router: Router
  ) {}

  deleteArticle$ = createEffect(() =>
    this.actions$.pipe(
      ofType(deleteArticleAction),
      switchMap(( {articleId} ) => {
        return this.articleService.deleteArticle(articleId).pipe(
          map(() => {
            return deleteArticleActionSuccess();
          }),
          catchError(() => {
            return of(deleteArticleActionFailure());
          })
        );
      })
    )
  );

  redirectAfterDelete$ = createEffect(
    () =>
    this.actions$.pipe(
      ofType(deleteArticleActionSuccess),
      tap(() => {
        this.router.navigate(['/'])
      })
    ),
    {dispatch :false}
  )
}
