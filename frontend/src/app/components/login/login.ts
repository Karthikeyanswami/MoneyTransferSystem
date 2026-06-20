import { Component } from '@angular/core';
import { FormBuilder, Validators } from '@angular/forms';
import { Router } from '@angular/router';
import { AuthService } from '../../core/services/auth';


@Component({
  selector: 'app-login',
  standalone: false,
  templateUrl: './login.html',
  styleUrl: './login.css',
})
export class Login {
  loginForm;
  errorMsg = '';


	isLoggedin = false;
	error: string = '';
  data : any = {};

  constructor(private fb: FormBuilder, private router: Router, private authService : AuthService) {
    this.loginForm = this.fb.group({
      username: ['', [Validators.required]],
      password: ['', [Validators.required]]
    })
  };


  onLogin() {
    if (this.loginForm.invalid) {
      this.errorMsg = 'Please fill all fields';
      return;
    }
    
    const username = this.loginForm.get('username')!.value as string;
    const password = this.loginForm.get('password')!.value as string;

    if(username !== '' && username !== null 
        && password !== '' && password !== null) {
          console.log(username,password);
          this.authService.authenticate(username,password)
            .subscribe(data=>{
              this.data = data;
              console.log("after login : "+this.data);
              this.router.navigate(['/dashboard', username]);
        }); 
        } else {
          console.log("invalid");
          alert("Invalid creditials!!!");
        }

    
  }
}