import { IArticle } from "src/app/shared/types/article.interface";

export interface IGetFeedResponse {
  articles: IArticle[] | null,
  articlesCount: number | null,
  totalCount: number | null,
  pageSize: number | null,
  pageCount: number | null,
  results: IArticle[]  | null
}
