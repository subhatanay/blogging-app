import { IArticle } from "src/app/shared/types/article.interface"
import { IBackendError } from "src/app/shared/types/backendErrors.interface"

export interface EditArticleStateInterface {
  article: IArticle | null
  isLoading: boolean
  isSubmitting: boolean
  validationErrors: IBackendError
}
