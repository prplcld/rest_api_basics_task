import { Component, OnInit } from '@angular/core';
import { GiftCertificateService } from '../shared/services/gift-certificate.service';
import { FormArray, FormBuilder, FormGroup, Validators } from '@angular/forms';
import { GiftCertificate } from '../shared/models/gift-certificate';
import { Router } from '@angular/router';

@Component({
  selector: 'app-add-certificate',
  templateUrl: './add-certificate.component.html',
  styleUrls: ['./add-certificate.component.css']
})
export class AddCertificateComponent implements OnInit {

  public form!: FormGroup;
  public tagsList!: FormArray;
  giftCertificate !: GiftCertificate;
  id !: number

  constructor(private giftCertificateService : GiftCertificateService, private fb : FormBuilder, private router : Router) { }

  get tagsFormGroup() {
    return this.form.get('tags') as FormArray;
  }

  ngOnInit() {
    this.form = this.fb.group({
      name: [null, Validators.compose([Validators.required])],
      description: [null, Validators.compose([Validators.required])],
      price: [null, Validators.compose([Validators.required])],
      duration: [null, Validators.compose([Validators.required])],
      tags: this.fb.array([this.createTag()])
    });

    this.tagsList = this.form.get('tags') as FormArray;
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

  submit() {
    this.giftCertificate = this.form.value
    this.giftCertificateService.addCertificate(this.giftCertificate)
    .subscribe((data : GiftCertificate) => this.router.navigate([`certificate/${data.id}`]));
  }
}
