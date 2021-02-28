import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { User } from '../_helpers/user';
import { AuthService } from '../_services/auth.service';
import { UserService } from '../_services/user.service';

@Component({
  selector: 'app-user-detail',
  templateUrl: './user-detail.component.html',
  styleUrls: ['./user-detail.component.css']
})
export class UserDetailComponent implements OnInit {
  users:User[];
  role:any[];
  user=new User();
  constructor(private router:Router,private userService:UserService,private activatedRoute: ActivatedRoute) { }

  ngOnInit(): void {
    this.reloadUser();
  }

  reloadUser(){

       this.userService.getAllUsers().subscribe(data=>{
          console.log(data);
          this.users=data;
          for(let i=0;i<this.users.length;i++){
            //this.role=this.users.roles.la
          }
       },error=>console.log(error))

  }

  deleteUser(id: number) {
    this.userService.deleteUser(id).subscribe(
      (data) => {
        console.log(data);
        this.reloadUser();
        alert('Deleted Successfully');
      },
      (error) => console.log(error)
    );
  }

  changeRole(id:number,){
    console.log('id in change status=', id);
    this.userService.getUserById(id).subscribe((resp) => {
      console.log(resp);
      this.user = resp;
      console.log(this.user);
      this.userService.changeUserRole(id, this.user).subscribe(
        (data) => {
          console.log(data);
          this.reloadUser();
        },
        (error) => console.log(error)
      );
    });
  }
  

}
