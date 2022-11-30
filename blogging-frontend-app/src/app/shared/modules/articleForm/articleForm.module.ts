import { CommonModule } from "@angular/common";
import { NgModule } from "@angular/core";
import { ReactiveFormsModule } from "@angular/forms";
import { BackendErrorMessagesModule } from "../backendErrorMessages/backendErrorMessages.module";
import { AtricleFormComponent } from "./components/articleForm/articleForm.component";



@NgModule({
  imports:[CommonModule,BackendErrorMessagesModule, ReactiveFormsModule],
  declarations:[AtricleFormComponent],
  exports: [AtricleFormComponent]
})
export class ArticleFormModule {

}
