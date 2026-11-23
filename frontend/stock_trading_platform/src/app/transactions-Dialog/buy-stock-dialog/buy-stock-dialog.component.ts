import { CommonModule, CurrencyPipe } from '@angular/common';
import { Component, inject, Inject } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { MAT_DIALOG_DATA, MatDialogRef } from '@angular/material/dialog';
import { TransactionService } from '../../services/transaction.service';
import { transaction } from '../../../models/transaction';
import { HotToastService } from '@ngneat/hot-toast';

@Component({
  selector: 'app-buy-stock-dialog',
  standalone: true,
  imports: [CommonModule,CurrencyPipe,FormsModule],
  templateUrl: './buy-stock-dialog.component.html',
  styleUrl: './buy-stock-dialog.component.css'
})
export class BuyStockDialogComponent {
  toastService=inject(HotToastService)
  quantity:any=1;
  transactionService=inject(TransactionService)
  transactionData:transaction={} as transaction
  user:any=localStorage.getItem('user')
  stringUser=JSON.parse(this.user)
  constructor(
    @Inject(MAT_DIALOG_DATA) public stock:any,
    private dialogRef:MatDialogRef<BuyStockDialogComponent>,
  

  ){}


    get totalAmount():any {
     return this.quantity*this.stock.price
  }

  get isValid(): boolean {
    return Number.isInteger(this.quantity) &&
      this.quantity > 0 &&
      this.quantity <= 100;
  }

  closeDialog(): void {
    this.dialogRef.close();
  }

   confirmPurchase(): void {
    if (!this.isValid) return;
    console.log("Sucessfully Purchased")
    const user:any=localStorage.getItem("user")
    this.transactionData.pricePerShare=this.stock.price;
    this.transactionData.quantity=this.quantity
    this.transactionData.stock=this.stock
    this.transactionData.user=JSON.parse(user)
    console.log(this.transactionData)
    this.transactionService.buyTrade(this.transactionData).pipe(
      this.toastService.observe({
        loading:"Performing transaction...",
        success:"Data synced successfully!",
        error:"Transaction failed."
      })
    ).subscribe({
      next:(res)=>{
        console.log(res)
        this.dialogRef.close({});
         this.toastService.success("Sucessfully transaction completed.")
      },
      error:(err)=>{
        console.log(err)
          this.dialogRef.close({});
        this.toastService.error("Error : "+err.error.message)
      
      }
    })
    //this.dialogRef.close({});// Passes resultData to the parent component if any in close()
  }



}
