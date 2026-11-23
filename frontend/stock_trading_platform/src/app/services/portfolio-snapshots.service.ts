import { HttpClient } from '@angular/common/http';
import { inject, Injectable } from '@angular/core';
import { environment } from '../../environments/environment.development';

@Injectable({
  providedIn: 'root'
})
export class PortfolioSnapshotsService {
  http=inject(HttpClient)
  constructor() { }

  getPortFolioSnapshotsByUserId(userId:number,page:number,size:number){
    return this.http.get(environment.apiUrl+"/portfolioSnapshots/get/all?userId="+userId+"&&page="+page+"&&size="+size)
  }





}
