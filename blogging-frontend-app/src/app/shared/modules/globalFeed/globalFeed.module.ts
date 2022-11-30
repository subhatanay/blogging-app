import { CommonModule } from "@angular/common";
import { NgModule } from "@angular/core";
import { RouterModule } from "@angular/router";
import { BannerModule } from "../banner/banner.module";
import { FeedTogglerModule } from "../feed-toggler/feed-toggler.module";
import { FeedModule } from "../feed/feed.module";
import { PopularTagsListModule } from "../popular-tags-list/popular-tags-list.module";
import { GlobalFeedComponent } from "./components/globalFeed/globalFeed.component";


const routes = [
  {
    path: '',
    component: GlobalFeedComponent
  }
]

@NgModule({
  imports: [CommonModule, RouterModule.forChild(routes), FeedModule,BannerModule,PopularTagsListModule,FeedTogglerModule],
  declarations: [GlobalFeedComponent]
}
)
export class GloabalFeedModule {}

