import { ICurrentUserInput } from "src/app/shared/types/ICurrentUserInput.interface";

export interface IUpdateUserRequest {
  fullName: string | null,
  bio: string | null,
  password: string | null,
  profileImageLink: string | null
}
