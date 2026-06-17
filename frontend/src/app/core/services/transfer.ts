import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Transaction } from '../interface/transferinterface';
import { Transferrequest } from '../dto/transferrequest';
import { Transferresponse } from '../dto/transferresponse';


@Injectable({
  providedIn: 'root',
})
export class TransferService {


  private API = 'http://localhost:8080/api/v1/transfers';

  constructor(private http: HttpClient) {}

  transfer(transferReq:Transferrequest) {
   return this.http.post<Transferresponse>(this.API, transferReq)
      
  }
}