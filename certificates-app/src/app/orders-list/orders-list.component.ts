import { Component, OnInit } from '@angular/core';
import { PageEvent } from '@angular/material/paginator';
import { Order } from '../shared/models/order';
import { OrderService } from '../shared/services/order.service';

@Component({
  selector: 'app-orders-list',
  templateUrl: './orders-list.component.html',
  styleUrls: ['./orders-list.component.css']
})
export class OrdersListComponent implements OnInit {

  public orders !: Order[]

  constructor(private orderService : OrderService) { }

  ngOnInit(): void {
    this.orderService.getOrders(1, 8, 0).subscribe((data : Order[]) => this.orders = data);
  }

  public OnPageChange(event : PageEvent) {
    this.orderService.getOrders(event.pageIndex + 1, event.pageSize, 0).subscribe((data : Order[]) => this.orders = data);
  }
}
