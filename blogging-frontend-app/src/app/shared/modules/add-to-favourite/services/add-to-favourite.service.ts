import { HttpClient } from "@angular/common/http";
import { Injectable } from "@angular/core";
import { map, Observable } from "rxjs";
import { IArticle } from "src/app/shared/types/article.interface";
import { IArticleResponse } from "src/app/shared/types/articleResponse.interface";
import { environment } from "src/environments/environment";

@Injectable()
export class AddToFavouriteService {

  constructor(private http : HttpClient) {}

  favouriteArticle(articleId: number) : Observable<IArticle> {
    const fullUrl = environment.localApiUrl + "/articles/" + articleId + "/like"
    return this.http.post<IArticleResponse>(fullUrl,{}).pipe(
      map((articleResponse: IArticleResponse) => articleResponse.article)
    )

  }

  unFavouriteArticle(articleId: number) :  Observable<IArticle> {
    const fullUrl = environment.localApiUrl + "/articles/" + articleId + "/like"
    return this.http.delete<IArticleResponse>(fullUrl).pipe(
      map((articleResponse: IArticleResponse) => articleResponse.article))
  }

}
