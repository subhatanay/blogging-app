import { CommonModule } from '@angular/common';
import { HttpClientModule } from '@angular/common/http';
import { NgModule } from '@angular/core';
import { ReactiveFormsModule } from '@angular/forms';
import { RouterModule } from '@angular/router';
import { EffectsModule } from '@ngrx/effects';
import { StoreModule } from '@ngrx/store';
import {  BackendErrorMessagesModule } from '../shared/modules/backendErrorMessages/backendErrorMessages.module';
import { PersistanceServoce } from '../shared/services/persistance.service';
import { LoginComponent } from './components/login/login.component';
import { RegisterComponent } from './components/register/register.compoenent';
import { AuthService } from './services/auth.service';
import { reducers } from './store/reducers';
import { RegisterEffect } from './store/register.effect';
import { LoginEffect } from './store/login.effect';
import {  GetCurrentUserEffect } from './store/getCurrentUser.effect';
import { UpdateCurrentEffect } from './store/updateCurrentUser.effect';
import { LogoutEffect } from './store/logout.effect';

const routes = [
  {
    path: 'register',
    component: RegisterComponent,
  },
  {
    path: 'login',
    component: LoginComponent
  }
];
@NgModule({
  imports: [
    CommonModule,
    RouterModule.forChild(routes),
    ReactiveFormsModule,
    StoreModule.forFeature('auth',reducers),
    HttpClientModule,
    EffectsModule.forFeature([RegisterEffect, LoginEffect,UpdateCurrentEffect, GetCurrentUserEffect,LogoutEffect]),
    BackendErrorMessagesModule
  ],
  declarations: [RegisterComponent,LoginComponent],
  providers: [AuthService, PersistanceServoce]
})
export class AuthModule {}
