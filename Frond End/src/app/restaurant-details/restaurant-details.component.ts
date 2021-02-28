import { HttpClient } from '@angular/common/http';
import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { Restaurant } from '../_helpers/restaurant';
import { RestaurantService } from '../_services/restaurant.service';

@Component({
  selector: 'app-restaurant-details',
  templateUrl: './restaurant-details.component.html',
  styleUrls: ['./restaurant-details.component.css']
})
export class RestaurantDetailsComponent implements OnInit {

  restaurantDetail=new Array<Restaurant>();
  restaurantList: any;
  url: string="";
  finalurl: string="";
  constructor(
    private zonionsserve: RestaurantService,
    private activated_route: ActivatedRoute) {
  }

  ngOnInit(): void {
    let restaurantName = this.activated_route.snapshot.params['restaurantName'];
    console.log(restaurantName);
    this.zonionsserve.getRestaurantList().subscribe((data) => {
      console.log(data.length);
      this.restaurantDetail = data;
      console.log(this.restaurantDetail);
      for (let i = 0; i < data.length; i++) {
        if (data[i].restaurantName === restaurantName) {
          this.restaurantList = data[i];
        }
      }
      console.log(this.restaurantList)

      console.log(this.restaurantList.name);
      console.log(this.restaurantList.id);

      this.finalurl = 'http://localhost:8080/zonions/file';
      this.url = `${this.finalurl}/${this.restaurantList.name}/${this.restaurantList.id}`;

      console.log(this.url);
    });
  }

}
