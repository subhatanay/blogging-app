import { Component, OnInit } from "@angular/core";
import { FormBuilder, FormGroup, Validators } from "@angular/forms";
import { select, Store } from "@ngrx/store";
import { Observable } from "rxjs";
import { IBackendError } from "src/app/shared/types/backendErrors.interface";
import { loginAction } from "../../store/login.actions";
import { isSubmittingSelector, validationErrorsSelector } from "../../store/selectors";
import { ILoginRequest } from "../../types/loginRequest.interface";

@Component({
  selector: 'mc-login-component',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.scss']
})
export class LoginComponent implements OnInit {
  form : FormGroup
  isSubmitting$: Observable<Boolean>
  backendErrors$: Observable<IBackendError | null>

  constructor(private fb : FormBuilder,private store: Store) {}

  ngOnInit(): void {
    this.initializeForm()
    this.initializeValue();
  }

  initializeForm() : void {
    this.form = this.fb.group({
      emailId: ['', Validators.required],
      password : ['', Validators.required]
    })
  }

  initializeValue() : void {
    this.isSubmitting$ = this.store.pipe(select(isSubmittingSelector))
    this.backendErrors$ = this.store.pipe(select(validationErrorsSelector))
  }

  onSubmit() {
    const request: ILoginRequest = {
      emailId: this.form.value["emailId"],
      password: this.form.value["password"]
    }
    this.store.dispatch(loginAction({request}))
  }
}
