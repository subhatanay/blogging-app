import { Component, Input } from "@angular/core";

@Component({
  selector: "mc-comment-card",
  templateUrl: "./single-comment-card.component.html"
})
export class SingleCommentCard {
  @Input("commentText") commentDataProps;
  @Input("commenterUsername") commenterUsernameProps;
  @Input("commenterUserlogo") commenterUserlogoProps;


}
