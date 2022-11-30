import { Component, OnDestroy, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { select, Store } from '@ngrx/store';
import { combineLatest, filter, map, Observable, Subscription } from 'rxjs';
import { currentUserSelector } from 'src/app/auth/store/selectors';
import { ICurrentUser } from 'src/app/shared/types/currentUser.interface';
import { getUserProfileAction } from '../../store/actions/getUserProfile.action';
import {
  dataUserProfileSelector,
  errorUserProfileSelector,
  isLoadingSelector,
} from '../../store/selectors';
import { IUserProfile } from '../../types/userProfile.interface';

@Component({
  selector: 'mc-user-profile',
  templateUrl: './userProfile.component.html',
})
export class UserProfileComponent implements OnInit, OnDestroy {
  isLoading$;
  userProfile: IUserProfile;
  error$: Observable<string | null>;
  userProfileSubscription: Subscription;
  apiUrl: string;
  userId: number;
  isCurrentUserProfile$ : Observable<boolean>
  slugRouterValueChange$: Subscription

  constructor(private store: Store, private route: ActivatedRoute, private router : Router) {}

  ngOnInit(): void {
    this.initailizeValues();
    this.initializeListeners()

  }

  initializeListeners(): void {
    this.userProfileSubscription = this.store
      .pipe(select(dataUserProfileSelector), filter(Boolean))
      .subscribe((userProfile): IUserProfile => {
        return this.userProfile = userProfile;
      });

    this.slugRouterValueChange$ = this.route.params.subscribe((param) => {
      this.userId = param['userId']

      this.fetchUserProfileData()
    })


  }

  initailizeValues(): void {
    this.userId = parseInt(this.route.snapshot.paramMap.get('userId'));
    this.isLoading$ = this.store.pipe(select(isLoadingSelector));
    this.error$ = this.store.pipe(select(errorUserProfileSelector));


    this.isCurrentUserProfile$ = combineLatest(
      this.store.pipe(select(currentUserSelector) ,filter(Boolean)),
      this.store.pipe(select(dataUserProfileSelector) , filter(Boolean)),

    ).pipe(
      map(([currentUser, userProfile] : [ICurrentUser,IUserProfile] ) => {
        return currentUser.username === userProfile.username
      })
    )
  }

  getApiUrl(): string {
    const isFavouries = this.router.url.includes("favorites")
    return this.apiUrl = isFavouries ? "/users/"+ this.userId + "/profile/liked/articles/"   : "/users/" +  this.userId + "/articles";
  }

  fetchUserProfileData() {
    this.store.dispatch(getUserProfileAction({ userId: this.userId }));
  }
  ngOnDestroy(): void {
    this.slugRouterValueChange$.unsubscribe()
    this.userProfileSubscription.unsubscribe()
  }





}
