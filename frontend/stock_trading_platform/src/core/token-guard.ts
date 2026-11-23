import { inject } from "@angular/core";
import { CanActivateFn, Router } from "@angular/router";
import { UserService } from "../app/services/user.service";
import { HotToastService } from '@ngneat/hot-toast';
export const tokenGuard: CanActivateFn =(route,state)=>{
  const router = inject(Router);
  const userService = inject(UserService)
  const toastrService=inject(HotToastService)

  if(userService.isLoggedIn){
    if(userService.isTokenExpired){
       toastrService.show("Please re-login")
        userService.logOut()
        return false
    }
    return true
  }
  toastrService.show("Please login first.")
  router.navigateByUrl('/login')
  return false
 
    
}