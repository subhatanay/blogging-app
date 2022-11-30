import { CommonModule } from '@angular/common';
import { NgModule } from '@angular/core';
import { EffectsModule } from '@ngrx/effects';
import { AddToFavouriteComponent } from './comonents/add-to-favourite.component';
import { AddToFavouriteService } from './services/add-to-favourite.service';
import { AddToFavoriteEffect } from './store/effects/add-to-favourite.effect';

@NgModule({
  imports: [CommonModule, EffectsModule.forFeature([AddToFavoriteEffect])],
  declarations: [AddToFavouriteComponent],
  exports: [AddToFavouriteComponent],
  providers: [AddToFavouriteService]
})
export class AddToFavouriteModule {}
