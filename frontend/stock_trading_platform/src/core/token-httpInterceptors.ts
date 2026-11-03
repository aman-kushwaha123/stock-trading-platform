import { HttpInterceptorFn } from "@angular/common/http";
import { inject } from "@angular/core";
import { UserService } from "../app/services/user.service";
import { Router } from "@angular/router";
import { catchError } from "rxjs";

export const tokenHttpInterceptor:HttpInterceptorFn=(req,next)=>{
  const userService=inject(UserService)
  const router=inject(Router)
  const token=localStorage.getItem("token")


  if(token!=null){
/*
1. req
This is the original HTTP request going from your Angular application to the backend API.

2. req.clone()
It creates a copy of the original request.

Why? Because Angular HTTP requests are immutable, meaning we cannot directly modify the original request.

3. setHeaders
This adds a new header or updates an existing header in the request.

4. Authorization
This tells the backend that the request contains authentication information.

5. Bearer ${token}
This sends your login token to the backend.*/
    req=req.clone(
      {
        setHeaders:{
          setAuthorization:`Bearer ${token}`,
        }
      }
    )
  }
// forwarding this requst further
  return next(req)
  /*.pipe(
    catchError((error)=>{
      if  authService.istokenExpired(token)) {

        localStorage.removeItem('token')
        localStorage.removeItem('user'),
          localStorage.removeItem('driver')
        router.navigateByUrl('/')

      }
        return thorwError(()=>Error)

    })
  )*/

}
