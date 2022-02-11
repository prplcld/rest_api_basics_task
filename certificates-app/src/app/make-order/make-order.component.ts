import { Component, OnInit } from '@angular/core';
import { FormArray, FormBuilder, FormGroup, Validators } from '@angular/forms';
import { Router } from '@angular/router';
import { map } from 'rxjs';
import { GiftCertificate } from '../shared/models/gift-certificate';
import { Order } from '../shared/models/order';
import { GiftCertificateService } from '../shared/services/gift-certificate.service';
import { OrderService } from '../shared/services/order.service';

@Component({
  selector: 'app-make-order',
  templateUrl: './make-order.component.html',
  styleUrls: ['./make-order.component.css']
})
export class MakeOrderComponent implements OnInit {

  public certificates !: GiftCertificate[];
  public certificateIds : Array<number> = [];
  public chosenCertificates : GiftCertificate[] = [];
  public form!: FormGroup;
  public certificatesFormArray !: FormArray;
  public cost : number = 0;
  public order !: Order;
  public userId !: number;

  constructor(private giftCertificateService : GiftCertificateService, private fb : FormBuilder, private orderService : OrderService, private router: Router) { }

  ngOnInit(): void {
    this.getCertificates();
    this.form = this.fb.group({
      userId: [null, Validators.compose([Validators.required])]
    });
  }



  public getCertificates() : void {
    this.giftCertificateService.getCertificates(1, 50, new Map()).subscribe((data : GiftCertificate[]) => this.certificates = data)
  }

  public addCertificateToOrder(certificate : GiftCertificate) {
    this.certificateIds.push(certificate.id);
    this.chosenCertificates.push(certificate);
    this.cost += certificate.price;
  }

  submit() {
    this.certificateIds = this.chosenCertificates.map((c : GiftCertificate) => {return c.id});
    this.userId = this.form.get('userId')?.value;
    this.order = new Order(NaN, this.userId, this.certificateIds, this.cost, new Date);
    this.orderService.addOrder(this.order).subscribe();
    this.router.navigate(['']);
  }
}
