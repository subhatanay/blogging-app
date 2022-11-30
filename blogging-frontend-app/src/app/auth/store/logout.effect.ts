import { Injectable } from '@angular/core';
import { Router } from '@angular/router';
import { Actions, createEffect, ofType } from '@ngrx/effects';
import { tap } from 'rxjs';
import { PersistanceServoce } from 'src/app/shared/services/persistance.service';
import { logoutAction } from './sync.actions';

@Injectable()
export class LogoutEffect {
  constructor(
    private actions$: Actions,
    private persistanceService: PersistanceServoce,
    private router: Router
  ) {}

  logoutEffect$ = createEffect(() =>
    this.actions$.pipe(
      ofType(logoutAction),
      tap(() => {
        this.persistanceService.remove('access_token');
        this.router.navigate(['/'])
      })
    )
    ,{dispatch:false}
  );
}
