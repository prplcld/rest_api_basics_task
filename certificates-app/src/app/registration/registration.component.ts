import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { Router } from '@angular/router';
import { AuthService } from '../shared/services/auth.service';

@Component({
  selector: 'app-registration',
  templateUrl: './registration.component.html',
  styleUrls: ['./registration.component.css']
})
export class RegistrationComponent implements OnInit {

  public form!: FormGroup;

  constructor(private fb : FormBuilder, private router : Router, private authService : AuthService) { }

  ngOnInit(): void {
    this.form = this.fb.group({
      username: [null, Validators.compose([Validators.required])],
      email: [null, Validators.compose([Validators.required])],
      password: [null, Validators.compose([Validators.required])],
      confirmPassword: [null, Validators.compose([Validators.required])]
    });
  }

  submit() {
    this.authService.register(this.form.get("username")?.value, this.form.get("password")?.value, this.form.get("email")?.value);
    this.router.navigate(['login']);
  }  

  validatePasswords() : boolean {
    return this.form.get("password")?.value == this.form.get("confirmPassword")?.value
  }
}
