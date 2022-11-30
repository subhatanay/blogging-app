import { Component, EventEmitter, Input, NgModule, OnChanges, OnInit, Output, SimpleChanges } from "@angular/core";
import { select, Store } from "@ngrx/store";
import { Observable } from "rxjs";
import { isLoggedInSelector } from "src/app/auth/store/selectors";
import { favouriteAction, unFavouriteAction } from "../store/actions/add-to-favorite.actions";

@Component({
  selector: 'mc-add-to-faviourites',
  templateUrl: './add-to-favourite.component.html'
})
export class AddToFavouriteComponent implements OnInit {
  @Input("dynamic") dynamicProps : boolean
  @Input("isFavioured") isFaviouredProps : boolean
  @Input("articleSlug") articleSlugProps: number
  @Input("favouriteCount") favouriteCountProps: number
  @Input("buttonText") buttonTextProps: string


  favouriteCount: number = 5
  isFavorited: boolean

  constructor(private store : Store) {}


  ngOnInit(): void {
    console.log(this.isFaviouredProps)
    if (!this.dynamicProps) {
      this.favouriteCount = this.favouriteCountProps
      this.isFavorited = this.isFaviouredProps
    }

  }

  onFavouiteClick() : void {
    if (this.isFaviouredProps || this.isFavorited) {
     this.favouriteCount--
      this.store.dispatch(unFavouriteAction({articleId: this.articleSlugProps}))
    } else {
      this.favouriteCount++
      this.store.dispatch(favouriteAction({articleId: this.articleSlugProps}))
    }

    this.isFavorited = !this.isFavorited

  }


}
