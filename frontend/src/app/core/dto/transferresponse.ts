import { TransactionStatus } from "../enums/transaction-status.enum";

export interface Transferresponse {
    transactionId: string;       
  status: TransactionStatus;   
  message: string;
  debitedFrom: number;         
  creditedTo: number;          
  amount: number; 

}
