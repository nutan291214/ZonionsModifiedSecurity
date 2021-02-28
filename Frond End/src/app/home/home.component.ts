import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { Observable } from 'rxjs';
import { Restaurant } from '../_helpers/restaurant';
import { RestaurantService } from '../_services/restaurant.service';
import { UserService } from '../_services/user.service';

@Component({
  selector: 'app-home',
  templateUrl: './home.component.html',
  styleUrls: ['./home.component.css']
})
export class HomeComponent implements OnInit {
  restaurant = new Array<Restaurant>();
  public errorMessage: string = '';

  constructor(private zonionsService: RestaurantService, 
    ) 
  {
  
  }

  ngOnInit(): void {
    this.getRestaurants();
  }

  // changeLanguage(lang:string){
  //        this.translateConfigService.changeLanguage(lang);
  // }
  getRestaurants() {
    return this.zonionsService.getRestaurantList().subscribe((data) => {
      console.log(data);
      console.log(data.length);
      this.restaurant = data;
    });

    return this.zonionsService.getRestaurantList().subscribe((data)=>{
      this.restaurant=data;
    },
    (error)=>{
      console.log(error)
    //     this.errorHandlerService.handleError(error);
    //     this.errorMessage=this.errorHandlerService.errorMessage;
     })
  }
}
