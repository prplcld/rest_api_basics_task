import { Component, OnInit } from '@angular/core';
import { FormArray, FormBuilder, FormControl, FormGroup, Validators } from '@angular/forms';
import { PageEvent } from '@angular/material/paginator';
import { from, map } from 'rxjs';
import { GiftCertificate } from '../shared/models/gift-certificate';
import { GiftCertificateService } from '../shared/services/gift-certificate.service';


@Component({
  selector: 'app-certificates-list',
  templateUrl: './certificates-list.component.html',
  styleUrls: ['./certificates-list.component.css']
})
export class CertificatesListComponent implements OnInit {

  public form!: FormGroup;
  public tagsList!: FormArray;
  public sortByDate = new FormControl();
  public sortByName = new FormControl();

  public certificates!: GiftCertificate[];
  public paramsMap = new Map();
  constructor(private giftCertificateService : GiftCertificateService, private fb : FormBuilder) { }

  ngOnInit(): void {
    this.form = this.fb.group({
      name: [null],
      description: [null],
      tags: this.fb.array([this.createTag()])
    });

    this.tagsList = this.form.get('tags') as FormArray;
    this.getCertificates();
  }

  createTag(): FormGroup {
    return this.fb.group({
      name: [null, Validators.compose([Validators.required])]
    });
  }

  addTag() {
    this.tagsList.push(this.createTag());
  }

  removeTag(index : number) {
    this.tagsList.removeAt(index);
  }

  getTagsFormGroup(index : number): FormGroup {
    const formGroup = this.tagsList.controls[index] as FormGroup;
    return formGroup;
  }

  get tagsFormGroup() {
    return this.form.get('tags') as FormArray;
  }

  submit() {
    this.paramsMap.clear;
    const name = this.form.get("name")?.value
    const description = this.form.get("description")?.value
    let tags = this.form.get("tags")?.value
    let sortByDate = this.sortByDate.value
    let sortByName = this.sortByName.value
    if(name) {
      this.paramsMap.set("name", name);
    }
    if(description){
      this.paramsMap.set("description", description)
    }
    if(tags.length != 0) {
      this.paramsMap.set("tags", tags)
    }
    if(sortByDate) {
      this.paramsMap.set("sortByDate", sortByDate)
    }
    if(sortByName) {
      this.paramsMap.set("sortByName", sortByName)
    }
    this.giftCertificateService.getCertificates(1, 8, this.paramsMap).subscribe((data : GiftCertificate[]) => this.certificates = data);
  }


  public getCertificates() : void {
    this.giftCertificateService.getCertificates(1, 8, this.paramsMap).subscribe((data : GiftCertificate[]) => this.certificates = data)
  }

  public OnPageChange(event : PageEvent) {
    this.giftCertificateService.getCertificates(event.pageIndex + 1, event.pageSize, this.paramsMap).subscribe((data : GiftCertificate[]) => this.certificates = data)
  }

  deleteCertificate(index : number) {
    this.giftCertificateService.deleteCertificate(index);
    window.location.reload();
  }
}
