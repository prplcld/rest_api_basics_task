import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { GiftCertificate } from '../shared/models/gift-certificate';
import { GiftCertificateService } from '../shared/services/gift-certificate.service';
import { CommonModule } from "@angular/common";

@Component({
  selector: 'app-certificate-info',
  templateUrl: './certificate-info.component.html',
  styleUrls: ['./certificate-info.component.css']
})
export class CertificateInfoComponent implements OnInit {

  public certificate !: GiftCertificate;
  public id !: number;

  constructor(private giftCertificateService : GiftCertificateService, private route : ActivatedRoute) { }

  ngOnInit(): void {
    this.route.params.subscribe(params => {
      this.id = params["id"];
  });

  this.getCertificate(this.id);
  }

  public getCertificate(id : number) : void {
    this.giftCertificateService.getCertificate(id).subscribe((data : GiftCertificate) => this.certificate = data)
  }
}
