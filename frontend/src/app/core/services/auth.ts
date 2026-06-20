import { isPlatformBrowser } from '@angular/common';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Inject, Injectable, PLATFORM_ID } from '@angular/core';
import { catchError, map, throwError } from 'rxjs';


@Injectable({
  providedIn: 'root',
})
export class AuthService {
  private readonly USER_KEY = 'auth_user';
  private readonly TOKEN_KEY = 'auth_token';
 
  constructor(
    private http: HttpClient,
    @Inject(PLATFORM_ID) private platformId: Object
  ) {}
 
  private get isBrowser(): boolean {
    return isPlatformBrowser(this.platformId);
  }
 
  
authenticate(username: string, password: string) {
  if (!this.isBrowser) {
    return throwError(() => new Error('Authentication can only run in the browser.'));
  }
 
  const token = btoa(`${username}:${password}`);
  const headers = new HttpHeaders({ Authorization: `Basic ${token}` });
 
  return this.http.get('http://localhost:8080/auth', {
    headers,
    responseType: 'text'
  }).pipe(
    map((res: string) => {
      if (res !== 'Authenticated') throw new Error('Authentication failed');
      sessionStorage.setItem(this.USER_KEY, username);
      sessionStorage.setItem(this.TOKEN_KEY, token);
      return true;
    }),
    catchError(err => throwError(() => err))
  );
}
 
  logout(): void {
    if (this.isBrowser) {
      sessionStorage.removeItem(this.USER_KEY);
      sessionStorage.removeItem(this.TOKEN_KEY);
    }
  }
 
  isUserLoggedin(): boolean {
    return this.isBrowser && !!sessionStorage.getItem(this.TOKEN_KEY);
  }
 
  get token(): string | null {
    return this.isBrowser ? sessionStorage.getItem(this.TOKEN_KEY) : null;
  }
 
  getLoggedinUser(): string {
    return this.isBrowser ? (sessionStorage.getItem(this.USER_KEY) ?? '') : '';
  }
  }
  