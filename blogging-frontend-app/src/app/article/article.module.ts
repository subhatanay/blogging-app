import { CommonModule } from '@angular/common';
import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';
import { EffectsModule } from '@ngrx/effects';
import { StoreModule } from '@ngrx/store';
import { EditArticleModule } from '../editArticle/editArticle.module';
import { AddToFavouriteModule } from '../shared/modules/add-to-favourite/add-to-favourite.module';
import { BannerModule } from '../shared/modules/banner/banner.module';
import { CommentModule } from '../shared/modules/comment/comment.module';
import { ErrorMessageModule } from '../shared/modules/errorMessage/errorMessage.module';
import { FollowAuthorModule } from '../shared/modules/follow-author/follow-author.module';
import { LoadingModule } from '../shared/modules/loading/loading.module';
import { TagListModule } from '../shared/modules/tag-list/tagList.module';
import { ArticleService as SharedArticleService } from '../shared/services/articles.service';
import { ArticleComponent } from './components/article/article.component';
import { ArticleService } from './services/article.service';
import { DeleteArticleEffect } from './store/effects/deleteArticle.effect';


import { ArticleEffect } from './store/effects/getArticle.effect';
import { reducers } from './store/reducers';

const routes = [
  {
    path: 'articles/:articleId',
    component: ArticleComponent
  }
]

@NgModule({
  imports: [
    CommonModule,
    RouterModule.forChild(routes),
    EffectsModule.forFeature([ArticleEffect,DeleteArticleEffect]),
    BannerModule,
    LoadingModule,
    ErrorMessageModule,
    TagListModule,
    StoreModule.forFeature('article', reducers),
    AddToFavouriteModule,
    FollowAuthorModule,
    CommentModule

  ],
  declarations: [ArticleComponent],
  exports: [],
  providers: [SharedArticleService, ArticleService],
})
export class ArticleModule {}
