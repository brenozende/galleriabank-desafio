import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Injectable, inject } from '@angular/core';
import { Observable } from 'rxjs';
import { environment } from '../../../environments/environment';
import { Pedido } from '../models/pedido';
import { AuthService } from './auth.service';

@Injectable({ providedIn: 'root' })
export class PedidoService {
  private readonly apiUrl = `${environment.apiUrl}/pedidos`;
  private readonly http = inject(HttpClient);
  private readonly authService = inject(AuthService);

  private getAuthHeaders(): HttpHeaders {
    return new HttpHeaders({ Authorization: `Bearer ${this.authService.getToken() ?? ''}` });
  }

  list(): Observable<Pedido[]> {
    return this.http.get<Pedido[]>(this.apiUrl, { headers: this.getAuthHeaders() });
  }

  create(pedido: Pedido): Observable<Pedido> {
    return this.http.post<Pedido>(this.apiUrl, pedido, { headers: this.getAuthHeaders() });
  }

  getById(id: number): Observable<Pedido> {
    return this.http.get<Pedido>(`${this.apiUrl}/${id}`, { headers: this.getAuthHeaders() });
  }
}
