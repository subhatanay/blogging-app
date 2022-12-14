import { IBackendError } from "src/app/shared/types/backendErrors.interface";
import { ICurrentUser } from "src/app/shared/types/currentUser.interface";

export interface IAuthState {
  isSubmitting: boolean,
  currentUser: ICurrentUser | null,
  isLoggedIn: boolean | null,
  validationErrors: IBackendError | null,
  isLoading: boolean
}
