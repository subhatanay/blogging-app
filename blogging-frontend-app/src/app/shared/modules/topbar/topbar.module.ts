import { CommonModule } from "@angular/common";
import { NgModule } from "@angular/core";
import { RouterModule } from "@angular/router";
import { LoginComponent } from "src/app/auth/components/login/login.component";
import { RegisterComponent } from "src/app/auth/components/register/register.compoenent";
import { TopbarComponent } from "./components/topbar.component";

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
  imports: [CommonModule,RouterModule],
  declarations:[TopbarComponent],
  exports: [TopbarComponent]
})
export class TopbarModule {

}
