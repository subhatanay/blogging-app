import { Component } from "@angular/core";
import { select, Store } from "@ngrx/store";
import { Observable } from "rxjs";
import { currentUserSelector, isAnnonymousSelector, isLoggedInSelector } from "src/app/auth/store/selectors";
import { ICurrentUser } from "src/app/shared/types/currentUser.interface";

@Component({
  selector: "mc-topbar",
  templateUrl: './topbar.component.html',
  styleUrls: ['./topbar.component.scss']
})
export class TopbarComponent {
  isLoggedIn$ : Observable<boolean>
  isAnonymous$ : Observable<boolean>
  currentUser$ : Observable<ICurrentUser>


  constructor(private store: Store) {}

  ngOnInit(): void {
    this.isLoggedIn$ = this.store.pipe(select(isLoggedInSelector))
    this.isAnonymous$ = this.store.pipe(select(isAnnonymousSelector))
    this.currentUser$ = this.store.pipe(select(currentUserSelector))
  }

}
