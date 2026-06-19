import { Component, Input, OnInit } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { CommonModule } from '@angular/common';
import { HttpClientModule } from '@angular/common/http';

export interface RewardBalance {
  accountId: number;
  pointsBalance: number;
}

@Component({
  selector: 'app-rewards',
  templateUrl: './rewards.html',
  imports: [CommonModule, HttpClientModule], 
  styleUrls: ['./rewards.css']
})
export class RewardsComponent implements OnInit {
  @Input() accountId!: number;

  private readonly baseUrl = '/api/rewards';

  balance: RewardBalance | null = null;
  loading = true;
  error: string | null = null;

  readonly TIERS = [
    { name: 'Bronze',   min: 0,    max: 99,      next: 'Silver',   nextAt: 100  },
    { name: 'Silver',   min: 100,  max: 499,     next: 'Gold',     nextAt: 500  },
    { name: 'Gold',     min: 500,  max: 999,     next: 'Platinum', nextAt: 1000 },
    { name: 'Platinum', min: 1000, max: Infinity, next: null,       nextAt: null },
  ];

  constructor(private http: HttpClient) {}

  ngOnInit(): void {
    this.loadRewards();
  }

  loadRewards(): void {
    this.loading = true;
    this.error = null;

    this.http.get<RewardBalance>(`${this.baseUrl}/${this.accountId}/balance`)
      .subscribe({
        next: (data) => {
          this.balance = data;
          this.loading = false;
        },
        error: () => {
          this.error = 'Could not load rewards. Please try again.';
          this.loading = false;
        }
      });
  }

  get currentTier() {
    const pts = this.balance?.pointsBalance ?? 0;
    return this.TIERS.find(t => pts >= t.min && pts <= t.max) ?? this.TIERS[0];
  }

  get progressPercent(): number {
    const pts = this.balance?.pointsBalance ?? 0;
    const tier = this.currentTier;
    if (!tier.nextAt) return 100;
    return Math.round(((pts - tier.min) / (tier.nextAt - tier.min)) * 100);
  }

  get ptsToNextTier(): number {
    const pts = this.balance?.pointsBalance ?? 0;
    return this.currentTier.nextAt ? this.currentTier.nextAt - pts : 0;
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
    return (this.balance?.pointsBalance ?? 0) >= min;
  }
}