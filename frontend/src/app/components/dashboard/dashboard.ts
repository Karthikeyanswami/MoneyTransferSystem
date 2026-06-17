import { ChangeDetectorRef, Component } from '@angular/core';
import { Account } from '../../core/services/account';
import { ActivatedRoute, Router } from '@angular/router';
import { AccountInterface } from '../../core/interface/accountInterface';
import { AccountStatus } from '../../core/enums/account-status.enum';
import { AuthService } from '../../core/services/auth';

@Component({
  selector: 'app-dashboard',
  standalone: false,
  templateUrl: './dashboard.html',
  styleUrl: './dashboard.css',
})
export class Dashboard {
  
    isLoggedin = false;
    userName  : number= 0;

    account: AccountInterface=
                {
                  id: 0,
                  holderName: '',
                  balance: 0,
                  status: AccountStatus.ACTIVE,
                  version: 0,
                  lastUpdated: new Date(),
                };

  
    constructor(private route: ActivatedRoute,private router: Router,private accountService: Account, private cd:ChangeDetectorRef,private authService: AuthService) {}
  
    goTransfer() {
      this.router.navigate(['/transfer', this.userName]);
    }
  
    goHistory() {
      console.log(this.userName);
      this.router.navigate(['/history',this.userName]);
    }
  
    goProfile() {
       this.router.navigate(['/profile',this.userName]);
    }

  ngOnInit() : void{
    this.isLoggedin = this.authService.isUserLoggedin();
    if(this.isLoggedin){
         this.userName = Number(this.route.snapshot.paramMap.get('username'));
        this.accountService.getAccount(this.userName).subscribe({
          next: data => {this.account = data; console.log(this.account);this.cd.detectChanges();},
          error: err => console.error(err),
      
    });
    }
    else{
      this.router.navigateByUrl('login');
    }
   
    
  }
  
    logout() {
      this.authService.logout();
      this.router.navigate(['/login']);

    }
  }
  