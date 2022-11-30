import { HttpClient } from "@angular/common/http";
import { Injectable } from "@angular/core";
import { Observable } from "rxjs";
import { environment } from "src/environments/environment";
import { IComment, ICommentsResponse } from "../types/IComment.interface";

@Injectable()
export class CommentService {

  constructor(private http: HttpClient){}

  createComment(articleId: number, comment: IComment) : Observable<IComment> {
    const fullUrl = environment.localApiUrl + "/articles/" + articleId + "/comments"
    return this.http.post<IComment>(fullUrl, comment)
  }

  getCommentsPerArticle(articleId: number) : Observable<ICommentsResponse> {
    const fullUrl = environment.localApiUrl + "/articles/" + articleId + "/comments"
    return this.http.get<ICommentsResponse>(fullUrl)
  }

}
