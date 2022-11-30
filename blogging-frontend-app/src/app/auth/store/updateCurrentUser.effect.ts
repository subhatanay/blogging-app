import { HttpErrorResponse } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Router } from '@angular/router';
import { Actions, createEffect, ofType } from '@ngrx/effects';
import { catchError, map, of, switchMap, tap } from 'rxjs';
import { PersistanceServoce } from 'src/app/shared/services/persistance.service';
import { ICurrentUser } from 'src/app/shared/types/currentUser.interface';
import { AuthService } from '../services/auth.service';

import { updateCurrentUserAction, updateCurrentUserActionFailure, updateCurrentUserActionSuccess } from './updateCurrentUser.action';

@Injectable()
export class UpdateCurrentEffect {
  constructor(
    private actions$: Actions,
    private authService: AuthService
  ) {}

  updateUserEffect$ = createEffect(() =>
    this.actions$.pipe(
      ofType(updateCurrentUserAction),
      switchMap(({userId, data }) => {
        return this.authService.updateCurrentUser(userId,data).pipe(
          map((currentUser: ICurrentUser) => {
            return updateCurrentUserActionSuccess({ currentUser });
          }),
          catchError((errorResponse: HttpErrorResponse) => {
            return of(
              updateCurrentUserActionFailure({ errors: errorResponse.error })
            );
          })
        );
      })
    )
  );


}
