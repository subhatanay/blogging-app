import { Injectable } from '@angular/core';
import { Router } from '@angular/router';
import { Actions, createEffect, ofType } from '@ngrx/effects';
import { catchError, map, of, switchMap, tap } from 'rxjs';
import { ArticleService } from 'src/app/shared/services/articles.service';
import { IArticle } from 'src/app/shared/types/article.interface';
import { getArticleAction, getArticleActionFailure, getArticleActionSuccess} from '../actions/article.actions';
import { deleteArticleActionSuccess } from '../actions/delete-article.actions';

@Injectable()
export class ArticleEffect {
  constructor(
    private actions$: Actions,
    private articleService: ArticleService,

  ) {}

  getArticle$ = createEffect(() =>
    this.actions$.pipe(
      ofType(getArticleAction),
      switchMap(( {articleId} ) => {
        return this.articleService.getArticle(articleId).pipe(
          map((article: IArticle) => {
            return getArticleActionSuccess({ article });
          }),
          catchError(() => {
            return of(getArticleActionFailure());
          })
        );
      })
    )
  );



}
