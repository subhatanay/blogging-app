import { Component, OnInit } from "@angular/core";
import { ActivatedRoute } from "@angular/router";
import { Store } from "@ngrx/store";

@Component({
  selector: 'mc-tag-feed',
  templateUrl : './tagFeed.component.html',
  styleUrls: ['./tagFeed.component.scss']
})
export class TagFeedComponent implements OnInit {
  apiUrl = "/articles"
  tagName: string


  constructor(private route: ActivatedRoute) {}

  ngOnInit(): void {
    this.tagName = this.route.snapshot.paramMap.get('slug')
    console.log(this.tagName)
    this.apiUrl = "/articles?tag=" + this.tagName
  }



}
