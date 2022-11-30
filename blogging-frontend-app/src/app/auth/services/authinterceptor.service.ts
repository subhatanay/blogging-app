import {
  HttpEvent,
  HttpHandler,
  HttpInterceptor,
  HttpRequest,
} from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { PersistanceServoce } from 'src/app/shared/services/persistance.service';

@Injectable()
export class AuthIntercepter implements HttpInterceptor {
  constructor(private persistance: PersistanceServoce) {}

  intercept(
    req: HttpRequest<any>,
    next: HttpHandler
  ): Observable<HttpEvent<any>> {
    const token = this.persistance.get('access_token');

    req = req.clone({
      setHeaders: {
        Authorization: token ? `Bearer ${token}` : '',
      },
    });

    return next.handle(req);
  }
}
