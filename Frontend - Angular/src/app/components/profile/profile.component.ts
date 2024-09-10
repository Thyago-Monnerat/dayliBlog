import { CommonModule } from '@angular/common';
import { Component, OnInit } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { DefaultButtonComponent } from '../default-button/default-button.component';
import { UserService } from '../../services/user.service';
import { UserAuthService } from '../../services/user-auth.service';
import 'sweetalert2/src/sweetalert2.scss';
import { MyPostsComponent } from '../my-posts/my-posts.component';
import { ProfilePhotoComponent } from './profile-photo/profile-photo.component';
import { ChangePasswordComponent } from "./change-password/change-password.component";

@Component({
	selector: 'app-profile',
	standalone: true,
	imports: [
    CommonModule,
    FormsModule,
    DefaultButtonComponent,
    MyPostsComponent,
    ProfilePhotoComponent,
    ChangePasswordComponent
],
	templateUrl: './profile.component.html',
	styleUrls: [
		'./profile.component.css',
		'../../pages/login/login.component.css',
	],
})
export class ProfileComponent implements OnInit {
	newPassword: string = '';
	confirmPassword: string = '';
	resMessage: string = '';
	user: any;
	newPic: string = '';

	constructor(
		private userService: UserService,
		private authService: UserAuthService
	) {}

	ngOnInit(): void {
		this.user = this.authService.user;
	}

	deleteMyAccount() {
		this.userService.deleteMyAccount();
	}

	logout() {
		this.authService.logout();
	}
}
