import { CommonModule } from "@angular/common";
import { NgModule } from "@angular/core";
import {  ErrorMessagesComponent } from "./components/errorMessage/errorMessage.component";

@NgModule({
  imports: [CommonModule],
  declarations: [ErrorMessagesComponent],
  exports: [ErrorMessagesComponent]
})
export class ErrorMessageModule {

}
