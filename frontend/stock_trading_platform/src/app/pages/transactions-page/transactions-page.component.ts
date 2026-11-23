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

@Component({
  selector: 'app-transactions-page',
  standalone: true,
  imports: [CommonModule,MatTableModule,FormsModule,MatPaginatorModule,MatLabel,MatInputModule],
  templateUrl: './transactions-page.component.html',
  styleUrl: './transactions-page.component.css'
})
export class TransactionsPageComponent {
  displayedColumns: string[] = ['id', 'username','stockSymbol', 'quantity', 'transactionType', 'pricePerShare',
    'totalPrice','status', 'createdAt'
  ];
  dataSource = new MatTableDataSource<any>();
  transactionService = inject(TransactionService)
  stocks: any[] = []
  toastService = inject(HotToastService)
  @ViewChild(MatPaginator) paginator!: MatPaginator;

  totalElements = 0; // Total rows in DB (returned by backend API)
  totalPages = 0;
  pageSize = 5;      // Default rows per page
  pageIndex = 0;     // 0-based page number (Page 1 = 0)


    ngOnInit() {
      const user:any=localStorage.getItem('user')
      this.settingUsers(JSON.parse(user).id,this.pageIndex, this.pageSize)
  }

  settingUsers(userId:number,page: number, size: number) {
    console.log("calling setting users.")
    this.transactionService.getAllTransactionsByUserId(userId,page, size).subscribe({
      next: (res: any) => {
        console.log(res)
        if (res != null) {
          this.dataSource.data = res.transactionsByUserId.content
          this.totalElements = res.transactionsByUserId.totalElements
          this.totalPages = res.transactionsByUserId.totalPages
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


}
