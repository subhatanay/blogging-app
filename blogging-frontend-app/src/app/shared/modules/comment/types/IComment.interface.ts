export interface IComment {
  parentCommentId: number | null,
  commentData: string,
  commentId: number | null,
  commentUsername: string | null,
  commentUserlogo: string | null
}

export interface ICommentsResponse {
  totalCount : number,
  pageSize: number,
  pageCount: number,
  results: IComment[]
}
