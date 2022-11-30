import { Component, Input, OnInit } from "@angular/core";
import { FormBuilder, FormGroup, Validators } from "@angular/forms";
import { ActivatedRoute } from "@angular/router";
import { select, Store } from "@ngrx/store";
import { Observable } from "rxjs";
import { currentUserSelector, isLoggedInSelector } from "src/app/auth/store/selectors";
import { IBackendError } from "src/app/shared/types/backendErrors.interface";
import { ICurrentUser } from "src/app/shared/types/currentUser.interface";
import { TagListComponent } from "../../../tag-list/components/tag-list/tag-list.component";
import { createCommentAction } from "../../store/actions/comment.action";
import { isSubmittingSelector, validationErrorSelector } from "../../store/selectors";
import { IComment } from "../../types/IComment.interface";

@Component({
  selector: "mc-comment",
  templateUrl: "./comment.component.html"
})
export class CommentComponent implements OnInit {
  form: FormGroup
  @Input("articleId") articleIdProps : number
  isSubmitting$: Observable<boolean>
  validationErrors: Observable<IBackendError>
  isLoggedIn$: Observable<boolean> = this.store.pipe(select(isLoggedInSelector))

  constructor(private fb: FormBuilder, private route :ActivatedRoute, private store: Store) {
  }

  ngOnInit(): void {
    this.form = this.fb.group({
      commentData: ['',Validators.required]
    })
  }
  initializeValues(): void {
    this.isSubmitting$ = this.store.pipe(select(isSubmittingSelector))
    this.validationErrors = this.store.pipe(select(validationErrorSelector))
  }

  onSubmit() :void {
     const request : IComment = this.form.value;
     this.store.dispatch(createCommentAction({articleId: this.articleIdProps, comment:request}));
  }

}
