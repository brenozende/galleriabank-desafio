import { CommonModule } from '@angular/common';
import { Component, OnInit, inject } from '@angular/core';
import { FormsModule, FormBuilder, ReactiveFormsModule, Validators } from '@angular/forms';
import { MatButtonModule } from '@angular/material/button';
import { MatCardModule } from '@angular/material/card';
import { MatFormFieldModule } from '@angular/material/form-field';
import { MatInputModule } from '@angular/material/input';
import { MatListModule } from '@angular/material/list';
import { MatSelectModule } from '@angular/material/select';
import { Router } from '@angular/router';
import { Cliente } from '../../core/models/cliente';
import { Pedido } from '../../core/models/pedido';
import { Produto } from '../../core/models/produto';
import { Usuario } from '../../core/models/usuario';
import { ClienteService } from '../../core/services/cliente.service';
import { PedidoService } from '../../core/services/pedido.service';
import { ProdutoService } from '../../core/services/produto.service';
import { UsuarioService } from '../../core/services/usuario.service';
import { AuthService } from '../../core/services/auth.service';

@Component({
  selector: 'app-dashboard',
  standalone: true,
  imports: [CommonModule, FormsModule, ReactiveFormsModule, MatCardModule, MatButtonModule, MatFormFieldModule, MatInputModule, MatListModule, MatSelectModule],
  templateUrl: './dashboard.html',
  styleUrl: './dashboard.css',
})
export class Dashboard implements OnInit {
  private readonly fb = inject(FormBuilder);
  private readonly usuarioService = inject(UsuarioService);
  private readonly clienteService = inject(ClienteService);
  private readonly produtoService = inject(ProdutoService);
  private readonly pedidoService = inject(PedidoService);
  private readonly authService = inject(AuthService);
  private readonly router = inject(Router);

  clients: Cliente[] = [];
  products: Produto[] = [];
  orders: Array<Pedido & { clientName?: string; productNames?: string[]; itemDetails?: string[] }> = [];
  orderIdsInput = '';

  userForm = this.fb.group({
    name: ['', Validators.required],
    username: ['', Validators.required],
    password: ['', Validators.required],
  });

  clientForm = this.fb.group({
    name: ['', Validators.required],
    cpf: ['', Validators.required],
    phoneNumber: ['', Validators.required],
  });

  productForm = this.fb.group({
    description: ['', Validators.required],
    value: [0, [Validators.required, Validators.min(0)]],
  });

  orderForm = this.fb.group({
    clientId: [null as number | null, Validators.required],
    products: [[] as number[], Validators.required],
    description: [''],
  });

  ngOnInit(): void {
    this.loadClients();
    this.loadProducts();
  }

  createUser(): void {
    if (this.userForm.invalid) {
      this.userForm.markAllAsTouched();
      return;
    }

    const payload: Usuario = this.userForm.getRawValue() as Usuario;
    this.usuarioService.create(payload).subscribe({
      next: () => {
        this.userForm.reset();
        alert('Usuário cadastrado com sucesso');
      },
      error: () => alert('Não foi possível cadastrar o usuário'),
    });
  }

  createClient(): void {
    if (this.clientForm.invalid) {
      this.clientForm.markAllAsTouched();
      return;
    }

    const payload: Cliente = this.clientForm.getRawValue() as Cliente;
    this.clienteService.create(payload).subscribe({
      next: () => {
        this.clientForm.reset();
        this.loadClients();
        alert('Cliente cadastrado com sucesso');
      },
      error: () => alert('Não foi possível cadastrar o cliente'),
    });
  }

  createProduct(): void {
    if (this.productForm.invalid) {
      this.productForm.markAllAsTouched();
      return;
    }

    const payload: Produto = {
      description: this.productForm.value.description ?? '',
      value: Number(this.productForm.value.value ?? 0),
    };

    this.produtoService.create(payload).subscribe({
      next: () => {
        this.productForm.reset({ description: '', value: 0 });
        this.loadProducts();
        alert('Produto cadastrado com sucesso');
      },
      error: () => alert('Não foi possível cadastrar o produto'),
    });
  }

  createOrder(): void {
    if (this.orderForm.invalid) {
      this.orderForm.markAllAsTouched();
      return;
    }

    const payload: Pedido = {
      clientId: Number(this.orderForm.value.clientId),
      products: (this.orderForm.value.products ?? []).map((id) => Number(id)),
      description: this.orderForm.value.description ?? '',
    };

    this.pedidoService.create(payload).subscribe({
      next: (pedidoCriado) => {
        this.orderForm.reset({ clientId: null, products: [], description: '' });
        this.addOrderById(pedidoCriado.id);
        alert('Pedido registrado com sucesso');
      },
      error: () => alert('Não foi possível registrar o pedido'),
    });
  }

  private loadClients(): void {
    this.clienteService.list().subscribe({
      next: (clients) => (this.clients = clients),
      error: () => this.clients = [],
    });
  }

  private loadProducts(): void {
    this.produtoService.list().subscribe({
      next: (products) => (this.products = products),
      error: () => this.products = [],
    });
  }

  loadOrdersByIds(): void {
    const ids = this.orderIdsInput
      .split(',')
      .map((value) => Number(value.trim()))
      .filter((value) => Number.isFinite(value) && value > 0);

    this.orders = [];

    if (ids.length === 0) {
      return;
    }

    ids.forEach((id) => this.addOrderById(id));
    queueMicrotask(() => {
      this.orders = [...this.orders];
    });
  }

  logout(): void {
    this.authService.logout();
    this.router.navigate(['/login']);
  }

  private addOrderById(id?: number): void {
    if (!id) {
      return;
    }

    this.pedidoService.getById(id).subscribe({
      next: (pedido) => {
        const client = this.clients.find((item) => item.id === pedido.clientId);
        const productNames = (pedido.items ?? [])
          .map((item) => item.product?.description)
          .filter((name): name is string => !!name);
        const itemDetails = (pedido.items ?? [])
          .map((item) => {
            const description = item.product?.description ?? 'Produto sem descrição';
            const value = item.product?.value ?? 0;
            return `${description} - R$ ${value.toFixed(2)}`;
          })
          .filter((detail) => !!detail);

        this.orders = [
          ...this.orders.filter((item) => item.id !== pedido.id),
          {
            ...pedido,
            clientName: client?.name,
            productNames,
            itemDetails,
          },
        ].sort((a, b) => (a.id ?? 0) - (b.id ?? 0));
      },
      error: () => {
        this.orders = this.orders.filter((item) => item.id !== id);
      },
    });
  }
}
