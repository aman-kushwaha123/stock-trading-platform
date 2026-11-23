import { Routes } from '@angular/router';
import { RegisterPageComponent } from './auth/register-page/register-page.component';
import { LoginPageComponent } from './auth/login-page/login-page.component';
import { AppComponent } from './app.component';
import { MarketChartComponent } from './components/market-chart/market-chart.component';
import { CreateStockComponent } from './pages/stock/create-stock/create-stock.component';
import { HomePageComponent } from './pages/home-page/home-page.component';
import { AdminsUsersComponent } from './admin-pages/users/admins-users/admins-users.component';
import { StockPageComponent } from './pages/stock/stock-page/stock-page.component';
import { TransactionsPageComponent } from './pages/transactions-page/transactions-page.component';
import { HoldingPageComponent } from './pages/holding-page/holding-page.component';
import { PortfolioSnapshotPageComponent } from './pages/portfolio-snapshot-page/portfolio-snapshot-page.component';
import { tokenGuard } from '../core/token-guard';
import { adminGaurd } from '../core/admin-guard';
import { MyAccountPageComponent } from './pages/my-account-page/my-account-page.component';
import { UpdateCashBalanceComponent } from './components/update-cash-balance/update-cash-balance.component';


/*
    Steps to provide the routes in app.routes.ts
    1)include <router-outlet></router-outlet> tag in app-component.html
    2)check providers(routes) in app.config
    3)add paths inside the app.routes.ts
 */

export const routes: Routes = [
  {
    path:"",
    component:HomePageComponent,
    canActivate:[tokenGuard]
  },
  {
    path:"register",
    component:RegisterPageComponent,
  },
  {
    path:"login",
    component:LoginPageComponent,
  },
  {
    path:"myAccount",
    component:MyAccountPageComponent,
    canActivate:[tokenGuard]
  },
   {
    path:"myAccount/updateUser",
    component:UpdateCashBalanceComponent,
    canActivate:[tokenGuard]
  },
  {
    path:"stocks",
    component:StockPageComponent,
    canActivate:[tokenGuard]
  },
  {
    path:"holdings",
    component:HoldingPageComponent,
    canActivate:[tokenGuard]
  },
  {
    path:"transactions",
    component:TransactionsPageComponent,
    canActivate:[tokenGuard]
  },
  {
    path:"portfolioSnapshots",
    component:PortfolioSnapshotPageComponent,
    canActivate:[tokenGuard]
  },
   {
    path:"admin/create-stock",
    component:CreateStockComponent,
    canActivate:[tokenGuard,adminGaurd]
  },
  {
    path:"admin/all-users",
    component:AdminsUsersComponent,
    canActivate:[tokenGuard,adminGaurd]
  },

];
