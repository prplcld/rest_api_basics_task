export class Order {

  constructor(
    id: number, 
    userId: number, 
    certificateIds: Array<number>, 
    cost: number, 
    purchaseDate: Date
) {
    this.id = id
    this.userId = userId
    this.certificateIds = certificateIds
    this.cost = cost
    this.purchaseDate = purchaseDate
  }


    id : number;
    userId : number;
    certificateIds : Array<number>;
    cost : number;
    purchaseDate : Date;
}