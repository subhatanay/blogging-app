import { Component, OnInit } from "@angular/core";
import { select, Store } from "@ngrx/store";
import { Observable } from "rxjs";
import { ArticleActionType } from "src/app/article/store/articleActionTypes";
import { IArticleInput, ICreateArticleRequest } from "src/app/shared/modules/articleForm/types/articleInput.interface";
import { IBackendError } from "src/app/shared/types/backendErrors.interface";
import { createArticleAction } from "../../store/actions/article.actions";
import { isSubmittingSelector, validationErrorsSelector } from "../../store/selectors";

@Component({
  selector: 'mc-create-article-form',
  templateUrl: './createArticle.component.html'
})
export class CreateArticleComponent implements OnInit {
  intialValues : IArticleInput = {
    subject: '',
    description: '',
    content: '',
    tagList: [],
    title: "",
    body: ""
  }

  isSubmitting$ : Observable<boolean>
  backendError$: Observable<IBackendError| null>

  constructor(private store: Store) {}

  ngOnInit(): void {
   this.isSubmitting$ = this.store.pipe(select(isSubmittingSelector))
   this.backendError$ = this.store.pipe(select(validationErrorsSelector))
  }


  onSubmit(articleInput: IArticleInput) {
    console.log(articleInput)
    this.store.dispatch(createArticleAction({article : articleInput}))
  }

}
