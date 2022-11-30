import { HttpErrorResponse, HttpResponse } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Router } from '@angular/router';
import { Actions, createEffect, ofType } from '@ngrx/effects';
import { Store } from '@ngrx/store';
import { catchError, map, of, switchMap, tap } from 'rxjs';
import { CommentService } from '../../services/comment.service';
import { IComment, ICommentsResponse } from '../../types/IComment.interface';
import {
  createCommentAction,
  createCommentActionFailure,
  createCommentActionSuccess,
  getCommentsAction,
  getCommentsActionFailure,
  getCommentsActionSuccess,
} from '../actions/comment.action';

@Injectable()
export class CommentEffect {
  constructor(
    private actions$: Actions,
    private commentService: CommentService,
    private router: Router,
    private store: Store
  ) {}

  createCommentEffect$ = createEffect(() =>
    this.actions$.pipe(
      ofType(createCommentAction),
      switchMap(({ articleId, comment }) => {
        return this.commentService.createComment(articleId, comment).pipe(
          map((comment: IComment) => {
            this.store.dispatch(getCommentsAction({articleId}))
            return createCommentActionSuccess();
          }),
          catchError((response: HttpErrorResponse) => {
            return of(createCommentActionFailure({ error: response.error }));
          })
        );
      })
    )
  );

  getCommentsEffect$ = createEffect(() =>
    this.actions$.pipe(
      ofType(getCommentsAction),
      switchMap(({ articleId }) => {
        return this.commentService.getCommentsPerArticle(articleId).pipe(
          map((comments: ICommentsResponse) => {
            return getCommentsActionSuccess({ comments });
          }),
          catchError(() => {
            return of(getCommentsActionFailure());
          })
        );
      })
    )
  );
}
