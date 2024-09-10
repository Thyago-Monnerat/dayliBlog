import { isPlatformBrowser } from '@angular/common';
import { Inject, Injectable, PLATFORM_ID } from '@angular/core';
import { NavigationEnd, Router } from '@angular/router';
import { jwtDecode } from 'jwt-decode';
import Swal from 'sweetalert2';

@Injectable({
	providedIn: 'root',
})
export class UserAuthService {
	user: any = '';
	token: string | null = '';
	fetchStatus: boolean = false;

	constructor(
		@Inject(PLATFORM_ID) private platformId: Object,
		private router: Router
	) {
		if (isPlatformBrowser(this.platformId)) {
			this.token = localStorage.getItem('token');
			if (this.token) {
				this.user = jwtDecode(this.token);
			}
		}
	}

	login(loginUsername: string, loginPassword: string) {
		fetch('http://localhost:8080/auth/login', {
			method: 'POST',
			headers: {
				'Content-Type': 'application/json',
			},
			body: JSON.stringify({
				username: loginUsername,
				password: loginPassword,
			}),
		})
			.then((data) => {
				this.fetchStatus = data.ok;
				return data.text();
			})
			.then((message) => {
				if (this.fetchStatus) {
					localStorage.setItem('token', message);
					this.router.navigate(['']).then(() => {
						location.reload();
					});
				} else {
					Swal.fire({
						title: 'Ops!',
						text: message,
					});
				}
			});
	}

	register(usernameRegister: string, passwordRegister: string) {
		fetch('http://localhost:8080/auth/register', {
			method: 'POST',
			headers: {
				'Content-Type': 'application/json',
			},
			body: JSON.stringify({
				username: usernameRegister,
				password: passwordRegister,
			}),
		})
			.then((data) => {
				this.fetchStatus = data.ok;
				return data.text();
			})
			.then((message) => {
				if (this.fetchStatus) {
					Swal.fire({
						text: 'Registrado com sucesso!',
					}).then(() => {
						this.login(usernameRegister, passwordRegister);
					});
				} else {
					Swal.fire({
						title: 'Ops!',
						text: message,
					});
				}
			});
	}

	checkAuth(): boolean {
		if (this.token) {
			const json = jwtDecode(this.token);
			const exp = json.exp;
			const currentTimesamp = Math.floor(Date.now() / 1000);
			if (exp && currentTimesamp > exp) {
				return false;
			}

			return true;
		}
		return false;
	}

	updateUser(): void {
		if (this.token) {
			localStorage.getItem('token');
			this.user = jwtDecode(this.token);
		}
	}

	logout() {
		Swal.fire({
			text: 'Deseja mesmo sair?',
			confirmButtonText: 'Sim',
			showCancelButton: true,
			cancelButtonText: 'Não',
		}).then((res) => {
			if (res.isConfirmed) {
				localStorage.clear();
				this.router.navigate(['']).then(() => {
					location.reload();
				});
			}
		});
	}
}
