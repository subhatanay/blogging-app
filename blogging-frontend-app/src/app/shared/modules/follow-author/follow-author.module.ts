import { CommonModule } from "@angular/common";
import { NgModule } from "@angular/core";
import { EffectsModule } from "@ngrx/effects";
import { FollowAuthorComponent } from "./components/follow-author/follow-author.component";
import { FollowAuthorService } from "./services/follow-author.service";
import { FollowAuthorEffect } from "./store/effects/follow-author.effects";

@NgModule({
  imports: [CommonModule,EffectsModule.forFeature([FollowAuthorEffect])],
  declarations: [FollowAuthorComponent],
  providers: [FollowAuthorService],
  exports: [FollowAuthorComponent]
})
export class FollowAuthorModule {

}
