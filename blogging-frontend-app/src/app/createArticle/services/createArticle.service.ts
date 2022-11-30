import { HttpClient } from "@angular/common/http";
import { Injectable } from "@angular/core";
import { map, Observable } from "rxjs";
import { IArticle } from "src/app/shared/types/article.interface";
import { ISaveArticleResponse } from "src/app/shared/types/articleResponse.interface";
import { environment } from "src/environments/environment";
import { IArticleInput, ICreateArticleRequest } from "../../shared/modules/articleForm/types/articleInput.interface";

@Injectable()
export class CreateArticleService {
  constructor(private http: HttpClient) {}

  createArticle(articleInput: IArticleInput) : Observable<IArticle> {
    const fullUrl = environment.localApiUrl + "/articles"
    return this.http.post<IArticle>(fullUrl, articleInput)

  }

}
