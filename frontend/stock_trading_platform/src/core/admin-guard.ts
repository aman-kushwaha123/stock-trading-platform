import { inject } from "@angular/core";
import { CanActivateFn, Router } from "@angular/router";
import { UserService } from "../app/services/user.service";
import { HotToastService } from "@ngneat/hot-toast";

export const adminGaurd:CanActivateFn=(route,state)=>{
  const router = inject(Router);
  const userService = inject(UserService)
  const toastrService=inject(HotToastService)

  if(userService.isAdmin){
    return true;
  }
  toastrService.show("Administration Required.")
  router.navigateByUrl('/home')
  return false;
}