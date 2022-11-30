import { IArticle } from "src/app/shared/types/article.interface";

export interface IArticleState {
  isLoading: boolean,
  error: string | null,
  data: IArticle | null,
  isFavourited: boolean | null
}
