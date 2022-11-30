import { ICurrentUser } from "./currentUser.interface";

export interface ICurrentUserInput extends ICurrentUser {
  password: string,

}
