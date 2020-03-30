export interface IDetailTransaction {
  id?: number;
  pgDataCode?: string;
  dataValue?: string;
  active?: boolean;
  transactionId?: number;
}

export class DetailTransaction implements IDetailTransaction {
  constructor(
    public id?: number,
    public pgDataCode?: string,
    public dataValue?: string,
    public active?: boolean,
    public transactionId?: number
  ) {
    this.active = this.active || false;
  }
}
