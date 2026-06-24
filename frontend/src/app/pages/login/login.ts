import { Component, inject } from '@angular/core';
import { FormBuilder, ReactiveFormsModule, Validators } from '@angular/forms';
import { MatButtonModule } from '@angular/material/button';
import { MatCardModule } from '@angular/material/card';
import { MatFormFieldModule } from '@angular/material/form-field';
import { MatInputModule } from '@angular/material/input';
import { Router } from '@angular/router';

import { AuthService } from '../../core/services/auth.service';
import { UsuarioService } from '../../core/services/usuario.service';

@Component({
  selector: 'app-login',
  imports: [ReactiveFormsModule, MatCardModule, MatButtonModule, MatFormFieldModule, MatInputModule],
  templateUrl: './login.html',
  styleUrl: './login.css',
  standalone: true,
})
export class Login {
  private readonly fb = inject(FormBuilder);
  private readonly authService = inject(AuthService);
  private readonly usuarioService = inject(UsuarioService);
  private readonly router = inject(Router);

  form = this.fb.group({
    username: ['', Validators.required],
    password: ['', Validators.required],
  });

  registerForm = this.fb.group({
    name: ['', Validators.required],
    username: ['', Validators.required],
    password: ['', Validators.required],
  });

  login(): void {
    if (this.form.invalid) {
      this.form.markAllAsTouched();
      return;
    }

    const credentials = this.form.getRawValue();
    const loginRequest = {
      username: credentials.username ?? '',
      password: credentials.password ?? '',
    };

    this.authService.login(loginRequest).subscribe({
      next: () => {
        this.router.navigate(['/dashboard']);
      },
      error: (err) => {
        console.error('Erro no login:', err);
        console.error('Status:', err?.status);
        console.error('Mensagem:', err?.message);
        console.error('URL:', err?.url);
        alert('Usuário ou senha inválidos');
      },
    });
  }

  register(): void {
    if (this.registerForm.invalid) {
      this.registerForm.markAllAsTouched();
      return;
    }

    const payload = this.registerForm.getRawValue();
    this.usuarioService.create({
      name: payload.name ?? '',
      username: payload.username ?? '',
      password: payload.password ?? '',
    }).subscribe({
      next: () => {
        this.authService.login({
          username: payload.username ?? '',
          password: payload.password ?? '',
        }).subscribe({
          next: () => this.router.navigate(['/dashboard']),
          error: () => alert('Usuário criado, mas não foi possível fazer login automaticamente.'),
        });
      },
      error: () => alert('Não foi possível criar o usuário.'),
    });
  }
}
