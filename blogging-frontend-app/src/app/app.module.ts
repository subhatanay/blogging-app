import { HTTP_INTERCEPTORS } from '@angular/common/http';
import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';
import { EffectsModule } from '@ngrx/effects';
import { StoreModule } from '@ngrx/store';
import { StoreDevtools, StoreDevtoolsModule } from '@ngrx/store-devtools';
import { environment } from 'src/environments/environment';

import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { AuthModule } from './auth/auth.module';
import { AuthIntercepter } from './auth/services/authinterceptor.service';
import { GloabalFeedModule } from './shared/modules/globalFeed/globalFeed.module';
import { TopbarModule } from './shared/modules/topbar/topbar.module';
import { PersistanceServoce } from './shared/services/persistance.service';
import { routerReducer, StoreRouterConnectingModule } from '@ngrx/router-store';
import { YourFeedModule } from './shared/modules/yourFeed/yourFeed.module';
import { TagFeedModule } from './shared/modules/tagFeed/tagFeed.module';
import { ArticleModule } from './article/article.module';
import { TagListModule } from './shared/modules/tag-list/tagList.module';
import { CreateArticleComponent } from './createArticle/components/createArticle/createArticle.component';
import { CreateArticleModule } from './createArticle/createArticle.module';
import { EditArticleModule } from './editArticle/editArticle.module';
import { SettingsModule } from './settings/settings.module';
import { UserProfileModule } from './userProfile/userProfile.module';

@NgModule({
  declarations: [
    AppComponent
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    AuthModule,
    StoreModule.forRoot({
      router: routerReducer
    }),
    StoreDevtoolsModule.instrument({
      maxAge: 25,
      logOnly: environment.production
    }),
    EffectsModule.forRoot([]),
    TopbarModule,
    GloabalFeedModule,
    YourFeedModule,
    TagFeedModule,
    CreateArticleModule,
    ArticleModule,
    EditArticleModule,
    SettingsModule,
    UserProfileModule,
    StoreRouterConnectingModule.forRoot()
  ],
  providers: [
    PersistanceServoce,
    {
      provide: HTTP_INTERCEPTORS,
      useClass: AuthIntercepter,
      multi: true
    }
  ],
  bootstrap: [AppComponent]
})
export class AppModule { }
