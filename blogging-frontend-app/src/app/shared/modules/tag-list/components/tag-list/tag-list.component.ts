import { Component, Input } from "@angular/core";
import { PopularTagType } from "src/app/shared/types/popular.type";

@Component({
  selector: 'mc-tag-list',
  templateUrl: './tag-list.component.html',
  styleUrls: ['./tag-list.component.scss']
})
export class TagListComponent {

  @Input("tags") tagsListProps: PopularTagType[]

}
