export interface IPersonDocument {
  id?: number;
  documentNumber?: string;
  expirationDate?: string;
  isValid?: boolean;
  documentTypeCode?: string;
  active?: boolean;
  customerId?: number;
}

export class PersonDocument implements IPersonDocument {
  constructor(
    public id?: number,
    public documentNumber?: string,
    public expirationDate?: string,
    public isValid?: boolean,
    public documentTypeCode?: string,
    public active?: boolean,
    public customerId?: number
  ) {
    this.isValid = this.isValid || false;
    this.active = this.active || false;
  }
}
