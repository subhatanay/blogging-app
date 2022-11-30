import { HttpClient } from "@angular/common/http";
import { Injectable } from "@angular/core";
import { map, Observable } from "rxjs";
import { environment } from "src/environments/environment";
import { IGetUserProfileResponse } from "../types/getUserProfileResponse.interface";
import { IUserProfile } from "../types/userProfile.interface";

@Injectable()
export class UserProfileService {

  constructor(private http: HttpClient) {}

  getUserProfile(userId : number) : Observable<IUserProfile> {
    const url = environment.localApiUrl + "/users/" + userId + "/profile/info"
    return this.http.get<IUserProfile>(url).pipe(map((userReponse : IUserProfile) => userReponse))
  }
}
