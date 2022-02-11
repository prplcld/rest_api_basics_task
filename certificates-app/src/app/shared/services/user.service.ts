import { Injectable } from "@angular/core";
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { environment } from "src/environments/environment";
import { User } from "../models/user";
import { Observable } from "rxjs";

@Injectable({providedIn : 'root'}) export class UserService {
    private apiServerUrl = environment.apiBaseUrl;

    constructor(private http: HttpClient) {}
    

    public getUsers(page : number, amount : number) : Observable<User[]> {
        return this.http.get<User[]>(`${this.apiServerUrl}/users?page=${page}&amount=${amount}`);   
    }

    public getUser(id : number) : Observable<User> {
        return this.http.get<User>(`${this.apiServerUrl}/users/${id}`);
    }
}