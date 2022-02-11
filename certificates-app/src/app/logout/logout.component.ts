import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { AuthService } from '../shared/services/auth.service';

@Component({
  selector: 'app-logout',
  templateUrl: './logout.component.html',
  styleUrls: ['./logout.component.css']
})
export class LogoutComponent implements OnInit {

  constructor(private authSevice : AuthService, private router: Router) { }

  ngOnInit(): void {
    this.authSevice.logOut();
    this.router.navigate(['']);
  }
}
