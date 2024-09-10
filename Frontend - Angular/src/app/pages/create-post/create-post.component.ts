import { Component, Input } from '@angular/core';
import { HeaderComponent } from '../../components/header/header.component';
import { FooterComponent } from '../../components/footer/footer.component';
import { PostService } from '../../services/post.service';
import { FormsModule, NgForm } from '@angular/forms';

@Component({
	selector: 'app-create-post',
	standalone: true,
	imports: [HeaderComponent, FooterComponent, FormsModule],
	templateUrl: './create-post.component.html',
	styleUrl: './create-post.component.css',
})
export class CreatePostComponent {
	@Input()
	title: string = '';
	@Input()
	urlImg: string = '';
	@Input()
	content: string = '';
	constructor(private postService: PostService) {}

	createPost(form: NgForm): void {
		this.postService.createPost(form);
	}
}
