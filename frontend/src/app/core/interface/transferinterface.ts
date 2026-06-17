import { TransactionStatus } from '../enums/transaction-status.enum';

export interface Transaction {
 
  id: string;
  fromAccountId: number;
  toAccountId: number;
  amount: string;
  status: TransactionStatus;
  failureReason?: string | null;
  idempotencyKey: string;
  createdOn: string;
}