import { ChangeDetectorRef, Component } from '@angular/core';
import { AccountStatus } from '../../core/enums/account-status.enum';
import { ActivatedRoute, Router } from '@angular/router';
import { AccountInterface } from '../../core/interface/accountInterface';
import { Account } from '../../core/services/account';
import { AuthService } from '../../core/services/auth';

@Component({
  selector: 'app-profile',
  standalone: false,
  templateUrl: './profile.html',
  styleUrl: './profile.css',
})
export class Profile {
  isLoggedin: boolean = false;
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
  
  ngOnInit() : void{
    this.isLoggedin = this.authService.isUserLoggedin();
    if (!this.isLoggedin){
      this.router.navigateByUrl('login');
    }
    this.userName = Number(this.route.snapshot.paramMap.get('username'));
    this.accountService.getAccount(this.userName).subscribe({
      next: data => {this.account = data; console.log(this.account);this.cd.detectChanges();},
      error: err => console.error(err),
      
    });
    
  }
}
