import { Component, Input, OnInit } from "@angular/core";
import { IBackendError } from "src/app/shared/types/backendErrors.interface";

@Component({
  selector: "mc-backend-error-messages",
  templateUrl: './backendErrorMessages.component.html',
  styleUrls: ['./backendErrorMessages.component.scss']
})
export class BackendErrorMessagesComponent implements OnInit {


  @Input("backendErrors")  backendErrorsProps: IBackendError

  errorMessage: string

  ngOnInit(): void {
    this.errorMessage = this.backendErrorsProps["errorMessage"]
  }

}
