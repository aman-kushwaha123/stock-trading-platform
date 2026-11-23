import { HttpClient } from '@angular/common/http';
import { inject, Injectable } from '@angular/core';
import { environment } from '../../environments/environment.development';

@Injectable({
  providedIn: 'root'
})
export class HoldingService {
   http=inject(HttpClient)
  constructor() { }



  getHoldingById(holdingId:number){
       return this.http.get(environment.apiUrl+"/get/"+holdingId)
  }

  getAllHoldingByUserId(userId:number,page:number,size:number){
    return this.http.get(environment.apiUrl+"/holding/get/all?userId="+userId+"&&page="+page+"&&size="+size)
  }

  deleteHoldingById(holdingId:number){
    return this.http.delete(environment.apiUrl+"/delete/"+holdingId)
  }
}
