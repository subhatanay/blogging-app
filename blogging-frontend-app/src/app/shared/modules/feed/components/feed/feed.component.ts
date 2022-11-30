import { Component, Input, OnChanges, OnDestroy, OnInit, SimpleChanges } from '@angular/core';
import { ActivatedRoute, Params, Router } from '@angular/router';
import { select, Store } from '@ngrx/store';
import { parseUrl, stringify } from 'query-string';
import { Observable, Subscription } from 'rxjs';
import { isLoggedInSelector } from 'src/app/auth/store/selectors';
import { environment } from 'src/environments/environment';
import { getFeedAction } from '../../store/actions/getFeed.actions';

import {
  dataFeedSelector,
  errorFeedSelector,
  isLoadingFeedSelector,
} from '../../store/selectors';
import { IGetFeedResponse } from '../../types/getFeedResponse.interface';

@Component({
  selector: 'mc-feed',
  templateUrl: './feed.component.html',
  styleUrls: ['./feed.component.scss'],
})
export class FeedComponent implements OnInit , OnDestroy, OnChanges {
  @Input('apiUrl') apiUrlProps: string;
  feed$: Observable<IGetFeedResponse | null>;
  error$: Observable<string | null>;
  isLoading$: Observable<boolean>;
  isLoggedIn$: Observable<boolean>;
  limit: number;
  baseUrl: string;
  queryParamsSubscription: Subscription
  currentPage: number

  constructor(private store: Store, private router: Router, private route : ActivatedRoute) {}

  ngOnInit(): void {
    this.initializeValues();
    this.initializeListeners()

  }

  initializeListeners() {
    this.queryParamsSubscription = this.route.queryParams.subscribe((params: Params) => {
      this.currentPage = params["page"] || 1;
      this.fetchFeedData();
    } )
  }


  initializeValues(): void {
    this.feed$ = this.store.pipe(select(dataFeedSelector));
    this.error$ = this.store.pipe(select(errorFeedSelector));
    this.isLoading$ = this.store.pipe(select(isLoadingFeedSelector));
    this.isLoggedIn$ = this.store.pipe(select(isLoggedInSelector));
    this.limit = environment.limit;
    this.baseUrl = this.router.url.split('?')[0];
  }

  fetchFeedData(): void {
    const offset = this.currentPage - 1
    const parsedUrl = parseUrl(this.apiUrlProps)
    const stringfiedParams = stringify({
      limit: this.limit,
      offset,
      ...parsedUrl.query
    })
    const apiUrlWithParams = parsedUrl.url + "?" + stringfiedParams
    console.log(apiUrlWithParams)
    this.store.dispatch(getFeedAction({ url: apiUrlWithParams }));
  }

  ngOnChanges(changes: SimpleChanges): void {
     if (!changes['apiUrlProps'].firstChange &&changes['apiUrlProps'].previousValue !== changes['apiUrlProps'].currentValue) {
        this.fetchFeedData()
     }
  }

  ngOnDestroy(): void {
    this.queryParamsSubscription.unsubscribe();
  }
}
