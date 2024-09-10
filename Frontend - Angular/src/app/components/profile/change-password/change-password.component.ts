import { Component } from '@angular/core';
import { DefaultButtonComponent } from '../../default-button/default-button.component';
import { FormsModule } from '@angular/forms';
import { UserService } from '../../../services/user.service';

@Component({
	selector: 'app-change-password',
	standalone: true,
	imports: [DefaultButtonComponent, FormsModule],
	templateUrl: './change-password.component.html',
	styleUrl: './change-password.component.css',
})
export class ChangePasswordComponent {
	newPassword: string = '';
	confirmPassword: string = '';

	constructor(private userService: UserService) {}

	changePassword() {
		this.userService.changePassword(this.newPassword, this.confirmPassword);
	}
}
