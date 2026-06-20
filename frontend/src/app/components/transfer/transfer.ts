
import { ChangeDetectorRef, Component } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { TransferService } from '../../core/services/transfer';
import { Transferrequest } from '../../core/dto/transferrequest';
import { Transferresponse } from '../../core/dto/transferresponse';
import { Errorresponse } from '../../core/dto/errorresponse';
import { HttpErrorResponse } from '@angular/common/http';
import { AuthService } from '../../core/services/auth';


@Component({
  selector: 'app-transfer',
  standalone: false,
  templateUrl: './transfer.html',
  styleUrl: './transfer.css',
})
export class Transfer {
  showModal: boolean = false;
  modalTitle: string = '';
  modalMessage: string = '';
  modalDetails: any = null;
  modalCase: String=''
  isLoggedin : boolean = false;

  transferReq: Transferrequest = {
      fromAccountId : 0,
      toAccountId : 0,
      amount : 0,
      idempotencyKey : '',
  };
  note = '';

  constructor(private route: ActivatedRoute,private router: Router,private transferService: TransferService, private cd:ChangeDetectorRef,private authService : AuthService) {}
  
  ngOnInit():void{
    this.isLoggedin = this.authService.isUserLoggedin();
    if (!this.isLoggedin){
      this.router.navigateByUrl('login');
    }
    
    this.transferReq.fromAccountId = Number(this.route.snapshot.paramMap.get('username'));
    

      

   
  }

 
  transferMoney() {
    this.transferReq.idempotencyKey=this.uuidv4();
    this.transferService.transfer(this.transferReq).subscribe({
        next: (res: Transferresponse) => {
            this.modalTitle = 'Transfer Success';
            this.modalMessage = 'The transfer has been successfully completed.';
            this.modalDetails = res; 
            this.showModal = true;
            this.modalCase = 'success';
            this.cd.detectChanges();
        },
        error: (err : HttpErrorResponse) =>{
              const apiErr = err?.error as { errorCode?: string; message?: string } | undefined;
              const code = apiErr?.errorCode ?? `HTTP_${err.status}`; // e.g., "ACC-403" or fallback
              const msg  = apiErr?.message   ?? 'Something went wrong';
              
              this.modalTitle = 'Transfer Failed';
              this.modalMessage = 'The transfer has failed.';
              this.modalDetails = { errorCode: code, message: msg };
              this.showModal = true;
              this.modalCase = 'failure';
               this.cd.detectChanges();

        }


    });
    
  }


  cancelTransaction(){
   
    this.router.navigate(['/dashboard', this.transferReq.fromAccountId]);
  }

   closeModal() {
    this.showModal = false; 
  }

  
  uuidv4() {
    return crypto.randomUUID(); 
  }


  
}