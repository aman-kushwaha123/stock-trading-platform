import { HttpClient } from '@angular/common/http';
import { inject, Injectable } from '@angular/core';
import { environment } from '../../environments/environment.development';

@Injectable({
  providedIn: 'root'
})
export class StockService {
http=inject(HttpClient)
  constructor() { }

  createStock(stock:object){
     return this.http.post(environment.apiUrl+"/stock/add",stock)
  }

  updateStock(stock:object,stockId:number){
    return this.http.put(environment.apiUrl+"/stock/update/"+stockId,stock)
  }

  getStockById(stockId:number){
    return this.http.get(environment.apiUrl+"/stock/get/"+stockId)
  }

  getAllStock(page:number,size:number){
    return this.http.get(environment.apiUrl+"/stock/get/all?page="+page+"&&size="+size)
  }

  deleteStockById(stockId:number){
    return this.http.delete(environment.apiUrl+"/stock/delete/"+stockId)
  }

}
