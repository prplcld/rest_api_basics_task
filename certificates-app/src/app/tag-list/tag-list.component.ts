import { Component, OnInit } from '@angular/core';
import { PageEvent } from '@angular/material/paginator';
import { Tag } from '../shared/models/tag';
import { AuthService } from '../shared/services/auth.service';
import { TagService } from '../shared/services/tag.service';

@Component({
  selector: 'app-tag-list',
  templateUrl: './tag-list.component.html',
  styleUrls: ['./tag-list.component.css']
})
export class TagListComponent implements OnInit {

  public tags !: Tag[]

  constructor(private tagService : TagService, private authService : AuthService) { }

  ngOnInit(): void {
    this.tagService.getTags(1, 8).subscribe((data : Tag[]) => this.tags = data);
  }

  public OnPageChange(event : PageEvent) {
    this.tagService.getTags(event.pageIndex + 1, event.pageSize).subscribe((data : Tag[]) => this.tags = data);
  }
}
