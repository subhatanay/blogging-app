import { HttpClient } from "@angular/common/http";
import { Injectable } from "@angular/core";
import { map, Observable } from "rxjs";
import { environment } from "src/environments/environment";
import { IArticle } from "../types/article.interface";
import { IArticleResponse } from "../types/articleResponse.interface";

@Injectable()
export class ArticleService {
  constructor(private http : HttpClient) {}

  getArticle(articleId: number) : Observable<IArticle> {
    const fullUrl = environment.localApiUrl + "/articles/" + articleId;
    return this.http.get<IArticle>(fullUrl).pipe(
      map((response: IArticle) => {
        return response
      })
    )
  }

}

