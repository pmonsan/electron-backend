export interface ICustomerType {
  id?: number;
  code?: string;
  label?: string;
  active?: boolean;
}

export class CustomerType implements ICustomerType {
  constructor(public id?: number, public code?: string, public label?: string, public active?: boolean) {
    this.active = this.active || false;
  }
}
