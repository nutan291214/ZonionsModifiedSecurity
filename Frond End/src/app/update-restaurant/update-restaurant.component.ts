import { HttpClient } from '@angular/common/http';
import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { Restaurant } from '../_helpers/restaurant';
import { RestaurantService } from '../_services/restaurant.service';

@Component({
  selector: 'app-update-restaurant',
  templateUrl: './update-restaurant.component.html',
  styleUrls: ['./update-restaurant.component.css']
})
export class UpdateRestaurantComponent implements OnInit {
  data:any;
  restaurantData:any;
  restaurant:Restaurant;
  id:number=0;
  imagename:string="";
  url:string="";
  finalurl:string="";
  file:any;
  t:number=0;
  q:number=0;
  //open_time:string='';
  //close_time:string="";
  open_time={
    hour:10,
    minute: 30
  }
  close_time={
    hour:21,
    minute: 30
  }
  constructor(private zonionsserve:RestaurantService,private route: ActivatedRoute,private router: Router) { 
      this.restaurant=new Restaurant();
  }
  ngOnInit(): void {
    this.restaurant=new Restaurant();
    this.id=this.route.snapshot.params['id'];
    console.log(this.id)
    this.zonionsserve.getRestoId(this.id).subscribe(data=>{
      console.log(data)
      this.restaurant=data;
       this.t=parseInt(this.restaurant.open_time.slice(0,2))
       this.q=parseInt(this.restaurant.open_time.slice(3,5))
      this.open_time={ hour:this.t,minute:this.q}
      var ch:number=parseInt(this.restaurant.close_time.slice(0,2))
      var cm:number=parseInt(this.restaurant.close_time.slice(3,5))
      this.close_time={ hour:ch,minute:cm}
      console.log("image name",this.restaurant.name);
      this.finalurl="http://localhost:8080/zonions/file";
      this.url=`${this.finalurl}/${this.restaurant.name}/${this.id}`;
      
        console.log(this.url);
    })
  }
  updateResto(){
    
    this.zonionsserve.update(this.id,this.restaurant).subscribe(data=>{
      console.log(data);
      alert(data);
    
      
    console.log("openTime==",this.open_time)
     // alert("Updated Successfully");
      this.restaurant=new Restaurant();

      this.gotoRestaurantList();
    },error=>console.log(error)) 
  }
  onSubmit(){
    this.restaurant.open_time = this.open_time.hour + ':' + this.open_time.minute;
    this.restaurant.close_time = this.close_time.hour + ':' + this.close_time.minute;
    this.updateResto();
  }
  onChange(file:any){
      this.file=file;

  }
  updateImage(){
    console.log("I am in upload"+this.file);
    this.zonionsserve.pushFileToStorage(this.file,this.id).subscribe((resp:any)=>{
      console.log(resp);
    
    },(error:any)=>console.log(error))
  }
  gotoRestaurantList(){
    this.router.navigate(['restaurants'])
  } 
  backEvent(){
    this.router.navigate(['admin']);

  }

  }
