import { CommonModule, CurrencyPipe } from '@angular/common';
import { Component, inject, Inject } from '@angular/core';
import { FormsModule, Validators } from '@angular/forms';
import { MAT_DIALOG_DATA, MatDialogRef } from '@angular/material/dialog';
import { TransactionService } from '../../services/transaction.service';
import { transaction } from '../../../models/transaction';
  import { HotToastService } from '@ngneat/hot-toast';

@Component({
  selector: 'app-sell-stock-dialog',
  standalone: true,
  imports: [CommonModule,CurrencyPipe,FormsModule,FormsModule],
  templateUrl: './sell-stock-dialog.component.html',
  styleUrl: './sell-stock-dialog.component.css'
})
export class SellStockDialogComponent {
  toastService=inject(HotToastService)
  quantity:any=1;
  transactionService=inject(TransactionService)
  transactionData:transaction={} as transaction
  user:any=localStorage.getItem('user')
  stringUser=JSON.parse(this.user)
  constructor(
    @Inject(MAT_DIALOG_DATA) public holding:any,
    private dialogRef:MatDialogRef<SellStockDialogComponent>,
  

  ){}

   get totalAmount():any {
     return this.quantity*this.holding.stock.price
  }

  get isValid(): boolean {
    return Number.isInteger(this.quantity) &&
      this.quantity > 0 &&
      this.quantity <= this.holding.quantity;
  }

  closeDialog(): void {
    this.dialogRef.close();
  }

   confirmPurchase(): void {
    if (!this.isValid) return;
    console.log("Sucessfully Purchased")
    const user:any=localStorage.getItem("user")
    this.transactionData.pricePerShare=this.holding.stock.price;
    this.transactionData.quantity=this.quantity
    this.transactionData.stock=this.holding.stock
    this.transactionData.user=JSON.parse(user)
    console.log(this.transactionData)
    this.transactionService.sellTrade(this.transactionData).pipe(
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
