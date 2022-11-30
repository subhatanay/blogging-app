import { CommonModule } from '@angular/common';
import { NgModule } from '@angular/core';
import { ReactiveFormsModule } from '@angular/forms';
import { EffectsModule } from '@ngrx/effects';
import { StoreModule } from '@ngrx/store';
import { CommentComponent } from './components/comment/comment.component';
import { SingleCommentCard } from './components/single-comment-card/single-comment-card.component';
import { CommentService } from './services/comment.service';
import { CommentEffect } from './store/effects/component.effect';
import { reducers } from './store/reducers';

@NgModule({
  imports: [
    CommonModule,
    ReactiveFormsModule,
    StoreModule.forFeature('comments', reducers),
    EffectsModule.forFeature([CommentEffect]),
  ],
  declarations: [SingleCommentCard, CommentComponent],
  providers: [CommentService],
  exports: [SingleCommentCard, CommentComponent],
})
export class CommentModule {}
