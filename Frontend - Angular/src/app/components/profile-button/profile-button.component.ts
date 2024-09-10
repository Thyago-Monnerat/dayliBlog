import { CommonModule } from '@angular/common';
import { Component, OnInit } from '@angular/core';
import { RouterLink } from '@angular/router';
import { jwtDecode } from 'jwt-decode';
import { UserAuthService } from '../../services/user-auth.service';

@Component({
	selector: 'app-profile-button',
	standalone: true,
	imports: [CommonModule, RouterLink],
	templateUrl: './profile-button.component.html',
	styleUrl: './profile-button.component.css',
})
export class ProfileButtonComponent implements OnInit {
	profilePic: string = '';

	constructor(private userAuth: UserAuthService) {}

	ngOnInit(): void {
		this.profilePic = this.userAuth.user.profilePic;
	}
}
