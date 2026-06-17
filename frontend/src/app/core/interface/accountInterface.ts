import { AccountStatus } from "../enums/account-status.enum";

export interface AccountInterface {
  id: number;              
  holderName: string;
  balance: number;         
  status: AccountStatus;
  version: number;
  lastUpdated: Date;     
}
