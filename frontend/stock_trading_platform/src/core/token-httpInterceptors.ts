import { HttpErrorResponse, HttpInterceptorFn, HttpResponse, HttpResponseBase } from "@angular/common/http";
import { inject } from "@angular/core";
import { UserService } from "../app/services/user.service";
import { Router } from "@angular/router";
import { catchError, throwError } from "rxjs";
import { tap } from 'rxjs';
import { HotToastService } from "@ngneat/hot-toast";




/*Global Refresh Trigger Using an HTTP Interceptor
• Intercept outgoing requests. Check if the HTTP method modifies data (POST, PUT, PATCH, DELETE) so you do not create an infinite loop on GET requests.
• Emit a global refresh signal. Notify a centralized RefreshService inside finalize() or tap() once the response arrives.
 */

export const tokenHttpInterceptor:HttpInterceptorFn=(req,next)=>{
  const userService=inject(UserService)
  const router=inject(Router)
  const token=localStorage.getItem('token')
  const  toastService=inject(HotToastService);


  if(token!=null){
/*
1. req
This is the original HTTP request going from your Angular application to the backend API.
provideHttpClient(withInterceptors([tokenHttpInterceptor]))  add this in app.config.ts
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
          Authorization:`Bearer ${token}`,
        }
      }
    )
  }
// forwarding this requst further
  return next(req).pipe(
    catchError((err:HttpErrorResponse)=>{
        if(err.status==401 || err.status==403){
          console.log("interceptor works")
            localStorage.removeItem('token')
            localStorage.removeItem('user')
            router.navigateByUrl('/login')
        }
        return throwError(()=>err);
    })
  )


}
