import { Injectable } from '@angular/core';
import { map, Observable } from 'rxjs';
import { HttpClient } from '@angular/common/http';

import { ICurrentUser } from 'src/app/shared/types/currentUser.interface';
import { IRegisterRequest } from '../types/registerRequest.interface';
import { environment } from 'src/environments/environment';
import { IAuthResponse } from '../types/authResponse.interface';
import { ILoginRequest } from '../types/loginRequest.interface';
import { ICurrentUserInput } from 'src/app/shared/types/ICurrentUserInput.interface';
import { IUpdateUserRequest } from '../types/updateUserRequest.interface';

@Injectable()
export class AuthService {
  constructor(private http: HttpClient) {}

  register(data: IRegisterRequest): Observable<ICurrentUser> {
    const url = environment.localApiUrl + '/users/signup';
    return this.http
      .post<ICurrentUser>(url, data)
      .pipe(map((response: ICurrentUser) => response));
  }

  login(data: ILoginRequest): Observable<ICurrentUser> {
    const url = environment.localApiUrl + '/users/login';
    return this.http
      .post<ICurrentUser>(url, data)
      .pipe(map((response: ICurrentUser) => response));
  }

  getCurrentUser(): Observable<ICurrentUser> {
    const url = environment.localApiUrl + '/users/me';
    return this.http
      .get(url)
      .pipe(map((response: ICurrentUser) => response));
  }

  updateCurrentUser(userId: number, data: IUpdateUserRequest): Observable<ICurrentUser> {
    const url = environment.localApiUrl + '/users/' + userId + "/profile/info";
    return this.http
    .patch(url, data)
      .pipe(map((response: ICurrentUser) => response));
  }
}
