import { HttpErrorResponse } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Router } from '@angular/router';
import { Actions, createEffect, ofType } from '@ngrx/effects';
import { catchError, map, of, switchMap, tap } from 'rxjs';
import { PersistanceServoce } from 'src/app/shared/services/persistance.service';
import { ICurrentUser } from 'src/app/shared/types/currentUser.interface';
import { AuthService } from '../services/auth.service';
import {
  registerAction,
  registerFailureAction,
  registerSuccessAction,
} from './register.actions';

@Injectable()
export class RegisterEffect {
  constructor(
    private actions$: Actions,
    private authService: AuthService,
    private persistanceService: PersistanceServoce,
    private router: Router
  ) {}

  register$ = createEffect(() =>
    this.actions$.pipe(
      ofType(registerAction),
      switchMap(({ request }) => {
        return this.authService.register(request).pipe(
          map((currentUser: ICurrentUser) => {
            this.persistanceService.set('access_token', currentUser.token);
            return registerSuccessAction({ currentUser });
          }),
          catchError((errorResponse: HttpErrorResponse) => {
            console.log(errorResponse)
            return of(
              registerFailureAction({ errors: errorResponse.error })
            );
          })
        );
      })
    )
  );

  redirectAfterSubmit$ = createEffect(() =>
    this.actions$.pipe(
      ofType(registerSuccessAction),
      tap(() => {
        this.router.navigateByUrl('/');
      })
    ),
    {dispatch : false}
  );
}
