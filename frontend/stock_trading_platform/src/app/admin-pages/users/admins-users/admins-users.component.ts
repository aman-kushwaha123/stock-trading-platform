import { Component, inject } from '@angular/core';
import { UserService } from '../../../services/user.service';
import { HotToastService } from '@ngneat/hot-toast';
import { AfterViewInit, ViewChild } from '@angular/core';
import { MatPaginator, MatPaginatorModule, PageEvent } from '@angular/material/paginator';
import { MatTableDataSource, MatTableModule } from '@angular/material/table';
import { MatInputModule } from '@angular/material/input';
import { MatFormField, MatFormFieldModule, MatLabel } from '@angular/material/form-field';
import { CommonModule } from '@angular/common';
import { Router } from '@angular/router';

interface User {
  id: number;
  username: string;
  role: string;
  cashBalance: number;
}
@Component({
  selector: 'app-admins-users',
  standalone: true,
  imports: [MatTableModule, MatPaginatorModule, MatFormFieldModule, MatInputModule, MatTableModule,
    CommonModule
  ],
  templateUrl: './admins-users.component.html',
  styleUrl: './admins-users.component.css'
})

export class AdminsUsersComponent {
  displayedColumns: string[] = ['id', 'username', 'role', 'cashBalance', 'actions'];
  router = new (Router)
  dataSource = new MatTableDataSource<User>();
  userService = inject(UserService)
  user = JSON.parse(localStorage.getItem('user') as any);
  users: User[] = []
  toastService = inject(HotToastService)
  @ViewChild(MatPaginator) paginator!: MatPaginator;


  totalElements = 0; // Total rows in DB (returned by backend API)
  totalPages = 0;
  pageSize = 5;      // Default rows per page
  pageIndex = 0;     // 0-based page number (Page 1 = 0)
  ngOnInit() {
    this.settingUsers(this.pageIndex, this.pageSize)
  }


  settingUsers(page: number, size: number) {
    console.log("calling setting users.")
    this.userService.getAllUsers(page, size).subscribe({
      next: (res: any) => {
        console.log(res)
        if (res != null) {
          this.dataSource.data = res.content
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

  updateUserRole(userId: number) {
    this.userService.updateUserRole(userId).subscribe({
      next: (res: any) => {
        console.log('update e user response', res.user)
        if (userId === this.user.id) {
          localStorage.removeItem('user')
          localStorage.setItem('user', JSON.stringify(res.user))
          this.toastService.success("user role updated.")
          this.router.navigateByUrl('/home')
        }
        else {
          //Re-initialize the Current Route Without a Browser Reload 
          /* • Navigate away and back silently. Use skipLocationChange: true so the browser URL history stays clean.
• Run inside the API subscribe block. Trigger this navigation once after your API call completes.
*/
          const currentUrl = this.router.url;
          this.router.navigateByUrl('/', { skipLocationChange: true }).then(() => {
            this.router.navigate([currentUrl])
          })
          this.toastService.success("user role updated.")
        }

      }
      , error: (err) => {
        this.toastService.error("failed  to update user role.")
        const currentUrl = this.router.url;
          this.router.navigateByUrl('/', { skipLocationChange: true }).then(() => {
            this.router.navigate([currentUrl])
          })
      }
    })
  }


  onDelete(userId:number){
    this.userService.deleteUserById(userId).subscribe({
      next: (res: any) => {
        console.log('update e user response', res.user)
        if (userId === this.user.id) {
          this.userService.logOut()
          return;
        }
         const currentUrl = this.router.url;
          this.router.navigateByUrl('/', { skipLocationChange: true }).then(() => {
            this.router.navigate([currentUrl])
          })
          this.toastService.success("user deleted.")

    }, error: (err) => {
        this.toastService.error("failed  to delete user.")
        const currentUrl = this.router.url;
          this.router.navigateByUrl('/', { skipLocationChange: true }).then(() => {
            this.router.navigate([currentUrl])
          })
      }
  })
}


}
