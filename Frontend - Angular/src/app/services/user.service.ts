import { Injectable } from '@angular/core';
import { Router } from '@angular/router';
import Swal from 'sweetalert2';

@Injectable({
	providedIn: 'root',
})
export class UserService {
	statusHttp: boolean = false;
	constructor(private router: Router) {}

	async changePic(newPic: string) {
		fetch('http://localhost:8080/user/changeMyPic', {
			method: 'PUT',
			headers: {
				'Content-Type': 'application/json',
				Authorization: 'Bearer ' + localStorage.getItem('token'),
			},
			body: JSON.stringify({
				newPic: newPic,
			}),
		})
			.then((data) => {
				return data.text();
			})
			.then((data) => {
				Swal.fire({
					text: 'Deseja mesmo alterar sua foto?',
					confirmButtonText: 'Sim',
					showCancelButton: true,
					cancelButtonText: 'Não',
				}).then((res) => {
					if (res.isConfirmed) {
						localStorage.setItem('token', data);
						location.reload();
					}
				});
			});
	}

	changePassword(newPassword: string, confirmPassword: string) {
		Swal.fire({
			text: 'Deseja mesmo mudar sua senha?',
			confirmButtonText: 'Sim',
			showCancelButton: true,
			cancelButtonText: 'Não',
		}).then((res) => {
			if (res.isConfirmed) {
				fetch('http://localhost:8080/user/changeMyPassword', {
					method: 'PUT',
					headers: {
						'Content-Type': 'application/json',
						Authorization: 'Bearer ' + localStorage.getItem('token'),
					},
					body: JSON.stringify({
						newPassword: newPassword,
						confirmPassword: confirmPassword,
					}),
				})
					.then((data) => {
						return data.text();
					})
					.then((message) => {
						Swal.fire({
							text: message,
						}).then(() => {
							location.reload();
						});
					});
			}
		});
	}

	deleteMyAccount() {
		Swal.fire({
			title: 'Tem certeza que deseja apagar sua conta?',
			text: 'Essa ação não poderá ser desfeita',
			showCancelButton: true,
			cancelButtonText: 'Não',
			confirmButtonText: 'Sim',
			customClass: {
				confirmButton: 'btn-confirm',
			},
		}).then((res) => {
			if (res.isConfirmed) {
				fetch('http://localhost:8080/user/deleteMyAccount', {
					method: 'DELETE',
					headers: {
						'Content-Type': 'application/json',
						Authorization: 'Bearer ' + localStorage.getItem('token'),
					},
				})
					.then((status) => {
						this.statusHttp = status.ok;
						return status.text();
					})
					.then((message) => {
						if (this.statusHttp) {
							localStorage.clear();
							Swal.fire({ text: message }).then(() => {
								this.router.navigate(['']).then(() => {
									location.reload();
								});
							});
						} else {
							Swal.fire({
								text: message,
							});
						}
					});
			}
		});
	}
}
