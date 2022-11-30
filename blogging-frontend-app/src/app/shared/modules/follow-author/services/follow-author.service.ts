import { HttpClient } from "@angular/common/http";
import { Injectable } from "@angular/core";
import { map, Observable } from "rxjs";
import { IGetUserProfileResponse } from "src/app/userProfile/types/getUserProfileResponse.interface";
import { IUserProfile } from "src/app/userProfile/types/userProfile.interface";
import { environment } from "src/environments/environment";

@Injectable()
export class FollowAuthorService {

  constructor(private http: HttpClient) {}


  followAuthor(authorId: number) : Observable<IUserProfile> {
    const fullUrl = environment.localApiUrl + "/users/" + authorId + "/follow"
    return this.http.post<IGetUserProfileResponse>(fullUrl,{})
    .pipe(map((userProfileResponse: IGetUserProfileResponse) => userProfileResponse.profile))
  }

  unFollowAuthor(authorId: number) : Observable<IUserProfile> {
    const fullUrl = environment.localApiUrl + "/users/" + authorId + "/follow"
    return this.http.delete<IGetUserProfileResponse>(fullUrl)
    .pipe(map((userProfileResponse: IGetUserProfileResponse) => userProfileResponse.profile))
  }


}
