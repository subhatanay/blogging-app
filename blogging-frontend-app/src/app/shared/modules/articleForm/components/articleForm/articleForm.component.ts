import { Component, EventEmitter, Input, OnInit, Output } from "@angular/core";
import { FormBuilder, FormGroup } from "@angular/forms";
import { IBackendError } from "src/app/shared/types/backendErrors.interface";
import { IArticleInput, ICreateArticleRequest } from "../../types/articleInput.interface";

@Component({
  selector: 'mc-article-form',
  templateUrl: './articleForm.component.html'
})
export class AtricleFormComponent implements OnInit {
  @Input("initialValues") initialValues: IArticleInput | null
  @Input("error") errorProps: IBackendError | null
  @Input("isSubmitting") isSubmittingProps: boolean

  @Output('articleSubmit') articleSubmitEvent = new EventEmitter<IArticleInput>()

  form: FormGroup

  constructor(private fb: FormBuilder) {}

  ngOnInit(): void {
    this.initalizeForm()

  }

  initalizeForm() : void {
    console.log(this.initialValues)
    this.form = this.fb.group({
      subject: this.initialValues.subject,
      description: this.initialValues.description,
      content: this.initialValues.content
    })
  }

  onSubmit() : void {
    console.log('hellp')
    const articleInputData : IArticleInput = this.form.value
    this.articleSubmitEvent.emit(articleInputData)
  }




}
