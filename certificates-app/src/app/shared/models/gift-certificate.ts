import { Tag } from "./tag";

export class GiftCertificate {


  constructor(
    id: number, 
    name: string, 
    description: string, 
    price: number, 
    duration: number, 
    createDate: Date, 
    lastUpdateDate: Date, 
    tags: Array<Tag>
) {
    this.id = id
    this.name = name
    this.description = description
    this.price = price
    this.duration = duration
    this.createDate = createDate
    this.lastUpdateDate = lastUpdateDate
    this.tags = tags
  }

    id : number;
    name : string;
    description : string;
    price : number;
    duration : number;
    createDate : Date;
    lastUpdateDate : Date;
    tags : Array<Tag>;
}