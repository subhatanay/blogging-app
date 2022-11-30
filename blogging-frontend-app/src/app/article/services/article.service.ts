import { HttpClient } from "@angular/common/http";
import { Injectable } from "@angular/core";
import { Observable } from "rxjs";
import { environment } from "src/environments/environment";

@Injectable()
export class ArticleService {

  constructor(private http: HttpClient) {}

  deleteArticle(articleId: number) : Observable<{}> {
    const url = environment.localApiUrl + "/articles/" + articleId
    return this.http.delete<{}>(url)
  }
}
