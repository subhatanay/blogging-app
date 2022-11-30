import { IProfile } from "./profile.interface"

export interface IArticle {
  articleId: number,
  subject: string
  content: string
  title: string
  slug: string
  body: string
  createdAt: string
  updatedAt: string
  tagList: string[]
  description: string
  favorited: boolean
  likesCount: number
  liked: boolean
  favoritesCount: number
  author: IProfile
  userId: string,
  userName: string

}
