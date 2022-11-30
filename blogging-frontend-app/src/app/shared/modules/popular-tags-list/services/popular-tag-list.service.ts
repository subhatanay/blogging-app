import { HttpClient } from "@angular/common/http";
import { Injectable } from "@angular/core";
import { Observable } from "rxjs";
import { environment } from "src/environments/environment";
import { IPopularTagsResponse } from "../types/popular-tag-interface";

@Injectable()
export class PopularTagsListService {
  url: string

  constructor(private http: HttpClient) {
    this.url = environment.apiUrl
  }

  getPopularTags(): Observable<IPopularTagsResponse> {
    const fullUrl = this.url + "/tags"
    return this.http.get<IPopularTagsResponse>(fullUrl)
  }

}
