import { Injectable } from "@angular/core";
import { Actions, createEffect, ofType } from "@ngrx/effects";
import { catchError, map, of, switchMap } from "rxjs";
import { getPopularTagsListAction, getPopularTagsListActionFailure, getPopularTagsListActionSuccess } from "../actions/popular-tags-list.actions";
import { PopularTagsListService } from "../../services/popular-tag-list.service";
import { IPopularTagsResponse } from "../../types/popular-tag-interface";

@Injectable()
export class PopularTagsListEffect {

  constructor(private popularTagsService: PopularTagsListService, private actions$ : Actions) {}

  getPopularTags$ = createEffect(() =>
    this.actions$.pipe(
      ofType(getPopularTagsListAction),
      switchMap(() => {
        return this.popularTagsService.getPopularTags().pipe(
          map((tags: IPopularTagsResponse) => {
            return getPopularTagsListActionSuccess({ tags });
          }),
          catchError(() => {
            return of(getPopularTagsListActionFailure());
          })
        );
      })
    )
  );

}
