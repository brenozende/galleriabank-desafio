import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Injectable, inject } from '@angular/core';
import { Observable } from 'rxjs';
import { environment } from '../../../environments/environment';
import { Produto } from '../models/produto';
import { AuthService } from './auth.service';

@Injectable({ providedIn: 'root' })
export class ProdutoService {
  private readonly apiUrl = `${environment.apiUrl}/produtos`;
  private readonly http = inject(HttpClient);
  private readonly authService = inject(AuthService);

  private getAuthHeaders(): HttpHeaders {
    return new HttpHeaders({ Authorization: `Bearer ${this.authService.getToken() ?? ''}` });
  }

  list(): Observable<Produto[]> {
    return this.http.get<Produto[]>(this.apiUrl, { headers: this.getAuthHeaders() });
  }

  create(produto: Produto): Observable<Produto> {
    return this.http.post<Produto>(this.apiUrl, produto, { headers: this.getAuthHeaders() });
  }
}
