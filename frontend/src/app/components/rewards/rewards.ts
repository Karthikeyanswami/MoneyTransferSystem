import { ChangeDetectorRef, Component, Input, OnInit } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { CommonModule } from '@angular/common';
import { HttpClientModule } from '@angular/common/http';


@Component({
  selector: 'app-rewards',
  standalone: false,
  templateUrl: './rewards.html',
  styleUrls: ['./rewards.css']
})
export class RewardsComponent implements OnInit {
  @Input() accountId!: number;

  private readonly baseUrl = 'http://localhost:8080/api/v1/accounts';

  rewards : number = 0;
  loading = true;
  error: string | null = null;

  readonly TIERS = [
  { name: 'Bronze',   min: 0,    max: 99,      next: 'Silver',   nextAt: 100,  cssClass: 'bronze',   icon: '🥉' },
  { name: 'Silver',   min: 100,  max: 499,     next: 'Gold',     nextAt: 500,  cssClass: 'silver',   icon: '🥈' },
  { name: 'Gold',     min: 500,  max: 999,     next: 'Platinum', nextAt: 1000, cssClass: 'gold',     icon: '🥇' },
  { name: 'Platinum', min: 1000, max: Infinity, next: null,       nextAt: null, cssClass: 'platinum', icon: '💎' },
];

  constructor(private http: HttpClient,  private cd:ChangeDetectorRef) {}

  ngOnInit(): void {
  
    this.http.get<any>(`${this.baseUrl}/${this.accountId}/rewards`)
      .subscribe({
        next: (data) => {
          console.log(data);
          this.rewards = data;
          this.loading = false;
          this.cd.detectChanges();
          console.log("rewards : ", this.rewards);
          console.log(this.loading);
        },
        error: () => {
          this.error = 'Could not load rewards. Please try again.';
          this.loading = false;
        }
      });
    
    // this.balance = {accountId: 1, pointsBalance: 29};
    // this.loading = false;

  }

  loadRewards(): void {
    this.error = null;

    this.http.get<any>(`${this.baseUrl}/${this.accountId}/rewards`)
      .subscribe({
        next: (data) => {
          console.log(data);
          this.rewards = data;
          console.log("rewards : ", this.rewards);
          this.loading = false;
          console.log(this.loading);
        },
        error: () => {
          this.error = 'Could not load rewards. Please try again.';
          this.loading = false;
        }
      });
  }

  get currentTier() {
    return this.TIERS.find(t => this.rewards >= t.min && this.rewards <= t.max) ?? this.TIERS[0];
  }

  get progressPercent(): number {
    const tier = this.currentTier;
    if (!tier.nextAt) return 100;
    return Math.round(((this.rewards - tier.min) / (tier.nextAt - tier.min)) * 100);
  }

  get ptsToNextTier(): number {
    return this.currentTier.nextAt ? this.currentTier.nextAt - this.rewards : 0;
  }

  get hintText(): string {
    const tier = this.currentTier;
    if (!tier.next) return "You've reached Platinum — the highest tier.";
    return `Transfer Rs ${(this.ptsToNextTier * 100).toLocaleString('en-IN')} more to reach ${tier.next}.`;
  }

  isTierActive(tierName: string): boolean {
    return this.currentTier.name === tierName;
  }

  isTierUnlocked(min: number): boolean {
    return (this.rewards ?? 0) >= min;
  }
}