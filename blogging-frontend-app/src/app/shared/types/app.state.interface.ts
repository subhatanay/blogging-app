import { IArticleState } from "src/app/article/types/articleState.interface";
import { CreateArticleStateInterface } from "src/app/createArticle/types/createArticleState.interface";
import { EditArticleStateInterface } from "src/app/editArticle/types/editArticle.interface";
import { ISettingsState } from "src/app/settings/components/types/setingsState.inerface";
import { IUserProfileState } from "src/app/userProfile/types/userProfileState";
import { IAuthState } from "../../auth/types/authState.interface";
import { ICommentState } from "../modules/comment/types/comment.state";
import { IFeedState } from "../modules/feed/types/feedState.interface";
import { IPopularTagsState } from "../modules/popular-tags-list/types/popular-tags-state.interface";

export interface IAppState {
  auth: IAuthState,
  feedData: IFeedState,
  popularTags : IPopularTagsState,
  article: IArticleState,
  createArticle: CreateArticleStateInterface,
  editArticle: EditArticleStateInterface,
  settings: ISettingsState,
  userProfile: IUserProfileState,
  comments: ICommentState
}
