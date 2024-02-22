import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { User } from 'src/app/user';
import { UserServiceService } from 'src/app/user-service.service';

@Component({
  selector: 'app-register',
  templateUrl: './register.component.html',
  styleUrls: ['./register.component.css']
})
export class RegisterComponent implements OnInit{
  constructor(private userService: UserServiceService, private router: Router) { }
  ngOnInit(): void {
    throw new Error('Method not implemented.');
    console.log(this.userService.baseUrl)
  }
  nom: string = '';
  prenom: string = '';
  email: string = '';
  password: string = '';
  confirmPassword: string = '';
  showPassword: boolean = false; // Nouvelle variable pour contrôler l'affichage du mot de passe


  register() {
    const nameRegex = /^[A-Za-z]+$/;
    const emailRegex = /^[a-z0-9._%+-]+@[a-z0-9.-]+\.[a-z]{2,}$/;
    const passwordRegex = /^(?=.*[a-z])(?=.*[A-Z])(?=.*\d)(?=.*[@$!%*?&])[A-Za-z\d@$!%*?&]{8,}$/; // Au moins 8 caractères, une majuscule, une minuscule, un chiffre, un caractère spécial
    const commonPasswords = ['password', '123456', 'qwerty']; // Liste de mots de passe courants à éviter
  
    if (!this.nom.match(nameRegex) || !this.prenom.match(nameRegex)) {
      alert("Le nom et le prénom ne doivent contenir que des lettres.");
      return;
    }
  
    if (!this.email.match(emailRegex)) {
      alert("Veuillez entrer une adresse email valide.");
      return;
    }
  
    if (!passwordRegex.test(this.password)) {
      alert("Le mot de passe doit contenir au moins 8 caractères, une majuscule, une minuscule, un chiffre et un caractère spécial.");
      return;
    }
  
    if (commonPasswords.includes(this.password.toLowerCase())) {
      alert("Veuillez choisir un mot de passe plus fort.");
      return;
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
