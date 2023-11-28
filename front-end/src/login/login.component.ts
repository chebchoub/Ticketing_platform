import { Component } from '@angular/core';
import { Router } from '@angular/router';
import { AuthenticationRequest } from 'src/app/authentication-request';
import { AuthenticationResponse } from 'src/app/authentication-response';
import { UserServiceService } from 'src/app/user-service.service';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css']
})
export class LoginComponent {
  constructor(private userService: UserServiceService, private router: Router) { }

  email: string = '';
  password: string = '';
  loginUser() {

    const userCredentials: AuthenticationRequest = {
      email: this.email,
      password: this.password
    };
    if (!this.email || !this.password)
    {
      alert("Veuillez remplir tous les champs.");

    } else
    {
      this.userService.login(userCredentials).subscribe(
        (response: AuthenticationResponse) => {
          localStorage.setItem('token', response.token);
        },
        (error) => {
          if (error.status === 403) {

            alert("verifie le mail ou le mdp");
          } else {
            
          }
        }

      );
  }
}
}
