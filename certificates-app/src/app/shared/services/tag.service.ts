import { Injectable } from "@angular/core";
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { map, Observable, throwError } from "rxjs";
import { Tag } from "../models/tag";
import { environment } from "src/environments/environment";

@Injectable({providedIn : 'root'}) export class TagService {
    private apiServerUrl = environment.apiBaseUrl;

    constructor(private http: HttpClient) {}

    public getTags(page : number, amount : number) : Observable<Tag[]> {
        return this.http.get<Tag[]>(`${this.apiServerUrl}/tags?page=${page}&amount=${amount}`).pipe(map((data:any) => {
            let tagsList = data["tags"];
                
            return tagsList.map(function(c: any): Tag {
                return new Tag(c.id, c.name);
            });
        }));
    }

    public getTag(id : number) : Observable<Tag> {
        return this.http.get<Tag>(`${this.apiServerUrl}/tags/${id}`);
    }

    public deleteTag(id : number) {
        return this.http.delete(`${this.apiServerUrl}/tags/${id}`);
    }

    public addTag(tag : Tag) : Observable<Tag> {
        return this.http.post<Tag>(`${this.apiServerUrl}/tags`, tag);
    }

    public getmostUsedTag() : Observable<Tag> {
        return this.http.get<Tag>(`${this.apiServerUrl}/tags/most_used_tag`); 
    }
}