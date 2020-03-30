export interface ICustomerLimit {
  id?: number;
  limitTypeCode?: string;
  accountTypeCode?: string;
  customerTypeCode?: string;
  value?: number;
  comment?: string;
  active?: boolean;
  customerId?: number;
}

export class CustomerLimit implements ICustomerLimit {
  constructor(
    public id?: number,
    public limitTypeCode?: string,
    public accountTypeCode?: string,
    public customerTypeCode?: string,
    public value?: number,
    public comment?: string,
    public active?: boolean,
    public customerId?: number
  ) {
    this.active = this.active || false;
  }
}
