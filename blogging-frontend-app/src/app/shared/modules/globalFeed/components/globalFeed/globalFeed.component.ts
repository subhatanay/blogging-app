import { Component, OnInit } from "@angular/core";
import { Store } from "@ngrx/store";

@Component({
  selector: 'mc-global-feed',
  templateUrl : './globalFeed.component.html',
  styleUrls: ['./globalFeed.component.scss']
})
export class GlobalFeedComponent implements OnInit {
  apiUrl = "/feed/top"


  constructor(private store : Store) {}

  ngOnInit(): void {

  }



}
