import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';

import { RegisterComponent } from './register/register.component';
import { LoginComponent } from './login/login.component';
import { HomeComponent } from './home/home.component';
import { ProfileComponent } from './profile/profile.component';
import { BoardUserComponent } from './board-user/board-user.component';
import { BoardAdminComponent } from './board-admin/board-admin.component';
import { CreateRestaurantComponent } from './create-restaurant/create-restaurant.component';
import { UpdateRestaurantComponent } from './update-restaurant/update-restaurant.component';
import { RestaurantDetailsComponent } from './restaurant-details/restaurant-details.component';
import { RestaurantListComponent } from './restaurant-list/restaurant-list.component';
import { UserDetailComponent } from './user-detail/user-detail.component';

const routes: Routes = [
  { path: 'home', component: HomeComponent },
  { path: 'login', component: LoginComponent },
  { path: 'register', component: RegisterComponent },
  { path: 'profile', component: ProfileComponent },
  { path: 'create', component: CreateRestaurantComponent},
  { path: 'update/:id', component: UpdateRestaurantComponent},
  { path: 'restaurantDetail/:restaurantName', component: RestaurantDetailsComponent},
  { path: 'restaurants', component: RestaurantListComponent},
  { path: 'user', component: BoardUserComponent },
  { path: 'admin', component: BoardAdminComponent },
  {path:'userDetail',component:UserDetailComponent},
  { path: '', redirectTo: 'login', pathMatch: 'full' }

  // {path:"home",component:HomeComponent },
  // {path:"login",component:LoginComponent},
  // {path:"restaurant" , component:RestaurantComponent},
  // {path:"restaurantDetail/:restaurantName", component:RestaurantDetailComponent},
  // {path:"logout",component:LogoutComponent},
  // {path:"update/:id",component:UpdateRestaurantComponent},
  // {path:"admin",component:AdminComponent},
  // {path:"restaurantList",component:RestaurantListComponent},
  // {path:"registration",component:RegistrationComponent},
  // {path:"user",component:BoardUserComponent},
  // {path:"profile",component:ProfileComponent},
  // {path:"admin",component:BoardAdminComponent},
  // {path:"500", component:InternalServeErrorComponent},
  // {path:"404",component:PageNotFoundErrorComponent},
  // {path:"",redirectTo:"home",pathMatch:'full'},
  // {path:"**",component:PageNotFoundErrorComponent},
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
