import { HttpErrorResponse } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Router } from '@angular/router';
import { Actions, createEffect, ofType } from '@ngrx/effects';
import { catchError, map, of, switchMap, tap } from 'rxjs';
import { PersistanceServoce } from 'src/app/shared/services/persistance.service';
import { ICurrentUser } from 'src/app/shared/types/currentUser.interface';
import { AuthService } from '../services/auth.service';
import {
  getCurrentUserAction,
  getCurrentUserActionFailure,
  getCurrentUserActionSuccess,
} from './getCurrentUser.actions';

@Injectable()
export class GetCurrentUserEffect {
  constructor(
    private actions$: Actions,
    private authService: AuthService,
    private persistanceService: PersistanceServoce,
    private router: Router
  ) {}

  getCurrentUser$ = createEffect(() =>
    this.actions$.pipe(
      ofType(getCurrentUserAction),
      switchMap(() => {
        const token = this.persistanceService.get('access_token');
        if (!token) {
          return of(getCurrentUserActionFailure());
        }
        return this.authService.getCurrentUser().pipe(
          map((currentUser: ICurrentUser) => {
            return getCurrentUserActionSuccess({ currentUser });
          }),
          catchError((errorResponse: HttpErrorResponse) => {
            this.persistanceService.remove('access_token')
            return of(getCurrentUserActionFailure());
          })
        );
      })
    )
  );
}
