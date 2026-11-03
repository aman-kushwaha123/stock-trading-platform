import { inject,Injectable } from '@angular/core';
import { HttpClient,HttpErrorResponse } from '@angular/common/http';
import { Router } from '@angular/router';
import { environment } from '../../environments/environment.development';
import { UserLogin } from '../../models/user';

@Injectable({
  providedIn: 'root'
})
export class UserService {
  http=inject(HttpClient)
  router=inject(Router)

  constructor() { }

   register(userRegister:Object) {
    return this.http.post(environment.apiUrl+"/user/register",userRegister)
  }

  login(userLogin:UserLogin) {
    return this.http.post(environment.apiUrl+"/user/register",userLogin)
  }
}
