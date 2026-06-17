export interface Transferrequest { 
    fromAccountId: number;
    toAccountId: number;
    amount: number;            
    idempotencyKey: string;

}
