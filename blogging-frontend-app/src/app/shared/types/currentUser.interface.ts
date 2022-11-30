export interface ICurrentUser {
  userId : number
  emailId: string | null
  createdAt: string | null
  updatedAt: string | null
  username: string
  bio: string | null
  userLogoUrl: string | null
  token: string
  fullName: string | null
  userLogoLink: string | null
}
