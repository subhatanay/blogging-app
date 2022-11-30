import { HttpClient } from "@angular/common/http";
import { Injectable } from "@angular/core";
import { Observable } from "rxjs";
import { environment } from "src/environments/environment";
import { IGetFeedResponse } from "../types/getFeedResponse.interface";

@Injectable()
export class FeedService {

  constructor(private http : HttpClient) {}

  getFeed(url: string): Observable<IGetFeedResponse> {
    const fullUrl = environment.localApiUrl + url;

    return this.http.get<IGetFeedResponse>(fullUrl)
  }

}
