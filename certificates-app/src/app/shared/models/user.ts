export class User {


  constructor(
    id: number, 
    username: string, 
    email: string, 
    role: string
) {
    this.id = id
    this.username = username
    this.email = email
    this.role = role
  }


    id : number;
    username : string;
    email : string;
    role : string;
}