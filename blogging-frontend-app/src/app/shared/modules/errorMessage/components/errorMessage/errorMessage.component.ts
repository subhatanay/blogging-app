import { Component, Input } from "@angular/core";

@Component({
  selector: 'mc-error-message',
  template: '<div>{{messageProps}}</div>',
  styleUrls: ['./errorMessage.component.scss']
})
export class ErrorMessagesComponent {
    @Input('message') messageProps : string = 'Something went wrong'
}
