export interface IArticleInput {
  subject: string | null,
  content: string | null,
  title: string | null,
  description : string | null,
  body: string | null,
  tagList: string[] | null
}

export interface ICreateArticleRequest {
  article: IArticleInput
}
