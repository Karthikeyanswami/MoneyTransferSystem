import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { Login } from './components/login/login';
import { Dashboard } from './components/dashboard/dashboard';
import { History } from './components/history/history';
import { Transfer } from './components/transfer/transfer';
import { Profile } from './components/profile/profile';



const routes: Routes = [
   { path: '', redirectTo: 'login', pathMatch: 'full' },
  { path: 'login', component: Login },
  { path: 'dashboard/:username', component: Dashboard },
  {path: 'history/:username',component:History},
  {path: 'transfer/:username',component:Transfer},
  {path: 'profile/:username',component:Profile},
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
