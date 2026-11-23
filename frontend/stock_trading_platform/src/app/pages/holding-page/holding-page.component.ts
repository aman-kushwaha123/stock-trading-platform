import { Component, inject } from '@angular/core';

import { HotToastService } from '@ngneat/hot-toast';
import { AfterViewInit, ViewChild } from '@angular/core';
import { MatPaginator, MatPaginatorModule, PageEvent } from '@angular/material/paginator';
import { MatTableDataSource, MatTableModule } from '@angular/material/table';
import { MatInputModule } from '@angular/material/input';
import { MatFormField, MatFormFieldModule, MatLabel } from '@angular/material/form-field';
import { CommonModule } from '@angular/common';

import { MatDialog, MatDialogModule } from '@angular/material/dialog';
import { TransactionService } from '../../services/transaction.service';
import { FormsModule } from '@angular/forms';
import { HoldingService } from '../../services/holding.service';
import { SellStockDialogComponent } from '../../transactions-Dialog/sell-stock-dialog/sell-stock-dialog.component';

interface holding {
  id: number;
  user: object;
  stock: object;
  quantity: number;
  totalInvestment: number;
  averageBuyPrice: number;
  createdAt: Date;
  updatedAt: Date;

}

@Component({
  selector: 'app-holding-page',
  standalone: true,
  imports: [CommonModule, MatTableModule, FormsModule, MatPaginatorModule, MatLabel, MatInputModule],
  templateUrl: './holding-page.component.html',
  styleUrl: './holding-page.component.css'
})
export class HoldingPageComponent {
  displayedColumns: string[] = ['id', 'username', 'stockSymbol', 'quantity', 'averageBuyPrice', 'totalInvestment'
    , 'createdAt', 'updatedAt','actions'
  ];

  dataSource = new MatTableDataSource<any>();
  holdingService = inject(HoldingService)
  stocks: any[] = []
  toastService = inject(HotToastService)
  @ViewChild(MatPaginator) paginator!: MatPaginator;

  totalElements = 0; // Total rows in DB (returned by backend API)
  totalPages = 0;
  pageSize = 5;      // Default rows per page
  pageIndex = 0;     // 0-based page number (Page 1 = 0)

  constructor(private dialog:MatDialog){

  }

   ngOnInit() {
      const user:any=localStorage.getItem('user')
      this.settingUsers(JSON.parse(user).id,this.pageIndex, this.pageSize)
  }

  settingUsers(userId:number,page: number, size: number) {
    console.log("calling setting users.")
    this.holdingService.getAllHoldingByUserId(userId,page, size).subscribe({
      next: (res: any) => {
        console.log(res)
        if (res != null) {
          this.dataSource.data = res.holdingsPage.content
          this.totalElements = res.holdingsPage.totalElements
          this.totalPages = res.holdingsPage.totalPages
        }

      },
      error: (err) => {
        console.log(err)

      }
    })
  }

  applyFilter(event: Event) {
      const filterValue = (event.target as HTMLInputElement).value;
      this.dataSource.filter = filterValue.trim().toLowerCase();
  
  
    }
  
    // TypeScript: Receives the PageEvent and fetches the matching slice from your backend
    onPageChange(event: PageEvent): void {
      this.pageIndex = event.pageIndex; // Update current page
      this.pageSize = event.pageSize;  // Update items per page
  
      // Call your backend API with the updated numbers
      const user:any=localStorage.getItem('user')
      this.settingUsers(JSON.parse(user).id,this.pageIndex, this.pageSize)
    }

    OpenDialog(holding:any):void{
        this.dialog.open(SellStockDialogComponent,{
          width: '450px',
          maxWidth: '95vw',
          panelClass: 'buy-stock-dialog',
          data: holding,
          autoFocus: true
        });


}
}

