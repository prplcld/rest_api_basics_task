import { Injectable } from '@angular/core';
import {HttpClient, HttpHeaders } from '@angular/common/http';
import { environment } from 'src/environments/environment';
import { map, Observable, throwError } from 'rxjs';
import { tokenize } from '@angular/compiler/src/ml_parser/lexer';

@Injectable({
  providedIn: 'root'
}) export class AuthService {

    private apiServerUrl = environment.apiBaseUrl;
  
    constructor(private http: HttpClient) { }

    public login(username: string, password: string)  {

        let body = new URLSearchParams();
        body.set("username", username);
        body.set("password", password);

        let options = {
            headers: new HttpHeaders().set('Content-Type', 'application/x-www-form-urlencoded')
        };
        
        this.http.post<any>(`${this.apiServerUrl}/login`, body.toString(), options)
            .subscribe(
                {
                    next: data => {
                        localStorage.removeItem("token")
                        localStorage.setItem("token", data["access-token"])
                    },
                    error : error => {
                        throwError(() => error)
                    }
                }
            )
    }

    public isLoggedIn() : boolean {
        const value = localStorage.getItem('token');
        return value != null;
    }

    public logOut() {
        localStorage.removeItem('token');
    }

    public register(username: string, password: string, email : string) {
        let body = new URLSearchParams();
        body.set("username", username);
        body.set("password", password);
        body.set("email", email);
        this.http.post<any>(`${this.apiServerUrl}/registration`, body.toString()).subscribe();
    }
}