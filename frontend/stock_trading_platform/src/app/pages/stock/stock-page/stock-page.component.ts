import { Component, inject } from '@angular/core';
import { UserService } from '../../../services/user.service';
import { HotToastService } from '@ngneat/hot-toast';
import { AfterViewInit, ViewChild } from '@angular/core';
import { MatPaginator, MatPaginatorModule, PageEvent } from '@angular/material/paginator';
import { MatTableDataSource, MatTableModule } from '@angular/material/table';
import { MatInputModule } from '@angular/material/input';
import { MatFormField, MatFormFieldModule, MatLabel } from '@angular/material/form-field';
import { CommonModule } from '@angular/common';
import { StockService } from '../../../services/stock.service';

import { MatDialog, MatDialogModule } from '@angular/material/dialog';
import { BuyStockDialogComponent } from '../../../transactions-Dialog/buy-stock-dialog/buy-stock-dialog.component';

@Component({
  selector: 'app-stock-page',
  standalone: true,
  imports: [MatTableModule, MatPaginatorModule, MatFormFieldModule, MatInputModule, MatTableModule,
    CommonModule,MatDialogModule
  ],
  templateUrl: './stock-page.component.html',
  styleUrl: './stock-page.component.css'
})
export class StockPageComponent {
  displayedColumns: string[] = ['id', 'companyName', 'price', 'actions'];
  dataSource = new MatTableDataSource<any>();
  stockService = inject(StockService)
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
    this.settingUsers(this.pageIndex, this.pageSize)
  }

  settingUsers(page: number, size: number) {
    console.log("calling setting users.")
    this.stockService.getAllStock(page, size).subscribe({
      next: (res: any) => {
        console.log(res)
        if (res != null) {
          this.dataSource.data = res.stockPage.content
          this.totalElements = res.totalElements
          this.totalPages = res.totalPages
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
    this.settingUsers(this.pageIndex, this.pageSize);
  }


  OpenDialog(stock:any):void{
    this.dialog.open(BuyStockDialogComponent,{
      width: '450px',
      maxWidth: '95vw',
      panelClass: 'buy-stock-dialog',
      data: stock,
      autoFocus: false
    });

  }



}
