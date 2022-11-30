import { IBackendError } from "src/app/shared/types/backendErrors.interface";
import { IUserProfile } from "./userProfile.interface";

export interface IUserProfileState {
  data: IUserProfile | null,
  isLoading: boolean,
  error: string | null
}
