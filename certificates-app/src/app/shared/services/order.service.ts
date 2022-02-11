import { Injectable } from "@angular/core";
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { environment } from "src/environments/environment";
import { map, Observable } from "rxjs";
import { Order } from "../models/order";

@Injectable({providedIn : 'root'}) export class OrderService {
    private apiServerUrl = environment.apiBaseUrl;

    constructor(private http: HttpClient) {}
    

    public getOrders(page : number, amount : number, userId : number) : Observable<Order[]> {
        return this.http.get<Order[]>(`${this.apiServerUrl}/orders?page=${page}&amount=${amount}`).pipe(map((data:any) => {
            let orderList = data["orders"];
            return orderList.map(function(o: any): Order {
                return new Order(o.id, o.userId, o.certificateIds, o.cost, o.purchaseDate);
            });
          }));
    }

    public getOrder(id : number) : Observable<Order> {
        return this.http.get<Order>(`${this.apiServerUrl}/orders/${id}`);
    }
    
    public addOrder(order : Order) : Observable<Order> {
        return this.http.post<Order>(`${this.apiServerUrl}/orders`, order);
    }
}
