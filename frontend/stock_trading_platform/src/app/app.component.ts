import { Component, inject } from '@angular/core';
import { RouterLink, RouterOutlet } from '@angular/router';

import { RegisterPageComponent } from "./auth/register-page/register-page.component";
import { LoginPageComponent } from "./auth/login-page/login-page.component";
import {MatTabNavPanel, MatTabsModule} from '@angular/material/tabs';
import { MatSlideToggleModule } from '@angular/material/slide-toggle';
import { MatSidenav, MatSidenavContainer, MatSidenavContent } from '@angular/material/sidenav';
import { MatToolbarModule } from '@angular/material/toolbar';
import { MatIconModule } from '@angular/material/icon';
import { FooterComponent } from './components/footer/footer.component';
import { UserService } from './services/user.service';
import { CommonModule } from '@angular/common';
import { MatDividerModule } from '@angular/material/divider';

@Component({
  selector: 'app-root',
  standalone: true,
  imports: [RouterOutlet, RegisterPageComponent, LoginPageComponent, MatSlideToggleModule,
    MatSidenavContainer, MatSidenav, MatSidenavContent, MatToolbarModule, MatIconModule, MatTabsModule, RouterLink, MatTabNavPanel, FooterComponent
  ,CommonModule,MatDividerModule],
  templateUrl: './app.component.html',
  styleUrl: './app.component.css'
})
export class AppComponent {
  title = 'stock_trading_platform';
  userService=inject(UserService)



  
}
