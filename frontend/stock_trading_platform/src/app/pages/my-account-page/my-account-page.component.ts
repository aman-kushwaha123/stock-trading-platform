import { CommonModule } from '@angular/common';
import { Component } from '@angular/core';
import { MatIconModule } from '@angular/material/icon';
import { RouterLink } from '@angular/router';

@Component({
  selector: 'app-my-account-page',
  standalone: true,
  imports: [MatIconModule,CommonModule,RouterLink],
  templateUrl: './my-account-page.component.html',
  styleUrl: './my-account-page.component.css'
})
export class MyAccountPageComponent {
   user:any=localStorage.getItem('user')
   stringUser=JSON.parse(this.user)

}
