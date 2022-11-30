import { Component, OnInit } from "@angular/core";
import { ActivatedRoute } from "@angular/router";
import { select, Store } from "@ngrx/store";
import { filter, map, Observable } from "rxjs";
import { IArticleInput, ICreateArticleRequest } from "src/app/shared/modules/articleForm/types/articleInput.interface";
import { IArticle } from "src/app/shared/types/article.interface";
import { IBackendError } from "src/app/shared/types/backendErrors.interface";
import { editArticleAction, getArticleAction } from "../../store/actions/article.actions";
import { articleEditArticleSelector, isLoadingEditArticleSelector, isSubmittingSelector, validationErrorsSelector } from "../../store/selectors";

@Component({
  selector: 'mc-edit-article-form',
  templateUrl: './editArticle.component.html'
})
export class EditArticleComponent implements OnInit {
  intialValues$ : Observable<IArticleInput>
  isSubmitting$ : Observable<boolean>
  isLoading$ : Observable<boolean>
  backendError$: Observable<IBackendError| null>
  articleId: number

  constructor(private store: Store, private route: ActivatedRoute) {}

  ngOnInit(): void {
    this.initalizeValues()
    this.fetchArticleData()
  }


  initalizeValues() {
    this.articleId = parseInt(this.route.snapshot.paramMap.get("articleId" ))
    this.isSubmitting$ = this.store.pipe(select(isSubmittingSelector))
    this.isLoading$ = this.store.pipe(select(isLoadingEditArticleSelector))
    this.backendError$ = this.store.pipe(select(validationErrorsSelector))
    this.intialValues$ = this.store.pipe(
      select(articleEditArticleSelector),
      filter(Boolean),
      map((article: IArticle) => {
        return {
          subject: article.subject,
          description: article.description,
          content: article.content,
          title: '',
          body: '',
          tagList: []
        }
      })
    )
  }

  fetchArticleData() {
    this.store.dispatch(getArticleAction({articleId:this.articleId}))
  }


  onSubmit(articleInput: IArticleInput) {
    console.log(articleInput)
    this.store.dispatch(editArticleAction({articleId:this.articleId,article : articleInput}))
  }

}
