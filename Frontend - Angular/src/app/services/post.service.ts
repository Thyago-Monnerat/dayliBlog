import { isPlatformBrowser } from '@angular/common';
import { Inject, Injectable, PLATFORM_ID } from '@angular/core';
import { NgForm } from '@angular/forms';
import { Router } from 'express';
import { jwtDecode } from 'jwt-decode';
import Swal from 'sweetalert2';

@Injectable({
	providedIn: 'root',
})
export class PostService {
	token: string | null = '';

	constructor() {}

	createPost(form: NgForm): void {
		const formFetch = form.value;
		fetch('http://localhost:8080/post/create', {
			method: 'POST',
			headers: {
				'Content-Type': 'application/json',
				Authorization: 'Bearer ' + localStorage.getItem('token'),
			},
			body: JSON.stringify({
				title: formFetch.title,
				imgUrl: formFetch.urlImg,
				content: formFetch.content,
			}),
		})
			.then((data) => {
				return data.text();
			})
			.then((message) => {
				Swal.fire({
					text: message,
				});
			});
	}

	deletePost(id: string) {
		Swal.fire({
			text: 'Deseja mesmo apagar seu post? essa ação não poderá ser desfeita',
			confirmButtonText: 'Sim',
			confirmButtonColor: 'red',
			showCancelButton: true,
			cancelButtonText: 'Não',
		}).then((res) => {
			if (res.isConfirmed) {
				fetch(`http://localhost:8080/post/delete/${id}`, {
					method: 'DELETE',
					headers: {
						'Content-Type': 'application/json',
						Authorization: 'Bearer ' + localStorage.getItem('token'),
					},
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
}
