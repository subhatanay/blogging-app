import { CommonModule } from "@angular/common";
import { NgModule } from "@angular/core";
import { BannerComonent } from "./components/banner/banner.component";

@NgModule({
  imports: [CommonModule],
  declarations: [BannerComonent],
  exports: [BannerComonent]
})
export class BannerModule {

}
