import { CommonModule } from "@angular/common";
import { NgModule } from "@angular/core";
import {   TagListComponent } from "./components/tag-list/tag-list.component";

@NgModule({
  imports: [CommonModule],
  declarations: [TagListComponent],
  exports: [TagListComponent]
})
export class TagListModule {

}
