import { Component, OnInit } from "@angular/core";
import { Store } from "@ngrx/store";

@Component({
  selector: 'mc-your-feed',
  templateUrl : './yourFeed.component.html',
  styleUrls: ['./yourFeed.component.scss']
})
export class YourFeedComponent implements OnInit {
  apiUrl = "/feed/me"


  constructor(private store : Store) {}

  ngOnInit(): void {

  }



}
