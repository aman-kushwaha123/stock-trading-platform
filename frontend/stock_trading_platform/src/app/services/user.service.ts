import { inject,Injectable } from '@angular/core';
import { HttpClient,HttpErrorResponse } from '@angular/common/http';
import { HotToastService } from '@ngneat/hot-toast';
import { Router } from '@angular/router';
import { environment } from '../../environments/environment.development';
import { UserLogin } from '../../models/user';

@Injectable({
  providedIn: 'root'
})
export class UserService {
  http=inject(HttpClient)
  router=inject(Router)
  toastService=inject(HotToastService)

  constructor() { }

   register(userRegister:Object) {
    return this.http.post(environment.apiUrl+"/user/register",userRegister)
  }

  login(userLogin:UserLogin) {
    return this.http.post(environment.apiUrl+"/user/login",userLogin)
  }

  updateUser(userId:number,updateUser:Object){
    return this.http.put(environment.apiUrl+"/user/update/"+userId,updateUser)
  }

  updateUserRole(userId:number){
    return this.http.patch(environment.apiUrl+"/user/update/roleUpdate/"+userId,{})
  }

  getUserById(userId:number){
       return this.http.get(environment.apiUrl+"/user/get/"+userId)
  }

  getAllUsers(page:number,size:number){
    return this.http.get(environment.apiUrl+"/user/get/all?page="+page+"&&size="+size)
  }

  deleteUserById(userId:number){
    return this.http.delete(environment.apiUrl+"/user/delete/"+userId)
  }

  swggerUI(){
    //_blank tell the browser to open brand new tab 
    window.open(`${environment.apiUrl}/swagger-ui/index.html`, '_blank');
  }


  logOut(){
    const token=localStorage.getItem('token')
    const user=localStorage.getItem('user')

    if(token !=null && user !=null){
      localStorage.removeItem('token')
      localStorage.removeItem('user')
       this.router.navigateByUrl("/login")
      this.toastService.success('Sucessfully user logged out.')
     
      
    }
  }

  get isLoggedIn(){
    const token=localStorage.getItem('token');
    if (token){
      return true;
    }
    else{
      return false;
    }
  }


  get isAdmin(){
    const user:any=localStorage.getItem('user')
    const stringUser=JSON.parse(user)
    if(user==null){
      return false;
    }

    if(stringUser.role=='ADMIN'){
      return true;
    }
    return false;
  }

  //add this in HttpInterceptor
  get isTokenExpired(){
    const token=localStorage.getItem('token');
    if(!token){
      return false;        //if no token found treated as expired
    }
    try{
      // Extract the payload (second part of the JWT)
      const payloadBase64 = token.split('.')[1];
      const decodedJson = atob(payloadBase64.replace(/-/g, '+').replace(/_/g, '/'));
      const payload = JSON.parse(decodedJson);

      if(!payload.exp){
        return false; // Token has no expiration claim
      }

      // payload.exp is in seconds; Date.now() is in milliseconds
      const expiryInMillis=payload.exp * 1000;
      return Date.now()>=expiryInMillis
    }
    catch(err){
             return true;
    }
  }


}
