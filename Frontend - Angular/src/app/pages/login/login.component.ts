import { Component } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { UserAuthService } from '../../services/user-auth.service';
import { HeaderComponent } from '../../components/header/header.component';
import { FooterComponent } from '../../components/footer/footer.component';
import Swal from 'sweetalert2';

@Component({
	selector: 'app-login',
	standalone: true,
	imports: [FormsModule, HeaderComponent, FooterComponent],
	templateUrl: './login.component.html',
	styleUrl: './login.component.css',
})
export class LoginComponent {
	username: string = '';
	password: string = '';

	constructor(private userService: UserAuthService) {}

	login() {
		this.userService.login(this.username, this.password);
	}
}
