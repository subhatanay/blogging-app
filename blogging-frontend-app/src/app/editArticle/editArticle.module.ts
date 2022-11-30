import { CommonModule } from '@angular/common';
import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';
import { EffectsModule } from '@ngrx/effects';
import { StoreModule } from '@ngrx/store';
import { ArticleFormModule } from '../shared/modules/articleForm/articleForm.module';
import { EditArticleComponent } from './components/editArticle/editArticle.component';
import { reducers } from './store/reducers';
import { EditArticleService } from './services/editArticle.service';
import { LoadingModule } from '../shared/modules/loading/loading.module';
import { ArticleService } from '../shared/services/articles.service';
import { EditArticleEffect } from './store/effects/editArticle.effect';

const routes = [
  {
    path: 'articles/:articleId/edit',
    component: EditArticleComponent
  }
]

@NgModule({
  imports: [
    CommonModule,
    RouterModule.forChild(routes),
    ArticleFormModule,
    EffectsModule.forFeature([EditArticleEffect]),
    StoreModule.forFeature('editArticle', reducers),
    LoadingModule
  ],
  declarations: [EditArticleComponent],
  providers: [EditArticleService, ArticleService],
})
export class EditArticleModule {}
