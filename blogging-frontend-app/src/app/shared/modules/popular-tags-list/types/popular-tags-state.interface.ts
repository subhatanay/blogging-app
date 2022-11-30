import { IPopularTagsResponse } from "./popular-tag-interface";

export interface IPopularTagsState {
  isLoading: boolean,
  data: IPopularTagsResponse | null,
  error: string | null

}
