import { Injectable } from '@angular/core';
import { Actions, createEffect, ofType } from '@ngrx/effects';
import { catchError, map, of, switchMap, tap } from 'rxjs';
import { FeedService } from '../../services/feed.service';
import { IGetFeedResponse } from '../../types/getFeedResponse.interface';
import { getFeedAction, getFeedActionFailure, getFeedActionSuccess } from '../actions/getFeed.actions';

@Injectable()
export class FeedEffect {
  constructor(
    private actions$: Actions,
    private feedService: FeedService
  ) {}

  getFeed$ = createEffect(() =>
    this.actions$.pipe(
      ofType(getFeedAction),
      switchMap(( {url} ) => {
        return this.feedService.getFeed(url).pipe(
          map((feed: IGetFeedResponse) => {
            return getFeedActionSuccess({ feed });
          }),
          catchError(() => {
            return of(getFeedActionFailure());
          })
        );
      })
    )
  );
}
