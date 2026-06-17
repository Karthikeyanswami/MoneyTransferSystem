import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { AccountInterface } from '../interface/accountInterface';
import { Transaction } from '../interface/transferinterface';

@Injectable({
  providedIn: 'root',
})
export class Account {
  

  private API = 'http://localhost:8080/api/v1/accounts';

  constructor(private http: HttpClient) {}

  getBalance() {
    return this.http.get<any>(`${this.API}/balance`);
  }
  
  getAccount(id:number) {
    return this.http.get<AccountInterface>(this.API+`/${id}`);
  }

  getTransactions(id:number){
    console.log("lllll",id);
    return this.http.get<Transaction[]>(this.API+`/${id}`+'/transactions');
  }

}