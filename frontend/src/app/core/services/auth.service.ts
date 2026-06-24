import { isPlatformBrowser } from '@angular/common';
import { HttpClient } from '@angular/common/http';
import { Injectable, PLATFORM_ID, inject } from '@angular/core';
import { Observable, tap } from 'rxjs';

import { environment } from '../../../environments/environment';
import { LoginRequest, LoginResponse } from '../models/auth';

@Injectable({
  providedIn: 'root',
})
export class AuthService {
  private readonly http = inject(HttpClient);
  private readonly platformId = inject(PLATFORM_ID);

  private getStorage(): Storage | null {
    return isPlatformBrowser(this.platformId) ? window.localStorage : null;
  }

  private getPersistentStorage(): Storage | null {
    return isPlatformBrowser(this.platformId) ? window.sessionStorage : null;
  }

  login(request: LoginRequest): Observable<LoginResponse> {
    return this.http.post<LoginResponse>(`${environment.apiUrl}/auth/login`, request).pipe(
      tap((response) => {
        this.getStorage()?.setItem('token', response.token);
        this.getPersistentStorage()?.setItem('token', response.token);
      })
    );
  }

  logout(): void {
    this.getStorage()?.removeItem('token');
    this.getPersistentStorage()?.removeItem('token');
  }

  isAuthenticated(): boolean {
    return !!(this.getStorage()?.getItem('token') ?? this.getPersistentStorage()?.getItem('token'));
  }

  getToken(): string | null {
    return this.getStorage()?.getItem('token') ?? this.getPersistentStorage()?.getItem('token') ?? null;
  }
}