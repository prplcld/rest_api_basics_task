import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup } from '@angular/forms';
import { ActivatedRoute, Router } from '@angular/router';
import { CertificatesListComponent } from '../certificates-list/certificates-list.component';
import { AuthService } from '../shared/services/auth.service';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css']
})
export class LoginComponent implements OnInit {

  public form: any = {
    username: null,
    password: null
  };
  isLoggedIn = false;
  isLoginFailed = false;
  errorMessage = '';
  roles: string[] = [];

  constructor(private formBuilder: FormBuilder,
    private route: ActivatedRoute,
    private router: Router, 
    private authService : AuthService) { }

  ngOnInit(): void {
  }

  onSubmit(): void {
    const { username, password } = this.form;
    this.authService.login(username, password);
    this.router.navigate(['']);
  }
}
