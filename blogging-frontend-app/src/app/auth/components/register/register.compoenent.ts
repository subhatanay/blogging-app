import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { select, Store } from '@ngrx/store';
import { Observable } from 'rxjs';
import { IBackendError } from 'src/app/shared/types/backendErrors.interface';
import { ICurrentUser } from 'src/app/shared/types/currentUser.interface';
import { AuthService } from '../../services/auth.service';

import { registerAction, registerButtonPressAction } from '../../store/register.actions';
import { isSubmittingSelector, validationErrorsSelector } from '../../store/selectors';
import { IRegisterRequest } from '../../types/registerRequest.interface';

@Component({
  selector: 'mc-register',
  templateUrl: './register.component.html',
  styleUrls: ['./register.compoenent.scss'],
})
export class RegisterComponent implements OnInit {
  form: FormGroup
  isSubmitting$: Observable<Boolean>
  validationErrors$: Observable<IBackendError | null>

  constructor(private fb: FormBuilder, private store: Store,private authService: AuthService) {}

  ngOnInit(): void {
    this.initializeForm()
    this.initializeValues()
  }

  initializeValues() : void {
    this.isSubmitting$ = this.store.pipe(select(isSubmittingSelector))
    this.validationErrors$ = this.store.pipe(select(validationErrorsSelector))
  }

  initializeForm(): void {
    this.form = this.fb.group({
      username: ['', Validators.required],
      emailId: ['', Validators.required],
      password: ['', Validators.required],
    });
  }

  onSubmit() {
    console.log(this.form.value)
    this.store.dispatch(registerButtonPressAction())
    const registerRequest : IRegisterRequest = {
      emailId: this.form.value["emailId"],
      username: this.form.value["username"],
      password: this.form.value["password"],
    }
    this.store.dispatch(registerAction({ request : registerRequest }));
  }
}

