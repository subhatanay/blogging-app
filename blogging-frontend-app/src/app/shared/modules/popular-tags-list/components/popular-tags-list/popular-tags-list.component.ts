import { Component, OnInit } from "@angular/core";
import { select, Store } from "@ngrx/store";
import { Observable } from "rxjs";
import { getPopularTagsListAction } from "../../store/actions/popular-tags-list.actions";
import { dataPopularTagsSelector, errorPoplularTagsSelector, isLoadingPoplularTagsSelector, popularFeatureSelector } from "../../store/selectors";
import { IPopularTagsResponse } from "../../types/popular-tag-interface";

@Component({
  selector: 'mc-popular-tags-list',
  templateUrl : './popular-tags-list.component.html',
  styleUrls: ['./popular-tags-list.component.scss']
})
export class PopularTagsListComponent implements OnInit {

  popularTags$: Observable<IPopularTagsResponse | null>
  error$: Observable<string | null>
  isLoading$ : Observable<boolean>

  constructor(private store: Store) {}

  ngOnInit(): void {
    this.initializeValues();
    this.fetchPopularTags();
  }

  initializeValues() : void {
    this.popularTags$ = this.store.pipe(select(dataPopularTagsSelector))
    this.error$ = this.store.pipe(select(errorPoplularTagsSelector))
    this.isLoading$ = this.store.pipe(select(isLoadingPoplularTagsSelector))
  }

  fetchPopularTags(): void {
    this.store.dispatch(getPopularTagsListAction())
  }





}
