import { Injectable } from '@angular/core';
import { Router } from '@angular/router';
import { Actions, createEffect, ofType } from '@ngrx/effects';
import { catchError, map, of, switchMap, tap } from 'rxjs';
import { UserProfileService } from '../../services/userProfile.services';
import { IUserProfile } from '../../types/userProfile.interface';
import { getUserProfileAction, getUserProfileActionFailre, getUserProfileActionSuccess } from '../actions/getUserProfile.action';

@Injectable()
export class GetUserProfileEffect {
  constructor(
    private actions$: Actions,
    private userProfileService: UserProfileService,

  ) {}

  getUserProfile$ = createEffect(() =>
    this.actions$.pipe(
      ofType(getUserProfileAction),
      switchMap(( {userId} ) => {
        return this.userProfileService.getUserProfile(userId).pipe(
          map((userProfile: IUserProfile) => {
            return getUserProfileActionSuccess({ userProfile });
          }),
          catchError(() => {
            return of(getUserProfileActionFailre());
          })
        );
      })
    )
  );



}
