import { IBackendError } from "src/app/shared/types/backendErrors.interface";
import { ICommentsResponse } from "./IComment.interface";

export interface ICommentState {
  isSubmitting: boolean,
  isLoading: boolean,
  validationErrors: IBackendError
  comments: ICommentsResponse
}
