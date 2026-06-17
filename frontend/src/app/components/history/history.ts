import { ChangeDetectorRef, Component } from '@angular/core';
import { Transaction } from '../../core/interface/transferinterface';
import { ActivatedRoute, Router } from '@angular/router';
import { Account } from '../../core/services/account';
import { AuthService } from '../../core/services/auth';

 
@Component({
  selector: 'app-history',
  standalone: false,
  templateUrl: './history.html',
  styleUrls: ['./history.css'],
})
export class History {
  userName: number = 0;
  transactions: any[] = [];
  activeTab: 'all' | 'sent' | 'received' = 'all';
  isLoggedin : boolean = false;
 
  constructor(
    private route: ActivatedRoute,
    private router: Router,
    private accountService: Account,
    private cd: ChangeDetectorRef,
    private authService: AuthService
  ) {}
 
  ngOnInit(): void {
    this.isLoggedin = this.authService.isUserLoggedin();
    if (!this.isLoggedin){
      this.router.navigateByUrl('login');
    }


    const rawParam = this.route.snapshot.paramMap.get('username');
    this.userName = Number(rawParam);
    if (!Number.isFinite(this.userName)) this.userName = 0;
 
    this.accountService.getTransactions(this.userName).subscribe({
      next: (data: any) => {
        const rows: any[] = Array.isArray(data) ? data : (data?.items ?? []);
        this.transactions = rows.map((t: any) => {
          const createdOn =
            t?.created_at ??
            t?.createdOn ??
            t?.created_on ??
            t?.timestamp ??
            null;
 
          let createdAtMs = 0;
          if (createdOn != null) {
            if (typeof createdOn === 'number') {
              createdAtMs = createdOn;
            } else if (typeof createdOn === 'string') {
              const normalized = createdOn.includes(' ')
                ? createdOn.replace(' ', 'T')
                : createdOn;
              const ms = new Date(normalized).getTime();
              createdAtMs = Number.isFinite(ms) ? ms : 0;
            } else if (createdOn instanceof Date) {
              createdAtMs = createdOn.getTime();
            }
          }
 
          const statusRaw = t?.status;
          const statusText =
            statusRaw === 'SUCCESS' || statusRaw === 0 ? 'SUCCESS' : 'FAILED';
 
          const fromId =
            t?.fromAccountId ?? t?.from_account_id ?? t?.from_accountID;
          const toId =
            t?.toAccountId ?? t?.to_account_id ?? t?.to_accountID;
 
          return {
            ...t,
            fromAccountId: Number(fromId),
            toAccountId: Number(toId),
            createdAtMs: Number.isFinite(createdAtMs) ? createdAtMs : 0,
            statusText,
          };
        });
 
        this.cd.detectChanges();
      },
      error: (err) => {
        console.error('getTransactions error', err);
        this.transactions = [];
        this.cd.detectChanges();
      },
    });
  }
 
  private num(v: any): number {
    const n = Number(v);
    return Number.isFinite(n) ? n : NaN;
  }
 
  isSent(tx: any): boolean {
    return this.num(tx?.fromAccountId) === this.num(this.userName);
  }
 
  isReceived(tx: any): boolean {
    return this.num(tx?.toAccountId) === this.num(this.userName);
  }
 
  amountSign(tx: any): string {
    return this.isSent(tx) ? '-' : '+';
  }
 
  amountClass(tx: any): string {
    return this.isSent(tx) ? 'amount sent' : 'amount received';
  }
 
  statusClass(tx: any): string {
    return tx?.statusText === 'SUCCESS' ? 'badge success' : 'badge failed';
  }
 
  goTransfer(): void {
    this.router.navigate(['/transfer', this.userName]);
  }
 
  goHistory(): void {
    this.router.navigate(['/history', this.userName]);
  }
 
  goHome(): void {
    this.router.navigate(['/dashboard', this.userName]);
  }
 
  get filteredTransactions(): any[] {
    const base = Array.isArray(this.transactions) ? this.transactions : [];
    const sorted = [...base].sort((a: any, b: any) => {
      const ams = Number(a?.createdAtMs) || 0;
      const bms = Number(b?.createdAtMs) || 0;
      return bms - ams;
    });
 
    const successOnly = sorted.filter((tx) => tx?.statusText === 'SUCCESS');
 
    if (this.activeTab === 'sent') return successOnly.filter((tx) => this.isSent(tx));
    if (this.activeTab === 'received') return successOnly.filter((tx) => this.isReceived(tx));
    return successOnly;
  }
}