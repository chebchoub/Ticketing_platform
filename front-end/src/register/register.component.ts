import { Component } from '@angular/core';
import { Router } from '@angular/router';
import { User } from 'src/app/user';
import { UserServiceService } from 'src/app/user-service.service';

@Component({
  selector: 'app-register',
  templateUrl: './register.component.html',
  styleUrls: ['./register.component.css']
})
export class RegisterComponent {
  constructor(private userService: UserServiceService, private router: Router) { }
  nom: string = '';
  prenom: string = '';
  email: string = '';
  password: string = '';
  confirmPassword: string = '';

  register() {
    const nameRegex = /^[A-Za-z]+$/; // Expression régulière pour vérifier les lettres alphabétiques
    const emailRegex = /^[a-z0-9._%+-]+@[a-z0-9.-]+\.[a-z]{2,}$/; // Expression régulière pour vérifier le format de l'email

    if (!this.nom.match(nameRegex) || !this.prenom.match(nameRegex)) {
      alert("Le nom et le prénom ne doivent contenir que des lettres.");
      return; // Arrêter le processus d'inscription si le nom ou le prénom n'est pas valide
    }

    if (!this.email.match(emailRegex)) {
      alert("Veuillez entrer une adresse email valide.");
      return; // Arrêter le processus d'inscription si l'email n'est pas valide
    }

    const newUser: User = {
      nom: this.nom,
      prenom: this.prenom,
      email: this.email,
      password: this.password
    };
    

    if (!this.nom || !this.prenom || !this.email || !this.password || !this.confirmPassword) {
      alert("Veuillez remplir tous les champs.");
    } else if (this.password !== this.confirmPassword) {
      alert("Les mots de passe ne correspondent pas.");
    } else {
      this.userService.register(newUser).subscribe(
        (response) => {
          console.log(response);
          this.router.navigate(['/login']);
        },
        (error) => {
          if (error.status === 409) {
            alert("L'utilisateur existe déjà.");
          } else {
            // Gérer d'autres erreurs ici
          }
        }
      );
    }
  }
}
