import { Component } from '@angular/core';
import { DefaultButtonComponent } from '../../default-button/default-button.component';
import { FormsModule } from '@angular/forms';
import { UserService } from '../../../services/user.service';
import { UserAuthService } from '../../../services/user-auth.service';
import { CommonModule } from '@angular/common';

@Component({
	selector: 'app-profile-photo',
	standalone: true,
	imports: [DefaultButtonComponent, FormsModule, CommonModule],
	templateUrl: './profile-photo.component.html',
	styleUrl: './profile-photo.component.css',
})
export class ProfilePhotoComponent {
	newPhoto: string = '';
	user: any = '';

	constructor(
		private userService: UserService,
		private authService: UserAuthService
	) {
		this.user = this.authService.user;
	}
	changePhoto(): void {
		this.userService.changePic(this.newPhoto);
		this.authService.updateUser();
	}
}
