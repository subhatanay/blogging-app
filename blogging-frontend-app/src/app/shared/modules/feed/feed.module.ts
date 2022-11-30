import { CommonModule } from '@angular/common';
import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';
import { EffectsModule } from '@ngrx/effects';
import { StoreModule } from '@ngrx/store';
import { AddToFavouriteModule } from '../add-to-favourite/add-to-favourite.module';
import { ErrorMessageModule } from '../errorMessage/errorMessage.module';
import { LoadingModule } from '../loading/loading.module';
import { PaginationModule } from '../pagination/pagination.module';
import { TagListModule } from '../tag-list/tagList.module';
import { FeedComponent } from './components/feed/feed.component';
import { FeedService } from './services/feed.service';
import { FeedEffect } from './store/effects/feed.effect';
import { reducers } from './store/reducers';

@NgModule({
  imports: [
    CommonModule,
    RouterModule.forChild([]),
    EffectsModule.forFeature([FeedEffect]),
    StoreModule.forFeature('feedData', reducers),
    ErrorMessageModule,
    LoadingModule,
    PaginationModule,
    TagListModule,
    AddToFavouriteModule
  ],
  declarations: [FeedComponent],
  exports: [FeedComponent],
  providers: [FeedService],
})
export class FeedModule {}
