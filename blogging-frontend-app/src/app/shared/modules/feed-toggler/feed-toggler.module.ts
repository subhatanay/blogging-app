import { CommonModule } from "@angular/common";
import { NgModule } from "@angular/core";
import { RouterModule } from "@angular/router";
import { FeedTogglerComponent } from "./components/feed-toggler/feed-toggler.component";

@NgModule({
  imports: [CommonModule,RouterModule],
  declarations: [FeedTogglerComponent],
  exports:[FeedTogglerComponent]

})
export class FeedTogglerModule{}
