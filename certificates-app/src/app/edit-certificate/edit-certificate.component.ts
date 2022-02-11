import { Component, OnInit } from '@angular/core';
import { FormArray, FormBuilder, FormGroup, Validators } from '@angular/forms';
import { ActivatedRoute, Router } from '@angular/router';
import { GiftCertificate } from '../shared/models/gift-certificate';
import { GiftCertificateService } from '../shared/services/gift-certificate.service';

@Component({
  selector: 'app-edit-certificate',
  templateUrl: './edit-certificate.component.html',
  styleUrls: ['./edit-certificate.component.css']
})
export class EditCertificateComponent implements OnInit {

  public form !: FormGroup;
  public tagsList!: FormArray;
  public certificate !: GiftCertificate;
  public id !: number;

  constructor(private giftCertificateService : GiftCertificateService, private route : ActivatedRoute, private fb : FormBuilder, private router : Router) { }

  get tagsFormGroup() {
    return this.form.get('tags') as FormArray;
  }

   ngOnInit(){

    this.route.params.subscribe(params => {
      this.id = params["id"];
    });

     this.getCertificate(this.id);

    this.tagsList = this.form.get('tags') as FormArray;

    this.certificate.tags.forEach(t => {
      this.tagsList.push(this.createTagWithName(t.name));
    });
  }

  public getCertificate(id : number) : void {
    this.giftCertificateService.getCertificate(id)
    .subscribe((data : GiftCertificate) => {this.certificate = data;

      this.form = this.fb.group({
        name: [{value: this.certificate.name, disabled: false}, [Validators.compose([Validators.required])]],
        description: [{value: this.certificate.description, disabled: false}, Validators.compose([Validators.required])],
        price: [{value: this.certificate.price, disabled: false}, Validators.compose([Validators.required])],
        duration: [{value: this.certificate.duration, disabled: false}, Validators.compose([Validators.required])],
        tags: this.fb.array([])
      }); 

      this.tagsList = this.form.get('tags') as FormArray;

    this.certificate.tags.forEach(t => {
      this.tagsList.push(this.createTagWithName(t.name));
    });

    });
  }
  
  createTagWithName(name : string): FormGroup {
    return this.fb.group({
      name: [name, Validators.compose([Validators.required])]
    });
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
    this.certificate = this.form.value;
    this.certificate.id = this.id;
    this.giftCertificateService.updateCertificate(this.certificate)
    .subscribe((data : GiftCertificate) => this.router.navigate([`certificate/${data.id}`]))
  }
}
