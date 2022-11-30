import { CommonModule } from '@angular/common';
import { NgModule } from '@angular/core';
import { EffectsModule } from '@ngrx/effects';
import { PopularTagsListEffect } from './store/effects/popular-tags-list.effect';
import { PopularTagsListService } from './services/popular-tag-list.service';
import { StoreModule } from '@ngrx/store';
import { reducers } from './store/reducers';
import { PopularTagsListComponent } from './components/popular-tags-list/popular-tags-list.component';
import { LoadingModule } from '../loading/loading.module';
import { ErrorMessageModule } from '../errorMessage/errorMessage.module';
import { RouterModule } from '@angular/router';

@NgModule({
  imports: [
    CommonModule,
    StoreModule.forFeature('popularTags', reducers),
    EffectsModule.forFeature([PopularTagsListEffect]),
    LoadingModule,
    ErrorMessageModule,
    RouterModule
  ],
  declarations: [PopularTagsListComponent],
  providers: [PopularTagsListService],
  exports: [PopularTagsListComponent],
})
export class PopularTagsListModule {}
