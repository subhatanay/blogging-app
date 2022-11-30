import { IBackendError } from "src/app/shared/types/backendErrors.interface"

export class ISettingsState {
  isSubmitting: boolean
  validationErrors : IBackendError | null
}
