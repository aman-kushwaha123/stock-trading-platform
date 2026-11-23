import { HttpClient } from '@angular/common/http';
import { inject, Injectable } from '@angular/core';
import { environment } from '../../environments/environment.development';

@Injectable({
  providedIn: 'root'
})
export class TransactionService {
 http=inject(HttpClient)
  constructor() { }

  buyTrade(transactionDTO:object){
    return this.http.post(environment.apiUrl+"/transaction/buy",transactionDTO)
  }

 sellTrade(transactionDTO:any){
    return this.http.post(environment.apiUrl+"/transaction/sell",transactionDTO)
  }

  getAllTransactionsByUserId(userId:number,page:number,size:number){
      return this.http.get(environment.apiUrl+"/transaction/get/all?userId="+userId+"&&page="+page+"&&size="+size)
  }


  
}
