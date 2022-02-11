import { Injectable } from "@angular/core";
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { map, Observable, throwError } from "rxjs";
import { GiftCertificate } from "../models/gift-certificate";
import { environment } from "src/environments/environment";
import { TagService } from "./tag.service";

@Injectable({providedIn : 'root'}) export class GiftCertificateService {
    private apiServerUrl = environment.apiBaseUrl;
    private url : string = "";
    constructor(private http: HttpClient) {}

    public getCertificates(page : number, amount : number, paramsMap : Map<string, any>) : Observable<GiftCertificate[]> {
        this.url = `${this.apiServerUrl}/certificates?page=${page}&amount=${amount}`;
        for (const [key, value] of paramsMap) {
            if(key == "tags") {
                for(var i = 0; i < value.length; i++) {
                    this.url += "&"
                    this.url += key
                    this.url += "="
                    this.url += value[i]["name"]
                }
            
            }
            else{
                this.url += "&"
                this.url += key
                this.url += "="
                this.url += value
            }
            console.log(this.url)
        }
        return this.http.get<GiftCertificate[]>(this.url).pipe(map((data:any) => {
            let certificatesList = data["giftCertificates"];
            return certificatesList.map(function(c: any): GiftCertificate {
                return new GiftCertificate(c.id, c.name, c.description, c.price, c.duration, c.createDate, c.lastUpdateDate, c.tags);
            });
          }));
    }

    public getCertificate(id : number) : Observable<GiftCertificate> {
        return this.http.get<GiftCertificate>(`${this.apiServerUrl}/certificates/${id}`).pipe(map((data:any) => {
            let c = data["giftCertificate"];
            return new GiftCertificate(c.id, c.name, c.description, c.price, c.duration, c.createDate, c.lastUpdateDate, c.tags);
          }));
    }

    public addCertificate(certificate : GiftCertificate) : Observable<GiftCertificate> {
        return this.http.post<GiftCertificate>(`${this.apiServerUrl}/certificates`, certificate).pipe(map((data:any) => {
            let c = data["giftCertificate"];
            return new GiftCertificate(c.id, c.name, c.description, c.price, c.duration, c.createDate, c.lastUpdateDate, c.tags);
          }));;
    }   


    public deleteCertificate(id : number) {
        console.log(id)
        this.http.delete(`${this.apiServerUrl}/certificates/${id}`).subscribe();
    }

    public updateCertificate(certificate : GiftCertificate) : Observable<GiftCertificate> {
        return this.http.put<GiftCertificate>(`${this.apiServerUrl}/certificates/${certificate.id}`, certificate).pipe(map((data:any) => {
            let c = data["giftCertificate"];
            return new GiftCertificate(c.id, c.name, c.description, c.price, c.duration, c.createDate, c.lastUpdateDate, c.tags);
          }));;
    }
}
